package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class DocumentDAO {

    @Autowired
    private DocumentRepository documentRepository;

    // RF17 - Crear nuevo documento
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    // RF19 - Actualizar metadatos del documento
    public Document update(Document document) {
        return documentRepository.save(document);
    }

    // RF20 - Eliminar documento
    public void delete(Document document) {
        documentRepository.delete(document);
    }

    // RF21 - Listar todos los documentos de la organización (paginado)
    public Page<Document> findByOrganization(Long organizationId, Pageable pageable) {
        return documentRepository.findByOrganizationOrganizationId(organizationId, pageable);
    }

    // RF21 - Listar documentos ordenados por fecha de creación descendente
    public Page<Document> findByOrganizationOrderedByDate(Long organizationId, Pageable pageable) {
        return documentRepository.findByOrganizationOrganizationIdOrderByCreatedAtDesc(organizationId, pageable);
    }

    // RF10 - Buscar documento verificando que pertenece a la organización (aislamiento)
    public Optional<Document> findByIdAndOrganization(Long documentId, Long organizationId) {
        return documentRepository.findByDocumentIdAndOrganizationOrganizationId(documentId, organizationId);
    }

    // Buscar documento por ID
    public Optional<Document> findById(Long documentId) {
        return documentRepository.findById(documentId);
    }

    // RF22 - Filtrar documentos por tipo documental (paginado)
    public Page<Document> findByOrganizationAndType(Long organizationId, Long documentTypeId, Pageable pageable) {
        return documentRepository.findByOrganizationOrganizationIdAndDocumentTypeDocumentTypeId(organizationId, documentTypeId, pageable);
    }

    // RF22 - Filtrar documentos por estado (paginado)
    public Page<Document> findByOrganizationAndState(Long organizationId, String currentState, Pageable pageable) {
        return documentRepository.findByOrganizationOrganizationIdAndCurrentState(organizationId, currentState, pageable);
    }

    // RF22 - Filtrar documentos por rango de fechas (paginado)
    public Page<Document> findByOrganizationAndDateRange(Long organizationId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return documentRepository.findByOrganizationOrganizationIdAndCreatedAtBetween(organizationId, from, to, pageable);
    }

    // RF22 - Filtro combinado: tipo + estado + fechas (paginado)
    public Page<Document> findByFilters(Long organizationId, Long documentTypeId, String state,
                                        LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return documentRepository.findByFilters(organizationId, documentTypeId, state, from, to, pageable);
    }

    // RF26 - Verificar si existen documentos de un tipo antes de eliminarlo
    public boolean existsByDocumentType(Long documentTypeId) {
        return documentRepository.existsByDocumentTypeDocumentTypeId(documentTypeId);
    }

    // RF42 - Verificar si existen documentos en estado activo para un tipo
    public boolean existsByDocumentTypeAndStateNot(Long documentTypeId, String state) {
        return documentRepository.existsByDocumentTypeDocumentTypeIdAndCurrentStateNot(documentTypeId, state);
    }

    // RF17 - Buscar documento por código de referencia dentro de la organización
    public Optional<Document> findByReferenceCodeAndOrganization(String referenceCode, Long organizationId) {
        return documentRepository.findByReferenceCodeAndOrganizationOrganizationId(referenceCode, organizationId);
    }
}
