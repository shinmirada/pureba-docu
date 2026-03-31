package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear un nuevo usuario en la organización")
public class UserAccountCreateDto {

    @Schema(description = "Nombre de usuario único dentro de la organización",
            example = "jdoe",
            required = true,
            maxLength = 100)
    private String username;

    @Schema(description = "Correo electrónico único dentro de la organización",
            example = "jdoe@techcorp.com",
            required = true,
            maxLength = 200)
    private String email;

    @Schema(description = "Contraseña del usuario — se almacena hasheada, nunca en texto plano",
            example = "SecurePass123!",
            required = true)
    private String password;

    @Schema(description = "Nombre completo del usuario",
            example = "John Doe",
            maxLength = 200)
    private String fullName;
}
