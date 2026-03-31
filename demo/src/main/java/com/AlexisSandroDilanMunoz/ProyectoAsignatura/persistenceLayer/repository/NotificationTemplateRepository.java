package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

    // Listar plantillas por organización (RF40, RF43)
    List<NotificationTemplate> findByOrganizationOrganizationId(Long organizationId);

    // Buscar plantilla por id validando que pertenezca a la organización (RF43)
    Optional<NotificationTemplate> findByTemplateIdAndOrganizationOrganizationId(
            Long templateId, Long organizationId);

    // Buscar plantilla por nombre dentro de la organización (RF40)
    Optional<NotificationTemplate> findByOrganizationOrganizationIdAndName(
            Long organizationId, String name);

    // Listar solo plantillas activas (RF40, RF43)
    List<NotificationTemplate> findByOrganizationOrganizationIdAndIsActiveTrue(Long organizationId);

    // Buscar plantilla activa por nombre — se usa como identificador del evento (RF37, RF38, RF39)
    Optional<NotificationTemplate> findByOrganizationOrganizationIdAndNameAndIsActiveTrue(
            Long organizationId, String name);

    // Validar si ya existe una plantilla con ese nombre (RF40)
    boolean existsByOrganizationOrganizationIdAndName(Long organizationId, String name);
}
