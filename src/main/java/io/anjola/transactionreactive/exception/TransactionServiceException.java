package io.anjola.transactionreactive.exception;


public class TransactionServiceException extends RuntimeException{
    public TransactionServiceException() {
    }

    public TransactionServiceException(String message) {
        super(message);
    }

    public TransactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionServiceException(Throwable cause) {
        super(cause);
    }

    public TransactionServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
