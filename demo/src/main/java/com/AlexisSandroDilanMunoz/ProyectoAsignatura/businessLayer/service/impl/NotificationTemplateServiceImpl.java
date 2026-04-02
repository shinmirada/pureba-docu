package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.NotificationTemplateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.SecurityContextHelper;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.NotificationTemplateService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.NotificationTemplate;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.NotificationTemplateRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final NotificationTemplateRepository templateRepository;
    private final OrganizationRepository organizationRepository;
    private final SecurityContextHelper securityHelper;

    @Override
    public NotificationTemplateDto createTemplate(NotificationTemplateDto dto, Long organizationId) {
        log.info("Creando plantilla '{}' para organización {}", dto.getName(), organizationId);

        if (templateRepository.existsByOrganizationOrganizationIdAndName(organizationId, dto.getName())) {
            throw new IllegalArgumentException("Ya existe una plantilla con ese nombre en esta organización");
        }

        Organization org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));

        NotificationTemplate template = new NotificationTemplate();
        template.setName(dto.getName());
        template.setSubject(dto.getSubject());
        template.setBodyHtml(dto.getBodyHtml());
        template.setBodyText(dto.getBodyText());
        template.setVariablesJson(dto.getVariablesJson());
        template.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        template.setOrganization(org);

        NotificationTemplate saved = templateRepository.save(template);
        return toDto(saved);
    }

    @Override
    public NotificationTemplateDto updateTemplate(Long templateId, NotificationTemplateDto dto) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        NotificationTemplate template = templateRepository
                .findByTemplateIdAndOrganizationOrganizationId(templateId, currentOrg)
                .orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));

        if (dto.getName() != null && !dto.getName().equals(template.getName()) &&
                templateRepository.existsByOrganizationOrganizationIdAndName(currentOrg, dto.getName())) {
            throw new IllegalArgumentException("Nombre ya en uso");
        }

        if (dto.getName() != null)
            template.setName(dto.getName());
        if (dto.getSubject() != null)
            template.setSubject(dto.getSubject());
        if (dto.getBodyHtml() != null)
            template.setBodyHtml(dto.getBodyHtml());
        if (dto.getBodyText() != null)
            template.setBodyText(dto.getBodyText());
        if (dto.getVariablesJson() != null)
            template.setVariablesJson(dto.getVariablesJson());
        if (dto.getIsActive() != null)
            template.setIsActive(dto.getIsActive());

        return toDto(templateRepository.save(template));
    }

    @Override
    public void deleteTemplate(Long templateId) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        NotificationTemplate template = templateRepository
                .findByTemplateIdAndOrganizationOrganizationId(templateId, currentOrg)
                .orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));

        // Opcional: verificar si tiene notificaciones asociadas (RF43 recomienda
        // desactivar en lugar de eliminar)
        // Por ahora permitimos eliminar si no tiene registros
        templateRepository.delete(template);
    }

    @Override
    public void activateTemplate(Long templateId) {
        NotificationTemplate template = findByIdAndOrganization(templateId);
        template.setIsActive(true);
        templateRepository.save(template);
    }

    @Override
    public void deactivateTemplate(Long templateId) {
        NotificationTemplate template = findByIdAndOrganization(templateId);
        template.setIsActive(false);
        templateRepository.save(template);
    }

    @Override
    public NotificationTemplateDto getTemplateById(Long templateId) {
        return toDto(findByIdAndOrganization(templateId));
    }

    @Override
    public List<NotificationTemplateDto> getTemplatesByOrganization(Long organizationId) {
        return templateRepository.findByOrganizationOrganizationId(organizationId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<NotificationTemplateDto> getActiveTemplatesByOrganization(Long organizationId) {
        return templateRepository.findByOrganizationOrganizationIdAndIsActiveTrue(organizationId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private NotificationTemplate findByIdAndOrganization(Long templateId) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        return templateRepository
                .findByTemplateIdAndOrganizationOrganizationId(templateId, currentOrg)
                .orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));
    }

    private NotificationTemplateDto toDto(NotificationTemplate entity) {
        NotificationTemplateDto dto = new NotificationTemplateDto();
        dto.setTemplateId(entity.getTemplateId());
        dto.setOrganizationId(entity.getOrganization().getOrganizationId());
        dto.setOrganizationName(entity.getOrganization().getName());
        dto.setName(entity.getName());
        dto.setSubject(entity.getSubject());
        dto.setBodyHtml(entity.getBodyHtml());
        dto.setBodyText(entity.getBodyText());
        dto.setVariablesJson(entity.getVariablesJson());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }
}