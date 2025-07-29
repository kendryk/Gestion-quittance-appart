package fr.auth.service.domain.services;

import fr.auth.service.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthService {

    private final Map<String, String> temporaryCodes = new HashMap<>();
    private final JwtUtil jwtUtil;
    private final AuthUserDetailsService authUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            JwtUtil jwtUtil,
            AuthUserDetailsService authUserDetailsService,
            PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authUserDetailsService = authUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validateLogin(String identifiantUtilisateur, String password) {
        try {
            UserDetails userDetails = authUserDetailsService.loadUserByUsername(identifiantUtilisateur);
            boolean isPasswordValid = passwordEncoder.matches(password, userDetails.getPassword());

            if (isPasswordValid) {
                log.info("Authentification réussie pour l'utilisateur: {}", identifiantUtilisateur);
                return true;
            } else {
                log.warn("Échec d'authentification - mot de passe incorrect pour: {}", identifiantUtilisateur);
                return false;
            }
        } catch (UsernameNotFoundException e) {
            log.warn("Échec d'authentification - utilisateur introuvable: {}", identifiantUtilisateur);
            return false;
        } catch (Exception e) {
            log.error("Erreur lors de la validation de l'authentification pour: {}", identifiantUtilisateur, e);
            return false;
        }
    }

    public boolean verifyCode(String username, String code2fa) {
        return code2fa.equals(temporaryCodes.get(username));
    }

    /**
     * Génère un token JWT pour un utilisateur authentifié
     */
    public String generateJwtToken(String identifiantUtilisateur) {
        try {
            // Récupération des détails de l'utilisateur avec ses autorités
            UserDetails userDetails = authUserDetailsService.loadUserByUsername(identifiantUtilisateur);

            // Extraction des rôles/autorités
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            // Log pour debugging (optionnel)
            log.debug("Génération du token JWT pour l'utilisateur: {} avec les rôles: {}",
                    identifiantUtilisateur, authorities);

            // Génération du token avec l'utilisateur et ses autorités
            return jwtUtil.generateToken(identifiantUtilisateur, authorities);

        } catch (UsernameNotFoundException e) {
            log.error("Utilisateur introuvable lors de la génération du token JWT: {}", identifiantUtilisateur);
            throw new RuntimeException("Impossible de générer le token : utilisateur introuvable", e);
        } catch (Exception e) {
            log.error("Erreur lors de la génération du token JWT pour l'utilisateur: {}", identifiantUtilisateur, e);
            throw new RuntimeException("Erreur lors de la génération du token JWT", e);
        }
    }

    public String generate2FACode(String username) {
        // Générer un code 2FA sécurisé
        String code = generateSecureCode();
        temporaryCodes.put(username, code);

        // Optionnel : programmer l'expiration du code
        scheduleCodeExpiration(username);

        log.info("Code 2FA généré pour l'utilisateur: {}", username);
        return code;
    }

    private String generateSecureCode() {
        // Génération d'un code 6 chiffres sécurisé
        return String.format("%06d", new SecureRandom().nextInt(1000000));
    }

    private void scheduleCodeExpiration(String username) {
        // Programmer l'expiration du code après 5 minutes par exemple
        CompletableFuture.delayedExecutor(5, TimeUnit.MINUTES)
                .execute(() -> {
                    temporaryCodes.remove(username);
                    log.info("Code 2FA expiré pour l'utilisateur: {}", username);
                });
    }

    public boolean validate2FACode(String username, String code) {
        String storedCode = temporaryCodes.get(username);
        if (storedCode != null && storedCode.equals(code)) {
            temporaryCodes.remove(username); // Code utilisé, on le supprime
            return true;
        }
        return false;
    }


}
