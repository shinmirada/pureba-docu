package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentUpdateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentType;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para Document ↔ DTOs — EL MÁS COMPLEJO DEL PROYECTO
 *
 * ¿Por qué uses = {...}?
 * DocumentDto tiene cuatro listas de DTOs anidados:
 *   List<DocumentVersionDto> documentVersions
 *   List<TaskDto>            tasks
 *   List<AuditLogDto>        auditLogs
 *   List<NotificationDto>    notifications
 *
 * La entidad Document tiene las entidades correspondientes.
 * Con "uses", MapStruct busca automáticamente en esos mappers
 * el método que convierte List<X> → List<XDto> y lo aplica.
 * Sin "uses", MapStruct no sabría cómo hacer esas conversiones.
 *
 * MAPEOS CON DOT-NOTATION:
 * - organizationId   ← entity.organization.organizationId
 * - organizationName ← entity.organization.name
 * - documentTypeId   ← entity.documentType.documentTypeId
 * - documentTypeName ← entity.documentType.name
 * - createdById      ← entity.createdBy.userId
 * - createdByName    ← entity.createdBy.fullName
 *
 * MAPEOS AUTOMÁTICOS (mismo nombre):
 * - documentId, title, description, currentState,
 *   createdAt, updatedAt, referenceCode
 * - documentVersions, tasks, auditLogs, notifications
 *   (automáticos gracias a "uses" + mismo nombre de campo)
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {
            DocumentVersionMapper.class,
            TaskMapper.class,
            AuditLogMapper.class,
            NotificationMapper.class
        }
)
public interface DocumentMapper {

    /**
     * Entidad → DTO de respuesta completo.
     * Las cuatro listas se convierten automáticamente gracias a "uses".
     */
    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "documentTypeId",   source = "documentType.documentTypeId")
    @Mapping(target = "documentTypeName", source = "documentType.name")
    @Mapping(target = "createdById",      source = "createdBy.userId")
    @Mapping(target = "createdByName",    source = "createdBy.fullName")
    DocumentDto toDTO(Document entity);

    /** Lista de entidades → lista de DTOs */
    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "documentTypeId",   source = "documentType.documentTypeId")
    @Mapping(target = "documentTypeName", source = "documentType.name")
    @Mapping(target = "createdById",      source = "createdBy.userId")
    @Mapping(target = "createdByName",    source = "createdBy.fullName")
    List<DocumentDto> toDTOList(List<Document> entities);

    /**
     * DocumentCreateDto → Entidad (RF17 — crear documento).
     *
     * El cliente solo envía: title, description, documentTypeId.
     * El Service completa TODO lo demás:
     * - organization: del token JWT del usuario autenticado
     * - createdBy:    del token JWT
     * - currentState: el Service lo inicializa en "CREADO"
     * - referenceCode: el Service lo genera (ej: DOC-2024-001)
     * - createdAt, updatedAt: el Service los asigna con LocalDateTime.now()
     * - Las 4 listas: vacías al crear, JPA las gestiona
     */
    @Mapping(target = "documentId",       ignore = true)
    @Mapping(target = "organization",     ignore = true)
    @Mapping(target = "createdBy",        ignore = true)
    @Mapping(target = "currentState",     ignore = true)
    @Mapping(target = "createdAt",        ignore = true)
    @Mapping(target = "updatedAt",        ignore = true)
    @Mapping(target = "referenceCode",    ignore = true)
    @Mapping(target = "documentVersions", ignore = true)
    @Mapping(target = "tasks",            ignore = true)
    @Mapping(target = "auditLogs",        ignore = true)
    @Mapping(target = "notifications",    ignore = true)
    @Mapping(target = "documentType",     source = "documentTypeId", qualifiedByName = "documentTypeFromId")
    Document toEntity(DocumentCreateDto createDto);

    /**
     * Actualización parcial de metadatos (RF19 — editar título y descripción).
     *
     * CAMPOS NUNCA ACTUALIZABLES por este DTO:
     * - documentId:     identificador inmutable
     * - organization:   el documento no cambia de organización
     * - documentType:   cambiar tipo documental requeriría revalidar flujos
     * - createdBy:      el creador es inmutable
     * - currentState:   lo gestiona el flujo de aprobación (RF30), no esta edición
     * - referenceCode:  código inmutable una vez generado
     * - createdAt:      fecha de creación inmutable
     * - updatedAt:      el Service lo actualiza con LocalDateTime.now()
     * - Las 4 listas:   se gestionan con sus propios endpoints
     */
    @Mapping(target = "documentId",       ignore = true)
    @Mapping(target = "organization",     ignore = true)
    @Mapping(target = "documentType",     ignore = true)
    @Mapping(target = "createdBy",        ignore = true)
    @Mapping(target = "currentState",     ignore = true)
    @Mapping(target = "createdAt",        ignore = true)
    @Mapping(target = "updatedAt",        ignore = true)
    @Mapping(target = "referenceCode",    ignore = true)
    @Mapping(target = "documentVersions", ignore = true)
    @Mapping(target = "tasks",            ignore = true)
    @Mapping(target = "auditLogs",        ignore = true)
    @Mapping(target = "notifications",    ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(DocumentUpdateDto updateDto, @MappingTarget Document entity);

    /**
     * MÉTODO AUXILIAR: Crea DocumentType con solo el ID.
     * Mismo patrón que createSellerEntityFromId del ejemplo del profesor.
     * El Service valida que el tipo exista en la organización antes de crear.
     */
    @Named("documentTypeFromId")
    default DocumentType documentTypeFromId(Long documentTypeId) {
        if (documentTypeId == null) return null;
        DocumentType dt = new DocumentType();
        dt.setDocumentTypeId(documentTypeId);
        return dt;
    }

    /** Referencia a Organization usando solo el ID */
    @Named("organizationFromId")
    default Organization organizationFromId(Long organizationId) {
        if (organizationId == null) return null;
        Organization org = new Organization();
        org.setOrganizationId(organizationId);
        return org;
    }

    /** Referencia a UserAccount usando solo el ID */
    @Named("userFromId")
    default UserAccount userFromId(Long userId) {
        if (userId == null) return null;
        UserAccount ua = new UserAccount();
        ua.setUserId(userId);
        return ua;
    }
}
