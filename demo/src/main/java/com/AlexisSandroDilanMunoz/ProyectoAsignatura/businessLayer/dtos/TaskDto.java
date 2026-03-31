package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de una tarea asignada dentro de un flujo de aprobación")
public class TaskDto {

    @Schema(description = "ID único de la tarea", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long taskId;

    @Schema(description = "ID del documento sobre el que aplica la tarea",
            example = "10", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long documentId;

    @Schema(description = "Título del documento", example = "Contrato Marco 2024",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String documentTitle;

    @Schema(description = "Código de referencia del documento", example = "DOC-2024-010",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String documentReferenceCode;

    @Schema(description = "ID del paso del flujo que generó esta tarea",
            example = "2", accessMode = Schema.AccessMode.READ_ONLY)
    private Long stepId;

    @Schema(description = "Nombre del paso del flujo", example = "Revisión legal",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String stepName;

    @Schema(description = "Orden del paso en el flujo", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Integer stepOrder;

    @Schema(description = "ID del usuario al que está asignada la tarea",
            example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    private Long assignedToId;

    @Schema(description = "Nombre completo del usuario asignado", example = "Jane Smith",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String assignedToName;

    @Schema(description = "Estado actual de la tarea", example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "COMPLETADA", "VENCIDA", "RECHAZADA"},
            accessMode = Schema.AccessMode.READ_ONLY)
    private String status;

    @Schema(description = "Fecha de creación de la tarea", example = "2024-06-10T08:00:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Fecha límite para completar la tarea", example = "2024-06-13T08:00:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dueDate;

    @Schema(description = "Fecha y hora en que se completó la tarea",
            example = "2024-06-12T10:15:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime completedAt;

    @Schema(description = "Comentarios del usuario al aprobar o rechazar (obligatorio al rechazar)",
            example = "El clausulado de penalidades requiere ajuste.")
    private String comments;
}
