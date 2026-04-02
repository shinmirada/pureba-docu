package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.NotificationTemplateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.NotificationTemplate;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface NotificationTemplateMapper {

    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    NotificationTemplateDto toDTO(NotificationTemplate entity);

    @Mapping(target = "organizationId",   source = "organization.organizationId")
    @Mapping(target = "organizationName", source = "organization.name")
    List<NotificationTemplateDto> toDTOList(List<NotificationTemplate> entities);

    /**
     * DTO → Entidad (crear plantilla — RF40).
     */
    @Mapping(target = "templateId",    ignore = true)
    @Mapping(target = "organization",  ignore = true)
    @Mapping(target = "notifications", ignore = true)
    NotificationTemplate toEntity(NotificationTemplateDto dto);

    /**
     * Actualización parcial de plantilla (RF43).
     * name no se cambia: es el identificador del evento (ej: "DOCUMENTO_CREADO").
     */
    @Mapping(target = "templateId",    ignore = true)
    @Mapping(target = "organization",  ignore = true)
    @Mapping(target = "name",          ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(NotificationTemplateDto dto, @MappingTarget NotificationTemplate entity);

    /** Referencia a Organization usando solo el ID */
    @Named("organizationFromId")
    default Organization organizationFromId(Long organizationId) {
        if (organizationId == null) return null;
        Organization org = new Organization();
        org.setOrganizationId(organizationId);
        return org;
    }
}
