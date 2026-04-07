package com.AlexisSandroDilanMunoz.ProyectoAsignatura.presentationLayer.controllers;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.NotificationService;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de notificaciones.
 *
 * CARACTERÍSTICAS:
 * - Envío de notificaciones por eventos del sistema
 * - Notificación de creación de documentos
 * - Notificación de cambios de estado
 * - Notificación de asignación de tareas
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    // Inyección del servicio mediante constructor
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Enviar notificación cuando se crea un documento
     */
    @PostMapping("/document-created")
    public void sendDocumentCreatedNotification(
            @RequestParam Long documentId,
            @RequestParam Long creatorId) {

        notificationService.sendDocumentCreatedNotification(documentId, creatorId);
    }

    /**
     * Enviar notificación cuando cambia el estado de un documento
     */
    @PostMapping("/document-state-changed")
    public void sendDocumentStateChangedNotification(
            @RequestParam Long documentId,
            @RequestParam String newState,
            @RequestParam(required = false) String comment) {

        notificationService.sendDocumentStateChangedNotification(documentId, newState, comment);
    }

    /**
     * Enviar notificación cuando se asigna una tarea
     */
    @PostMapping("/task-assigned")
    public void sendTaskAssignedNotification(
            @RequestParam Long taskId,
            @RequestParam Long userId) {

        notificationService.sendTaskAssignedNotification(taskId, userId);
    }
}