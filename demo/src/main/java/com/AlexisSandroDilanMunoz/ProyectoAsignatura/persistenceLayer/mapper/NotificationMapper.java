package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.NotificationDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Notification;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.NotificationTemplate;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para Notification ↔ NotificationDto
 *
 * MAPEOS CON DOT-NOTATION:
 * - templateId    ← entity.template.templateId
 * - templateName  ← entity.template.name
 * - userId        ← entity.user.userId
 * - userName      ← entity.user.fullName
 * - userEmail     ← entity.user.email
 * - documentId    ← entity.document.documentId
 * - documentTitle ← entity.document.title
 *
 * MAPEOS AUTOMÁTICOS:
 * - notificationId, sentAt, status, channel, payloadJson
 *
 * NOTA: Las notificaciones las crea el sistema automáticamente (RF37, RF38, RF39).
 * Son de solo lectura para el cliente — el Service las registra
 * junto con el envío del correo.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface NotificationMapper {

    @Mapping(target = "templateId",    source = "template.templateId")
    @Mapping(target = "templateName",  source = "template.name")
    @Mapping(target = "userId",        source = "user.userId")
    @Mapping(target = "userName",      source = "user.fullName")
    @Mapping(target = "userEmail",     source = "user.email")
    @Mapping(target = "documentId",    source = "document.documentId")
    @Mapping(target = "documentTitle", source = "document.title")
    NotificationDto toDTO(Notification entity);

    @Mapping(target = "templateId",    source = "template.templateId")
    @Mapping(target = "templateName",  source = "template.name")
    @Mapping(target = "userId",        source = "user.userId")
    @Mapping(target = "userName",      source = "user.fullName")
    @Mapping(target = "userEmail",     source = "user.email")
    @Mapping(target = "documentId",    source = "document.documentId")
    @Mapping(target = "documentTitle", source = "document.title")
    List<NotificationDto> toDTOList(List<Notification> entities);

    /**
     * DTO → Entidad (uso interno del Service para registrar el envío — RF37).
     * notificationId lo genera la BD.
     * template, user y document los asigna el Service.
     */
    @Mapping(target = "notificationId", ignore = true)
    @Mapping(target = "template",  source = "templateId",   qualifiedByName = "templateFromId")
    @Mapping(target = "user",      source = "userId",       qualifiedByName = "userFromId")
    @Mapping(target = "document",  source = "documentId",   qualifiedByName = "documentFromId")
    Notification toEntity(NotificationDto dto);

    /** Referencia a NotificationTemplate usando solo el ID */
    @Named("templateFromId")
    default NotificationTemplate templateFromId(Long templateId) {
        if (templateId == null) return null;
        NotificationTemplate t = new NotificationTemplate();
        t.setTemplateId(templateId);
        return t;
    }

    /** Referencia a UserAccount usando solo el ID */
    @Named("userFromId")
    default UserAccount userFromId(Long userId) {
        if (userId == null) return null;
        UserAccount ua = new UserAccount();
        ua.setUserId(userId);
        return ua;
    }

    /** Referencia a Document usando solo el ID */
    @Named("documentFromId")
    default Document documentFromId(Long documentId) {
        if (documentId == null) return null;
        Document doc = new Document();
        doc.setDocumentId(documentId);
        return doc;
    }
}
