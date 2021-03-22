package com.toy.platform.cafe.service;

import com.toy.platform.cafe.domain.map.response.MapResponse;
import com.toy.platform.cafe.exception.BusinessException;
import com.toy.platform.cafe.exception.error.ErrorCode;
import com.toy.platform.cafe.request.DjangoFeignClient;
import com.toy.platform.cafe.response.DjangoApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 지도 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MapService {

    private final DjangoFeignClient djangoFeignClient;

    /**
     * 테스트 통신
     */
    public List<MapResponse.Info> getMostSimilarLocation(String originalFileName) {
        DjangoApiResponse<List<MapResponse.Info>> response = djangoFeignClient.getMostSimilarLocation(originalFileName);
        log.info("response: {}", response);

        if (response.getStatus() != 200) {
            log.error("[유사 카페 이미지 검색 실패] STATUS: {}", response.getStatus());
            throw new BusinessException(ErrorCode.FAIL_TO_SEARCH_LOCATION);
        }
        return response.getData();
    }
}
