package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Notificaciones de un usuario ordenadas por fecha (RF37, RF38, RF39)
    List<Notification> findByUserUserIdOrderBySentAtDesc(Long userId);

    // Notificaciones asociadas a un documento (RF38)
    List<Notification> findByDocumentDocumentId(Long documentId);

    // Notificaciones por estado dentro de la organización (RF37 — historial de envíos)
    List<Notification> findByDocumentOrganizationOrganizationIdAndStatus(Long organizationId, String status);

    // Notificaciones de un usuario por estado (RF39 — alertas pendientes/fallidas)
    List<Notification> findByUserUserIdAndStatus(Long userId, String status);

    // Historial de notificaciones de un documento por estado (RF37 — registro envío exitoso/fallido)
    List<Notification> findByDocumentDocumentIdAndStatus(Long documentId, String status);
}
