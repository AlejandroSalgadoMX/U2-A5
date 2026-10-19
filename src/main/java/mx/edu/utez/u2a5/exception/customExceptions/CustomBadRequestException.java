package mx.edu.utez.u2a5.exception.customExceptions;

public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(String message) {
        super(message);
    }
}
