package com.toy.platform.cafe.request;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.toy.platform.cafe.config.FeignConfig;
import com.toy.platform.cafe.domain.map.response.MapResponse;
import com.toy.platform.cafe.fallback.FeignFallbackFactory;
import com.toy.platform.cafe.response.DjangoApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@FeignClient(name="DjangoApiFeign",
        url="${django.api-url}",
        configuration = {FeignConfig.class},
        fallbackFactory = FeignFallbackFactory.class)
public interface DjangoFeignClient {

//    @HystrixCommand(commandKey = "getMerchantAvailablePayMethod")
//    @PostMapping(value = "/api/v1/merchants-available-pay-methods/merchant-id/{merchantId}/pay-method/{payMethod}/detail", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    DataApiBasicResponse<MerchantAvailablePayMethodResponse> getMerchantAvailablePayMethod(@PathVariable("merchantId") Long merchantId,
//                                                                                           @PathVariable("payMethod") String payMethodName,
//                                                                                           @RequestParam("isActive") String isActive);

//    @HystrixCommand(commandKey = "saveWechatPaymentRequest")
//    @PostMapping(value = "/api/v1/wechat/payment-request", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    DataApiBasicResponse saveWechatPaymentRequest(@RequestBody WechatRequest.WechatSavePaymentRequest saveRequest);

    /**
     * 테스트 통신
     *
     * @return
     */
    @HystrixCommand(commandKey = "testRequest")
    @GetMapping(value = "/test/{img}", produces = MediaType.APPLICATION_JSON_VALUE)
    DjangoApiResponse<List<MapResponse.Info>> getMostSimilarLocation(@PathVariable("img") String imgFileName);
}
