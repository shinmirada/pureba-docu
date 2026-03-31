package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Role;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleDAO {

    @Autowired
    private RoleRepository roleRepository;

    // RF05 - Guardar un nuevo rol
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    // RF05 - Listar roles disponibles en la organización
    public List<Role> findByOrganization(Long organizationId) {
        return roleRepository.findByOrganizationOrganizationId(organizationId);
    }

    // RF05 - Buscar rol por nombre dentro de la organización (ej: ADMIN, USER)
    public Role findByNameAndOrganization(Long organizationId, String name) {
        return roleRepository.findByOrganizationOrganizationIdAndName(organizationId, name);
    }

    // RF15 - Verificar que el rol pertenece a la organización antes de asignarlo
    public Role findByIdAndOrganization(Long roleId, Long organizationId) {
        return roleRepository.findByRoleIdAndOrganizationOrganizationId(roleId, organizationId);
    }

    // RF05 - Verificar si ya existe un rol con ese nombre en la organización
    public boolean existsByNameInOrganization(Long organizationId, String name) {
        return roleRepository.existsByOrganizationOrganizationIdAndName(organizationId, name);
    }

    // Buscar rol por ID
    public Optional<Role> findById(Long roleId) {
        return roleRepository.findById(roleId);
    }
}
