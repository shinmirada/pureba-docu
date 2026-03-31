package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.WorkflowStep;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkflowStepDAO {

    @Autowired
    private WorkflowStepRepository workflowStepRepository;

    // RF28 - Crear paso del flujo
    public WorkflowStep save(WorkflowStep workflowStep) {
        return workflowStepRepository.save(workflowStep);
    }

    // RF28 - Actualizar paso del flujo
    public WorkflowStep update(WorkflowStep workflowStep) {
        return workflowStepRepository.save(workflowStep);
    }

    // RF28 - Eliminar paso del flujo
    public void delete(WorkflowStep workflowStep) {
        workflowStepRepository.delete(workflowStep);
    }

    // RF28 / RF31 - Obtener todos los pasos del flujo
    public List<WorkflowStep> findByWorkflow(Long workflowId) {
        return workflowStepRepository.findByWorkflowWorkflowId(workflowId);
    }

    // RF31 - Obtener pasos del flujo en orden ascendente de ejecución
    public List<WorkflowStep> findByWorkflowOrdered(Long workflowId) {
        return workflowStepRepository.findByWorkflowWorkflowIdOrderByStepOrderAsc(workflowId);
    }

    // RF31 - Obtener un paso específico según su número de orden dentro del workflow
    public Optional<WorkflowStep> findByWorkflowAndOrder(Long workflowId, Integer stepOrder) {
        return workflowStepRepository.findByWorkflowWorkflowIdAndStepOrder(workflowId, stepOrder);
    }

    // RF29 - Obtener pasos asignados a un rol específico
    public List<WorkflowStep> findByAssignedRole(Long roleId) {
        return workflowStepRepository.findByAssignedRoleRoleId(roleId);
    }

    // RF28 - Verificar si ya existe un orden de paso dentro del workflow (evitar duplicados)
    public boolean existsByWorkflowAndOrder(Long workflowId, Integer stepOrder) {
        return workflowStepRepository.existsByWorkflowWorkflowIdAndStepOrder(workflowId, stepOrder);
    }

    // Buscar paso por ID
    public Optional<WorkflowStep> findById(Long stepId) {
        return workflowStepRepository.findById(stepId);
    }
}
