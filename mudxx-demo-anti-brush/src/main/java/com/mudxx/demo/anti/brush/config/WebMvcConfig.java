package com.mudxx.demo.anti.brush.config;

import com.mudxx.demo.anti.brush.intercept.AntiBrushIntercept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author laiw
 * @date 2023/4/28 16:40
 */
@Slf4j
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AntiBrushIntercept antiBrushIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(antiBrushIntercept);
    }
}
