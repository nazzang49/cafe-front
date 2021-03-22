package com.toy.platform.cafe.exception;

import com.toy.platform.cafe.exception.error.ErrorCode;
import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private ErrorCode errorCode;
    @Getter
    private String errorMessage;

    /**
     * 기본 500 에러
     */
    public BusinessException() {
        this(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 에러코드
     *
     * @param errorCode
     */
    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getMessage(), errorCode);
    }

    /**
     * 에러메시지 + 에러코드
     * 
     * @param message
     * @param errorCode
     */
    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * 에러코드 + 가변인자
     * 
     * @param errorCode
     * @param args
     */
    public BusinessException(ErrorCode errorCode, Object... args) {
        this(String.format(errorCode.getMessage(), args), errorCode);
    }
}