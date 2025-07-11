package fr.auth.service.exception;

import lombok.Getter;
import lombok.Setter;

public abstract class CommunException extends Exception {
    @Getter
    @Setter
    private final String code;

    protected CommunException(String message, String code) {
        super(message);
        this.code = code;
    }

    protected CommunException(String message, String code, Exception e) {
        super(message, e);
        this.code = code;
    }
}
