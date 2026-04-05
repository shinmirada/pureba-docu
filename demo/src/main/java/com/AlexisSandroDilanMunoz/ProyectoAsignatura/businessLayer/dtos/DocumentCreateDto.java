package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear un nuevo documento")
public class DocumentCreateDto {

        @Schema(description = "Título del documento", example = "Contrato Marco 2024", required = true, maxLength = 300)
        private String title;

        @Schema(description = "Descripción del documento", example = "Contrato marco de servicios tecnológicos para el año 2024")
        private String description;

        @Schema(description = "ID del tipo documental al que pertenece este documento", example = "3", required = true)
        private Long documentTypeId;
}
