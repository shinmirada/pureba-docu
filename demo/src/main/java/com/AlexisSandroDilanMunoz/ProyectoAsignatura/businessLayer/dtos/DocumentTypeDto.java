package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un tipo documental")
public class DocumentTypeDto {

    @Schema(description = "ID único del tipo documental", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long documentTypeId;

    @Schema(description = "ID de la organización propietaria",
            example = "1", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "Nombre del tipo documental (único por organización)",
            example = "Factura de venta", required = true)
    private String name;

    @Schema(description = "Esquema JSON de metadatos adicionales para este tipo",
            example = "{\"campos\": [\"proveedor\", \"numero_factura\"]}")
    private String metadataSchema;

    @Schema(description = "Indica si el tipo documental está activo y disponible",
            example = "true")
    private Boolean active;
}
