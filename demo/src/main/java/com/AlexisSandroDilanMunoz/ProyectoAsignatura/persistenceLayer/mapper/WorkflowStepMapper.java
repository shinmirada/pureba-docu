package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.WorkflowStepCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.WorkflowStepDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Role;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Workflow;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.WorkflowStep;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para WorkflowStep ↔ DTOs
 *
 * MAPEOS CON DOT-NOTATION:
 * - workflowId       ← entity.workflow.workflowId
 * - workflowName     ← entity.workflow.name
 * - assignedRoleId   ← entity.assignedRole.roleId
 * - assignedRoleName ← entity.assignedRole.name
 *
 * MAPEOS AUTOMÁTICOS:
 * - stepId, stepOrder, name, stepType, required, timeoutDays
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface WorkflowStepMapper {

    @Mapping(target = "workflowId",       source = "workflow.workflowId")
    @Mapping(target = "workflowName",     source = "workflow.name")
    @Mapping(target = "assignedRoleId",   source = "assignedRole.roleId")
    @Mapping(target = "assignedRoleName", source = "assignedRole.name")
    WorkflowStepDto toDTO(WorkflowStep entity);

    @Mapping(target = "workflowId",       source = "workflow.workflowId")
    @Mapping(target = "workflowName",     source = "workflow.name")
    @Mapping(target = "assignedRoleId",   source = "assignedRole.roleId")
    @Mapping(target = "assignedRoleName", source = "assignedRole.name")
    List<WorkflowStepDto> toDTOList(List<WorkflowStep> entities);

    /**
     * WorkflowStepCreateDto → Entidad (se usa en WorkflowMapper al crear el flujo — RF28).
     * stepId lo genera la BD.
     * workflow lo asigna WorkflowMapper/Service después de crear el Workflow padre.
     * assignedRole se construye con el ID — el Service valida su existencia.
     */
    @Mapping(target = "stepId",       ignore = true)
    @Mapping(target = "workflow",     ignore = true)  // Lo asigna el Service tras crear el Workflow
    @Mapping(target = "assignedRole", source = "assignedRoleId", qualifiedByName = "roleFromId")
    @Mapping(target = "tasks",        ignore = true)
    WorkflowStep toEntityFromCreate(WorkflowStepCreateDto createDto);

    /** Lista de WorkflowStepCreateDto → lista de entidades */
    @Mapping(target = "stepId",       ignore = true)
    @Mapping(target = "workflow",     ignore = true)
    @Mapping(target = "assignedRole", source = "assignedRoleId", qualifiedByName = "roleFromId")
    @Mapping(target = "tasks",        ignore = true)
    List<WorkflowStep> toEntityListFromCreate(List<WorkflowStepCreateDto> createDtos);

    /** Referencia a Role usando solo el ID */
    @Named("roleFromId")
    default Role roleFromId(Long roleId) {
        if (roleId == null) return null;
        Role role = new Role();
        role.setRoleId(roleId);
        return role;
    }

    /** Referencia a Workflow usando solo el ID (para casos donde sea necesario) */
    @Named("workflowFromId")
    default Workflow workflowFromId(Long workflowId) {
        if (workflowId == null) return null;
        Workflow wf = new Workflow();
        wf.setWorkflowId(workflowId);
        return wf;
    }
}
