package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.RoleDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountResponseDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountUpdateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserRole;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para UserAccount ↔ DTOs
 *
 * CASO ESPECIAL — Lista de roles:
 * La entidad tiene: List<UserRole> userRoles  (relación intermedia user_role)
 * El DTO tiene:     List<RoleDto>  roles       (información aplanada del rol)
 *
 * MapStruct NO puede resolver esto automáticamente porque:
 * 1. Los nombres de campos no coinciden (userRoles ≠ roles)
 * 2. El tipo intermedio UserRole necesita ser "desempaquetado"
 * Solución: método @Named auxiliar que hace la conversión manual.
 *
 * CASO ESPECIAL — Password:
 * - CreateDto tiene: password (texto plano)
 * - Entity tiene:    passwordHash (BCrypt)
 * - El mapper IGNORA passwordHash → el Service lo hashea y lo asigna.
 *
 * CASO ESPECIAL — Organization:
 * - El Service asigna la organization completa desde el JWT,
 *   no se mapea desde el DTO.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserAccountMapper {

    /**
     * Entidad → DTO de respuesta SEGURO (sin password).
     * Se usa en GET /users, GET /users/{id} y en el body de respuesta
     * de POST /users y PUT /users/{id}.
     */
    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "roles",            source = "userRoles", qualifiedByName = "userRolesToRoleDtos")
    UserAccountResponseDto toResponseDto(UserAccount entity);

    /** Lista de entidades → lista de DTOs de respuesta */
    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "roles",            source = "userRoles", qualifiedByName = "userRolesToRoleDtos")
    List<UserAccountResponseDto> toResponseDtoList(List<UserAccount> entities);

    /**
     * DTO de creación → Entidad (RF12 — crear usuario).
     *
     * IMPORTANTE:
     * - passwordHash se ignora aquí: el Service llama a
     *   BCryptPasswordEncoder.encode(createDto.getPassword()) y lo asigna.
     * - organization se ignora: el Service la extrae del token JWT.
     * - active, lastLogin, createdAt: el Service los inicializa.
     */
    @Mapping(target = "userId",           ignore = true)
    @Mapping(target = "organization",     ignore = true)
    @Mapping(target = "passwordHash",     ignore = true)  // Service hace el hash BCrypt
    @Mapping(target = "active",           ignore = true)  // Service lo inicializa en true
    @Mapping(target = "lastLogin",        ignore = true)
    @Mapping(target = "createdAt",        ignore = true)
    @Mapping(target = "userRoles",        ignore = true)
    @Mapping(target = "documents",        ignore = true)
    @Mapping(target = "documentVersions", ignore = true)
    @Mapping(target = "tasks",            ignore = true)
    @Mapping(target = "auditLogs",        ignore = true)
    @Mapping(target = "notifications",    ignore = true)
    UserAccount toEntity(UserAccountCreateDto createDto);

    /**
     * Actualización parcial de usuario (RF13 — editar email y nombre).
     *
     * CAMPOS NUNCA ACTUALIZABLES por este DTO:
     * - userId:        identificador inmutable
     * - username:      no se permite cambiar (es identificador de acceso)
     * - passwordHash:  tiene su propio endpoint dedicado
     * - organization:  el usuario no cambia de organización
     * - active:        tiene su propio endpoint (RF14)
     */
    @Mapping(target = "userId",           ignore = true)
    @Mapping(target = "organization",     ignore = true)
    @Mapping(target = "username",         ignore = true)
    @Mapping(target = "passwordHash",     ignore = true)
    @Mapping(target = "active",           ignore = true)
    @Mapping(target = "lastLogin",        ignore = true)
    @Mapping(target = "createdAt",        ignore = true)
    @Mapping(target = "userRoles",        ignore = true)
    @Mapping(target = "documents",        ignore = true)
    @Mapping(target = "documentVersions", ignore = true)
    @Mapping(target = "tasks",            ignore = true)
    @Mapping(target = "auditLogs",        ignore = true)
    @Mapping(target = "notifications",    ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UserAccountUpdateDto updateDto, @MappingTarget UserAccount entity);

    /**
     * MÉTODO AUXILIAR: Convierte List<UserRole> → List<RoleDto>.
     *
     * ¿Por qué manual?
     * UserRole es una tabla intermedia (user_id, role_id).
     * Para llegar al rol necesitamos: userRole.getRole() → Role → RoleDto.
     * MapStruct no puede inferir este "desempaquetado" automáticamente.
     *
     * @Named permite referenciar este método desde las anotaciones @Mapping.
     */
    @Named("userRolesToRoleDtos")
    default List<RoleDto> userRolesToRoleDtos(List<UserRole> userRoles) {
        if (userRoles == null) return Collections.emptyList();
        return userRoles.stream()
                .map(userRole -> {
                    var role = userRole.getRole();
                    RoleDto dto = new RoleDto();
                    dto.setRoleId(role.getRoleId());
                    dto.setName(role.getName());
                    dto.setDescription(role.getDescription());
                    if (role.getOrganization() != null) {
                        dto.setOrganizationId(role.getOrganization().getOrganizationId());
                        dto.setOrganizationName(role.getOrganization().getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
