package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informacion de un registro de auditoria")
public class AuditLogDto {

    @Schema(description = "ID único del producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long auditId;
  
    @Schema(description = "Documento asociado a la acción", example = "Documento{id=1, title='Informe de Proyecto', ...}",required = true,accessMode = Schema.AccessMode.READ_ONLY)
    private Document document;

    @Schema(description = "Usuario que realizó la acción", example = "UserAccount{id=1, username='john_doe', ...}" , required = true,accessMode = Schema.AccessMode.READ_ONLY)
    private UserAccount user;

    @Schema(description = "Tipo de acción realizada", example = "CREATED, UPDATED, DELETED")
    private String actionType;
    
    @Schema(description = "Detalles adicionales sobre la acción", example = "El usuario creó un nuevo documento con título 'Informe de Proyecto'")
    private String actionDetail;
    
    @Schema(description = "Fecha y hora en que se realizó la acción", example = "2024-06-01T12:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "Dirección IP del usuario", example = "192.168.1.1", accessMode = Schema.AccessMode.READ_ONLY)
    private String ipAddress;

    @Schema(description = "Metadatos adicionales", example = "{'browser': 'Chrome', 'os': 'Windows'}", accessMode = Schema.AccessMode.READ_ONLY)
    private String metadata;
    

}
