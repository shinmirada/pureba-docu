package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.*;
import java.util.List;

public interface WorkflowService {
    WorkflowDto createWorkflow(WorkflowCreateDto createDto, Long organizationId);

    void activateWorkflow(Long workflowId, Long documentTypeId);

    void deactivateWorkflow(Long workflowId);

    void startWorkflowForDocument(Long documentId, Long documentTypeId);

    void completeTask(Long taskId, boolean approved, String comment);

    List<TaskDto> getPendingTasksForUser(Long userId);
}