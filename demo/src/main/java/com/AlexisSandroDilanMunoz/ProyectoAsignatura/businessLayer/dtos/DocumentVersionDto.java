package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de una versión de documento")
public class DocumentVersionDto {

    @Schema(description = "ID único de la versión", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long versionId;

    @Schema(description = "ID del documento al que pertenece esta versión",
            example = "10", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long documentId;

    @Schema(description = "Título del documento padre", example = "Contrato Marco 2024",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String documentTitle;

    @Schema(description = "ID del usuario que subió el archivo",
            example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    private Long uploadedById;

    @Schema(description = "Nombre del usuario que subió el archivo",
            example = "Jane Smith", accessMode = Schema.AccessMode.READ_ONLY)
    private String uploadedByName;

    @Schema(description = "Número de versión secuencial", example = "3",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Integer versionNumber;

    @Schema(description = "Ruta interna del archivo en el sistema de almacenamiento",
            example = "/storage/org1/docs/10/v3.pdf",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String filePath;

    @Schema(description = "Nombre original del archivo", example = "contrato_marco_v3.pdf",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String fileName;

    @Schema(description = "Tipo MIME del archivo", example = "application/pdf",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String mimeType;

    @Schema(description = "Tamaño del archivo en bytes", example = "204800",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long size;

    @Schema(description = "Fecha y hora de carga del archivo",
            example = "2024-06-10T14:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime uploadedAt;

    @Schema(description = "Hash de verificación de integridad del archivo (SHA-256)",
            example = "a3f1b2c4...", accessMode = Schema.AccessMode.READ_ONLY)
    private String checksum;

    @Schema(description = "Indica si esta es la versión activa del documento",
            example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean isActive;
}
