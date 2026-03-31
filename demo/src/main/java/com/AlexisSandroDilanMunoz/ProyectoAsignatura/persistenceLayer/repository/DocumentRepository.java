package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Listar documentos por organización paginado (RF21)
    Page<Document> findByOrganizationOrganizationId(Long organizationId, Pageable pageable);

    // Buscar un documento por id validando que pertenezca a la organización (RF10, RF19, RF20)
    Optional<Document> findByDocumentIdAndOrganizationOrganizationId(Long documentId, Long organizationId);

    // Filtrar documentos por tipo dentro de la organización (RF22)
    Page<Document> findByOrganizationOrganizationIdAndDocumentTypeDocumentTypeId(
            Long organizationId, Long documentTypeId, Pageable pageable);

    // Filtrar documentos por estado dentro de la organización (RF22)
    Page<Document> findByOrganizationOrganizationIdAndCurrentState(
            Long organizationId, String currentState, Pageable pageable);

    // Filtrar documentos por rango de fechas (RF22)
    Page<Document> findByOrganizationOrganizationIdAndCreatedAtBetween(
            Long organizationId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Listar documentos ordenados por fecha de creación desc (RF21)
    Page<Document> findByOrganizationOrganizationIdOrderByCreatedAtDesc(
            Long organizationId, Pageable pageable);

    // Buscar documento por código de referencia dentro de la organización (RF17)
    Optional<Document> findByReferenceCodeAndOrganizationOrganizationId(
            String referenceCode, Long organizationId);

    // Validar si existen documentos asociados a un tipo documental — para RF26 (no eliminar si hay docs)
    boolean existsByDocumentTypeDocumentTypeId(Long documentTypeId);

    // Validar si existen documentos activos en proceso para un tipo — para RF42
    boolean existsByDocumentTypeDocumentTypeIdAndCurrentStateNot(Long documentTypeId, String state);

    // Filtro combinado: tipo + estado + fechas (RF22)
    @Query("SELECT d FROM Document d WHERE d.organization.organizationId = ?1 " +
           "AND (?2 IS NULL OR d.documentType.documentTypeId = ?2) " +
           "AND (?3 IS NULL OR d.currentState = ?3) " +
           "AND (?4 IS NULL OR d.createdAt >= ?4) " +
           "AND (?5 IS NULL OR d.createdAt <= ?5)")
    Page<Document> findByFilters(
            Long organizationId,
            Long documentTypeId,
            String state,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}
