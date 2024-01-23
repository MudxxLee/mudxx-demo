package com.mudxx.demo.boot.page.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author laiw
 * @date 2023/4/3 14:57
 */
@Controller
public class HelloController {

    @RequestMapping(value = "/demo")
    public String findOneCity(ModelMap map) {
        return "/index";
    }

}

