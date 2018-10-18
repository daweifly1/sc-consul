package com.xiaojun.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fp295 on 2018/4/17.
 */
@RestController
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/hi")
    protected Map<String, Object> sayHi() {
        Map m = new HashMap();
        m.put("hei", "ddd");
        return m;
    }
}
