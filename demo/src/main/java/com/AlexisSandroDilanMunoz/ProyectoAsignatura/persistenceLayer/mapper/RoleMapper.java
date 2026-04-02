package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.RoleDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Role;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface RoleMapper {

    /**
     * Entidad → DTO de respuesta.
     * MapStruct resuelve la dot-notation automáticamente.
     */
    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    RoleDto toDTO(Role entity);

    /** Lista de entidades → lista de DTOs */
    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    List<RoleDto> toDTOList(List<Role> entities);

    /**
     * DTO → Entidad (para crear un rol personalizado — RF05).
     *
     * NOTA: organization la asigna el Service porque necesita
     * la entidad completa desde el contexto JWT, no solo el ID.
     */
    @Mapping(target = "roleId",        ignore = true)
    @Mapping(target = "organization",  ignore = true)
    @Mapping(target = "userRoles",     ignore = true)
    @Mapping(target = "workflowSteps", ignore = true)
    Role toEntity(RoleDto dto);

    /**
     * MÉTODO AUXILIAR: Crea una Organization de referencia usando solo el ID.
     * Útil cuando el Service ya validó que el ID existe y solo necesita
     * la referencia para JPA.
     *
     * Mismo patrón que createSellerEntityFromId del ejemplo del profesor.
     */
    @Named("organizationFromId")
    default Organization organizationFromId(Long organizationId) {
        if (organizationId == null) return null;
        Organization org = new Organization();
        org.setOrganizationId(organizationId);
        return org;
    }
}
