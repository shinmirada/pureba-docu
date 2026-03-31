package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentVersion;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.DocumentVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocumentVersionDAO {

    @Autowired
    private DocumentVersionRepository documentVersionRepository;

    // RF18 - Guardar nueva versión del documento
    public DocumentVersion save(DocumentVersion documentVersion) {
        return documentVersionRepository.save(documentVersion);
    }

    // RF18 - Actualizar versión (p. ej. marcar isActive = false)
    public DocumentVersion update(DocumentVersion documentVersion) {
        return documentVersionRepository.save(documentVersion);
    }

    // RF18 - Obtener historial completo de versiones de un documento
    public List<DocumentVersion> findByDocument(Long documentId) {
        return documentVersionRepository.findByDocumentDocumentId(documentId);
    }

    // RF18 - Obtener versiones ordenadas — última versión primero
    public List<DocumentVersion> findByDocumentOrdered(Long documentId) {
        return documentVersionRepository.findByDocumentDocumentIdOrderByVersionNumberDesc(documentId);
    }

    // RF18 / RF23 - Obtener la versión activa del documento
    public Optional<DocumentVersion> findActiveByDocument(Long documentId) {
        return documentVersionRepository.findByDocumentDocumentIdAndIsActiveTrue(documentId);
    }

    // RF18 - Verificar si ya existe un número de versión para ese documento
    public boolean existsByDocumentAndVersionNumber(Long documentId, Integer versionNumber) {
        return documentVersionRepository.existsByDocumentDocumentIdAndVersionNumber(documentId, versionNumber);
    }

    // RF23 - Versiones subidas por un usuario específico
    public List<DocumentVersion> findByUploadedBy(Long userId) {
        return documentVersionRepository.findByUploadedByUserId(userId);
    }
}
