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
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return "map";
    }

    /**
     * 이미지 업로드 테스트
     *
     * @param fileList
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload")
    public ModelAndView getMostSimilarLocation(@RequestParam(value = "file") List<MultipartFile> fileList) {
        try {
            Optional<List<MultipartFile>> uploadFileList = Optional.ofNullable(fileList);
            log.info(uploadFileList.toString());

            if (uploadFileList.isPresent()) {
                String originalFileName = uploadFileList.get().get(0).getOriginalFilename();
                log.info("originalFileName: {}", originalFileName);
                File dest = new File("C:/cafe-image/upload/" + originalFileName);
                uploadFileList.get().get(0).transferTo(dest);

                List<MapResponse.Info> locationList = mapService.getMostSimilarLocation(originalFileName);
                log.info("[ 유사 카페 이미지 검색 ] 결과: {}", locationList);

                ModelAndView mav = new ModelAndView();
                mav.setViewName("map");
                mav.addObject("locationList", locationList);
                return mav;
            }
            throw new BusinessException(ErrorCode.NOT_FOUND_UPLOAD_IMG);
        } catch (BusinessException e) {
            log.error("[ 유사 카페 이미지 검색 ] 실패: {}", e.getErrorMessage());
            throw new BusinessException(e.getErrorCode());
        } catch (Exception e) {
            log.error("[ 유사 카페 이미지 검색 ] 실패: {}", e.getLocalizedMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 테스트 통신
     */
    @GetMapping(value = "/test")
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
