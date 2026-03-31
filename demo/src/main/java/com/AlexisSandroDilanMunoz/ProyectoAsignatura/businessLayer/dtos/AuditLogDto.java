package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Registro de auditoría de una acción sobre un documento")
public class AuditLogDto {

    @Schema(description = "ID único del registro de auditoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long auditId;

    @Schema(description = "ID del documento sobre el que se realizó la acción", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Long documentId;

    @Schema(description = "Título del documento relacionado", example = "Contrato Marco 2024", accessMode = Schema.AccessMode.READ_ONLY)
    private String documentTitle;

    @Schema(description = "ID del usuario que realizó la acción", example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "Nombre de usuario que realizó la acción", example = "john_doe", accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;

    @Schema(description = "Tipo de acción realizada", example = "CREADO", allowableValues = { "CREADO", "ACTUALIZADO",
            "ELIMINADO", "ESTADO_CAMBIADO",
            "ARCHIVO_CARGADO", "DESCARGADO" })
    private String actionType;

    @Schema(description = "Descripción detallada de la acción realizada", example = "El usuario creó el documento 'Contrato Marco 2024'")
    private String actionDetail;

    @Schema(description = "Fecha y hora exacta en que se realizó la acción", example = "2024-06-01T12:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime timestamp;

    @Schema(description = "Dirección IP del cliente que realizó la acción", example = "192.168.1.1", accessMode = Schema.AccessMode.READ_ONLY)
    private String ipAddress;

    @Schema(description = "Metadatos adicionales en formato JSON", example = "{\"browser\": \"Chrome\", \"os\": \"Windows\"}", accessMode = Schema.AccessMode.READ_ONLY)
    private String metadata;
}