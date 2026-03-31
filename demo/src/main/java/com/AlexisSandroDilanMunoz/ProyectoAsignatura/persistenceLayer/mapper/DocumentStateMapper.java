package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentStateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentState;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para DocumentState ↔ DocumentStateDto
 *
 * MAPEOS CON DOT-NOTATION:
 * - organizationId ← entity.organization.organizationId
 *
 * MAPEOS AUTOMÁTICOS:
 * - stateId, code, label, stateOrder
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface DocumentStateMapper {

    @Mapping(target = "organizationId", source = "organization.organizationId")
    DocumentStateDto toDTO(DocumentState entity);

    @Mapping(target = "organizationId", source = "organization.organizationId")
    List<DocumentStateDto> toDTOList(List<DocumentState> entities);

    /**
     * DTO → Entidad (crear/actualizar estado — RF41).
     * organization la asigna el Service desde el JWT.
     */
    @Mapping(target = "stateId",      ignore = true)
    @Mapping(target = "organization", ignore = true)
    DocumentState toEntity(DocumentStateDto dto);

    /**
     * Actualización parcial del estado (label y stateOrder).
     */
    @Mapping(target = "stateId",      ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "code",         ignore = true)   // El código no se puede cambiar
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(DocumentStateDto dto, @MappingTarget DocumentState entity);

    /** Referencia a Organization usando solo el ID */
    @Named("organizationFromId")
    default Organization organizationFromId(Long organizationId) {
        if (organizationId == null) return null;
        Organization org = new Organization();
        org.setOrganizationId(organizationId);
        return org;
    }
}
