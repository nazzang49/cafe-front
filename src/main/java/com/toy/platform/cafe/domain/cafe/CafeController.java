package com.toy.platform.cafe.domain.cafe;

import com.toy.platform.cafe.domain.cafe.request.CafeRequest.DetailRequestClient;
import com.toy.platform.cafe.exception.BusinessException;
import com.toy.platform.cafe.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "/cafe")
@RequiredArgsConstructor
public class CafeController {

    private CafeService cafeService;

    @GetMapping(value = "/detail")
    public String detail(@Valid DetailRequestClient detailRequestClient) {
        try {
            // rebase 테스트
            log.info("[ 카페 상세 조회 ] 요청 : {}", detailRequestClient);
            cafeService.detail(detailRequestClient);
            return null;
        } catch (BusinessException e) {
            log.error("[ 카페 상세 조회 ] 실패: {}", e.getErrorMessage());
            throw new BusinessException(e.getErrorCode());
            // TODO: 2021-03-26 에러 페이지 이동 + 얼럿 노출 => 수정
        } catch (Exception e) {
            log.error("[ 카페 상세 조회 ] 실패: {}", e.getLocalizedMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
            // TODO: 2021-03-26 에러 페이지 이동 + 얼럿 노출 => 수정
        }
    }
}
