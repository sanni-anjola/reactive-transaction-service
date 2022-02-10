package io.anjola.transactionreactive.exception;

public class OldTransactionException extends TransactionServiceException{
    public OldTransactionException() {
    }

    public OldTransactionException(String message) {
        super(message);
    }

    public OldTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public OldTransactionException(Throwable cause) {
        super(cause);
    }

    public OldTransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
