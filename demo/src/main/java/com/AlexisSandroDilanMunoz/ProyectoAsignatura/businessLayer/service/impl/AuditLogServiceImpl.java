package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.AuditLogDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.SecurityContextHelper;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.AuditLogService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.AuditLog;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.AuditLogRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.DocumentRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final DocumentRepository documentRepository;
    private final UserAccountRepository userAccountRepository;
    private final SecurityContextHelper securityHelper;

    @Override
    public void logAction(String actionType, String actionDetail, Long documentId, Long userId, String ipAddress) {
        Document doc = documentRepository.findById(documentId).orElse(null);
        UserAccount user = userAccountRepository.findById(userId).orElse(null);
        if (doc == null || user == null)
            return;
        AuditLog logEntry = new AuditLog();
        logEntry.setDocument(doc);
        logEntry.setUser(user);
        logEntry.setActionType(actionType);
        logEntry.setActionDetail(actionDetail);
        logEntry.setTimestamp(LocalDateTime.now());
        logEntry.setIpAddress(ipAddress);
        auditLogRepository.save(logEntry);
    }

    @Override
    public Page<AuditLogDto> getDocumentAuditLog(Long documentId, Pageable pageable) {
        Long orgId = securityHelper.getCurrentOrganizationId();
        documentRepository.findByDocumentIdAndOrganizationOrganizationId(documentId, orgId)
                .orElseThrow(() -> new RuntimeException("Documento no accesible"));
        return auditLogRepository.findByDocumentDocumentIdOrderByTimestampDesc(documentId, pageable)
                .map(this::toDto);
    }

    @Override
    public Page<AuditLogDto> getOrganizationAuditLog(Long organizationId, String actionType, LocalDateTime from,
            LocalDateTime to, Pageable pageable) {
        if (!securityHelper.isAdmin())
            throw new RuntimeException("Solo administradores pueden ver auditoría global");
        if (actionType != null && from != null && to != null)
            return auditLogRepository.findByDocumentOrganizationOrganizationIdAndActionTypeAndTimestampBetween(
                    organizationId, actionType, from, to, pageable).map(this::toDto);
        else if (from != null && to != null)
            return auditLogRepository.findByDocumentOrganizationOrganizationIdAndTimestampBetween(
                    organizationId, from, to, pageable).map(this::toDto);
        else
            return auditLogRepository.findByDocumentOrganizationOrganizationId(organizationId, pageable)
                    .map(this::toDto);
    }

    private AuditLogDto toDto(AuditLog log) {
        AuditLogDto dto = new AuditLogDto();
        dto.setAuditId(log.getAuditId());
        dto.setDocumentId(log.getDocument().getDocumentId());
        dto.setDocumentTitle(log.getDocument().getTitle());
        dto.setUserId(log.getUser().getUserId());
        dto.setUserName(log.getUser().getUsername());
        dto.setActionType(log.getActionType());
        dto.setActionDetail(log.getActionDetail());
        dto.setTimestamp(log.getTimestamp());
        dto.setIpAddress(log.getIpAddress());
        dto.setMetadata(log.getMetadata());
        return dto;
    }
}