package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public interface DocumentService {

    DocumentDto createDocument(DocumentCreateDto createDto, Long organizationId, Long creatorId);

    DocumentDto updateDocument(Long documentId, DocumentUpdateDto updateDto);

    void deleteDocument(Long documentId);

    DocumentDto getDocumentById(Long documentId);

    Page<DocumentDto> getDocumentsByOrganization(Long organizationId, Pageable pageable);

    Page<DocumentDto> filterDocuments(Long organizationId, Long documentTypeId, String state,
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    void uploadFile(Long documentId, MultipartFile file, Long userId);

    byte[] downloadFile(Long documentId);
}