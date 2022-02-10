package io.anjola.transactionreactive.repository;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.dto.TransactionStatistics;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Integer> getSize();
    Mono<Transaction> save(Transaction transaction);
    Mono<Void> deleteAll();
    Mono<TransactionStatistics> getTransactionStat();


}
