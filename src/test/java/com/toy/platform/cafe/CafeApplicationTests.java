package com.toy.platform.cafe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLEncoder;

@SpringBootTest
class CafeApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void urlEncodeTest() {
        String query = "카페";
        System.out.println(URLEncoder.encode(query));

        query = "갈비";
        System.out.println(URLEncoder.encode(query));

        query = "성수동카페";
        System.out.println(URLEncoder.encode(query));
    }
}
