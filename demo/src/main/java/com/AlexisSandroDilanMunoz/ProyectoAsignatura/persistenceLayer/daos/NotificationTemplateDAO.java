package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.NotificationTemplate;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.NotificationTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NotificationTemplateDAO {

    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;

    // RF40 - Crear nueva plantilla de correo
    public NotificationTemplate save(NotificationTemplate template) {
        return notificationTemplateRepository.save(template);
    }

    // RF43 - Actualizar plantilla existente
    public NotificationTemplate update(NotificationTemplate template) {
        return notificationTemplateRepository.save(template);
    }

    // RF43 - Eliminar plantilla
    public void delete(NotificationTemplate template) {
        notificationTemplateRepository.delete(template);
    }

    // RF43 - Listar todas las plantillas de la organización
    public List<NotificationTemplate> findByOrganization(Long organizationId) {
        return notificationTemplateRepository.findByOrganizationOrganizationId(organizationId);
    }

    // RF43 - Listar solo las plantillas activas de la organización
    public List<NotificationTemplate> findActiveByOrganization(Long organizationId) {
        return notificationTemplateRepository.findByOrganizationOrganizationIdAndIsActiveTrue(organizationId);
    }

    // RF40 - Buscar plantilla activa por nombre para usarla al enviar notificaciones
    public Optional<NotificationTemplate> findActiveByNameAndOrganization(Long organizationId, String name) {
        return notificationTemplateRepository.findByOrganizationOrganizationIdAndNameAndIsActiveTrue(organizationId, name);
    }

    // RF43 - Buscar plantilla verificando que pertenece a la organización
    public Optional<NotificationTemplate> findByIdAndOrganization(Long templateId, Long organizationId) {
        return notificationTemplateRepository.findByTemplateIdAndOrganizationOrganizationId(templateId, organizationId);
    }

    // RF40 - Verificar unicidad de nombre en la organización
    public boolean existsByNameInOrganization(Long organizationId, String name) {
        return notificationTemplateRepository.existsByOrganizationOrganizationIdAndName(organizationId, name);
    }

    // RF40 - Buscar plantilla por nombre dentro de la organización
    public Optional<NotificationTemplate> findByNameAndOrganization(Long organizationId, String name) {
        return notificationTemplateRepository.findByOrganizationOrganizationIdAndName(organizationId, name);
    }

    // Buscar por ID
    public Optional<NotificationTemplate> findById(Long templateId) {
        return notificationTemplateRepository.findById(templateId);
    }
}
