package fr.auth.service.application.controller;


import fr.auth.service.domain.services.AuthService;
import fr.authservice.application.controller.Verify2faApi;
import fr.authservice.domain.model.Verify2faPost200Response;
import fr.authservice.domain.model.Verify2faPostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Verify2FAController implements Verify2faApi {

    private final AuthService authService;

    public Verify2FAController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<Verify2faPost200Response> verify2faPost(Verify2faPostRequest request) {
        try {
            if (authService.verifyCode(request.getIdentifiantUtilisateur(), request.getCode2fa())) {
                String token = authService.generateJwtToken(request.getIdentifiantUtilisateur());
                Verify2faPost200Response verify2faPost200Response = new Verify2faPost200Response();
                verify2faPost200Response.setToken(token);
                verify2faPost200Response.setStatus("success");

                return ResponseEntity.ok(verify2faPost200Response);
            } else {
                Map<String, String> errorResponse = Map.of("error", "Code 2FA invalide");
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
