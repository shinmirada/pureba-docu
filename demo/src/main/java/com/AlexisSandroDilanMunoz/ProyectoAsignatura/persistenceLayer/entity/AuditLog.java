package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @Column(name = "action_type", length = 100)
    private String actionType;

    @Column(name = "action_detail", columnDefinition = "text")
    private String actionDetail;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "ip_address", length = 100)
    private String ipAddress;

    @Column(name = "metadata", columnDefinition = "json")
    private String metadata;
}
