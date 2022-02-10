package io.anjola.transactionreactive;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionEntityReactiveApplicationTests {

    private final Logger log = LoggerFactory.getLogger(TransactionEntityReactiveApplicationTests.class);
    @Autowired
    private WebTestClient client;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService.deleteTransactions().block();

        assertEquals(0, transactionService.getCount().block());
    }

    @Test
    void createTransactionTest() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(1.0), LocalDateTime.now());
        log.info("Transaction -> {}", transaction);
        String timestamp = transaction.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        postAndVerifyTransaction(transaction, HttpStatus.CREATED)
                .jsonPath("$.amount").isEqualTo(transaction.getAmount())
                .jsonPath("$.timestamp").isEqualTo(timestamp);
    }


    @Test
    void oldTransactionExceptionTest(){
        Transaction transaction = new Transaction(BigDecimal.valueOf(1.0), LocalDateTime.now().minusSeconds(31));

        postAndVerifyTransaction(transaction, HttpStatus.NO_CONTENT);

    }

    @Test
    void InvalidJsonExceptionTest(){
        Transaction transaction = new Transaction(BigDecimal.valueOf(1.0), LocalDateTime.now().plusSeconds(31));

        postAndVerifyTransaction(transaction, HttpStatus.UNPROCESSABLE_ENTITY)
                .jsonPath("$.path").isEqualTo("/api/v1/transactions")
                .jsonPath("$.status").isEqualTo("Fail")
                .jsonPath("$.message").isEqualTo("Transaction cannot be done in the future");

    }

    @Test
    void deleteTransactionsTest(){
        Transaction transaction1 = new Transaction(BigDecimal.valueOf(1.0), LocalDateTime.now().minusSeconds(2L));
        Transaction transaction2 = new Transaction(BigDecimal.valueOf(1.0), LocalDateTime.now().minusSeconds(10L));
        Transaction transaction3 = new Transaction(BigDecimal.valueOf(1.0), LocalDateTime.now());

        postAndVerifyTransaction(transaction1, HttpStatus.CREATED);
        postAndVerifyTransaction(transaction2, HttpStatus.CREATED);
        postAndVerifyTransaction(transaction3, HttpStatus.CREATED);

        StepVerifier.create(transactionService.getCount())
                .expectNext(3)
                .verifyComplete();

        client.delete()
                .uri("/api/v1/transactions")
                .exchange()
                .expectStatus().isNoContent();

        StepVerifier.create(transactionService.getCount())
                .expectNext(0)
                .verifyComplete();

    }

    @Test
    void getTransactionStatisticsTest(){
        Transaction transaction1 = new Transaction(BigDecimal.valueOf(1.0), LocalDateTime.now().minusSeconds(2L));
        Transaction transaction2 = new Transaction(BigDecimal.valueOf(10.0), LocalDateTime.now().minusSeconds(10L));
        Transaction transaction3 = new Transaction(BigDecimal.valueOf(1.0), LocalDateTime.now());

        postAndVerifyTransaction(transaction1, HttpStatus.CREATED);
        postAndVerifyTransaction(transaction2, HttpStatus.CREATED);
        postAndVerifyTransaction(transaction3, HttpStatus.CREATED);

        StepVerifier.create(transactionService.getCount())
                .expectNext(3)
                .verifyComplete();

        client.get()
                .uri("/api/v1/statistics")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.sum").isEqualTo(BigDecimal.valueOf(12.0))
                .jsonPath("$.max").isEqualTo(BigDecimal.valueOf(10.0))
                .jsonPath("$.min").isEqualTo(BigDecimal.valueOf(1.0))
                .jsonPath("$.avg").isEqualTo(BigDecimal.valueOf(4.0))
                .jsonPath("$.count").isEqualTo(3L);

    }

    private WebTestClient.BodyContentSpec postAndVerifyTransaction(Transaction transaction, HttpStatus httpStatus) {
        return client.post()
                .uri("/api/v1/transactions")
                .body(Mono.just(transaction), Transaction.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(httpStatus)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }


}
