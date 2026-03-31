package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "document_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_type_id")
    private Long documentTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "metadata_schema", columnDefinition = "json")
    private String metadataSchema;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "documentType", fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "documentType", fetch = FetchType.LAZY)
    private List<Workflow> workflows;
}
