package com.toy.platform.cafe.config;

import feign.RequestTemplate;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.encoding.HttpEncoding;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.http.MediaType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Objects;

@Slf4j
public class CustomEncoder extends SpringEncoder {

    private final SpringFormEncoder springFormEncoder = new SpringFormEncoder();

    public CustomEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) {
        Collection<String> contentTypes = request.headers().get(HttpEncoding.CONTENT_TYPE);
        MediaType requestContentType = null;
        if (contentTypes != null && !contentTypes.isEmpty()) {
            String type = contentTypes.iterator().next();
            requestContentType = MediaType.valueOf(type);
        }

        if (Objects.equals(requestContentType, MediaType.APPLICATION_FORM_URLENCODED)) {
            this.springFormEncoder.encode(requestBody, bodyType, request);
        } else {
            super.encode(requestBody, bodyType, request);
        }
    }
}