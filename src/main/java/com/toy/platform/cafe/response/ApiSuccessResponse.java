package com.toy.platform.cafe.response;

import com.toy.platform.cafe.util.RequestInfoUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * API 성공 응답 규약
 */
@Getter
@Builder
public class ApiSuccessResponse<T> {

    @ApiModelProperty(value = "상태코드")
    private int status;
    @ApiModelProperty(value = "응답코드")
    private String code;
    @ApiModelProperty(value = "응답메시지")
    private String message;
    @ApiModelProperty(value = "데이터")
    private T data;
    @ApiModelProperty(value = "요청번호")
    private String requestId;
    @ApiModelProperty(value = "요청시간")
    private LocalDateTime requestAt;
    @ApiModelProperty(value = "응답시간")
    private LocalDateTime responseAt;

    public static <T> ApiSuccessResponse<T> success() {
        return ApiSuccessResponse.<T>defaultBuilder().build();
    }

    public static <T> ApiSuccessResponse<T> success(T data) {
        return ApiSuccessResponse.<T>defaultBuilder()
                .data(data)
                .build();
    }

    private static <T> ApiSuccessResponseBuilder<T> defaultBuilder() {
        return ApiSuccessResponse.<T>builder()
                .status(200)
                .code("0000")
                .message("Success")
                .data(null)
                .requestId(RequestInfoUtil.getRequestId())
                .requestAt(RequestInfoUtil.getRequestAt())
                .responseAt(LocalDateTime.now());
    }
}
