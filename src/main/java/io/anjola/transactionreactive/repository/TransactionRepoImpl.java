package io.anjola.transactionreactive.repository;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.dto.TransactionStatistics;
import io.anjola.transactionreactive.exception.InvalidInputException;
import io.anjola.transactionreactive.exception.InvalidTransactionException;
import io.anjola.transactionreactive.exception.OldTransactionException;
import io.anjola.transactionreactive.mapper.TransactionMapper;
import io.anjola.transactionreactive.model.TransactionEntity;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class TransactionRepoImpl implements TransactionRepository{
    private final Logger log = LoggerFactory.getLogger(TransactionRepoImpl.class);
    private final ConcurrentMap<String, TransactionEntity> transactionDB;
    private final TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);
    private TransactionRepoImpl() {
        transactionDB = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<Integer> getSize() {
        return Mono.just(transactionDB.size());
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        if(transaction.getAmount() == null || transaction.getTimestamp() == null){
            log.error("Invalid input...");
            return Mono.error(new InvalidInputException("Cannot be saved. amount and timestamp are required, but one or both is empty."))
                    .thenReturn(transaction);
        }
        long timeLapse = Duration.between(transaction.getTimestamp(), LocalDateTime.now()).toSeconds();
        if(timeLapse > 30)
            return Mono.error(new OldTransactionException("Transaction over 30seconds cannot be registered"));
        if(timeLapse < 0)
            return Mono.error(new InvalidTransactionException("Transaction cannot be done in the future"));

        TransactionEntity entity = mapper.apiToEntity(transaction);
        entity.setId(createID());

        return addEntity(Mono.just(entity))
                .thenReturn(mapper.entityToApi(entity));
    }

    private Mono<Transaction> addEntity(Mono<TransactionEntity> entity) {
        return entity
                .mapNotNull(e -> transactionDB.put(e.getId(), e))
                .map(mapper::entityToApi);



    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.just(1)
                .map(e -> {
                    transactionDB.clear();
                    return e;
                })
                .then();
    }

    @Override
    public Mono<TransactionStatistics> getTransactionStat() {
        if(transactionDB.isEmpty()) return Mono.just(new TransactionStatistics());
        return findAll()
                .map(Transaction::getAmount)
                .collectList()
                .map(amountList -> {
                    TransactionStatistics statistics = new TransactionStatistics();
                    statistics.setCount(amountList.size());
                    statistics.setSum(amountList.stream().parallel().reduce(BigDecimal.ZERO, BigDecimal::add));
                    statistics.setAvg(amountList.stream().parallel().reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(amountList.size()), RoundingMode.HALF_UP));
                    statistics.setMax(amountList.stream().parallel().max(BigDecimal::compareTo).orElse(null));
                    statistics.setMin(amountList.stream().parallel().min(BigDecimal::compareTo).orElse(null));
                    return statistics;
                });
    }

        private Flux<Transaction> findAll() {
        return Flux.fromIterable(transactionDB.values())
                .filter((transactionEntity -> Duration.between(transactionEntity.getTimestamp(), LocalDateTime.now()).toSeconds() <= 30))
                .map(mapper::entityToApi);
    }

    private String createID(){
        return UUID.randomUUID().toString().replace("_", "");
    }
    private static class TransactionRepositorySingleTonHelper{
        private static final TransactionRepoImpl instance = new TransactionRepoImpl();
    }

    public static TransactionRepository getInstance(){
        return TransactionRepositorySingleTonHelper.instance;
    }
}
