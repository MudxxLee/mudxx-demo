package com.mudxx.demo.boot.web.api.message;

import com.mudxx.common.web.response.CommonResult;
import com.mudxx.demo.boot.web.modules.service.message.IBizMessageIdempotentService;
import com.mudxx.demo.boot.web.modules.service.message.bo.BizMessageIdempotentBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiw
 * @date 2023/4/3 15:56
 */
@Api(tags = "消息幂等记录管理")
@RestController
@RequestMapping("/api/message/idt")
public class BizMessageIdempotentController {

    @Autowired
    private IBizMessageIdempotentService bizMessageIdempotentService;

    @GetMapping("find")
    @ApiOperation(value = "查询")
    public CommonResult<BizMessageIdempotentBo> find(String msgUniqKey) {
        BizMessageIdempotentBo idempotentBo = bizMessageIdempotentService.findByMsgUniqKey(msgUniqKey);
        return CommonResult.success(idempotentBo);
    }

    @GetMapping("findCache")
    @ApiOperation(value = "查询")
    public CommonResult<BizMessageIdempotentBo> findCache(String msgUniqKey) {
        BizMessageIdempotentBo idempotentBo = bizMessageIdempotentService.findByMsgUniqKey(msgUniqKey);
        return CommonResult.success(idempotentBo);
    }

}
