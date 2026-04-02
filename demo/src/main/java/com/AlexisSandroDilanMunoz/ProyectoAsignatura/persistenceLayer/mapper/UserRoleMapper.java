package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserRoleDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Role;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserRole;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserRoleMapper {

    @Mapping(target = "userId",   source = "userAccount.userId")
    @Mapping(target = "username", source = "userAccount.username")
    @Mapping(target = "roleId",   source = "role.roleId")
    @Mapping(target = "roleName", source = "role.name")
    UserRoleDto toDTO(UserRole entity);

    @Mapping(target = "userId",   source = "userAccount.userId")
    @Mapping(target = "username", source = "userAccount.username")
    @Mapping(target = "roleId",   source = "role.roleId")
    @Mapping(target = "roleName", source = "role.name")
    List<UserRoleDto> toDTOList(List<UserRole> entities);

    /**
     * DTO → Entidad (asignar rol a usuario — RF15).
     * userRoleId lo genera la BD.
     * userAccount y role los construye el Service con los IDs del DTO,
     * verificando que ambos pertenezcan a la organización.
     */
    @Mapping(target = "userRoleId",  ignore = true)
    @Mapping(target = "userAccount", source = "userId",   qualifiedByName = "userAccountFromId")
    @Mapping(target = "role",        source = "roleId",   qualifiedByName = "roleFromId")
    UserRole toEntity(UserRoleDto dto);

    /** Referencia a UserAccount usando solo el ID */
    @Named("userAccountFromId")
    default UserAccount userAccountFromId(Long userId) {
        if (userId == null) return null;
        UserAccount ua = new UserAccount();
        ua.setUserId(userId);
        return ua;
    }

    /** Referencia a Role usando solo el ID */
    @Named("roleFromId")
    default Role roleFromId(Long roleId) {
        if (roleId == null) return null;
        Role role = new Role();
        role.setRoleId(roleId);
        return role;
    }
}
