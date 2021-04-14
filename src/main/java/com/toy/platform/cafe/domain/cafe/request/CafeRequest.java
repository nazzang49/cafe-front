package com.toy.platform.cafe.domain.cafe.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

/**
 * 카페 관련 요청 스펙
 */
public class CafeRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailRequestClient {

        @NotNull
        private int id;
    }

}