package io.anjola.transactionreactive.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Transaction(BigDecimal amount, LocalDateTime timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
