package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.AuditLog;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AuditLogDAO {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // RF33 - Registrar automáticamente una acción sobre un documento
    public AuditLog save(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    // RF34 - Ver historial de un documento (del más reciente al más antiguo)
    public List<AuditLog> findByDocument(Long documentId) {
        return auditLogRepository.findByDocumentDocumentIdOrderByTimestampDesc(documentId);
    }

    // RF34 - Ver historial verificando que el documento pertenece a la organización (paginado)
    public Page<AuditLog> findByDocumentAndOrganization(Long documentId, Long organizationId, Pageable pageable) {
        return auditLogRepository.findByDocumentOrganizationOrganizationId(organizationId, pageable);
    }

    // RF36 - Trazabilidad completa de la organización, paginada (solo admin)
    public Page<AuditLog> findByOrganization(Long organizationId, Pageable pageable) {
        return auditLogRepository.findByDocumentOrganizationOrganizationId(organizationId, pageable);
    }

    // RF36 - Filtrar trazabilidad por usuario dentro de la organización
    public Page<AuditLog> findByUserAndOrganization(Long userId, Long organizationId, Pageable pageable) {
        return auditLogRepository.findByUserUserIdAndDocumentOrganizationOrganizationId(userId, organizationId, pageable);
    }

    // RF36 - Filtrar trazabilidad por rango de fechas dentro de la organización
    public Page<AuditLog> findByOrganizationAndDateRange(Long organizationId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return auditLogRepository.findByDocumentOrganizationOrganizationIdAndTimestampBetween(organizationId, from, to, pageable);
    }

    // RF36 - Filtrar trazabilidad por tipo de acción + rango de fechas dentro de la organización
    public Page<AuditLog> findByFilters(Long organizationId, String actionType, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return auditLogRepository.findByDocumentOrganizationOrganizationIdAndActionTypeAndTimestampBetween(organizationId, actionType, from, to, pageable);
    }

    // RF36 - Filtrar por tipo de acción
    public List<AuditLog> findByActionType(String actionType) {
        return auditLogRepository.findByActionType(actionType);
    }
}
