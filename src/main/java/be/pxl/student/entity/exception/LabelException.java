package be.pxl.student.entity.exception;

public class LabelException extends Exception {
    public LabelException() {
    }

    public LabelException(String message) {
        super(message);
    }

    public LabelException(String message, Throwable cause) {
        super(message, cause);
    }

    public LabelException(Throwable cause) {
        super(cause);
    }
}
