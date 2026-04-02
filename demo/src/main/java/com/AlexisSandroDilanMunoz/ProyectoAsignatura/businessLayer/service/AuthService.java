package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.LoginRequestDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.LoginResponseDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.OrganizationDto;

public interface AuthService {
    OrganizationDto registerOrganization(OrganizationCreateDto createDto);

    LoginResponseDto login(LoginRequestDto loginRequest);

    void logout(String token);
}