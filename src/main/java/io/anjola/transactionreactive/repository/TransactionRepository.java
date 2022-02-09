package io.anjola.transactionreactive.repository;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.model.TransactionEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Long> getId();
    Mono<Integer> getSize();
    Mono<TransactionEntity> save(Transaction transaction);
    Flux<TransactionEntity> findAll();
    Mono<Void> deleteAll();


}
