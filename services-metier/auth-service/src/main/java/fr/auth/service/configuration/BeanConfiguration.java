package fr.auth.service.configuration;

import fr.auth.service.AuthServiceApplication;
import fr.auth.service.domain.repository.IUtilisateurRepository;
import fr.auth.service.domain.services.IUtilisateurService;
import fr.auth.service.domain.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = AuthServiceApplication.class)
public class BeanConfiguration {

    @Bean
    IUtilisateurService utilisateurService(
            IUtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder
    ){
        return new UtilisateurService(utilisateurRepository, passwordEncoder);
    }

}
