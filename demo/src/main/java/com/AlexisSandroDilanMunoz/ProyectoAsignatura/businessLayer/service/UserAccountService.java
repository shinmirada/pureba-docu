package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountCreateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountResponseDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.UserAccountUpdateDto;
import java.util.List;

public interface UserAccountService {
    UserAccountResponseDto createUser(UserAccountCreateDto createDto, Long organizationId);

    UserAccountResponseDto updateUser(Long userId, UserAccountUpdateDto updateDto);

    void activateUser(Long userId);

    void deactivateUser(Long userId);

    void assignRole(Long userId, Long roleId);

    void revokeRole(Long userId, Long roleId);

    UserAccountResponseDto getUserById(Long userId);

    List<UserAccountResponseDto> getUsersByOrganization(Long organizationId);
}