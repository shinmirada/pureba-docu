package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentStateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.SecurityContextHelper;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.DocumentStateService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.DocumentState;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.DocumentStateRepository;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DocumentStateServiceImpl implements DocumentStateService {

    private final DocumentStateRepository documentStateRepository;
    private final OrganizationRepository organizationRepository;
    private final SecurityContextHelper securityHelper;

    @Override
    public DocumentStateDto createState(DocumentStateDto dto, Long organizationId) {
        log.info("Creando estado '{}' para organización {}", dto.getCode(), organizationId);

        // Validar código único por organización
        if (documentStateRepository.existsByOrganizationOrganizationIdAndCode(organizationId, dto.getCode())) {
            throw new IllegalArgumentException("Ya existe un estado con ese código en esta organización");
        }

        Organization org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));

        DocumentState state = new DocumentState();
        state.setCode(dto.getCode());
        state.setLabel(dto.getLabel());
        state.setStateOrder(dto.getStateOrder());
        state.setOrganization(org);

        DocumentState saved = documentStateRepository.save(state);
        return toDto(saved);
    }

    @Override
    public DocumentStateDto updateState(Long stateId, DocumentStateDto dto) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        DocumentState state = documentStateRepository.findById(stateId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        if (!state.getOrganization().getOrganizationId().equals(currentOrg)) {
            throw new RuntimeException("No tiene permisos sobre este estado");
        }

        if (dto.getCode() != null && !dto.getCode().equals(state.getCode()) &&
                documentStateRepository.existsByOrganizationOrganizationIdAndCode(currentOrg, dto.getCode())) {
            throw new IllegalArgumentException("Código ya en uso");
        }

        if (dto.getCode() != null)
            state.setCode(dto.getCode());
        if (dto.getLabel() != null)
            state.setLabel(dto.getLabel());
        if (dto.getStateOrder() != null)
            state.setStateOrder(dto.getStateOrder());

        return toDto(documentStateRepository.save(state));
    }

    @Override
    public void deleteState(Long stateId) {
        Long currentOrg = securityHelper.getCurrentOrganizationId();
        DocumentState state = documentStateRepository.findById(stateId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        if (!state.getOrganization().getOrganizationId().equals(currentOrg)) {
            throw new RuntimeException("No tiene permisos");
        }

        // Podrías validar si algún documento usa este estado, pero normalmente los
        // documentos tienen un string currentState, no FK.
        documentStateRepository.delete(state);
    }

    @Override
    public List<DocumentStateDto> getStatesByOrganization(Long organizationId) {
        return documentStateRepository.findByOrganizationOrganizationId(organizationId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DocumentStateDto> getStatesOrdered(Long organizationId) {
        return documentStateRepository.findByOrganizationOrganizationIdOrderByStateOrderAsc(organizationId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void reorderStates(Long organizationId, List<Long> stateIdsInOrder) {
        List<DocumentState> states = documentStateRepository.findByOrganizationOrganizationId(organizationId);
        for (int i = 0; i < stateIdsInOrder.size(); i++) {
            Long stateId = stateIdsInOrder.get(i);
            final int order = i + 1;
            states.stream().filter(s -> s.getStateId().equals(stateId)).findFirst()
                    .ifPresent(s -> s.setStateOrder(order));
        }
        documentStateRepository.saveAll(states);
    }

    private DocumentStateDto toDto(DocumentState entity) {
        DocumentStateDto dto = new DocumentStateDto();
        dto.setStateId(entity.getStateId());
        dto.setOrganizationId(entity.getOrganization().getOrganizationId());
        dto.setCode(entity.getCode());
        dto.setLabel(entity.getLabel());
        dto.setStateOrder(entity.getStateOrder());
        return dto;
    }
}