package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "workflow")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_id")
    private Long workflowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "config_json", columnDefinition = "json")
    private String configJson;

    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    private List<WorkflowStep> workflowSteps;
}
