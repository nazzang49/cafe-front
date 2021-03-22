package com.toy.platform.cafe.domain.map.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 지도 관련 응답 스펙
 */
public class MapResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Info {
        private String name;
        @JsonProperty("lat")
        private String latitude;
        @JsonProperty("long")
        private String longitude;
    }

}
