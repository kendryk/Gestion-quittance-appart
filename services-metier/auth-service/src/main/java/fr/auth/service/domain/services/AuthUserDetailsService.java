package fr.auth.service.domain.services;

import fr.auth.service.domain.model.Utilisateur;
import fr.auth.service.domain.repository.IUtilisateurRepository;
import fr.auth.service.enums.UserStatus;
import fr.auth.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AuthUserDetailsService implements UserDetailsService {

    private final IUtilisateurRepository utilisateurRepository;

    public AuthUserDetailsService(
            IUtilisateurRepository utilisateurRepository
    ) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifiantUtilisateur) throws UsernameNotFoundException {

        log.debug("Chargement de l'utilisateur: {}", identifiantUtilisateur);

        try {
            // Récupération de l'utilisateur en base
            Utilisateur user = utilisateurRepository.rechercheUtilisateurByEmail(identifiantUtilisateur);

            // Vérification du statut du compte (optionnel mais recommandé)
            if (UserStatus.INACTIF.name().equals(user.getStatutCompte())) {
                throw new UsernameNotFoundException("Compte utilisateur inactif: " + identifiantUtilisateur);
            }

            // Vérification que le mot de passe existe
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new UsernameNotFoundException("Mot de passe manquant pour l'utilisateur: " + identifiantUtilisateur);
            }

            // Construction des autorités
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (user.getRole() != null && !user.getRole().isEmpty()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
            }

            log.debug("Utilisateur chargé avec succès: {} avec le rôle: {}", identifiantUtilisateur, user.getRole());

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    true, // enabled
                    true, // accountNonExpired
                    true, // credentialsNonExpired
                    true, // accountNonLocked
                    authorities
            );

        } catch (NotFoundException e) {
            log.warn("Utilisateur introuvable: {}", identifiantUtilisateur);
            throw new UsernameNotFoundException("Utilisateur introuvable: " + identifiantUtilisateur, e);
        } catch (Exception e) {
            log.error("Erreur lors du chargement de l'utilisateur: {}", identifiantUtilisateur, e);
            throw new UsernameNotFoundException("Erreur lors du chargement de l'utilisateur: " + identifiantUtilisateur, e);
        }

    }
}
