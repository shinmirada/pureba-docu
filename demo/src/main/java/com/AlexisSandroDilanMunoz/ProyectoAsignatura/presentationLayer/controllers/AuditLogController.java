package com.AlexisSandroDilanMunoz.ProyectoAsignatura.presentationLayer.controllers;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.AuditLogDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Controlador REST para la gestión de logs de auditoría.
 *
 * CARACTERÍSTICAS:
 * - Registro de acciones realizadas en el sistema
 * - Consulta de logs por documento
 * - Consulta de logs por organización con filtros opcionales
 */
@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    // Inyección del servicio mediante constructor
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    /**
     * Registrar una acción en el log de auditoría
     */
    @PostMapping
    public void logAction(
            @RequestParam String actionType,
            @RequestParam String actionDetail,
            @RequestParam Long documentId,
            @RequestParam Long userId,
            @RequestParam String ipAddress) {

        auditLogService.logAction(actionType, actionDetail, documentId, userId, ipAddress);
    }

    /**
     * Obtener los logs asociados a un documento específico
     */
    @GetMapping("/document/{documentId}")
    public Page<AuditLogDto> getDocumentLogs(
            @PathVariable Long documentId,
            Pageable pageable) {

        return auditLogService.getDocumentAuditLog(documentId, pageable);
    }

    /**
     * Obtener logs de una organización con filtros opcionales
     */
    @GetMapping("/organization/{organizationId}")
    public Page<AuditLogDto> getOrganizationLogs(
            @PathVariable Long organizationId,
            @RequestParam(required = false) String actionType,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            Pageable pageable) {

        return auditLogService.getOrganizationAuditLog(
                organizationId, actionType, from, to, pageable);
    }
}