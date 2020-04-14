package be.pxl.student.entity.exception;

import be.pxl.student.entity.exception.AccountException;

public class AccountNotFoundException extends AccountException {
    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}
