package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentVersionDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentVersion;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface DocumentVersionMapper {

    @Mapping(target = "documentId",     source = "document.documentId")
    @Mapping(target = "documentTitle",  source = "document.title")
    @Mapping(target = "uploadedById",   source = "uploadedBy.userId")
    @Mapping(target = "uploadedByName", source = "uploadedBy.fullName")
    DocumentVersionDto toDTO(DocumentVersion entity);

    @Mapping(target = "documentId",     source = "document.documentId")
    @Mapping(target = "documentTitle",  source = "document.title")
    @Mapping(target = "uploadedById",   source = "uploadedBy.userId")
    @Mapping(target = "uploadedByName", source = "uploadedBy.fullName")
    List<DocumentVersionDto> toDTOList(List<DocumentVersion> entities);

    /**
     * DTO → Entidad (uso interno del Service para guardar nueva versión).
     * versionId lo genera la BD.
     * document y uploadedBy los asigna el Service desde el contexto.
     * versionNumber, checksum, isActive los calcula el Service.
     */
    @Mapping(target = "versionId",    ignore = true)
    @Mapping(target = "document",     source = "documentId",   qualifiedByName = "documentFromId")
    @Mapping(target = "uploadedBy",   source = "uploadedById", qualifiedByName = "userFromId")
    @Mapping(target = "versionNumber",ignore = true)
    @Mapping(target = "checksum",     ignore = true)
    @Mapping(target = "isActive",     ignore = true)
    DocumentVersion toEntity(DocumentVersionDto dto);

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
