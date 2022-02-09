package io.anjola.transactionreactive.mapper;

import io.anjola.transactionreactive.dto.Transaction;
import io.anjola.transactionreactive.model.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    TransactionEntity apiToEntity (Transaction request);

    Transaction entityToApi(TransactionEntity entity);

}
