package com.toy.platform.cafe.fallback;

import com.toy.platform.cafe.domain.map.response.MapResponse;
import com.toy.platform.cafe.request.DjangoFeignClient;
import com.toy.platform.cafe.response.DjangoApiResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FeignFallback implements DjangoFeignClient {

    private final Throwable cause;

    public FeignFallback(Throwable cause) {
        log.error(cause.getLocalizedMessage());
        if (cause instanceof RuntimeException && "Hystrix circuit short-circuited and is OPEN".equals(cause.getLocalizedMessage())) {
        }
        this.cause = cause;
    }

    /**
     * 테스트 통신
     *
     * @return
     */
    @Override
    public DjangoApiResponse<List<MapResponse.Info>> getMostSimilarLocation(String originalFileName) {
        return null;
    }

//    @Override
//    public ResponseEntity<PaymentCancelConvertFormResponse> cancelFormRequest(PgModuleCancelRequest paymentClientCancelRequest) {
//        return null;
//    }

}
