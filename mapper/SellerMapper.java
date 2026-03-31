package com.rafaelperez.tiendaonline.persistenceLayer.mapper;

import com.rafaelperez.tiendaonline.businessLayer.dto.SellerCreateDTO;
import com.rafaelperez.tiendaonline.businessLayer.dto.SellerDTO;
import com.rafaelperez.tiendaonline.businessLayer.dto.SellerUpdateDTO;
import com.rafaelperez.tiendaonline.persistenceLayer.entity.SellerEntity;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para conversiones entre SellerEntity y DTOs usando MapStruct
 *
 * ¿QUÉ HACE MAPSTRUCT?
 * - Genera automáticamente el código de mapeo en tiempo de compilación
 * - Mapea campos con el mismo nombre automáticamente
 * - Permite configurar mapeos personalizados con anotaciones
 * - Optimiza performance (no usa reflection)
 *
 * CONFIGURACIÓN:
 * - componentModel = "spring": Crea el mapper como @Component de Spring
 * - unmappedTargetPolicy = WARN: Avisa si hay campos sin mapear
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface SellerMapper {

    /**
     * Convierte SellerEntity a SellerDTO (LECTURA)
     *
     * MAPEO AUTOMÁTICO:
     * - Todos los campos con mismo nombre se mapean automáticamente
     * - id, name, email, phone, address, createdAt, updatedAt
     *
     * CAMPOS IGNORADOS:
     * - products: No los incluimos en el DTO para evitar referencia circular
     */
    SellerDTO toDTO(SellerEntity entity);

    /**
     * Convierte lista de SellerEntity a lista de SellerDTO
     */
    List<SellerDTO> toDTOList(List<SellerEntity> entities);

    /**
     * Convierte SellerCreateDTO a SellerEntity (CREAR)
     *
     * CAMPOS IGNORADOS:
     * - id: Se genera automáticamente en BD
     * - createdAt/updatedAt: Los maneja automáticamente JPA
     * - products: Lista vacía por defecto
     *
     * MAPEO AUTOMÁTICO:
     * - name, email, phone, address se mapean automáticamente
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    SellerEntity toEntity(SellerCreateDTO createDTO);

    /**
     * Actualiza SellerEntity existente con datos de SellerUpdateDTO
     *
     * ¿POR QUÉ @MappingTarget?
     * - Actualiza la entidad existente en lugar de crear una nueva
     * - Preserva campos que no están en el UpdateDTO (como id, email, fechas)
     * - Permite actualización parcial (solo campos no-null del DTO)
     *
     * ESTRATEGIA NULL_VALUE_PROPERTY_MAPPING_STRATEGY.IGNORE:
     * - Si un campo en UpdateDTO es null, no actualiza ese campo en la entity
     * - Permite actualización parcial (PATCH)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)  // Email no se puede cambiar
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(SellerUpdateDTO updateDTO, @MappingTarget SellerEntity entity);

    /**
     * Convierte SellerDTO a SellerEntity (si necesario para algún caso especial)
     *
     * NOTA: Normalmente no se usa, pero puede ser útil para testing
     * o casos especiales donde necesites convertir de DTO a Entity
     */
    @Mapping(target = "products", ignore = true)
    SellerEntity toEntity(SellerDTO dto);
}
