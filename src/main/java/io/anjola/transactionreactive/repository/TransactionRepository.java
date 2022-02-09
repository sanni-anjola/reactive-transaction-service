package io.anjola.transactionreactive.repository;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.dto.TransactionStatistics;
import io.anjola.transactionreactive.model.TransactionEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Integer> getSize();
    Mono<Transaction> save(Transaction transaction);
    Flux<Transaction> findAll();
    Mono<Void> deleteAll();
    Mono<TransactionStatistics> getTransactionStat();


}
