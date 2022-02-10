package io.anjola.transactionreactive.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.anjola.transactionreactive.config.BigDecimalConfiguration;

import java.math.BigDecimal;

public class TransactionStatistics {
    @JsonSerialize(using = BigDecimalConfiguration.class)
    private BigDecimal sum;
    @JsonSerialize(using = BigDecimalConfiguration.class)
    private BigDecimal avg;
    @JsonSerialize(using = BigDecimalConfiguration.class)
    private BigDecimal max;
    @JsonSerialize(using = BigDecimalConfiguration.class)
    private BigDecimal min;
    private Long count;

    public TransactionStatistics() {
        this(null, null, null, null, 0);
    }

    public TransactionStatistics(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TransactionStatistics{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}
