package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.AuditLog;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentType;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentVersion;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Notification;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Task;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un documento")
public class DocumentDto {

    @Schema(description = "ID único del documento", example = "1", accessMode = Schema.AccessMode.READ_ONLY, required = true)
    private Long documentId;

    @Schema(description = "Organización a la que pertenece el documento", example = "Organization{id=1, name='Tech Corp', ...}", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Organization organization;

    @Schema(description = "Tipo de documento", example = "DocumentType{id=1, name='Informe', ...}", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private DocumentType documentType;

    @Schema(description = "Usuario que creó el documento", example = "UserAccount{id=1, username='john_doe', ...}", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private UserAccount createdBy;

    @Schema(description = "Título del documento", example = "Informe de ventas Q4")
    private String title;

    @Schema(description = "Descripción del documento", example = "Informe detallado de las ventas del cuatrimestre")
    private String description;

    @Schema(description = "Estado actual del documento", example = "BORRADOR, EN_REVISIÓN, APROBADO")
    private String currentState;
    
    @Schema(description = "Fecha de creación del documento", example = "2024-06-01T12:00:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha de la última actualización del documento", example = "2024-06-01T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Código de referencia del documento", example = "DOC-2024-001", accessMode = Schema.AccessMode.READ_ONLY)
    private String referenceCode;
    
    @Schema(description = "Versiones del documento", required = false)
    private List<DocumentVersion> documentVersions;
    
    @Schema(description = "Tareas asociadas al documento", required = false)
    private List<Task> tasks;

    @Schema(description = "Registros de auditoría relacionados con el documento", required = false)
    private List<AuditLog> auditLogs;

    @Schema(description = "Notificaciones relacionadas con el documento", required = false)
    private List<Notification> notifications;

}
