package com.toy.platform.cafe.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * 요청 및 응답 통신 기본 설정
 */
@Configuration
public class WebMvcConfig {

    /**
     * 응답 시 시간 규약 설정
     *
     * ISO 타입
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone("Asia/Seoul");
            /* LocalDate 및 LocalDateTime */
            jacksonObjectMapperBuilder.serializers(new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
            jacksonObjectMapperBuilder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
        };
    }
}