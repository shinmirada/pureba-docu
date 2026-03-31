package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "full_name", length = 200)
    private String fullName;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "uploadedBy", fetch = FetchType.LAZY)
    private List<DocumentVersion> documentVersions;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AuditLog> auditLogs;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Notification> notifications;
}
