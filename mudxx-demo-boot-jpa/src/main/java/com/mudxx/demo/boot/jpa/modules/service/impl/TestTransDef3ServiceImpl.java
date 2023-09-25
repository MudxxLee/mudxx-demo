package com.mudxx.demo.boot.jpa.modules.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.mudxx.demo.boot.jpa.modules.service.ITestTransDef3Service;
import com.mudxx.demo.boot.jpa.modules.service.ITestTransDefService;
import com.mudxx.demo.boot.jpa.modules.table.dao.PlatformActionLog;
import com.mudxx.demo.boot.jpa.modules.table.dto.PlatformActionLogForm;
import com.mudxx.demo.boot.jpa.modules.table.service.IPlatformActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author laiw
 * @date 2023/9/8 10:14
 */
@Service
public class TestTransDef3ServiceImpl implements ITestTransDef3Service {

    @Autowired
    private IPlatformActionLogService platformActionLogService;

    @Transactional(rollbackOn = Exception.class, value = Transactional.TxType.REQUIRES_NEW)
    @Override
    public void test3() {
        /*
         * 设置事务传播属性：PROPAGATION_REQUIRES_NEW（如果当前存在事务，那么将当前的事务挂起，并开启一个新事务去执行）
         * 1.外层事务不会影响内部事务的提交/回滚
         * 2.内部事务出现异常会影响外部事务的回滚
         */
        PlatformActionLogForm logForm = new PlatformActionLogForm.Builder()
                .ip("http://127.0.0.1:8080/test")
                .uri("test1111")
                .operateDate(new Date())
                .requestId(IdUtil.simpleUUID())
                .build();
        platformActionLogService.add(logForm);

        int i = 1 / 0;

    }

    @Transactional(rollbackOn = Exception.class, value = Transactional.TxType.REQUIRES_NEW)
    @Override
    public void test4(Long id, String uri) {
        PlatformActionLog log = platformActionLogService.findById(id);
        System.out.println("22: " + JSONUtil.toJsonStr(log));
        platformActionLogService.update(id, uri);
        log = platformActionLogService.findById(id);
        System.out.println("33: " + JSONUtil.toJsonStr(log));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void test5(Long id, String uri) {
        platformActionLogService.update(id, uri);
    }


}
