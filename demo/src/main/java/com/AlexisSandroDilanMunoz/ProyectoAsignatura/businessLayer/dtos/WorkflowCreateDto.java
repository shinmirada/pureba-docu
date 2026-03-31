package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear un nuevo flujo de aprobación")
public class WorkflowCreateDto {

    @Schema(description = "ID del tipo documental al que aplicará este flujo",
            example = "3",
            required = true)
    private Long documentTypeId;

    @Schema(description = "Nombre descriptivo del flujo (único por organización)",
            example = "Aprobación de contratos — dos niveles",
            required = true,
            maxLength = 200)
    private String name;

    @Schema(description = "Descripción del propósito del flujo",
            example = "Revisión legal seguida de aprobación gerencial")
    private String description;

    @Schema(description = "Pasos del flujo en orden de ejecución (mínimo 1 paso obligatorio)",
            required = true)
    private List<WorkflowStepCreateDto> steps;
}
