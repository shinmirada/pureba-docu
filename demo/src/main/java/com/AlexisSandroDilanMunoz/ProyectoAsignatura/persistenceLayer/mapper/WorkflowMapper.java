package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.WorkflowCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.WorkflowDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentType;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Workflow;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para Workflow ↔ DTOs
 *
 * MAPEOS CON DOT-NOTATION:
 * - organizationId   ← entity.organization.organizationId
 * - organizationName ← entity.organization.name
 * - documentTypeId   ← entity.documentType.documentTypeId
 * - documentTypeName ← entity.documentType.name
 *
 * MAPEOS CON uses (WorkflowStepMapper convierte automáticamente las listas):
 * - steps ← entity.workflowSteps  (List<WorkflowStep> → List<WorkflowStepDto>)
 *
 * MAPEOS AUTOMÁTICOS:
 * - workflowId, name, description, isActive, configJson
 *
 * NOTA sobre el campo steps vs workflowSteps:
 * La entidad tiene: List<WorkflowStep> workflowSteps
 * El DTO tiene:     List<WorkflowStepDto> steps
 * → Nombres diferentes → necesita @Mapping explícito.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = { WorkflowStepMapper.class }  // MapStruct usa WorkflowStepMapper para las listas
)
public interface WorkflowMapper {

    /**
     * Entidad → DTO de respuesta.
     * WorkflowStepMapper.toDTO se aplica automáticamente a workflowSteps → steps
     * gracias al parámetro uses = {WorkflowStepMapper.class}.
     */
    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "documentTypeId",   source = "documentType.documentTypeId")
    @Mapping(target = "documentTypeName", source = "documentType.name")
    @Mapping(target = "steps",            source = "workflowSteps")
    WorkflowDto toDTO(Workflow entity);

    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "documentTypeId",   source = "documentType.documentTypeId")
    @Mapping(target = "documentTypeName", source = "documentType.name")
    @Mapping(target = "steps",            source = "workflowSteps")
    List<WorkflowDto> toDTOList(List<Workflow> entities);

    /**
     * WorkflowCreateDto → Entidad (RF28 — crear flujo).
     *
     * workflowSteps: WorkflowStepMapper.toEntityListFromCreate convierte los pasos.
     * El Service luego recorre los pasos e inyecta la referencia al Workflow padre.
     *
     * CAMPOS IGNORADOS:
     * - workflowId:  lo genera la BD
     * - organization: el Service la inyecta desde el JWT
     * - isActive:    el Service lo inicializa en false (se activa con endpoint dedicado)
     * - configJson:  opcional, no está en CreateDto
     */
    @Mapping(target = "workflowId",    ignore = true)
    @Mapping(target = "organization",  ignore = true)
    @Mapping(target = "documentType",  source = "documentTypeId", qualifiedByName = "documentTypeFromId")
    @Mapping(target = "isActive",      ignore = true)
    @Mapping(target = "configJson",    ignore = true)
    @Mapping(target = "workflowSteps", source = "steps")  // WorkflowStepMapper se encarga de la conversión
    Workflow toEntity(WorkflowCreateDto createDto);

    /**
     * Actualización parcial del flujo (nombre, descripción, configJson — RF44).
     * documentType no se cambia: un flujo ya asociado no debería reasignarse.
     * isActive se gestiona con su propio endpoint de activar/desactivar.
     */
    @Mapping(target = "workflowId",    ignore = true)
    @Mapping(target = "organization",  ignore = true)
    @Mapping(target = "documentType",  ignore = true)
    @Mapping(target = "isActive",      ignore = true)
    @Mapping(target = "workflowSteps", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(WorkflowDto dto, @MappingTarget Workflow entity);

    /** Referencia a DocumentType usando solo el ID */
    @Named("documentTypeFromId")
    default DocumentType documentTypeFromId(Long documentTypeId) {
        if (documentTypeId == null) return null;
        DocumentType dt = new DocumentType();
        dt.setDocumentTypeId(documentTypeId);
        return dt;
    }

    /** Referencia a Organization usando solo el ID */
    @Named("organizationFromId")
    default Organization organizationFromId(Long organizationId) {
        if (organizationId == null) return null;
        Organization org = new Organization();
        org.setOrganizationId(organizationId);
        return org;
    }
}
