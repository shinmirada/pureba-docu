package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextHelper {

    public Long getCurrentOrganizationId() {
        UserAccount currentUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser.getOrganization().getOrganizationId();
    }

    public Long getCurrentUserId() {
        UserAccount currentUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser.getUserId();
    }

    public String getCurrentUserRole() {
        // Retorna el nombre del primer rol (simplificado)
        UserAccount currentUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser.getUserRoles() != null && !currentUser.getUserRoles().isEmpty()) {
            return currentUser.getUserRoles().get(0).getRole().getName();
        }
        return "USER";
    }

    public boolean isAdmin() {
        return "ADMIN".equals(getCurrentUserRole());
    }
}