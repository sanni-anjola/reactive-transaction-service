package io.anjola.transactionreactive.exception.api;

import io.anjola.transactionreactive.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    ApiErrorResponse handleNotFoundExceptions(ServerHttpRequest request, Exception ex){
        return createApiErrorResponse(HttpStatus.NOT_FOUND, request, ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody
    ApiErrorResponse handleBadRequestExceptions(ServerHttpRequest request, Exception ex){
        return createApiErrorResponse(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerException.class)
    public @ResponseBody
    ApiErrorResponse handleInternalServerException(ServerHttpRequest request, Exception exception){
        return createApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody
    ApiErrorResponse handleInvalidJsonErrors(ServerHttpRequest request, Exception ex){
        return createApiErrorResponse(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TransactionServiceException.class)
    public @ResponseBody
    ApiErrorResponse handleInternalServerExceptions(ServerHttpRequest request, Exception ex){
        return createApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(OldTransactionException.class)
    public @ResponseBody
    ApiErrorResponse handleOldTransactionException(ServerHttpRequest request, Exception ex){
        return createApiErrorResponse(HttpStatus.NO_CONTENT, request, ex);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidTransactionException.class)
    public @ResponseBody
    ApiErrorResponse handleFutureTransactionException(ServerHttpRequest request, Exception ex){
        return createApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, request, ex);
    }

    private ApiErrorResponse createApiErrorResponse(HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {
        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new ApiErrorResponse(httpStatus, path, message);
    }
}
