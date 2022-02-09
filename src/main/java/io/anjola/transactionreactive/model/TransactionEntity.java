package io.anjola.transactionreactive.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.anjola.transactionreactive.config.BigDecimalConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionEntity {
    private Long id;
    private Integer version;
    @JsonSerialize(using = BigDecimalConfiguration.class)
    private BigDecimal amount;
    //    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime timestamp;

    public TransactionEntity() {
        this(null, null);
    }
    public TransactionEntity(BigDecimal amount, LocalDateTime timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.version = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", version=" + version +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
