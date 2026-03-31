package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // Obtener todos los roles asignados a un usuario (RF05, RF15)
    List<UserRole> findByUserAccountUserId(Long userId);

    // Obtener todos los usuarios que tienen un rol específico (RF29 — asignar tareas por rol)
    List<UserRole> findByRoleRoleId(Long roleId);

    // Validar si un usuario ya tiene un rol asignado — evita duplicados (RF15)
    boolean existsByUserAccountUserIdAndRoleRoleId(Long userId, Long roleId);

    // Eliminar un rol específico de un usuario (RF15 — revocar rol)
    void deleteByUserAccountUserIdAndRoleRoleId(Long userId, Long roleId);
}
