package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Notification;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationDAO {

    @Autowired
    private NotificationRepository notificationRepository;

    // RF37 / RF38 - Registrar el envío de una notificación
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    // RF37 / RF38 - Actualizar estado del envío (ENVIADO / FALLIDO)
    public Notification update(Notification notification) {
        return notificationRepository.save(notification);
    }

    // RF37 / RF38 - Obtener historial de notificaciones de un documento
    public List<Notification> findByDocument(Long documentId) {
        return notificationRepository.findByDocumentDocumentId(documentId);
    }

    // RF38 - Obtener notificaciones enviadas a un usuario (más reciente primero)
    public List<Notification> findByUser(Long userId) {
        return notificationRepository.findByUserUserIdOrderBySentAtDesc(userId);
    }

    // RF39 - Obtener notificaciones de un usuario por estado (p.ej. pendientes/fallidas)
    public List<Notification> findByUserAndStatus(Long userId, String status) {
        return notificationRepository.findByUserUserIdAndStatus(userId, status);
    }

    // RF37 - Obtener notificaciones de la organización filtradas por estado
    public List<Notification> findByOrganizationAndStatus(Long organizationId, String status) {
        return notificationRepository.findByDocumentOrganizationOrganizationIdAndStatus(organizationId, status);
    }

    // RF38 - Historial de notificaciones de un documento filtradas por estado
    public List<Notification> findByDocumentAndStatus(Long documentId, String status) {
        return notificationRepository.findByDocumentDocumentIdAndStatus(documentId, status);
    }
}
