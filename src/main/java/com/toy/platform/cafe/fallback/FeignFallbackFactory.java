package com.toy.platform.cafe.fallback;

import com.toy.platform.cafe.request.DjangoFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignFallbackFactory implements FallbackFactory<DjangoFeignClient> {

    @Override
    public DjangoFeignClient create(Throwable cause) {
        return new FeignFallback(cause);
    }
}