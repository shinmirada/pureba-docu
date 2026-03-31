package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un paso dentro de un flujo de aprobación")
public class WorkflowStepDto {

    @Schema(description = "ID único del paso", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long stepId;

    @Schema(description = "ID del flujo al que pertenece este paso",
            example = "1", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long workflowId;

    @Schema(description = "Nombre del flujo", example = "Aprobación de contratos",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String workflowName;

    @Schema(description = "ID del rol responsable de ejecutar este paso",
            example = "2", required = true)
    private Long assignedRoleId;

    @Schema(description = "Nombre del rol responsable", example = "REVISOR_LEGAL",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String assignedRoleName;

    @Schema(description = "Posición de este paso en la secuencia del flujo (empieza en 1)",
            example = "1", required = true)
    private Integer stepOrder;

    @Schema(description = "Nombre descriptivo del paso",
            example = "Revisión legal", required = true)
    private String name;

    @Schema(description = "Tipo de paso", example = "APROBACION",
            allowableValues = {"APROBACION", "REVISION", "FIRMA"})
    private String stepType;

    @Schema(description = "Indica si este paso es obligatorio para avanzar en el flujo",
            example = "true")
    private Boolean required;

    @Schema(description = "Días máximos permitidos para completar este paso antes de vencer",
            example = "3")
    private Integer timeoutDays;
}
