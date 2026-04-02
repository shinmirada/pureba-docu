package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.impl;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security.SecurityContextHelper;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.OrganizationService;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Organization;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final SecurityContextHelper securityHelper;

    @Override
    public OrganizationDto createOrganization(OrganizationCreateDto createDto) {
        log.info("Creando organización: {}", createDto.getName());
        if (organizationRepository.existsByName(createDto.getName())) {
            throw new IllegalArgumentException("Ya existe una organización con ese nombre");
        }
        if (createDto.getDomain() != null && organizationRepository.existsByDomain(createDto.getDomain())) {
            throw new IllegalArgumentException("Ya existe una organización con ese dominio");
        }

        Organization org = new Organization();
        org.setName(createDto.getName());
        org.setDomain(createDto.getDomain());
        org.setCreatedAt(LocalDateTime.now());
        org.setStatus("ACTIVE");

        Organization saved = organizationRepository.save(org);

        OrganizationDto dto = new OrganizationDto();
        dto.setOrganizationId(saved.getOrganizationId());
        dto.setName(saved.getName());
        dto.setDomain(saved.getDomain());
        dto.setCreatedAt(saved.getCreatedAt());
        dto.setStatus(saved.getStatus());
        return dto;
    }

    @Override
    public OrganizationDto getOrganizationById(Long id) {
        Organization org = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));
        return toDto(org);
    }

    @Override
    public OrganizationDto getCurrentOrganization() {
        Long orgId = securityHelper.getCurrentOrganizationId();
        return getOrganizationById(orgId);
    }

    @Override
    public OrganizationDto updateOrganization(Long id, String name, String domain) {
        Organization org = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));
        if (name != null && !name.equals(org.getName()) && organizationRepository.existsByName(name)) {
            throw new IllegalArgumentException("Nombre ya en uso");
        }
        if (domain != null && !domain.equals(org.getDomain()) && organizationRepository.existsByDomain(domain)) {
            throw new IllegalArgumentException("Dominio ya en uso");
        }
        if (name != null)
            org.setName(name);
        if (domain != null)
            org.setDomain(domain);
        return toDto(organizationRepository.save(org));
    }

    @Override
    public void deleteOrganization(Long id) {
        // Validar que no tenga usuarios ni documentos activos (puedes agregar más
        // lógica)
        organizationRepository.deleteById(id);
    }

    @Override
    public boolean isDomainAvailable(String domain) {
        return !organizationRepository.existsByDomain(domain);
    }

    private OrganizationDto toDto(Organization org) {
        OrganizationDto dto = new OrganizationDto();
        dto.setOrganizationId(org.getOrganizationId());
        dto.setName(org.getName());
        dto.setDomain(org.getDomain());
        dto.setCreatedAt(org.getCreatedAt());
        dto.setStatus(org.getStatus());
        return dto;
    }
}