package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar los metadatos de un documento existente")
public class DocumentUpdateDto {

    @Schema(description = "Nuevo título del documento",
            example = "Contrato Marco 2024 — Versión revisada",
            required = true,
            maxLength = 300)
    private String title;

    @Schema(description = "Nueva descripción del documento",
            example = "Contrato marco de servicios tecnológicos actualizado con nuevo alcance")
    private String description;
}
