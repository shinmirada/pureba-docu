package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DocumentTypeService {
    DocumentTypeDto createDocumentType(DocumentTypeDto dto, Long organizationId);

    DocumentTypeDto updateDocumentType(Long typeId, DocumentTypeDto dto);

    void deleteDocumentType(Long typeId);

    void activateDocumentType(Long typeId);

    void deactivateDocumentType(Long typeId);

    DocumentTypeDto getDocumentTypeById(Long typeId);

    List<DocumentTypeDto> getAllDocumentTypesByOrganization(Long organizationId);

    Page<DocumentTypeDto> getDocumentTypesByOrganizationPaginated(Long organizationId, Pageable pageable);

    List<DocumentTypeDto> getActiveDocumentTypesByOrganization(Long organizationId);
}