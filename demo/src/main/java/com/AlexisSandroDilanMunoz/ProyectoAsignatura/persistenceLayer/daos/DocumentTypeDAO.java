package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentType;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocumentTypeDAO {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    // RF24 - Crear nuevo tipo documental
    public DocumentType save(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    // RF25 - Actualizar tipo documental
    public DocumentType update(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    // RF26 - Eliminar tipo documental
    public void delete(DocumentType documentType) {
        documentTypeRepository.delete(documentType);
    }

    // RF42 - Listar todos los tipos documentales de la organización
    public List<DocumentType> findByOrganization(Long organizationId) {
        return documentTypeRepository.findByOrganizationOrganizationId(organizationId);
    }

    // RF27 - Listar solo los tipos activos (para selector al crear documentos)
    public List<DocumentType> findActiveByOrganization(Long organizationId) {
        return documentTypeRepository.findByOrganizationOrganizationIdAndActiveTrue(organizationId);
    }

    // RF25 / RF26 - Buscar tipo verificando que pertenece a la organización
    public Optional<DocumentType> findByIdAndOrganization(Long documentTypeId, Long organizationId) {
        return documentTypeRepository.findByDocumentTypeIdAndOrganizationOrganizationId(documentTypeId, organizationId);
    }

    // RF24 - Verificar unicidad de nombre en la organización
    public boolean existsByNameInOrganization(Long organizationId, String name) {
        return documentTypeRepository.existsByOrganizationOrganizationIdAndName(organizationId, name);
    }

    // RF24 - Buscar tipo documental por nombre dentro de la organización
    public Optional<DocumentType> findByNameAndOrganization(Long organizationId, String name) {
        return documentTypeRepository.findByOrganizationOrganizationIdAndName(organizationId, name);
    }

    // Buscar tipo documental por ID (hereda de JpaRepository)
    public Optional<DocumentType> findById(Long documentTypeId) {
        return documentTypeRepository.findById(documentTypeId);
    }
}
