package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Tareas de un documento — flujo del documento (RF29, RF30)
    List<Task> findByDocumentDocumentId(Long documentId);

    // Tareas asignadas a un usuario — bandeja de tareas (RF29)
    List<Task> findByAssignedToUserId(Long userId);

    // Tareas de un usuario por estado — pendientes (RF29, RF39)
    List<Task> findByAssignedToUserIdAndStatus(Long userId, String status);

    // Tareas por estado — PENDING, COMPLETED, OVERDUE (RF29)
    List<Task> findByStatus(String status);

    // Tareas de un documento ordenadas por fecha (RF30)
    List<Task> findByDocumentDocumentIdOrderByCreatedAtDesc(Long documentId);

    // Tareas vencidas — para job de alertas automáticas (RF39)
    List<Task> findByStatusAndDueDateBefore(String status, LocalDateTime date);

    // Validar que la tarea pertenece al usuario asignado — control al aprobar/rechazar (RF30)
    Optional<Task> findByTaskIdAndAssignedToUserId(Long taskId, Long userId);

    // Tareas por organización (a través del documento) — vista admin (RF29, RF36)
    List<Task> findByDocumentOrganizationOrganizationIdAndStatus(Long organizationId, String status);
}
