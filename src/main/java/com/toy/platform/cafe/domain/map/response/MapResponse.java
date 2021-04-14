package com.toy.platform.cafe.domain.map.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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
        private String address;
        @JsonProperty("lat")
        private String latitude;
        @JsonProperty("long")
        private String longitude;
    }

    @Data
    public static class Search {
        private int total;
        private int display;
        private List<Item> items;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Item {
            private String title;
            private String link;
            private String category;
            private String description;
            private String telephone;
            private String address;
            private String roadAddress;
            private String mapx;
            private String mapy;
        }
    }
}
