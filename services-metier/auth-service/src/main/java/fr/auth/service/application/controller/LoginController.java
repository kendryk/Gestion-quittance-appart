package fr.auth.service.application.controller;

import fr.auth.service.domain.services.AuthService;
import fr.authservice.application.controller.LoginApi;
import fr.authservice.domain.model.LoginPostRequest;
import fr.authservice.domain.model.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements LoginApi {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<LoginResponseDto> loginPost(LoginPostRequest loginPostRequest) {
        if (authService.validateLogin(loginPostRequest.getIdentifiantUtilisateur(), loginPostRequest.getPassword())) {
            String code2fa = authService.generate2FACode(loginPostRequest.getIdentifiantUtilisateur());

            LoginResponseDto response = new LoginResponseDto()
                    .status("success")
                    .code2fa(code2fa); // Inclure le code 2FA dans la réponse pour les tests, mais en production, il serait envoyé par un autre canal (email, SMS, etc.)

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }

}
