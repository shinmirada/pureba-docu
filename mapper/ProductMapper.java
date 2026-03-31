package com.rafaelperez.tiendaonline.persistenceLayer.mapper;

import com.rafaelperez.tiendaonline.businessLayer.dto.ProductCreateDTO;
import com.rafaelperez.tiendaonline.businessLayer.dto.ProductDTO;
import com.rafaelperez.tiendaonline.businessLayer.dto.ProductUpdateDTO;
import com.rafaelperez.tiendaonline.persistenceLayer.entity.ProductEntity;
import com.rafaelperez.tiendaonline.persistenceLayer.entity.SellerEntity;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para conversiones entre ProductEntity y DTOs usando MapStruct
 *
 * COMPLEJIDAD ADICIONAL:
 * - ProductEntity tiene relación con SellerEntity
 * - ProductDTO incluye información denormalizada del vendedor (sellerName, sellerEmail)
 * - Necesitamos mapear sellerId ↔ sellerEntity
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ProductMapper {

    /**
     * Convierte ProductEntity a ProductDTO (LECTURA)
     *
     * MAPEOS PERSONALIZADOS:
     * - sellerId: Se extrae de sellerEntity.id
     * - sellerName: Se extrae de sellerEntity.name
     * - sellerEmail: Se extrae de sellerEntity.email
     *
     * MAPEO AUTOMÁTICO:
     * - id, name, description, price, stock, createdAt, updatedAt
     *
     * EXPRESSION: Permite usar código Java para mapeos complejos
     */
    @Mapping(target = "sellerId", source = "sellerEntity.id")
    @Mapping(target = "sellerName", source = "sellerEntity.name")
    @Mapping(target = "sellerEmail", source = "sellerEntity.email")
    ProductDTO toDTO(ProductEntity entity);

    /**
     * Convierte lista de ProductEntity a lista de ProductDTO
     */
    List<ProductDTO> toDTOList(List<ProductEntity> entities);

    /**
     * Convierte ProductCreateDTO a ProductEntity (CREAR)
     *
     * MAPEO COMPLEJO:
     * - sellerId del DTO se debe convertir a SellerEntity
     * - Usamos método auxiliar createSellerEntityFromId()
     *
     * CAMPOS IGNORADOS:
     * - id: Se genera automáticamente
     * - createdAt/updatedAt: Los maneja JPA
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sellerEntity", source = "sellerId", qualifiedByName = "createSellerEntityFromId")
    ProductEntity toEntity(ProductCreateDTO createDTO);

    /**
     * Actualiza ProductEntity existente con datos de ProductUpdateDTO
     *
     * NOTA IMPORTANTE:
     * - NO actualizamos sellerEntity (producto no cambia de vendedor)
     * - Estrategia IGNORE para valores null (actualización parcial)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sellerEntity", ignore = true)  // No se puede cambiar vendedor
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ProductUpdateDTO updateDTO, @MappingTarget ProductEntity entity);

    /**
     * Convierte ProductDTO a ProductEntity (para casos especiales)
     *
     * USADO EN: Testing o casos donde necesites la conversión inversa
     */
    @Mapping(target = "sellerEntity", source = "sellerId", qualifiedByName = "createSellerEntityFromId")
    ProductEntity toEntity(ProductDTO dto);

    /**
     * MÉTODO AUXILIAR: Crea SellerEntity con solo el ID
     *
     * ¿POR QUÉ ESTE MÉTODO?
     * - Cuando creamos un producto, solo tenemos el sellerId
     * - No necesitamos cargar todo el vendedor de BD, solo la referencia
     * - JPA manejará la relación correctamente con solo el ID
     *
     * @Named: Permite referenciar este método en otros mapeos
     */
    @Named("createSellerEntityFromId")
    default SellerEntity createSellerEntityFromId(Long sellerId) {
        if (sellerId == null) {
            return null;
        }
        SellerEntity seller = new SellerEntity();
        seller.setId(sellerId);
        return seller;
    }

    /**
     * MÉTODO AUXILIAR: Extrae el ID del SellerEntity
     *
     * USADO EN: Casos donde necesites obtener solo el ID del vendedor
     */
    @Named("extractSellerIdFromEntity")
    default Long extractSellerIdFromEntity(SellerEntity sellerEntity) {
        return sellerEntity != null ? sellerEntity.getId() : null;
    }
}
