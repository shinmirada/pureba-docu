package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Task;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskDAO {

    @Autowired
    private TaskRepository taskRepository;

    // RF29 - Crear nueva tarea al iniciar un paso del flujo
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    // RF30 - Actualizar tarea (aprobar / rechazar / marcar completada)
    public Task update(Task task) {
        return taskRepository.save(task);
    }

    // Buscar tarea por ID
    public Optional<Task> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    // RF29 - Bandeja de tareas: obtener tareas pendientes del usuario autenticado
    public List<Task> findPendingByUser(Long userId) {
        return taskRepository.findByAssignedToUserIdAndStatus(userId, "PENDIENTE");
    }

    // RF29 - Obtener tareas asignadas a un usuario
    public List<Task> findByUser(Long userId) {
        return taskRepository.findByAssignedToUserId(userId);
    }

    // RF30 - Verificar que la tarea le pertenece al usuario antes de aprobar/rechazar
    public Optional<Task> findByIdAndUser(Long taskId, Long userId) {
        return taskRepository.findByTaskIdAndAssignedToUserId(taskId, userId);
    }

    // RF29 - Obtener todas las tareas de un documento
    public List<Task> findByDocument(Long documentId) {
        return taskRepository.findByDocumentDocumentId(documentId);
    }

    // RF30 - Obtener las tareas de un documento ordenadas por fecha (más reciente primero)
    public List<Task> findByDocumentOrdered(Long documentId) {
        return taskRepository.findByDocumentDocumentIdOrderByCreatedAtDesc(documentId);
    }

    // RF29 - Obtener tareas por estado para la organización (vista admin)
    public List<Task> findByOrganizationAndStatus(Long organizationId, String status) {
        return taskRepository.findByDocumentOrganizationOrganizationIdAndStatus(organizationId, status);
    }

    // RF39 - Obtener tareas vencidas para enviar alertas
    public List<Task> findOverdueTasks(String pendingStatus) {
        return taskRepository.findByStatusAndDueDateBefore(pendingStatus, LocalDateTime.now());
    }

    // RF30 - Obtener tareas por estado
    public List<Task> findByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    // RF20 - Verificar si el documento tiene tareas activas antes de eliminar
    public boolean hasActivePendingTasksForDocument(Long documentId) {
        List<Task> pendingTasks = taskRepository.findByDocumentOrganizationOrganizationIdAndStatus(
            documentId, "PENDIENTE"
        );
        return !pendingTasks.isEmpty();
    }
}
