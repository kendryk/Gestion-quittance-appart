package fr.auth.service.exception;

public class NotFoundException extends CommunException {

    private static final long serialVersionUID = 1417176972900933181L;

    public NotFoundException(String message) {
        super(message, "NOT_FOUND");
    }

    public NotFoundException(String message, String code) {
        super(message, code);
    }

    public NotFoundException(String message, String code, Exception e) {
        super(message, code, e);
    }
}
