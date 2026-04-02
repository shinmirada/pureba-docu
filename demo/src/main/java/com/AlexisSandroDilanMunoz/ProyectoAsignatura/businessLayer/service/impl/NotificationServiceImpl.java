package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.NotificationTemplateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.NotificationService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.NotificationTemplateService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.*;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationTemplateService templateService;
    private final NotificationRepository notificationRepository;
    private final DocumentRepository documentRepository;
    private final UserAccountRepository userAccountRepository;
    private final TaskRepository taskRepository;
    private final JavaMailSender mailSender;
    private final NotificationTemplateRepository templateRepository; // para registrar notificación

    @Override
    public void sendDocumentCreatedNotification(Long documentId, Long creatorId) {
        Document doc = documentRepository.findById(documentId).orElse(null);
        UserAccount creator = userAccountRepository.findById(creatorId).orElse(null);
        if (doc == null || creator == null)
            return;

        NotificationTemplateDto template = getActiveTemplate(doc.getOrganization().getOrganizationId(),
                "DOCUMENT_CREADO");
        if (template == null)
            return;

        // Destinatarios: creador + administradores
        List<UserAccount> admins = userAccountRepository
                .findByOrganizationOrganizationIdAndActive(doc.getOrganization().getOrganizationId(), true)
                .stream().filter(u -> u.getUserRoles().stream().anyMatch(ur -> "ADMIN".equals(ur.getRole().getName())))
                .toList();

        Map<String, Object> variables = new HashMap<>();
        variables.put("titulo", doc.getTitle());
        variables.put("descripcion", doc.getDescription());
        variables.put("creador", creator.getFullName());
        variables.put("fecha", LocalDateTime.now().toString());

        sendEmail(template, variables, creator.getEmail(), doc.getDocumentId(), creator.getUserId());
        for (UserAccount admin : admins) {
            sendEmail(template, variables, admin.getEmail(), doc.getDocumentId(), admin.getUserId());
        }
    }

    @Override
    public void sendDocumentStateChangedNotification(Long documentId, String newState, String comment) {
        Document doc = documentRepository.findById(documentId).orElse(null);
        if (doc == null)
            return;

        NotificationTemplateDto template = getActiveTemplate(doc.getOrganization().getOrganizationId(),
                "DOCUMENTO_ESTADO_CAMBIADO");
        if (template == null)
            return;

        Map<String, Object> variables = new HashMap<>();
        variables.put("titulo", doc.getTitle());
        variables.put("nuevoEstado", newState);
        variables.put("comentario", comment);
        variables.put("fecha", LocalDateTime.now().toString());

        sendEmail(template, variables, doc.getCreatedBy().getEmail(), doc.getDocumentId(),
                doc.getCreatedBy().getUserId());
    }

    @Override
    public void sendTaskAssignedNotification(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        UserAccount assignedUser = userAccountRepository.findById(userId).orElse(null);
        if (task == null || assignedUser == null)
            return;

        Document doc = task.getDocument();
        NotificationTemplateDto template = getActiveTemplate(doc.getOrganization().getOrganizationId(),
                "TAREA_ASIGNADA");
        if (template == null)
            return;

        Map<String, Object> variables = new HashMap<>();
        variables.put("titulo", doc.getTitle());
        variables.put("paso", task.getStep().getName());
        variables.put("fechaLimite", task.getDueDate() != null ? task.getDueDate().toString() : "No definida");

        sendEmail(template, variables, assignedUser.getEmail(), doc.getDocumentId(), assignedUser.getUserId());
    }

    // ========== MÉTODOS PRIVADOS ==========

    private NotificationTemplateDto getActiveTemplate(Long organizationId, String templateName) {
        return templateService.getActiveTemplatesByOrganization(organizationId)
                .stream().filter(t -> templateName.equals(t.getName())).findFirst().orElse(null);
    }

    private void sendEmail(NotificationTemplateDto template, Map<String, Object> variables, String toEmail,
            Long documentId, Long userId) {
        String subject = replaceVariables(template.getSubject(), variables);
        String bodyHtml = replaceVariables(template.getBodyHtml(), variables);
        String bodyText = replaceVariables(template.getBodyText(), variables);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(bodyText, bodyHtml);

            mailSender.send(message);
            registerNotification(template, userId, documentId, "ENVIADO", "EMAIL", variables);
            log.info("Correo enviado a {} usando plantilla {}", toEmail, template.getName());
        } catch (MessagingException e) {
            log.error("Error al enviar correo a {}: {}", toEmail, e.getMessage());
            registerNotification(template, userId, documentId, "FALLIDO", "EMAIL", variables);
        }
    }

    private String replaceVariables(String content, Map<String, Object> variables) {
        if (content == null)
            return "";
        String result = content;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}",
                    entry.getValue() != null ? entry.getValue().toString() : "");
        }
        return result;
    }

    private void registerNotification(NotificationTemplateDto template, Long userId, Long documentId, String status,
            String channel, Map<String, Object> variables) {
        try {
            Notification notification = new Notification();
            notification.setTemplate(templateRepository.findById(template.getTemplateId()).orElse(null));
            notification.setUser(userAccountRepository.findById(userId).orElse(null));
            notification.setDocument(documentRepository.findById(documentId).orElse(null));
            notification.setSentAt(LocalDateTime.now());
            notification.setStatus(status);
            notification.setChannel(channel);
            notification.setPayloadJson(new ObjectMapper().writeValueAsString(variables));
            notificationRepository.save(notification);
        } catch (JsonProcessingException e) {
            log.error("Error serializando payload", e);
        }
    }
}