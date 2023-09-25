package com.mudxx.demo.anti.brush.web.api;


import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.anti.brush.security.anno.AntiBrush;
import com.mudxx.demo.anti.brush.security.anno.TestSpEL;
import com.mudxx.demo.anti.brush.web.entity.AEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author laiw
 * @date 2023/3/31 14:03
 */
@Component
public class TestService {

    @TestSpEL(model = "#no")
    public void test002(AEntity a, String no) {

    }

}
