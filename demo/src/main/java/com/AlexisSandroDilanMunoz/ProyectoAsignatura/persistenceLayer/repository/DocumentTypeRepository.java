package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {

    // Listar todos los tipos documentales de una organización (RF24–RF27, RF42)
    List<DocumentType> findByOrganizationOrganizationId(Long organizationId);

    // Listar paginado de una organización
    org.springframework.data.domain.Page<DocumentType> findByOrganizationOrganizationId(Long organizationId, org.springframework.data.domain.Pageable pageable);

    // Buscar por id validando organización — seguridad multi-tenant (RF25, RF26)
    Optional<DocumentType> findByDocumentTypeIdAndOrganizationOrganizationId(Long documentTypeId, Long organizationId);

    // Buscar por nombre dentro de la organización — validar unicidad (RF24)
    Optional<DocumentType> findByOrganizationOrganizationIdAndName(Long organizationId, String name);

    // Listar solo los tipos activos — selector en formulario de documento (RF27, RF42)
    List<DocumentType> findByOrganizationOrganizationIdAndActiveTrue(Long organizationId);

    // Validar si ya existe un tipo con ese nombre en la org (RF24, RF25)
    boolean existsByOrganizationOrganizationIdAndName(Long organizationId, String name);
}
