package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Definición de un paso al crear un flujo de aprobación")
public class WorkflowStepCreateDto {

    @Schema(description = "Nombre descriptivo del paso",
            example = "Revisión legal",
            required = true,
            maxLength = 200)
    private String name;

    @Schema(description = "Posición del paso en la secuencia (empieza en 1, sin repetir)",
            example = "1",
            required = true)
    private Integer stepOrder;

    @Schema(description = "ID del rol responsable de ejecutar este paso",
            example = "2",
            required = true)
    private Long assignedRoleId;

    @Schema(description = "Tipo de paso",
            example = "APROBACION",
            allowableValues = {"APROBACION", "REVISION", "FIRMA"})
    private String stepType;

    @Schema(description = "¿Es obligatorio completar este paso para avanzar?",
            example = "true")
    private Boolean required;

    @Schema(description = "Días máximos para completar el paso antes de vencer",
            example = "3")
    private Integer timeoutDays;
}
