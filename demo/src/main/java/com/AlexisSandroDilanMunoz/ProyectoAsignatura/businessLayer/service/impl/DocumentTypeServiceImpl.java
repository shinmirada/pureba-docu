package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentTypeDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.SecurityContextHelper;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.DocumentTypeService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentType;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.DocumentTypeRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;
    private final OrganizationRepository organizationRepository;
    private final SecurityContextHelper securityHelper;

    @Override
    public DocumentTypeDto createDocumentType(DocumentTypeDto dto, Long organizationId) {
        log.info("Creando tipo documental '{}' para organización {}", dto.getName(), organizationId);

        if (documentTypeRepository.existsByOrganizationOrganizationIdAndName(organizationId, dto.getName())) {
            throw new IllegalArgumentException("Ya existe un tipo documental con ese nombre en esta organización");
        }

        Organization org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));

        DocumentType documentType = new DocumentType();
        documentType.setName(dto.getName());
        documentType.setMetadataSchema(dto.getMetadataSchema());
        documentType.setActive(dto.getActive() != null ? dto.getActive() : true);
        documentType.setOrganization(org);

        DocumentType saved = documentTypeRepository.save(documentType);
        return toDto(saved);
    }

    @Override
    public DocumentTypeDto updateDocumentType(Long typeId, DocumentTypeDto dto) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        DocumentType documentType = documentTypeRepository.findByDocumentTypeIdAndOrganizationOrganizationId(typeId, currentOrg)
                .orElseThrow(() -> new RuntimeException("Tipo documental no encontrado"));

        if (dto.getName() != null && !dto.getName().equals(documentType.getName()) &&
                documentTypeRepository.existsByOrganizationOrganizationIdAndName(currentOrg, dto.getName())) {
            throw new IllegalArgumentException("Nombre ya en uso");
        }

        if (dto.getName() != null)
            documentType.setName(dto.getName());
        if (dto.getMetadataSchema() != null)
            documentType.setMetadataSchema(dto.getMetadataSchema());
        if (dto.getActive() != null)
            documentType.setActive(dto.getActive());

        return toDto(documentTypeRepository.save(documentType));
    }

    @Override
    public void deleteDocumentType(Long typeId) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        DocumentType documentType = documentTypeRepository.findByDocumentTypeIdAndOrganizationOrganizationId(typeId, currentOrg)
                .orElseThrow(() -> new RuntimeException("Tipo documental no encontrado"));

        if ((documentType.getDocuments() != null && !documentType.getDocuments().isEmpty()) || 
            (documentType.getWorkflows() != null && !documentType.getWorkflows().isEmpty())) {
            throw new RuntimeException("No se puede eliminar porque existen documentos o flujos vinculados");
        }
        
        documentTypeRepository.delete(documentType);
    }

    @Override
    public void activateDocumentType(Long typeId) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        DocumentType documentType = documentTypeRepository.findByDocumentTypeIdAndOrganizationOrganizationId(typeId, currentOrg)
                .orElseThrow(() -> new RuntimeException("Tipo documental no encontrado"));
        documentType.setActive(true);
        documentTypeRepository.save(documentType);
    }

    @Override
    public void deactivateDocumentType(Long typeId) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        DocumentType documentType = documentTypeRepository.findByDocumentTypeIdAndOrganizationOrganizationId(typeId, currentOrg)
                .orElseThrow(() -> new RuntimeException("Tipo documental no encontrado"));
        documentType.setActive(false);
        documentTypeRepository.save(documentType);
    }

    @Override
    public DocumentTypeDto getDocumentTypeById(Long typeId) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        DocumentType documentType = documentTypeRepository.findByDocumentTypeIdAndOrganizationOrganizationId(typeId, currentOrg)
                .orElseThrow(() -> new RuntimeException("Tipo documental no encontrado"));
        return toDto(documentType);
    }

    @Override
    public List<DocumentTypeDto> getAllDocumentTypesByOrganization(Long organizationId) {
        return documentTypeRepository.findByOrganizationOrganizationId(organizationId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<DocumentTypeDto> getDocumentTypesByOrganizationPaginated(Long organizationId, Pageable pageable) {
        return documentTypeRepository.findByOrganizationOrganizationId(organizationId, pageable)
                .map(this::toDto);
    }

    @Override
    public List<DocumentTypeDto> getActiveDocumentTypesByOrganization(Long organizationId) {
        return documentTypeRepository.findByOrganizationOrganizationIdAndActiveTrue(organizationId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private DocumentTypeDto toDto(DocumentType entity) {
        DocumentTypeDto dto = new DocumentTypeDto();
        dto.setDocumentTypeId(entity.getDocumentTypeId());
        dto.setOrganizationId(entity.getOrganization().getOrganizationId());
        dto.setOrganizationName(entity.getOrganization().getName());
        dto.setName(entity.getName());
        dto.setMetadataSchema(entity.getMetadataSchema());
        dto.setActive(entity.getActive());
        return dto;
    }
}
