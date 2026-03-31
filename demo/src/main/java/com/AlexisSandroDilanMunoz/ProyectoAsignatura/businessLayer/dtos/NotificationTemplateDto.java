package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de una plantilla de correo electrónico")
public class NotificationTemplateDto {

    @Schema(description = "ID único de la plantilla", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long templateId;

    @Schema(description = "ID de la organización propietaria",
            example = "1", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "Nombre identificador de la plantilla (único por organización)",
            example = "DOCUMENTO_CREADO", required = true)
    private String name;

    @Schema(description = "Asunto del correo electrónico",
            example = "Nuevo documento registrado: {{titulo}}", required = true)
    private String subject;

    @Schema(description = "Cuerpo del correo en formato HTML con variables dinámicas",
            example = "<p>Hola {{destinatario}}, se ha creado el documento <b>{{titulo}}</b>.</p>")
    private String bodyHtml;

    @Schema(description = "Cuerpo del correo en texto plano (alternativa sin HTML)",
            example = "Hola {{destinatario}}, se ha creado el documento {{titulo}}.")
    private String bodyText;

    @Schema(description = "Variables dinámicas disponibles en formato JSON",
            example = "[\"titulo\", \"destinatario\", \"estado\", \"fecha\"]")
    private String variablesJson;

    @Schema(description = "Indica si la plantilla está activa y disponible para usar",
            example = "true")
    private Boolean isActive;
}
