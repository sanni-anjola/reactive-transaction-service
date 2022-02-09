package io.anjola.transactionreactive.repository;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.model.TransactionEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TransactionRepoImpl implements TransactionRepository{
    @Override
    public Mono<Long> getId() {
        return null;
    }

    @Override
    public Mono<Integer> getSize() {
        return null;
    }

    @Override
    public Mono<TransactionEntity> save(Transaction transaction) {
        return null;
    }

    @Override
    public Flux<TransactionEntity> findAll() {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
