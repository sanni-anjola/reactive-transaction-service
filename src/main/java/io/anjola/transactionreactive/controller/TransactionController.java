package io.anjola.transactionreactive.controller;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.dto.TransactionStatistics;
import io.anjola.transactionreactive.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transaction> createTransaction(@RequestBody Transaction transaction){
        return transactionService.create(transaction);
    }

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<TransactionStatistics> getTransactionStatistics(){
        return transactionService.getTransactionStatistics();
    }

    @DeleteMapping(value = "/transactions")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTransactions(){
        return transactionService.deleteTransactions();
    }
}
