package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Registro de una notificación enviada o fallida")
public class NotificationDto {

    @Schema(description = "ID único de la notificación", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long notificationId;

    @Schema(description = "ID de la plantilla utilizada",
            example = "2", accessMode = Schema.AccessMode.READ_ONLY)
    private Long templateId;

    @Schema(description = "Nombre de la plantilla utilizada",
            example = "DOCUMENTO_CREADO", accessMode = Schema.AccessMode.READ_ONLY)
    private String templateName;

    @Schema(description = "ID del usuario destinatario de la notificación",
            example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "Nombre completo del usuario destinatario",
            example = "Jane Smith", accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;

    @Schema(description = "Correo electrónico del destinatario",
            example = "jsmith@techcorp.com", accessMode = Schema.AccessMode.READ_ONLY)
    private String userEmail;

    @Schema(description = "ID del documento relacionado con la notificación",
            example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Long documentId;

    @Schema(description = "Título del documento relacionado",
            example = "Contrato Marco 2024", accessMode = Schema.AccessMode.READ_ONLY)
    private String documentTitle;

    @Schema(description = "Fecha y hora en que se envió (o intentó enviar) la notificación",
            example = "2024-06-10T09:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime sentAt;

    @Schema(description = "Estado del envío", example = "ENVIADO",
            allowableValues = {"ENVIADO", "FALLIDO", "PENDIENTE"},
            accessMode = Schema.AccessMode.READ_ONLY)
    private String status;

    @Schema(description = "Canal de envío utilizado", example = "EMAIL",
            allowableValues = {"EMAIL", "PUSH", "SMS"},
            accessMode = Schema.AccessMode.READ_ONLY)
    private String channel;

    @Schema(description = "Payload enviado en formato JSON (variables resueltas)",
            example = "{\"titulo\": \"Contrato Marco 2024\", \"destinatario\": \"Jane Smith\"}",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String payloadJson;
}
