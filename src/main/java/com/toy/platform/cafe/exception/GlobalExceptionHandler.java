package com.toy.platform.cafe.exception;

import com.toy.platform.cafe.exception.error.ErrorCode;
import com.toy.platform.cafe.exception.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * common exception handler
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ErrorResponse handleNoHandlerFoundException(ServletException e) {
        log.error("http 404 status exception message: " + e.getMessage());
        return ErrorResponse.of(ErrorCode.NOT_FOUND, e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    protected ErrorResponse handleMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("http 400 status exception message: " + e.getMessage());
        return ErrorResponse.of(ErrorCode.BAD_REQUEST, e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    protected ErrorResponse handleBindException(BindException e) {
        log.error("http 400 status exception message: " + e.getMessage());
        return ErrorResponse.of(ErrorCode.BAD_REQUEST, getErrorMessageFrom(e.getBindingResult()).orElse(e.getLocalizedMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .findFirst()
                .map(violation -> String.format("[%s](은)는 %s / 입력 값: %s",
                        violation.getPropertyPath().toString().substring(violation.getPropertyPath().toString().lastIndexOf('.') + 1),
                        violation.getMessage(),
                        violation.getInvalidValue() == null ? "" : violation.getInvalidValue().toString()))
                .orElse(e.getLocalizedMessage());
        log.error("http 400 status exception message: " + e.getMessage());
        return ErrorResponse.of(ErrorCode.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error("http 500 status exception message: " + e.getMessage());
        return ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("BusinessException message : " + e.getMessage());
        ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getErrorMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    private Optional<String> getErrorMessageFrom(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> {
                    return String.format("[%s](은)는 %s / 입력 값: %s", fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
                });
    }
}
