package com.AlexisSandroDilanMunoz.ProyectoAsignatura.presentationLayer.controllers;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.*;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.WorkflowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de workflows de documentos.
 *
 * CARACTERÍSTICAS:
 * - Creación y configuración de workflows
 * - Activación y desactivación de flujos por tipo de documento
 * - Gestión de tareas dentro del flujo
 * - Consulta de tareas pendientes por usuario
 */
@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    // Inyección del servicio mediante constructor
    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    /**
     * Crear un nuevo workflow
     */
    @PostMapping
    public WorkflowDto createWorkflow(
            @RequestBody WorkflowCreateDto createDto,
            @RequestParam Long organizationId) {

        return workflowService.createWorkflow(createDto, organizationId);
    }

    /**
     * Activar un workflow para un tipo de documento
     */
    @PutMapping("/{workflowId}/activate")
    public void activateWorkflow(
            @PathVariable Long workflowId,
            @RequestParam Long documentTypeId) {

        workflowService.activateWorkflow(workflowId, documentTypeId);
    }

    /**
     * Desactivar un workflow
     */
    @PutMapping("/{workflowId}/deactivate")
    public void deactivateWorkflow(@PathVariable Long workflowId) {
        workflowService.deactivateWorkflow(workflowId);
    }

    /**
     * Completar una tarea dentro del workflow
     */
    @PutMapping("/tasks/{taskId}/complete")
    public void completeTask(
            @PathVariable Long taskId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String comment) {

        workflowService.completeTask(taskId, approved, comment);
    }

    /**
     * Obtener tareas pendientes de un usuario
     */
    @GetMapping("/tasks/pending/{userId}")
    public List<TaskDto> getPendingTasksForUser(
            @PathVariable Long userId) {

        return workflowService.getPendingTasksForUser(userId);
    }
}