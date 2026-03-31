package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    // Buscar organización por nombre (RF01, RF08 — unicidad)
    Organization findByName(String name);

    // Buscar por dominio (login SaaS por empresa)
    Organization findByDomain(String domain);

    // Filtrar por estado (ACTIVE, INACTIVE)
    List<Organization> findByStatus(String status);

    // Validar si ya existe una organización con ese nombre (RF01, RF08)
    boolean existsByName(String name);

    // Validar si ya existe un dominio registrado (RF01, RF08, RF11)
    boolean existsByDomain(String domain);
}
