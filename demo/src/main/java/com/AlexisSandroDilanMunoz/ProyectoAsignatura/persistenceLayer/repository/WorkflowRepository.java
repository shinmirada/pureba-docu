package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    // Listar workflows por organización (RF28, RF44)
    List<Workflow> findByOrganizationOrganizationId(Long organizationId);

    // Buscar workflow por id validando que pertenezca a la organización (RF28)
    Optional<Workflow> findByWorkflowIdAndOrganizationOrganizationId(Long workflowId, Long organizationId);

    // Listar workflows por tipo documental dentro de la organización (RF32, RF44)
    List<Workflow> findByOrganizationOrganizationIdAndDocumentTypeDocumentTypeId(
            Long organizationId, Long documentTypeId);

    // Listar solo workflows activos de la organización (RF44)
    List<Workflow> findByOrganizationOrganizationIdAndIsActiveTrue(Long organizationId);

    // Obtener el workflow activo de un tipo documental — iniciar flujo automáticamente (RF32)
    Optional<Workflow> findByOrganizationOrganizationIdAndDocumentTypeDocumentTypeIdAndIsActiveTrue(
            Long organizationId, Long documentTypeId);

    // Validar si ya existe un workflow con ese nombre en la organización (RF28)
    boolean existsByOrganizationOrganizationIdAndName(Long organizationId, String name);
}
