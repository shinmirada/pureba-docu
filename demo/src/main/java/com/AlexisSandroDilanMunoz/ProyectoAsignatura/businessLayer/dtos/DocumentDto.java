package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información completa de un documento, respuesta del servidor")
public class DocumentDto {

    @Schema(description = "ID único del documento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long documentId;

    @Schema(description = "ID de la organización propietaria del documento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "ID del tipo documental asignado", example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    private Long documentTypeId;

    @Schema(description = "Nombre del tipo documental", example = "Contrato", accessMode = Schema.AccessMode.READ_ONLY)
    private String documentTypeName;

    @Schema(description = "ID del usuario que creó el documento", example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    private Long createdById;

    @Schema(description = "Nombre completo del usuario que creó el documento", example = "Jane Smith", accessMode = Schema.AccessMode.READ_ONLY)
    private String createdByName;

    @Schema(description = "Título del documento", example = "Contrato Marco 2024")
    private String title;

    @Schema(description = "Descripción del documento", example = "Contrato marco de servicios tecnológicos para el año 2024")
    private String description;

    @Schema(description = "Estado actual del documento", example = "CREADO", allowableValues = { "CREADO",
            "EN_REVISION", "APROBADO", "RECHAZADO" }, accessMode = Schema.AccessMode.READ_ONLY)
    private String currentState;

    @Schema(description = "Fecha de creación del documento", example = "2024-06-01T12:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de la última modificación del documento", example = "2024-06-05T09:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "Código de referencia único generado por el sistema", example = "DOC-2024-001", accessMode = Schema.AccessMode.READ_ONLY)
    private String referenceCode;

    @Schema(description = "Historial de versiones del documento", accessMode = Schema.AccessMode.READ_ONLY)
    private List<DocumentVersionDto> documentVersions;

    @Schema(description = "Tareas del flujo asociadas al documento", accessMode = Schema.AccessMode.READ_ONLY)
    private List<TaskDto> tasks;

    @Schema(description = "Registros de auditoría del documento", accessMode = Schema.AccessMode.READ_ONLY)
    private List<AuditLogDto> auditLogs;

    @Schema(description = "Notificaciones generadas por este documento", accessMode = Schema.AccessMode.READ_ONLY)
    private List<NotificationDto> notifications;
}