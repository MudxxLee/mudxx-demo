package com.mudxx.demo.boot.web.modules.service.message.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.mudxx.demo.boot.web.modules.dao.message.BizMessageIdempotent;
import com.mudxx.demo.boot.web.modules.dao.message.mapper.BizMessageIdempotentRepository;
import com.mudxx.demo.boot.web.modules.service.message.IBizMessageIdempotentService;
import com.mudxx.demo.boot.web.modules.service.message.bo.BizMessageIdempotentBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author laiw
 * @date 2023/4/3 15:42
 */
@Slf4j
@Service
public class BizMessageIdempotentServiceImpl implements IBizMessageIdempotentService {

    @Autowired
    private BizMessageIdempotentRepository bizMessageIdempotentRepository;

    @Override
    @Cacheable(cacheNames = "message:findCache#60", key = "#msgUniqKey", unless = "#result == null")
    public BizMessageIdempotentBo findByMsgUniqKey(String msgUniqKey) {
        log.info("findByMsgUniqKey: {}", msgUniqKey);
        BizMessageIdempotent idempotent = bizMessageIdempotentRepository.findByMsgUniqKey(msgUniqKey);
        if(idempotent == null) {
            return null;
        }
        BizMessageIdempotentBo idempotentBo = new BizMessageIdempotentBo();
        BeanUtil.copyProperties(idempotent, idempotentBo, CopyOptions.create().setIgnoreNullValue(true));
        return idempotentBo;
    }

}
