package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para Organization ↔ DTOs
 *
 * MAPEOS AUTOMÁTICOS (mismo nombre en entidad y DTO):
 * - organizationId, name, domain, createdAt, status
 *
 * CAMPOS IGNORADOS al crear entidad:
 * - organizationId: lo genera la BD
 * - createdAt, status: los inicializa el Service
 * - Colecciones @OneToMany: no existen al crear, JPA las gestiona
 *
 * NOTA sobre OrganizationCreateDto:
 * - Los campos adminUsername, adminEmail, adminPassword, adminFullName
 *   NO se mapean aquí — el Service los usa para crear el primer UserAccount.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface OrganizationMapper {

    /** Entidad → DTO de respuesta */
    OrganizationDto toDTO(Organization entity);

    /** Lista de entidades → lista de DTOs */
    List<OrganizationDto> toDTOList(List<Organization> entities);

    /**
     * DTO de creación → Entidad
     * Solo se mapean: name y domain.
     * El resto lo completa el Service.
     */
    @Mapping(target = "organizationId",        ignore = true)
    @Mapping(target = "createdAt",             ignore = true)
    @Mapping(target = "status",                ignore = true)
    @Mapping(target = "userAccounts",          ignore = true)
    @Mapping(target = "roles",                 ignore = true)
    @Mapping(target = "documentTypes",         ignore = true)
    @Mapping(target = "documentStates",        ignore = true)
    @Mapping(target = "documents",             ignore = true)
    @Mapping(target = "workflows",             ignore = true)
    @Mapping(target = "notificationTemplates", ignore = true)
    Organization toEntity(OrganizationCreateDto createDto);

    /**
     * Actualización parcial de la organización (nombre y dominio).
     * NullValuePropertyMappingStrategy.IGNORE = si el campo del DTO es null, no sobreescribe.
     */
    @Mapping(target = "organizationId",        ignore = true)
    @Mapping(target = "createdAt",             ignore = true)
    @Mapping(target = "status",                ignore = true)
    @Mapping(target = "userAccounts",          ignore = true)
    @Mapping(target = "roles",                 ignore = true)
    @Mapping(target = "documentTypes",         ignore = true)
    @Mapping(target = "documentStates",        ignore = true)
    @Mapping(target = "documents",             ignore = true)
    @Mapping(target = "workflows",             ignore = true)
    @Mapping(target = "notificationTemplates", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(OrganizationDto dto, @MappingTarget Organization entity);
}
