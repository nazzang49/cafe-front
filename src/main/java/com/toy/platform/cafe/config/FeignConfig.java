package com.toy.platform.cafe.config;

import feign.Client;
import feign.Contract;
import feign.Logger;
import feign.Request;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Request and Response with Django API Service
 */
@Slf4j
public class FeignConfig {

    @Bean
    public Client client() {
        return new FeignClientConfig(null, null);
    }

    @Bean
    public Contract contract() {
        return new SpringMvcContract();
    }

    @Bean
    public Logger.Level logger() {
        return Logger.Level.FULL;
    }

    @Bean
    public Encoder encoder() {
        return new CustomEncoder(feignHttpMessageConverter());
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(20000, 100000, true);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            return new Exception(response.body().toString());
        };
    }

    /**
     * JSON 컨버팅
     * 
     * @return
     */
    private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new CustomMessageConverter());
        return new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return httpMessageConverters;
            }
        };
    }

    public class CustomMessageConverter extends MappingJackson2HttpMessageConverter {
        CustomMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.ALL);
            setSupportedMediaTypes(mediaTypes);
        }
    }
}