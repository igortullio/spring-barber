package com.igortullio.barber.adapter.exception;

import com.igortullio.barber.core.exception.BarberException;
import com.igortullio.barber.core.exception.not_found.AbstractNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class BarberExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error.ErrorBuilder errorBuilder = createError(exception, status);
        return super.handleExceptionInternal(exception, errorBuilder.build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error.Object> problemObjects = exception.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> {
                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Error.Object.builder()
                            .name(name)
                            .userMessage(objectError.getDefaultMessage())
                            .build();
                })
                .collect(Collectors.toList());
        Error.ErrorBuilder errorBuilder = createError(exception, status, problemObjects);
        errorBuilder.detail("Validation failed");

        return super.handleExceptionInternal(exception, errorBuilder.build(), headers, status, request);
    }

    @ExceptionHandler(AbstractNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(AbstractNotFoundException exception, WebRequest request) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request) {
        Error.ErrorBuilder errorBuilder = createError(exception, HttpStatus.BAD_REQUEST);

        if (Objects.nonNull(exception.getRootCause())) {
            errorBuilder.detail(exception.getRootCause().getMessage());
        }

        return super.handleExceptionInternal(exception, errorBuilder.build(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException exception, WebRequest request) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), exception.getStatusCode(), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(BarberException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(BarberException exception, WebRequest request) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private Error.ErrorBuilder createError(Exception exception, HttpStatus status, List<Error.Object> objects) {
        Error.ErrorBuilder errorBuilder = createError(exception, status);
        errorBuilder.objects(objects);
        return errorBuilder;
    }

    private Error.ErrorBuilder createError(Exception exception, HttpStatus status) {
        return Error.builder()
                .status(status.value())
                .detail(exception.getMessage())
                .timestamp(OffsetDateTime.now())
                .title(status.getReasonPhrase());
    }

}
