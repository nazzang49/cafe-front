package com.toy.platform.cafe.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.time.format.DateTimeFormatter;

/**
 * 요청 및 응답 통신 기본 설정
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

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

    /**
     * static 파일 경로 설정
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/public/**").addResourceLocations("classpath:static/public/");
    }

//    @Bean
//    public ViewResolver htmlViewResolver() {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
//        resolver.setContentType("text/html; charset=UTF-8");
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setViewNames(new String[] {"*.html"});
//        return resolver;
//    }
//
//    private ITemplateResolver htmlTemplateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setPrefix("/templates/");
//        resolver.setCacheable(false);
//        resolver.setTemplateMode(TemplateMode.HTML);
//        return resolver;
//    }
}