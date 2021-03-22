package com.toy.platform.cafe.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Django API common response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DjangoApiResponse<T> {

    private int status;
    private String message;
    private String code;
    private T data;
    private String requestId;
    private LocalDateTime requestAt;
    private LocalDateTime responseAt;

}
