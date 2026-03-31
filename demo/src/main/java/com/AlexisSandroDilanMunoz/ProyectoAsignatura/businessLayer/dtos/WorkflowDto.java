package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un flujo de aprobación (workflow)")
public class WorkflowDto {

    @Schema(description = "ID único del flujo", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long workflowId;

    @Schema(description = "ID de la organización propietaria",
            example = "1", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String organizationName;

    @Schema(description = "ID del tipo documental al que aplica este flujo",
            example = "3", required = true)
    private Long documentTypeId;

    @Schema(description = "Nombre del tipo documental", example = "Contrato",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String documentTypeName;

    @Schema(description = "Nombre del flujo (único por organización)",
            example = "Aprobación de contratos — dos niveles", required = true)
    private String name;

    @Schema(description = "Descripción del propósito del flujo",
            example = "Revisión legal y aprobación gerencial de contratos")
    private String description;

    @Schema(description = "Indica si el flujo está activo para el tipo documental",
            example = "true")
    private Boolean isActive;

    @Schema(description = "Configuración adicional en formato JSON",
            example = "{\"notificarCreador\": true}")
    private String configJson;

    @Schema(description = "Pasos del flujo en orden de ejecución",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<WorkflowStepDto> steps;
}
