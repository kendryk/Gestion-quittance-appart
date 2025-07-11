package fr.auth.service.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class UtilisateurNotFoundException extends NotFoundException {
    public UtilisateurNotFoundException(String message,  String code) {

        super(message, code);
    }
}
