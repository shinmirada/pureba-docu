package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.*;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.SecurityContextHelper;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.*;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.*;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final UserAccountRepository userAccountRepository;
    private final SecurityContextHelper securityHelper;
    // Nuevas dependencias para workflow, notificaciones y auditoría
    private final WorkflowService workflowService;
    private final NotificationService notificationService;
    private final AuditLogService auditLogService;

    @Override
    public DocumentDto createDocument(DocumentCreateDto createDto, Long organizationId, Long creatorId) {
        log.info("Creando documento: {}", createDto.getTitle());
        DocumentType docType = documentTypeRepository.findByDocumentTypeIdAndOrganizationOrganizationId(
                createDto.getDocumentTypeId(), organizationId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo documental no válido para esta organización"));
        UserAccount creator = userAccountRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));
        Organization org = creator.getOrganization();
        Document doc = new Document();
        doc.setTitle(createDto.getTitle());
        doc.setDescription(createDto.getDescription());
        doc.setCurrentState("CREADO");
        doc.setCreatedAt(LocalDateTime.now());
        doc.setUpdatedAt(LocalDateTime.now());
        doc.setReferenceCode(generateReferenceCode(org.getOrganizationId()));
        doc.setOrganization(org);
        doc.setDocumentType(docType);
        doc.setCreatedBy(creator);
        Document saved = documentRepository.save(doc);
        // Auditoría
        auditLogService.logAction("CREADO",
                String.format("Documento '%s' creado por usuario %d", saved.getTitle(), creatorId),
                saved.getDocumentId(), creatorId, obtenerIpActual());
        // Notificación
        notificationService.sendDocumentCreatedNotification(saved.getDocumentId(), creatorId);
        // Iniciar workflow automático
        workflowService.startWorkflowForDocument(saved.getDocumentId(), createDto.getDocumentTypeId());
        return toDto(saved);
    }

    @Override
    public DocumentDto updateDocument(Long documentId, DocumentUpdateDto updateDto) {
        Document doc = documentRepository
                .findByDocumentIdAndOrganizationOrganizationId(documentId, securityHelper.getCurrentOrganizationId())
                .orElseThrow(() -> new RuntimeException("Documento no encontrado o no pertenece a su organización"));
        String oldTitle = doc.getTitle();
        if (updateDto.getTitle() != null)
            doc.setTitle(updateDto.getTitle());
        if (updateDto.getDescription() != null)
            doc.setDescription(updateDto.getDescription());
        doc.setUpdatedAt(LocalDateTime.now());
        Document updated = documentRepository.save(doc);
        auditLogService.logAction("ACTUALIZADO",
                String.format("Documento '%s' actualizado (título anterior: '%s')", updated.getTitle(), oldTitle),
                documentId, securityHelper.getCurrentUserId(), obtenerIpActual());
        return toDto(updated);
    }

    @Override
    public void deleteDocument(Long documentId) {
        if (!securityHelper.isAdmin())
            throw new RuntimeException("Solo administradores pueden eliminar documentos");
        Document doc = documentRepository
                .findByDocumentIdAndOrganizationOrganizationId(documentId, securityHelper.getCurrentOrganizationId())
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        auditLogService.logAction("ELIMINADO",
                String.format("Documento '%s' (ID %d) eliminado", doc.getTitle(), documentId),
                documentId, securityHelper.getCurrentUserId(), obtenerIpActual());
        documentRepository.delete(doc);
    }

    @Override
    public DocumentDto getDocumentById(Long documentId) {
        Document doc = documentRepository
                .findByDocumentIdAndOrganizationOrganizationId(documentId, securityHelper.getCurrentOrganizationId())
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        return toDto(doc);
    }

    @Override
    public Page<DocumentDto> getDocumentsByOrganization(Long organizationId, Pageable pageable) {
        return documentRepository.findByOrganizationOrganizationId(organizationId, pageable).map(this::toDto);
    }

    @Override
    public Page<DocumentDto> filterDocuments(Long organizationId, Long documentTypeId, String state,
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return documentRepository.findByFilters(organizationId, documentTypeId, state, startDate, endDate, pageable)
                .map(this::toDto);
    }

    @Override
    public void uploadFile(Long documentId, MultipartFile file, Long userId) {
        Document doc = documentRepository
                .findByDocumentIdAndOrganizationOrganizationId(documentId, securityHelper.getCurrentOrganizationId())
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        UserAccount uploader = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        int nextVersion = documentVersionRepository.findByDocumentDocumentIdOrderByVersionNumberDesc(documentId)
                .stream().findFirst().map(v -> v.getVersionNumber() + 1).orElse(1);
        DocumentVersion version = new DocumentVersion();
        version.setDocument(doc);
        version.setUploadedBy(uploader);
        version.setVersionNumber(nextVersion);
        version.setFileName(file.getOriginalFilename());
        version.setMimeType(file.getContentType());
        version.setSize(file.getSize());
        version.setUploadedAt(LocalDateTime.now());
        version.setIsActive(true);
        String filePath = saveFile(file, doc.getOrganization().getOrganizationId(), doc.getDocumentId(), nextVersion);
        version.setFilePath(filePath);
        documentVersionRepository.save(version);
        documentVersionRepository.findByDocumentDocumentIdAndIsActiveTrue(documentId)
                .ifPresent(prev -> {
                    prev.setIsActive(false);
                    documentVersionRepository.save(prev);
                });
        auditLogService.logAction("ARCHIVO_CARGADO",
                String.format("Nueva versión %d subida: %s (%d bytes)", nextVersion, file.getOriginalFilename(),
                        file.getSize()),
                documentId, userId, obtenerIpActual());
    }

    @Override
    public byte[] downloadFile(Long documentId) {
        DocumentVersion activeVersion = documentVersionRepository.findByDocumentDocumentIdAndIsActiveTrue(documentId)
                .orElseThrow(() -> new RuntimeException("El documento no tiene archivo activo"));
        return readFile(activeVersion.getFilePath());
    }

    private String generateReferenceCode(Long orgId) {
        return "DOC-" + orgId + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String saveFile(MultipartFile file, Long orgId, Long docId, int version) {
        String path = String.format("/storage/org%d/doc%d/v%d_%s", orgId, docId, version, file.getOriginalFilename());
        // Aquí escribir el archivo en disco
        return path;
    }

    private byte[] readFile(String path) {
        // Implementar lectura del archivo
        return new byte[0];
    }

    private DocumentDto toDto(Document doc) {
        DocumentDto dto = new DocumentDto();
        dto.setDocumentId(doc.getDocumentId());
        dto.setOrganizationId(doc.getOrganization().getOrganizationId());
        dto.setOrganizationName(doc.getOrganization().getName());
        dto.setDocumentTypeId(doc.getDocumentType().getDocumentTypeId());
        dto.setDocumentTypeName(doc.getDocumentType().getName());
        dto.setCreatedById(doc.getCreatedBy().getUserId());
        dto.setCreatedByName(doc.getCreatedBy().getFullName());
        dto.setTitle(doc.getTitle());
        dto.setDescription(doc.getDescription());
        dto.setCurrentState(doc.getCurrentState());
        dto.setCreatedAt(doc.getCreatedAt());
        dto.setUpdatedAt(doc.getUpdatedAt());
        dto.setReferenceCode(doc.getReferenceCode());
        return dto;
    }

    // Método auxiliar para obtener la IP (debes implementarlo correctamente en el
    // controlador)
    private String obtenerIpActual() {
        return "127.0.0.1"; // Mock, reemplazar con IP real desde HttpServletRequest
    }
}