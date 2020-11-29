package com.toy.platform.cafe.response;

import com.toy.platform.cafe.util.RequestInfoUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponse<T> {

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

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>defaultBuilder().build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>defaultBuilder()
                .data(data)
                .build();
    }

    private static <T> ApiResponseBuilder<T> defaultBuilder() {
        return ApiResponse.<T>builder()
                .status(200)
                .code("C200")
                .message("SUCCESS")
                .data(null)
                .requestId(RequestInfoUtil.getRequestId())
                .requestAt(RequestInfoUtil.getRequestDate())
                .responseAt(LocalDateTime.now());
    }
}