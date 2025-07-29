package fr.auth.service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component// Annotations Spring indiquant que cette classe est un composant injectable comme bean.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Utilitaire pour gérer les opérations liées au JWT (génération, validation, etc.).
    private final JwtUtil jwtUtil;

    // Injection de dépendance via le constructeur.
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, // Requête HTTP en cours de traitement.
            HttpServletResponse response, // Réponse HTTP associée.
            FilterChain filterChain // Chaîne de filtres qui permet la poursuite du traitement.
    ) throws ServletException, IOException {

        // 1. Extraction du header d’autorisation depuis la requête HTTP.
        String authHeader = request.getHeader("Authorization");

        // 2. Vérification si le header existe et commence par "Bearer ".
        // Le préfixe "Bearer " est utilisé conventionnellement pour transporter un token JWT.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 3. Extraction du token en retirant le préfixe "Bearer ".
            String token = authHeader.substring(7);
            // 4. Validation du token : Vérifie sa structure, sa signature, et éventuellement sa date d’expiration.
            if (jwtUtil.validateToken(token)) {
                // 5. Extraction du nom d'utilisateur (ou autre information utile contenue dans le token).
               String username = jwtUtil.getUsernameFromToken(token);

                // 🔥 Extraire le rôle du token
                String role = jwtUtil.getRoleFromToken(token);
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                // 6. Création d'un objet d'authentification à partir du username.
                // Ici, aucune autorité spécifique (`Collections.emptyList()`) n'est associée à l'utilisateur.
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                       username, // Principal : Information principale pour identifier l'utilisateur.
                        null, // Credentials : Souvent le mot de passe, mais mis à null ici car non requis pour les JWT.
                        Collections.singletonList(authority)
               );

                // 7. Ajout des détails de la requête (comme l'adresse IP ou d'autres métadonnées) à l'objet d'authentification.
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 8. Stockage de l'objet d'authentification dans le contexte de sécurité de Spring.
                SecurityContextHolder.getContext().setAuthentication(auth);
           }
        }
        // 9. Passage au filtre suivant dans la chaîne (ou continuité du traitement de la requête).
        filterChain.doFilter(request, response);
    }
}
