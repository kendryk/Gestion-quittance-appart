package fr.auth.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expiration = 3600000; //1h

    /**
     * Génère un token JWT avec les autorités de l'utilisateur
     */
    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        // Extraction des rôles sous forme de liste de chaînes
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        log.debug("Génération du token pour l'utilisateur: {} avec les rôles: {}", username, roles);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
           log.warn("Erreur validation token: " + e.getMessage());
            return false;
        }
    }

//    public String getRoleFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .get("role", String.class);
//    }

    /**
     * Extrait le rôle principal du token (compatibilité)
     */
    public String getRoleFromToken(String token) {
        try {
            Claims claims = getClaims(token);

            // Récupérer la liste des rôles depuis les claims
            Object rolesObj = claims.get("roles");

            if (rolesObj instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<String> rolesList = (List<String>) rolesObj;
                // Retourner le premier rôle (rôle principal)
                if (!rolesList.isEmpty()) {
                    return rolesList.get(0);
                }
            }
            // Fallback: essayer "role" au singulier si "roles" n'existe pas
            String singleRole = claims.get("role", String.class);
            if (singleRole != null && !singleRole.trim().isEmpty()) {
                return singleRole;
            }

            log.warn("Aucun rôle trouvé dans le token");
            return null;
        } catch (Exception e) {
            log.error("Erreur lors de l'extraction du rôle du token", e);
            return null;
        }
    }

    /**
     * Méthode privée pour extraire les claims du token
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
