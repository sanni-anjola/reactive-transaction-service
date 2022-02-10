package io.anjola.transactionreactive.repository;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.exception.InvalidInputException;
import io.anjola.transactionreactive.exception.InvalidTransactionException;
import io.anjola.transactionreactive.exception.OldTransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionRepoImplTest {

    private final Logger log = LoggerFactory.getLogger(TransactionRepoImplTest.class);
    private final TransactionRepository repository = TransactionRepoImpl.getInstance();

    private Transaction savedTransaction;

    @BeforeEach
    void setUp(){
        StepVerifier.create(repository.deleteAll()).verifyComplete();
        log.debug("All fine");
        Transaction transaction = new Transaction(BigDecimal.TEN, LocalDateTime.now());

        // Verify that we can save, store the created transaction into the savedTransaction variable
        // and compare the saved transaction.
        StepVerifier.create(repository.save(transaction))
                .expectNextMatches(transaction1 -> {
                    savedTransaction = transaction1;
                    return assertEqualTransaction(transaction1, savedTransaction);
                })
                .verifyComplete();

        // verify the number of entities in the database
        StepVerifier.create(repository.getSize())
                .expectNext(1)
                .verifyComplete();
    }


    @Test
    void saveTransactionTest(){
        Transaction transactionA = new Transaction(BigDecimal.TEN, LocalDateTime.now());
        // Verify that we can save and compare the saved transaction.
        StepVerifier.create(repository.save(transactionA))
                .expectNextMatches(transaction1 -> assertEqualTransaction(transactionA, transaction1))
                .verifyComplete();

        // verify the number of entities in the database
        StepVerifier.create(repository.getSize())
                .expectNext(2)
                .verifyComplete();

        // verify that null transactions cannot be saved

        StepVerifier.create(repository.save(new Transaction()))
                .expectError(InvalidInputException.class)
                .verify();

        // verify that future transaction is not saved
        Transaction transactionB = new Transaction(BigDecimal.TEN, LocalDateTime.now().plusSeconds(40));
        StepVerifier.create(repository.save(transactionB))
                .expectError(InvalidTransactionException.class)
                .verify();

        // verify that transaction older than 30 seconds is not saved
        Transaction transactionC = new Transaction(BigDecimal.TEN, LocalDateTime.now().minusSeconds(40));
        StepVerifier.create(repository.save(transactionC))
                .expectError(OldTransactionException.class)
                .verify();

        // verify the number of entities in the database
        StepVerifier.create(repository.getSize())
                .expectNext(2)
                .verifyComplete();

    }

    @Test
    void deleteAllTest(){
        // verify number of entities in the database
        StepVerifier.create(repository.getSize())
                .expectNext(1)
                .verifyComplete();

        // verify that the all entities can be deleted
        StepVerifier.create(repository.deleteAll())
                .verifyComplete();

        // verify number of entities in the database
        StepVerifier.create(repository.getSize())
                .expectNext(0)
                .verifyComplete();
    }

    @Test
    void getTransactionStatisticsTest() {
        Transaction transactionA = new Transaction(BigDecimal.ONE, LocalDateTime.now());
        // Verify that we can save and compare the saved transaction.
        StepVerifier.create(repository.save(transactionA))
                .expectNextMatches(transaction1 -> assertEqualTransaction(transactionA, transaction1))
                .verifyComplete();

        Transaction transactionB = new Transaction(BigDecimal.ONE, LocalDateTime.now());
        // Verify that we can save and compare the saved transaction.
        StepVerifier.create(repository.save(transactionB))
                .expectNextMatches(transaction1 -> assertEqualTransaction(transactionB, transaction1))
                .verifyComplete();

        // verify number of entities in the database
        StepVerifier.create(repository.getSize())
                .expectNext(3)
                .verifyComplete();

        // verify that transaction statistics can be calculated
        StepVerifier.create(repository.getTransactionStat())
                .expectNextMatches(transactionStatistics -> {
                    log.info("Statistics -> {}",transactionStatistics);
                    return transactionStatistics.getSum().equals(BigDecimal.valueOf(12L))
                            && transactionStatistics.getMax().equals(BigDecimal.TEN)
                            && transactionStatistics.getMin().equals(BigDecimal.ONE)
                            && transactionStatistics.getAvg().equals(BigDecimal.valueOf(4L))
                            && transactionStatistics.getCount() == 3L;
                })
                .verifyComplete();
    }

    private boolean assertEqualTransaction(Transaction expectedTransaction, Transaction actualTransaction) {
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount());
        assertEquals(expectedTransaction.getTimestamp(), actualTransaction.getTimestamp());
        return (expectedTransaction.getAmount().equals(actualTransaction.getAmount()) &&
                (expectedTransaction.getTimestamp().equals(actualTransaction.getTimestamp())));
    }

}