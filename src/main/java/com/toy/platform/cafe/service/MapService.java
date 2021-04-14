package com.toy.platform.cafe.service;

import com.toy.platform.cafe.domain.map.response.MapResponse;
import com.toy.platform.cafe.exception.BusinessException;
import com.toy.platform.cafe.exception.error.ErrorCode;
import com.toy.platform.cafe.request.DjangoFeignClient;
import com.toy.platform.cafe.request.NaverFeignClient;
import com.toy.platform.cafe.response.DjangoApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 지도 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MapService {

    private final DjangoFeignClient djangoFeignClient;
    private final NaverFeignClient naverFeignClient;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

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

    /**
     * 이미지 업로드
     *
     * @param fileList
     */
    public List<String> upload(List<MultipartFile> fileList) throws IOException {
        log.info("fileList: {}", fileList);
        List<String> list = new ArrayList<>();
        for (MultipartFile file : fileList) {
            String originalFileName = file.getOriginalFilename();
            File dest = new File("C:/cafe-img/upload/" + originalFileName);
            file.transferTo(dest);
            list.add(originalFileName);
        }
        return list;
    }

    /**
     * 네이버 장소 검색 API
     *
     * @param query
     * @return
     */
    public MapResponse.Search searchPlaces(String query) throws UnsupportedEncodingException {
        String encodedQuery = URLEncoder.encode(query, "utf-8");
        ResponseEntity<MapResponse.Search> response = naverFeignClient.searchPlaces(clientId, clientSecret, encodedQuery);
        log.info("response : {}", response);
        return response.getBody();
    }
}
