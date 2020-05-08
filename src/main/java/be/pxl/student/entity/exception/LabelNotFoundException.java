package be.pxl.student.entity.exception;

public class LabelNotFoundException extends Exception {
    public LabelNotFoundException() {
    }

    public LabelNotFoundException(String message) {
        super(message);
    }

    public LabelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LabelNotFoundException(Throwable cause) {
        super(cause);
    }
}
