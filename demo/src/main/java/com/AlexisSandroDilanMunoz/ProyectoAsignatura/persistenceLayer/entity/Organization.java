package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "organization")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "domain", length = 200)
    private String domain;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status", length = 50)
    private String status;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<UserAccount> userAccounts;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Role> roles;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<DocumentType> documentTypes;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<DocumentState> documentStates;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Workflow> workflows;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<NotificationTemplate> notificationTemplates;
}
