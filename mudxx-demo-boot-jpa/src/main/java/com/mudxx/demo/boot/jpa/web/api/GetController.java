package com.mudxx.demo.boot.jpa.web.api;

import com.mudxx.demo.boot.jpa.web.vo.FileVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/get")
@RestController
public class GetController {

    @RequestMapping(value = "/okhttp3/get/params", method = RequestMethod.GET)
    public String getParams(@RequestParam(value = "param1") String param1, @RequestParam("param2") String param2) {
        System.out.println("==>/okhttp3/get/params, param1: " + param1 + ", param2: " + param2);

        // 处理业务逻辑
        return "/okhttp3/get/params SUCCESS!";
    }

    @RequestMapping(value = "/okhttp3/get/form", method = RequestMethod.GET)
    public String getForm(FileVO fileVO) {
        System.out.println("==>/okhttp3/get/form, fileVO: " + fileVO);

        // 处理业务逻辑
        return "/okhttp3/get/form SUCCESS";
    }
}

