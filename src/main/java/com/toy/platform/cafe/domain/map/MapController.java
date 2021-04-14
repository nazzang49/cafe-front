package com.toy.platform.cafe.domain.map;

import com.toy.platform.cafe.domain.map.response.MapResponse;
import com.toy.platform.cafe.exception.BusinessException;
import com.toy.platform.cafe.exception.error.ErrorCode;
import com.toy.platform.cafe.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 지도 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping(value = "/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    /**
     * 유사 카페 이미지 검색 테스트
     *
     * @return
     */
    @GetMapping
    public String map() {
        return "cafe/map";
    }

    /**
     * 이미지 업로드 테스트
     *
     * @param fileList
     * @return
     * @throws IOException
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public List<MapResponse.Info> getMostSimilarLocation(@RequestParam(value = "file") List<MultipartFile> fileList) {
        try {
            /* upload */
            List<String> originalFileNameList = mapService.upload(fileList);
            log.info("[ 유사 카페 이미지 검색 ] 업로드 이미지 파일명: {}", originalFileNameList.get(0));

            /* search img */
            List<MapResponse.Info> locationList = mapService.getMostSimilarLocation(originalFileNameList.get(0));
            log.info("[ 유사 카페 이미지 검색 ] 결과: {}", locationList);

//            List<MapResponse.Info> locationList = new ArrayList<>();
//            locationList.add(MapResponse.Info.builder().name("test.jpg").latitude("37.3595704").longitude("127.105399").build());
            return locationList;
        } catch (BusinessException e) {
            log.error("[ 유사 카페 이미지 검색 ] 실패: {}", e.getErrorMessage());
            throw new BusinessException(e.getErrorCode());
        } catch (Exception e) {
            log.error("[ 유사 카페 이미지 검색 ] 실패: {}", e.getLocalizedMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 네이버 장소 검색 API
     */
    @GetMapping(value = "search-places/{query}")
    @ResponseBody
    public MapResponse.Search searchPlaces(@PathVariable(value = "query") String query) {
        try {
            log.info("[ 네이버 장소 검색 ] QUERY: {}", query);
            return mapService.searchPlaces(query);
        } catch (BusinessException e) {
            log.error("[ 네이버 장소 검색 ] 실패: {}", e.getErrorMessage());
            throw new BusinessException(e.getErrorCode());
        } catch (Exception e) {
            log.error("[ 네이버 장소 검색 ] 실패: {}", e.getLocalizedMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 테스트 통신
     */
    @GetMapping(value = "test")
    @ResponseBody
    public List<MapResponse.Info> test() {
        try {
            return mapService.getMostSimilarLocation("test.jpg");
        } catch (BusinessException e) {
            log.error("[ 유사 카페 이미지 검색 ] 실패: {}", e.getErrorMessage());
            throw new BusinessException(e.getErrorCode());
        } catch (Exception e) {
            log.error("[ 유사 카페 이미지 검색 ] 실패: {}", e.getLocalizedMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
