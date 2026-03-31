package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_state", uniqueConstraints = @UniqueConstraint(columnNames = { "organization_id", "code" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Long stateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "label", length = 150)
    private String label;

    @Column(name = "state_order")
    private Integer stateOrder;
}
