package com.toy.platform.cafe.response;

import com.toy.platform.cafe.exception.ErrorCode;
import com.toy.platform.cafe.util.RequestInfoUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * API 실패 응답 규약
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiFailResponse {

    @ApiModelProperty(value = "상태코드")
    private int status;
    @ApiModelProperty(value = "응답코드")
    private String code;
    @ApiModelProperty(value = "응답메시지")
    private String message;
    /* 예외 및 실패 응답 시 data -> null */
    @ApiModelProperty(value = "데이터")
    private String data;
    @ApiModelProperty(value = "요청번호")
    private String requestId;
    @ApiModelProperty(value = "요청시간")
    private LocalDateTime requestAt;
    @ApiModelProperty(value = "응답시간")
    private LocalDateTime responseAt;


    public ApiFailResponse(ErrorCode errorCode, String message) {
        this.requestId = RequestInfoUtil.getRequestId();
        this.requestAt = RequestInfoUtil.getRequestAt();
        this.responseAt = LocalDateTime.now();
        /* default null */
        this.data = null;
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = message;
    }

    public static ApiFailResponse of(ErrorCode errorCode, String message) {
        return new ApiFailResponse(errorCode, message);
    }
}