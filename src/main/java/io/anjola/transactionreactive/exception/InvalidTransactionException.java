package io.anjola.transactionreactive.exception;

public class InvalidTransactionException extends TransactionServiceException{
    public InvalidTransactionException() {
    }

    public InvalidTransactionException(String message) {
        super(message);
    }

    public InvalidTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTransactionException(Throwable cause) {
        super(cause);
    }

    public InvalidTransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
