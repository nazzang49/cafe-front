package com.toy.platform.cafe.exception.error;

import com.toy.platform.cafe.util.RequestInfoUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private int status;
    private String message;
    private String code;
    private String data;
    private String requestId;
    private LocalDateTime requestAt;
    private LocalDateTime responseAt;

    /**
     * 예외 응답
     *
     * @param errorCode
     * @param message
     */
    public ErrorResponse(ErrorCode errorCode, String message) {
        this.requestId = RequestInfoUtil.getRequestId();
        this.requestAt = RequestInfoUtil.getRequestDate();
        this.responseAt = LocalDateTime.now();
        this.data = null;
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = message;
    }

    /**
     * 예외 응답
     *
     * @param errorCode
     * @param message
     * @return
     */
    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }
}