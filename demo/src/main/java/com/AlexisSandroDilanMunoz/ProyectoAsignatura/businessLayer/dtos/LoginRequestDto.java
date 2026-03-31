package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Credenciales para iniciar sesión en la plataforma")
public class LoginRequestDto {

    @Schema(description = "Nombre de usuario",
            example = "jdoe",
            required = true)
    private String username;

    @Schema(description = "Contraseña del usuario",
            example = "SecurePass123!",
            required = true)
    private String password;

    @Schema(description = "ID de la organización (necesario para el modelo multi-empresa)",
            example = "1",
            required = true)
    private Long organizationId;
}
