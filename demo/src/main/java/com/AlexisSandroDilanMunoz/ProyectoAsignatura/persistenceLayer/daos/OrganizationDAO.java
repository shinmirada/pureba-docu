package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrganizationDAO {

    @Autowired
    private OrganizationRepository organizationRepository;

    // RF01 / RF08 - Registrar nueva organización
    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

    // RF11 - Actualizar datos de la organización
    public Organization update(Organization organization) {
        return organizationRepository.save(organization);
    }

    // RF02 - Buscar organización por ID
    public Optional<Organization> findById(Long id) {
        return organizationRepository.findById(id);
    }

    // RF01 / RF08 - Verificar si ya existe una organización con ese nombre
    public boolean existsByName(String name) {
        return organizationRepository.existsByName(name);
    }

    // RF01 / RF08 - Verificar si ya existe una organización con ese dominio
    public boolean existsByDomain(String domain) {
        return organizationRepository.existsByDomain(domain);
    }

    // RF02 - Buscar organización por nombre (para login SaaS)
    public Organization findByName(String name) {
        return organizationRepository.findByName(name);
    }

    // RF01 - Buscar organización por dominio
    public Organization findByDomain(String domain) {
        return organizationRepository.findByDomain(domain);
    }

    // RF02 - Filtrar organizaciones por estado (ACTIVO / INACTIVO)
    public List<Organization> findByStatus(String status) {
        return organizationRepository.findByStatus(status);
    }

    // Listar todas las organizaciones
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }
}
