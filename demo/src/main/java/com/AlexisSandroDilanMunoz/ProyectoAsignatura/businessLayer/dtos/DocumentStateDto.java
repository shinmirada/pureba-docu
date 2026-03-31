package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del estado de un documento")
public class DocumentStateDto {

    @Schema(description = "ID único del estado", example = "1", accessMode = Schema.AccessMode.READ_ONLY,required = true)
    private Long stateId;

    @Schema(description = "Organización a la que pertenece el estado", example = "Organization{id=1, name='Tech Corp', ...}", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Organization organization;

    @Schema(description = "Código del estado", example = "DRAFT", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private String code;

    @Schema(description = "Etiqueta del estado", example = "Borrador")
    private String label;

    @Schema(description = "Orden del estado", example = "1")
    private Integer stateOrder;

}
