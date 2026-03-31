package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar la información de un usuario existente")
public class UserAccountUpdateDto {

    @Schema(description = "Nuevo correo electrónico (único dentro de la organización)",
            example = "john.doe@techcorp.com",
            required = true,
            maxLength = 200)
    private String email;

    @Schema(description = "Nombre completo actualizado",
            example = "John Alexander Doe",
            maxLength = 200)
    private String fullName;
}
