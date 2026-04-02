package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

        // Historial de un documento — más reciente primero (RF34)
        Page<AuditLog> findByDocumentDocumentIdOrderByTimestampDesc(Long documentId, Pageable pageable);

        // Filtrar por tipo de acción (RF36)
        List<AuditLog> findByActionType(String actionType);

        // Trazabilidad completa paginada por organización — vista admin (RF36)
        Page<AuditLog> findByDocumentOrganizationOrganizationId(Long organizationId, Pageable pageable);

        // Filtrar por usuario dentro de la organización (RF36)
        Page<AuditLog> findByUserUserIdAndDocumentOrganizationOrganizationId(
                        Long userId, Long organizationId, Pageable pageable);

        // Filtrar por rango de fechas dentro de la organización (RF36)
        Page<AuditLog> findByDocumentOrganizationOrganizationIdAndTimestampBetween(
                        Long organizationId, LocalDateTime start, LocalDateTime end, Pageable pageable);

        // Filtro combinado: organización + tipo de acción + rango de fechas (RF36)
        Page<AuditLog> findByDocumentOrganizationOrganizationIdAndActionTypeAndTimestampBetween(
                        Long organizationId, String actionType, LocalDateTime start, LocalDateTime end,
                        Pageable pageable);
}
