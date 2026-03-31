package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentTypeDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentType;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para DocumentType ↔ DocumentTypeDto
 *
 * MAPEOS CON DOT-NOTATION:
 * - organizationId   ← entity.organization.organizationId
 * - organizationName ← entity.organization.name
 *
 * MAPEOS AUTOMÁTICOS:
 * - documentTypeId, name, metadataSchema, active
 *
 * CAMPOS IGNORADOS al crear entidad:
 * - documentTypeId: lo genera la BD
 * - organization: el Service la asigna desde el JWT
 * - documents, workflows: colecciones @OneToMany
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface DocumentTypeMapper {

    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    DocumentTypeDto toDTO(DocumentType entity);

    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    List<DocumentTypeDto> toDTOList(List<DocumentType> entities);

    /**
     * DTO → Entidad (crear tipo documental — RF24).
     */
    @Mapping(target = "documentTypeId", ignore = true)
    @Mapping(target = "organization",   ignore = true)
    @Mapping(target = "documents",      ignore = true)
    @Mapping(target = "workflows",      ignore = true)
    DocumentType toEntity(DocumentTypeDto dto);

    /**
     * Actualización parcial (nombre, metadataSchema, active — RF25).
     */
    @Mapping(target = "documentTypeId", ignore = true)
    @Mapping(target = "organization",   ignore = true)
    @Mapping(target = "documents",      ignore = true)
    @Mapping(target = "workflows",      ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(DocumentTypeDto dto, @MappingTarget DocumentType entity);

    /**
     * MÉTODO AUXILIAR: Crea DocumentType con solo el ID.
     * Útil en DocumentMapper cuando solo tenemos documentTypeId del CreateDto.
     */
    @Named("documentTypeFromId")
    default DocumentType documentTypeFromId(Long documentTypeId) {
        if (documentTypeId == null) return null;
        DocumentType dt = new DocumentType();
        dt.setDocumentTypeId(documentTypeId);
        return dt;
    }
}
