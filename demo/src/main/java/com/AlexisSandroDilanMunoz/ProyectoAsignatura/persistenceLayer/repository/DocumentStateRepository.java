package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentStateRepository extends JpaRepository<DocumentState, Long> {

    // Listar todos los estados de una organización (RF41)
    List<DocumentState> findByOrganizationOrganizationId(Long organizationId);

    // Buscar un estado por código dentro de la organización — validar unicidad (RF41)
    Optional<DocumentState> findByOrganizationOrganizationIdAndCode(Long organizationId, String code);

    // Listar estados ordenados según el orden definido (RF41 — flujo correcto)
    List<DocumentState> findByOrganizationOrganizationIdOrderByStateOrderAsc(Long organizationId);

    // Validar existencia de un código en la organización — evitar duplicados (RF41)
    boolean existsByOrganizationOrganizationIdAndCode(Long organizationId, String code);
}
