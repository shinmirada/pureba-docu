package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Listar roles por organización (RF05, RF15)
    List<Role> findByOrganizationOrganizationId(Long organizationId);

    // Buscar rol por id validando organización (seguridad multi-tenant)
    Role findByRoleIdAndOrganizationOrganizationId(Long roleId, Long organizationId);

    // Buscar rol por nombre dentro de la organización (ej: ADMIN, USER)
    Role findByOrganizationOrganizationIdAndName(Long organizationId, String name);

    // Validar si ya existe un rol con ese nombre en la organización
    boolean existsByOrganizationOrganizationIdAndName(Long organizationId, String name);
}
