package fr.auth.service.application.controller;

import fr.auth.service.domain.services.AuthService;
import fr.authservice.application.controller.LoginApi;
import fr.authservice.domain.model.LoginPostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements LoginApi {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<String> loginPost(LoginPostRequest loginPostRequest) {
        if (authService.validateLogin(loginPostRequest.getIdentifiantUtilisateur(), loginPostRequest.getPassword())) {
            String code = authService.generate2FACode(loginPostRequest.getIdentifiantUtilisateur());
            // ⚠️ Ne pas exposer le code 2FA en production ceci est pour le test
            return ResponseEntity.ok("Login OK, code 2FA envoyé :" + code);
        } else {
            return ResponseEntity.status(401).body("Identifiants invalides");
        }
    }

}
