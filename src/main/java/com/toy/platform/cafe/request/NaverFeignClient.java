package com.toy.platform.cafe.request;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.toy.platform.cafe.config.FeignConfig;
import com.toy.platform.cafe.domain.map.response.MapResponse;
import com.toy.platform.cafe.fallback.FeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="NaverApiFeign",
        url="${naver.api-url}",
        configuration = {FeignConfig.class},
        fallbackFactory = FeignFallbackFactory.class)
public interface NaverFeignClient {

    /**
     * 네이버 장소 검색 => 최대 5개
     *
     * @return
     */
    @HystrixCommand(commandKey = "searchPlaces")
    @GetMapping(value = "/v1/search/local.json", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MapResponse.Search> searchPlaces(@RequestHeader("X-Naver-Client-Id") String clientId,
                                                    @RequestHeader("X-Naver-Client-Secret") String clientSecret,
                                                    @RequestParam("query") String query);
}
