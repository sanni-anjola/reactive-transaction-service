package io.anjola.transactionreactive.service;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.dto.TransactionStatistics;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
@Validated
public interface TransactionService {
    Mono<Transaction> create(@Valid Transaction transaction);
    Mono<TransactionStatistics> getTransactionStatistics();
    Mono<Void> deleteTransactions();
}
