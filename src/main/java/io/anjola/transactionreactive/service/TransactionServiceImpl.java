package io.anjola.transactionreactive.service;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.dto.TransactionStatistics;
import io.anjola.transactionreactive.repository.TransactionRepoImpl;
import io.anjola.transactionreactive.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository repository = TransactionRepoImpl.getInstance();
    @Override
    public Mono<Transaction> create(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public Mono<TransactionStatistics> getTransactionStatistics() {
        return repository.getTransactionStat();
    }

    @Override
    public Mono<Void> deleteTransactions() {
        return repository.deleteAll();
    }
}
