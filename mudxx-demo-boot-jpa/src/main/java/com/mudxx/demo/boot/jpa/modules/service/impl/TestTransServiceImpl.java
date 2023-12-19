package com.mudxx.demo.boot.jpa.modules.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.mudxx.demo.boot.jpa.modules.service.ITestTransDef3Service;
import com.mudxx.demo.boot.jpa.modules.service.ITestTransDefService;
import com.mudxx.demo.boot.jpa.modules.service.ITestTransService;
import com.mudxx.demo.boot.jpa.modules.table.dao.PlatformActionLog;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.PlatformActionLogForm;
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
public class TestTransServiceImpl implements ITestTransService {

    @Autowired
    private ITestTransDefService testTransDefService;
    @Autowired
    private ITestTransDef3Service testTransDef3Service;

    @Autowired
    private IPlatformActionLogService platformActionLogService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void test001() {
        PlatformActionLogForm logForm = new PlatformActionLogForm.Builder()
                .ip("http://127.0.0.1:8080/test")
                .uri("test001")
                .operateDate(new Date())
                .requestId(IdUtil.simpleUUID())
                .build();
        platformActionLogService.add(logForm);

        testTransDefService.test();
        int i = 1 / 0;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void test002() {
        PlatformActionLogForm logForm = new PlatformActionLogForm.Builder()
                .ip("http://127.0.0.1:8080/test")
                .uri("test002")
                .operateDate(new Date())
                .requestId(IdUtil.simpleUUID())
                .build();
        platformActionLogService.add(logForm);

        testTransDef3Service.test3();

//        int i = 1 / 0;
    }

//    @Transactional(rollbackOn = Exception.class)
    @Override
    public void test003() {
        PlatformActionLog log = platformActionLogService.findById(6639L);
        System.out.println("11: " + JSONUtil.toJsonStr(log));
        testTransDef3Service.test4(log.getId(), "test-" + System.currentTimeMillis());
        log = platformActionLogService.findById(log.getId());
        System.out.println("44: " + JSONUtil.toJsonStr(log));
        this.aVoid();
    }

    private void aVoid() {
        PlatformActionLog log = platformActionLogService.findById(6639L);
        System.out.println("55: " + JSONUtil.toJsonStr(log));
    }
}
