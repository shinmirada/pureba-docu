package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserRole;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleDAO {

    @Autowired
    private UserRoleRepository userRoleRepository;

    // RF15 - Asignar un rol a un usuario
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    // RF15 - Revocar un rol: eliminar la asignación por userId y roleId
    public void deleteByUserAndRole(Long userId, Long roleId) {
        userRoleRepository.deleteByUserAccountUserIdAndRoleRoleId(userId, roleId);
    }

    // RF15 - Obtener todos los roles asignados a un usuario
    public List<UserRole> findByUser(Long userId) {
        return userRoleRepository.findByUserAccountUserId(userId);
    }

    // RF15 - Obtener todos los usuarios con un rol específico
    public List<UserRole> findByRole(Long roleId) {
        return userRoleRepository.findByRoleRoleId(roleId);
    }

    // RF15 - Verificar si el usuario ya tiene ese rol (evitar duplicados)
    public boolean existsByUserAndRole(Long userId, Long roleId) {
        return userRoleRepository.existsByUserAccountUserIdAndRoleRoleId(userId, roleId);
    }
}
