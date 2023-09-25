package com.mudxx.demo.boot.jpa.modules.service.impl;

import cn.hutool.core.util.IdUtil;
import com.mudxx.demo.boot.jpa.modules.service.ITestTransDefService;
import com.mudxx.demo.boot.jpa.modules.table.dto.PlatformActionLogForm;
import com.mudxx.demo.boot.jpa.modules.table.service.IPlatformActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

/**
 * @author laiw
 * @date 2023/9/8 10:14
 */
@Service
public class TestTransDefServiceImpl implements ITestTransDefService {

    @Autowired
    private DefaultTransactionDefinition transactionDefinition;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private IPlatformActionLogService platformActionLogService;

    @Override
    public void test() {
        TransactionStatus transactionStatus = null;
        try {
            /*
             * 设置事务的传播属性为PROPAGATION_REQUIRES_NEW，会新开启事务
             * 1.外层事务不会影响内部事务的提交/回滚
             * 2.内部事务出现异常会影响外部事务的回滚
             */
            transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            // 设置事务的隔离级别为ISOLATION_READ_COMMITTED，防止insert语句产生的间隙锁而死锁
            transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
            transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
            PlatformActionLogForm logForm = new PlatformActionLogForm.Builder()
                    .ip("http://127.0.0.1:8080/test")
                    .uri("test1111")
                    .operateDate(new Date())
                    .requestId(IdUtil.simpleUUID())
                    .build();
            platformActionLogService.add(logForm);

            // 手动提交事务
            platformTransactionManager.commit(transactionStatus);

            int i = 1/ 0;

        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚
            if (transactionStatus != null && !transactionStatus.isCompleted()) {
                platformTransactionManager.rollback(transactionStatus);
            }
            throw new RuntimeException();
        }
    }

}
