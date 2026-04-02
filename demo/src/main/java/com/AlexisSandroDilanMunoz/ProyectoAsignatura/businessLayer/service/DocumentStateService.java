package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentStateDto;

import java.util.List;

public interface DocumentStateService {
    DocumentStateDto createState(DocumentStateDto dto, Long organizationId);

    DocumentStateDto updateState(Long stateId, DocumentStateDto dto);

    void deleteState(Long stateId);

    List<DocumentStateDto> getStatesByOrganization(Long organizationId);

    List<DocumentStateDto> getStatesOrdered(Long organizationId);

    void reorderStates(Long organizationId, List<Long> stateIdsInOrder);
}