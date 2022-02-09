package io.anjola.transactionreactive.mapper;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.model.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {
    private final TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);

    @Test
    void mapperTest(){
        assertNotNull(mapper);

        Transaction api = new Transaction(BigDecimal.ONE, LocalDateTime.now());
        TransactionEntity entity = mapper.apiToEntity(api);

        assertEquals(api.getAmount(), entity.getAmount());
        assertEquals(api.getTimestamp(), entity.getTimestamp());

        Transaction api2 = mapper.entityToApi(entity);

        assertEquals(entity.getAmount(), api2.getAmount());
        assertEquals(entity.getTimestamp(), api2.getTimestamp());

    }

}