package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "notification_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long templateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "subject", length = 300)
    private String subject;

    @Column(name = "body_html", columnDefinition = "text")
    private String bodyHtml;

    @Column(name = "body_text", columnDefinition = "text")
    private String bodyText;

    @Column(name = "variables_json", columnDefinition = "json")
    private String variablesJson;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
    private List<Notification> notifications;
}
