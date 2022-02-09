package io.anjola.transactionreactive.mapper;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.model.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    TransactionEntity apiToEntity (Transaction api);

    Transaction entityToApi(TransactionEntity entity);

}
