package com.toy.platform.cafe.domain.favicon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * favicon.ico 404 처리
 */
@Controller
public class FaviconController {

    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
    }
}
