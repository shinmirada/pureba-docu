package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.daos;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserAccountDAO {

    @Autowired
    private UserAccountRepository userAccountRepository;

    // RF12 - Crear nuevo usuario en la organización
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    // RF13 - Actualizar datos del usuario
    public UserAccount update(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    // Buscar usuario por ID
    public Optional<UserAccount> findById(Long userId) {
        return userAccountRepository.findById(userId);
    }

    // RF16 - Listar todos los usuarios de la organización
    public List<UserAccount> findByOrganization(Long organizationId) {
        return userAccountRepository.findByOrganizationOrganizationId(organizationId);
    }

    // RF14 - Listar usuarios activos o inactivos de la organización
    public List<UserAccount> findByOrganizationAndActive(Long organizationId, Boolean active) {
        return userAccountRepository.findByOrganizationOrganizationIdAndActive(organizationId, active);
    }

    // RF02 - Buscar usuario por username para inicio de sesión
    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    // RF02 - Buscar usuario por email
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    // RF02 - Validar credenciales verificando organización (multi-tenant)
    public Optional<UserAccount> findByUsernameAndOrganization(String username, Long organizationId) {
        return userAccountRepository.findByUsernameAndOrganizationOrganizationId(username, organizationId);
    }

    // RF02 - Buscar usuario por email + organización
    public Optional<UserAccount> findByEmailAndOrganization(String email, Long organizationId) {
        return userAccountRepository.findByEmailAndOrganizationOrganizationId(email, organizationId);
    }

    // RF12 - Verificar unicidad de username dentro de la organización
    public boolean existsByUsernameInOrganization(String username, Long organizationId) {
        return userAccountRepository.existsByUsernameAndOrganizationOrganizationId(username, organizationId);
    }

    // RF12 - Verificar unicidad de email dentro de la organización
    public boolean existsByEmailInOrganization(String email, Long organizationId) {
        return userAccountRepository.existsByEmailAndOrganizationOrganizationId(email, organizationId);
    }
}
