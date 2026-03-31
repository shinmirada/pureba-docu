package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.AuditLogDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.AuditLog;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para AuditLog ↔ AuditLogDto
 *
 * MAPEOS CON DOT-NOTATION:
 * - documentId    ← entity.document.documentId
 * - documentTitle ← entity.document.title
 * - userId        ← entity.user.userId
 * - userName      ← entity.user.username
 *
 * MAPEOS AUTOMÁTICOS:
 * - auditId, actionType, actionDetail, timestamp, ipAddress, metadata
 *
 * NOTA: AuditLog es SOLO LECTURA desde la perspectiva del API.
 * Se crea automáticamente por el sistema (RF33), nunca por el cliente.
 * Por eso solo necesitamos toDTO y toEntity (este último para uso interno del Service).
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AuditLogMapper {

    @Mapping(target = "documentId",    source = "document.documentId")
    @Mapping(target = "documentTitle", source = "document.title")
    @Mapping(target = "userId",        source = "user.userId")
    @Mapping(target = "userName",      source = "user.username")
    AuditLogDto toDTO(AuditLog entity);

    @Mapping(target = "documentId",    source = "document.documentId")
    @Mapping(target = "documentTitle", source = "document.title")
    @Mapping(target = "userId",        source = "user.userId")
    @Mapping(target = "userName",      source = "user.username")
    List<AuditLogDto> toDTOList(List<AuditLog> entities);

    /**
     * Crea AuditLog para que el Service lo registre automáticamente.
     * auditId lo genera la BD.
     * document y user los asigna el Service desde el contexto de la operación.
     */
    @Mapping(target = "auditId",  ignore = true)
    @Mapping(target = "document", source = "documentId", qualifiedByName = "documentFromId")
    @Mapping(target = "user",     source = "userId",     qualifiedByName = "userFromId")
    AuditLog toEntity(AuditLogDto dto);

    /** Referencia a Document usando solo el ID */
    @Named("documentFromId")
    default Document documentFromId(Long documentId) {
        if (documentId == null) return null;
        Document doc = new Document();
        doc.setDocumentId(documentId);
        return doc;
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
