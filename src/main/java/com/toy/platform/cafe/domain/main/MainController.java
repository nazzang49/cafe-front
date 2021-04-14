package com.toy.platform.cafe.domain.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = {"", "/", "main"})
public class MainController {

    @GetMapping
    public String main() {
        log.info("[ 메인 페이지 이동 ]");
        return "main";
    }
}
