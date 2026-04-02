package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.*;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.SecurityContextHelper;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.AuditLogService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.NotificationService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.WorkflowService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.*;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkflowServiceImpl implements WorkflowService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowStepRepository workflowStepRepository;
    private final TaskRepository taskRepository;
    private final DocumentRepository documentRepository;
    private final UserRoleRepository userRoleRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final NotificationService notificationService;
    private final AuditLogService auditLogService; // 🔽 NUEVO
    private final SecurityContextHelper securityHelper;

    @Override
    public WorkflowDto createWorkflow(WorkflowCreateDto createDto, Long organizationId) {
        log.info("Creando workflow '{}' para organización {}", createDto.getName(), organizationId);
        if (createDto.getSteps() == null || createDto.getSteps().isEmpty())
            throw new IllegalArgumentException("El flujo debe tener al menos un paso");
        if (workflowRepository.existsByOrganizationOrganizationIdAndName(organizationId, createDto.getName()))
            throw new IllegalArgumentException("Ya existe un flujo con ese nombre en esta organización");
        DocumentType docType = documentTypeRepository
                .findByDocumentTypeIdAndOrganizationOrganizationId(createDto.getDocumentTypeId(), organizationId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo documental no válido"));
        Workflow workflow = new Workflow();
        workflow.setName(createDto.getName());
        workflow.setDescription(createDto.getDescription());
        workflow.setIsActive(false);
        workflow.setOrganization(docType.getOrganization());
        workflow.setDocumentType(docType);
        workflow.setConfigJson(null);
        Workflow savedWorkflow = workflowRepository.save(workflow);
        for (WorkflowStepCreateDto stepDto : createDto.getSteps()) {
            WorkflowStep step = new WorkflowStep();
            step.setWorkflow(savedWorkflow);
            step.setName(stepDto.getName());
            step.setStepOrder(stepDto.getStepOrder());
            step.setStepType(stepDto.getStepType());
            step.setRequired(stepDto.getRequired() != null ? stepDto.getRequired() : true);
            step.setTimeoutDays(stepDto.getTimeoutDays());
            Role role = new Role();
            role.setRoleId(stepDto.getAssignedRoleId());
            step.setAssignedRole(role);
            workflowStepRepository.save(step);
        }
        return toWorkflowDto(savedWorkflow);
    }

    @Override
    public void activateWorkflow(Long workflowId, Long documentTypeId) {
        Long orgId = securityHelper.getCurrentOrganizationId();
        workflowRepository
                .findByOrganizationOrganizationIdAndDocumentTypeDocumentTypeIdAndIsActiveTrue(orgId, documentTypeId)
                .ifPresent(w -> {
                    w.setIsActive(false);
                    workflowRepository.save(w);
                });
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow no encontrado"));
        workflow.setIsActive(true);
        workflowRepository.save(workflow);
    }

    @Override
    public void deactivateWorkflow(Long workflowId) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow no encontrado"));
        workflow.setIsActive(false);
        workflowRepository.save(workflow);
    }

    @Override
    public void startWorkflowForDocument(Long documentId, Long documentTypeId) {
        Long orgId = securityHelper.getCurrentOrganizationId();
        Workflow workflow = workflowRepository
                .findByOrganizationOrganizationIdAndDocumentTypeDocumentTypeIdAndIsActiveTrue(orgId, documentTypeId)
                .orElseThrow(() -> new IllegalStateException("No hay flujo activo para este tipo de documento"));
        List<WorkflowStep> steps = workflowStepRepository
                .findByWorkflowWorkflowIdOrderByStepOrderAsc(workflow.getWorkflowId());
        if (steps.isEmpty())
            return;
        createTaskForStep(steps.get(0), documentId);
    }

    @Override
    public void completeTask(Long taskId, boolean approved, String comment) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Long currentUserId = securityHelper.getCurrentUserId();
        if (!task.getAssignedTo().getUserId().equals(currentUserId))
            throw new RuntimeException("No tiene permiso para completar esta tarea");
        task.setStatus("COMPLETED");
        task.setCompletedAt(LocalDateTime.now());
        task.setComments(comment);
        taskRepository.save(task);
        Document doc = task.getDocument();
        WorkflowStep currentStep = task.getStep();
        Workflow workflow = currentStep.getWorkflow();
        String oldState = doc.getCurrentState();
        if (approved) {
            List<WorkflowStep> steps = workflowStepRepository
                    .findByWorkflowWorkflowIdOrderByStepOrderAsc(workflow.getWorkflowId());
            int currentIndex = steps.indexOf(currentStep);
            if (currentIndex + 1 < steps.size()) {
                WorkflowStep nextStep = steps.get(currentIndex + 1);
                createTaskForStep(nextStep, doc.getDocumentId());
                doc.setCurrentState("EN_REVISION");
            } else {
                doc.setCurrentState("APROBADO");
                notificationService.sendDocumentStateChangedNotification(doc.getDocumentId(), "APROBADO", comment);
            }
        } else {
            doc.setCurrentState("RECHAZADO");
            notificationService.sendDocumentStateChangedNotification(doc.getDocumentId(), "RECHAZADO", comment);
        }
        documentRepository.save(doc);
        // Auditoría del cambio de estado
        auditLogService.logAction("ESTADO_CAMBIADO",
                String.format("Documento '%s' cambió de estado '%s' a '%s'. Tarea %d. Comentario: %s",
                        doc.getTitle(), oldState, doc.getCurrentState(), taskId, comment),
                doc.getDocumentId(), currentUserId, obtenerIpActual());
    }

    @Override
    public List<TaskDto> getPendingTasksForUser(Long userId) {
        return taskRepository.findByAssignedToUserIdAndStatus(userId, "PENDING")
                .stream().map(this::toTaskDto).collect(Collectors.toList());
    }

    private void createTaskForStep(WorkflowStep step, Long documentId) {
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        List<UserRole> userRoles = userRoleRepository.findByRoleRoleId(step.getAssignedRole().getRoleId());
        if (userRoles.isEmpty()) {
            log.warn("No hay usuarios con rol ID {} para la tarea '{}'", step.getAssignedRole().getRoleId(),
                    step.getName());
            return;
        }
        for (UserRole ur : userRoles) {
            Task task = new Task();
            task.setDocument(doc);
            task.setStep(step);
            task.setAssignedTo(ur.getUserAccount());
            task.setStatus("PENDING");
            task.setCreatedAt(LocalDateTime.now());
            if (step.getTimeoutDays() != null && step.getTimeoutDays() > 0)
                task.setDueDate(LocalDateTime.now().plusDays(step.getTimeoutDays()));
            taskRepository.save(task);
            notificationService.sendTaskAssignedNotification(task.getTaskId(), ur.getUserAccount().getUserId());
        }
    }

    private WorkflowDto toWorkflowDto(Workflow workflow) {
        WorkflowDto dto = new WorkflowDto();
        dto.setWorkflowId(workflow.getWorkflowId());
        dto.setOrganizationId(workflow.getOrganization().getOrganizationId());
        dto.setOrganizationName(workflow.getOrganization().getName());
        dto.setDocumentTypeId(workflow.getDocumentType().getDocumentTypeId());
        dto.setDocumentTypeName(workflow.getDocumentType().getName());
        dto.setName(workflow.getName());
        dto.setDescription(workflow.getDescription());
        dto.setIsActive(workflow.getIsActive());
        dto.setConfigJson(workflow.getConfigJson());
        List<WorkflowStepDto> stepDtos = workflowStepRepository
                .findByWorkflowWorkflowIdOrderByStepOrderAsc(workflow.getWorkflowId())
                .stream().map(this::toStepDto).collect(Collectors.toList());
        dto.setSteps(stepDtos);
        return dto;
    }

    private WorkflowStepDto toStepDto(WorkflowStep step) {
        WorkflowStepDto dto = new WorkflowStepDto();
        dto.setStepId(step.getStepId());
        dto.setWorkflowId(step.getWorkflow().getWorkflowId());
        dto.setWorkflowName(step.getWorkflow().getName());
        dto.setAssignedRoleId(step.getAssignedRole().getRoleId());
        dto.setAssignedRoleName(step.getAssignedRole().getName());
        dto.setStepOrder(step.getStepOrder());
        dto.setName(step.getName());
        dto.setStepType(step.getStepType());
        dto.setRequired(step.getRequired());
        dto.setTimeoutDays(step.getTimeoutDays());
        return dto;
    }

    private TaskDto toTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setTaskId(task.getTaskId());
        dto.setDocumentId(task.getDocument().getDocumentId());
        dto.setDocumentTitle(task.getDocument().getTitle());
        dto.setDocumentReferenceCode(task.getDocument().getReferenceCode());
        dto.setStepId(task.getStep().getStepId());
        dto.setStepName(task.getStep().getName());
        dto.setStepOrder(task.getStep().getStepOrder());
        dto.setAssignedToId(task.getAssignedTo().getUserId());
        dto.setAssignedToName(task.getAssignedTo().getFullName());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setDueDate(task.getDueDate());
        dto.setCompletedAt(task.getCompletedAt());
        dto.setComments(task.getComments());
        return dto;
    }

    private String obtenerIpActual() {
        return "127.0.0.1"; // Mock, reemplazar con IP real desde el controlador
    }
}