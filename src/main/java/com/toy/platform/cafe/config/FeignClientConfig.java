package com.toy.platform.cafe.config;

import feign.Client;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Request and Response with Django API Service
 */
@Slf4j
public class FeignClientConfig extends Client.Default {

    public FeignClientConfig(SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier) {
        super(sslSocketFactory, hostnameVerifier);
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {

        int responseStatus = 200;
        Response response = super.execute(request, options);
        InputStream bodyStream = response.body().asInputStream();
        if (response.status() < 500) {
            responseStatus = 200;
        }
        String responseBody = StreamUtils.copyToString(bodyStream, StandardCharsets.UTF_8);
        return response.toBuilder().body(responseBody, StandardCharsets.UTF_8).status(responseStatus).build();
    }
}