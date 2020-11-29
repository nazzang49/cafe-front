package com.toy.platform.cafe.exception;

import com.querydsl.core.NonUniqueResultException;
import com.toy.platform.cafe.exception.type.BusinessException;
import com.toy.platform.cafe.response.ApiFailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * 기본 예외 처리
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    /**
     * 최상위 예외
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return entity(ErrorCode.INTERNAL_SERVER_ERROR, e);
    }
    
    /**
     * 핸들러 부재 관련 예외
     * 
     * @param e
     * @return
     */
    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleNoHandlerFoundException(ServletException e) {
        return entity(ErrorCode.NOT_FOUND, e);
    }

    /**
     * 함수 인자 관련 예외
     * 
     * @param e
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return entity(ErrorCode.BAD_REQUEST, e);
    }

    /**
     * 바인딩 관련 예외
     * 
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    protected ResponseEntity<ApiFailResponse> handleBindException(BindException e) {
        String errorMessage = getErrorMessageFrom(e.getBindingResult()).orElse(e.getLocalizedMessage());
        return entity(ErrorCode.BAD_REQUEST, e, errorMessage);
    }

    /**
     * 함수 인자 유효성 관련 예외
     * 
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ApiFailResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = getErrorMessageFrom(e.getBindingResult()).orElse(e.getLocalizedMessage());
        return entity(ErrorCode.BAD_REQUEST, e, errorMessage);
    }

    /**
     * PathVariable name 유효성 관련 예외
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> {
                    try {
                        String field = StringUtils.substringAfterLast(violation.getPropertyPath().toString(), ".");
                        String fieldErrorMessage = violation.getMessage();
                        Object invalidValue = violation.getInvalidValue();
                        return String.format("[%s](은)는 %s / 입력값: %s", field, fieldErrorMessage, invalidValue);
                    } catch (Exception ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(e.getLocalizedMessage());
        return entity(ErrorCode.BAD_REQUEST, e, errorMessage);
    }

    /**
     * 비즈니스 로직 관련 예외
     *
     * made by developer
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiFailResponse> handleBusinessException(BusinessException e) {
        /* 임의 지정 에러 코드 */
        return entity(e.getErrorCode(), e, e.getErrorMessage());
    }

    /**
     * 별도 에러 메시지 사용 안하는 케이스
     * 
     * @param errorCode
     * @param e
     * @return
     */
    private ResponseEntity<ApiFailResponse> entity(ErrorCode errorCode, Exception e) {
        return entity(errorCode, e, e.getLocalizedMessage());
    }

    /**
     * 최종 실패 및 예외 응답 객체 생성
     * 
     * @param errorCode
     * @param e
     * @param errorMessage
     * @return
     */
    private ResponseEntity<ApiFailResponse> entity(ErrorCode errorCode, Exception e, String errorMessage) {
        log.error(e.getClass().getName(), e);
        ApiFailResponse apiFailResponse = ApiFailResponse.of(errorCode, errorMessage);
        return ResponseEntity.status(apiFailResponse.getStatus()).body(apiFailResponse);
    }

    /**
     * 바인딩 및 함수 인자 관련 예외 시 에러 메시지 추출
     * 
     * @param bindingResult
     * @return
     */
    private Optional<String> getErrorMessageFrom(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    try {
                        String message = messageSource.getMessage(fieldError, Locale.getDefault());
                        return String.format("[%s](은)는 %s / 입력값: %s", fieldError.getField(), message, fieldError.getRejectedValue());
                    } catch (NoSuchMessageException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst();
    }
}
