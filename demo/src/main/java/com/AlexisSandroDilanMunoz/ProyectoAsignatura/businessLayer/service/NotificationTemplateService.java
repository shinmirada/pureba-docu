package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.NotificationTemplateDto;
import java.util.List;

public interface NotificationTemplateService {
    NotificationTemplateDto createTemplate(NotificationTemplateDto dto, Long organizationId);

    NotificationTemplateDto updateTemplate(Long templateId, NotificationTemplateDto dto);

    void deleteTemplate(Long templateId);

    void activateTemplate(Long templateId);

    void deactivateTemplate(Long templateId);

    NotificationTemplateDto getTemplateById(Long templateId);

    List<NotificationTemplateDto> getTemplatesByOrganization(Long organizationId);

    List<NotificationTemplateDto> getActiveTemplatesByOrganization(Long organizationId);
}