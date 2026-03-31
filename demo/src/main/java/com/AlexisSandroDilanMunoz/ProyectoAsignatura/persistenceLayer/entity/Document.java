package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserAccount createdBy;

    @Column(name = "title", length = 300)
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "current_state", length = 50)
    private String currentState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "reference_code", length = 100)
    private String referenceCode;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<DocumentVersion> documentVersions;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<Notification> notifications;
}
