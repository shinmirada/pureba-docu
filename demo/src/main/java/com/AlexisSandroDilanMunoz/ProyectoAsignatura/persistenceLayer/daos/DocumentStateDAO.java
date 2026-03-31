package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentState;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.DocumentStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocumentStateDAO {

    @Autowired
    private DocumentStateRepository documentStateRepository;

    // RF41 - Crear nuevo estado de documento
    public DocumentState save(DocumentState documentState) {
        return documentStateRepository.save(documentState);
    }

    // RF41 - Actualizar estado existente
    public DocumentState update(DocumentState documentState) {
        return documentStateRepository.save(documentState);
    }

    // RF41 - Eliminar estado
    public void delete(DocumentState documentState) {
        documentStateRepository.delete(documentState);
    }

    // RF41 - Listar estados de la organización ordenados por stateOrder ASC
    public List<DocumentState> findByOrganizationOrdered(Long organizationId) {
        return documentStateRepository.findByOrganizationOrganizationIdOrderByStateOrderAsc(organizationId);
    }

    // RF41 - Listar todos los estados de la organización
    public List<DocumentState> findByOrganization(Long organizationId) {
        return documentStateRepository.findByOrganizationOrganizationId(organizationId);
    }

    // RF41 - Verificar unicidad de código en la organización
    public boolean existsByCodeInOrganization(Long organizationId, String code) {
        return documentStateRepository.existsByOrganizationOrganizationIdAndCode(organizationId, code);
    }

    // RF30 - Buscar estado por código dentro de la organización
    public Optional<DocumentState> findByCodeAndOrganization(Long organizationId, String code) {
        return documentStateRepository.findByOrganizationOrganizationIdAndCode(organizationId, code);
    }

    // Buscar estado por ID (hereda de JpaRepository)
    public Optional<DocumentState> findById(Long stateId) {
        return documentStateRepository.findById(stateId);
    }
}
