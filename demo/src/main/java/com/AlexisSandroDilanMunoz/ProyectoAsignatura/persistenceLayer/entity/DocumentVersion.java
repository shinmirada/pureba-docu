package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_version")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id")
    private Long versionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private UserAccount uploadedBy;

    @Column(name = "version_number")
    private Integer versionNumber;

    @Column(name = "file_path", length = 1000)
    private String filePath;

    @Column(name = "file_name", length = 500)
    private String fileName;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "size")
    private Long size;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "checksum", length = 128)
    private String checksum;

    @Column(name = "is_active")
    private Boolean isActive;
}
