package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.AuditLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AuditLogService {
    void logAction(String actionType, String actionDetail, Long documentId, Long userId, String ipAddress);

    Page<AuditLogDto> getDocumentAuditLog(Long documentId, Pageable pageable);

    Page<AuditLogDto> getOrganizationAuditLog(Long organizationId, String actionType, LocalDateTime from,
            LocalDateTime to, Pageable pageable);
}