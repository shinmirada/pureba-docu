package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationDto;

public interface OrganizationService {
    OrganizationDto createOrganization(OrganizationCreateDto createDto);

    OrganizationDto getOrganizationById(Long id);

    OrganizationDto getCurrentOrganization();

    OrganizationDto updateOrganization(Long id, String name, String domain);

    void deleteOrganization(Long id);

    boolean isDomainAvailable(String domain);
}