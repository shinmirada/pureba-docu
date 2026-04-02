package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

public interface NotificationService {
    void sendDocumentCreatedNotification(Long documentId, Long creatorId);

    void sendDocumentStateChangedNotification(Long documentId, String newState, String comment);

    void sendTaskAssignedNotification(Long taskId, Long userId);
}