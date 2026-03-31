package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowStepRepository extends JpaRepository<WorkflowStep, Long> {

    // Listar todos los pasos de un workflow (RF28)
    List<WorkflowStep> findByWorkflowWorkflowId(Long workflowId);

    // Obtener los pasos ordenados — orden correcto de ejecución (RF31)
    List<WorkflowStep> findByWorkflowWorkflowIdOrderByStepOrderAsc(Long workflowId);

    // Buscar pasos asignados a un rol específico (RF29)
    List<WorkflowStep> findByAssignedRoleRoleId(Long roleId);

    // Obtener un paso específico según el orden dentro del workflow (RF31)
    Optional<WorkflowStep> findByWorkflowWorkflowIdAndStepOrder(Long workflowId, Integer stepOrder);

    // Validar si ya existe un orden de paso dentro del workflow — evitar duplicados (RF28)
    boolean existsByWorkflowWorkflowIdAndStepOrder(Long workflowId, Integer stepOrder);
}
