package fr.auth.service.config;


import fr.auth.service.domain.services.AuthUserDetailsService;
import fr.auth.service.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final AuthUserDetailsService userDetailsService;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter,
            AuthUserDetailsService userDetailsService
    ) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Bean de type SecurityFilterChain qui configure les règles de sécurité pour l'application.
     * Cette configuration spécifie comment traiter les requêtes entrantes.
     * @param http L'objet HttpSecurity permettant de configurer la sécurité.
     * @return La chaîne de filtres de sécurité configurée.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Désactivation de la protection CSRF (Cross-Site Request Forgery), car nous utilisons des JWT.
                .csrf(csrf -> csrf.disable())
                // 2. Configuration de la gestion de session pour indiquer qu'elle est "sans état" (stateless).
                // Cela signifie que Spring Security ne stockera pas les informations d'utilisateur dans une session HTTP.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 3. Autorisations pour les requêtes HTTP.
                .authorizeHttpRequests(auth -> auth
                        // Autoriser toutes les requêtes commençant par `/auth/**` sans authentification.
                        .requestMatchers("/login", "verify-2fa", "/register").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/utilisateurs").hasRole("ADMIN")
                        .requestMatchers("/utilisateurs/**").hasRole("ADMIN")
                        // Toute autre requête doit être authentifiée (protection par défaut).
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                // 4. Ajout du filtre personnalisé `JwtAuthenticationFilter` avant le filtre standard `UsernamePasswordAuthenticationFilter`.
                // Cela permet à notre filtre d'intercepter et valider les JWT avant toute logique standard d'authentification.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // 5. Construction et retour de la configuration de la chaîne de filtres.
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
