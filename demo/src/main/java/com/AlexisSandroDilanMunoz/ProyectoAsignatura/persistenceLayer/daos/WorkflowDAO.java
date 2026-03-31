package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Workflow;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkflowDAO {

    @Autowired
    private WorkflowRepository workflowRepository;

    // RF28 - Crear nuevo flujo de aprobación
    public Workflow save(Workflow workflow) {
        return workflowRepository.save(workflow);
    }

    // RF44 - Actualizar flujo (activar/desactivar)
    public Workflow update(Workflow workflow) {
        return workflowRepository.save(workflow);
    }

    // RF44 - Listar todos los flujos de la organización
    public List<Workflow> findByOrganization(Long organizationId) {
        return workflowRepository.findByOrganizationOrganizationId(organizationId);
    }

    // RF44 - Listar flujos activos de la organización
    public List<Workflow> findActiveByOrganization(Long organizationId) {
        return workflowRepository.findByOrganizationOrganizationIdAndIsActiveTrue(organizationId);
    }

    // RF32 - Obtener el flujo activo para un tipo documental dentro de la organización
    public Optional<Workflow> findActiveByDocumentTypeAndOrganization(Long organizationId, Long documentTypeId) {
        return workflowRepository.findByOrganizationOrganizationIdAndDocumentTypeDocumentTypeIdAndIsActiveTrue(organizationId, documentTypeId);
    }

    // RF44 - Listar flujos de la organización por tipo documental
    public List<Workflow> findByOrganizationAndDocumentType(Long organizationId, Long documentTypeId) {
        return workflowRepository.findByOrganizationOrganizationIdAndDocumentTypeDocumentTypeId(organizationId, documentTypeId);
    }

    // RF44 - Buscar flujo verificando que pertenece a la organización
    public Optional<Workflow> findByIdAndOrganization(Long workflowId, Long organizationId) {
        return workflowRepository.findByWorkflowIdAndOrganizationOrganizationId(workflowId, organizationId);
    }

    // RF28 - Verificar si ya existe un flujo con ese nombre en la organización
    public boolean existsByNameInOrganization(Long organizationId, String name) {
        return workflowRepository.existsByOrganizationOrganizationIdAndName(organizationId, name);
    }

    // Buscar flujo por ID
    public Optional<Workflow> findById(Long workflowId) {
        return workflowRepository.findById(workflowId);
    }
}
