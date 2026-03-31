package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "workflow_step")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long stepId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_role_id", nullable = false)
    private Role assignedRole;

    @Column(name = "step_order")
    private Integer stepOrder;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "step_type", length = 100)
    private String stepType;

    @Column(name = "required")
    private Boolean required;

    @Column(name = "timeout_days")
    private Integer timeoutDays;

    @OneToMany(mappedBy = "step", fetch = FetchType.LAZY)
    private List<Task> tasks;
}
