package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {

    // Listar todas las versiones de un documento — historial (RF18)
    List<DocumentVersion> findByDocumentDocumentId(Long documentId);

    // Obtener versiones ordenadas — última versión primero (RF18, RF23)
    List<DocumentVersion> findByDocumentDocumentIdOrderByVersionNumberDesc(Long documentId);

    // Obtener la versión activa — archivo disponible para descarga (RF23)
    Optional<DocumentVersion> findByDocumentDocumentIdAndIsActiveTrue(Long documentId);

    // Validar si ya existe un número de versión para ese documento (RF18)
    boolean existsByDocumentDocumentIdAndVersionNumber(Long documentId, Integer versionNumber);

    // Buscar versiones subidas por un usuario
    List<DocumentVersion> findByUploadedByUserId(Long userId);
}
