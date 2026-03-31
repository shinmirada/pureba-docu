package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de una organización (tenant)")
public class OrganizationDto {

    @Schema(description = "ID único de la organización", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long organizationId;

    @Schema(description = "Nombre de la organización", example = "Tech Corp S.A.S", required = true)
    private String name;

    @Schema(description = "Dominio de la organización", example = "techcorp.com")
    private String domain;

    @Schema(description = "Fecha de creación", example = "2024-06-01T12:00:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Estado de la organización", example = "ACTIVE",
            allowableValues = {"ACTIVE", "INACTIVE"})
    private String status;
}
