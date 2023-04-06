package com.mudxx.demo.boot.quartz.web.api;

import com.mudxx.common.web.response.CommonPage;
import com.mudxx.demo.boot.quartz.database.service.IQuartzQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiw
 * @date 2023/4/6 09:19
 */
@RestController
@RequestMapping("/api/quartz/query/")
public class QuartzQueryController {

    @Autowired
    private IQuartzQueryService quartzQueryService;

    @GetMapping("page")
    @ResponseBody
    public CommonPage<?> page(Integer pageNum, Integer pageSize) {
        return quartzQueryService.selectPageJobDetails(pageNum, pageSize);
    }

}
