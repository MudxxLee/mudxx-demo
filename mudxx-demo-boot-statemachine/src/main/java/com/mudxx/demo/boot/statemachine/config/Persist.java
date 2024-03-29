package com.mudxx.demo.boot.statemachine.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laiw
 * @date 2023/10/16 15:40
 */
@Configuration
@Slf4j
public class Persist<E, S> {
    /**
     * 持久化到内存map中
     *
     * @return
     */
    @Bean(name = "stateMachineMemPersister")
    public static StateMachinePersister getPersister() {
        return new DefaultStateMachinePersister(new StateMachinePersist() {
            private final Map<Object, StateMachineContext> map = new HashMap<>();

            @Override
            public void write(StateMachineContext context, Object contextObj) {
                log.info("持久化状态机,context:{},contextObj:{}", JSON.toJSONString(context), JSON.toJSONString(contextObj));
                map.put(contextObj, context);
            }

            @Override
            public StateMachineContext read(Object contextObj) {
                log.info("获取状态机,contextObj:{}", JSON.toJSONString(contextObj));
                StateMachineContext stateMachineContext = map.get(contextObj);
                log.info("获取状态机结果,stateMachineContext:{}", JSON.toJSONString(stateMachineContext));
                return stateMachineContext;
            }
        });
    }

//    @Resource
//    private RedisConnectionFactory redisConnectionFactory;
//
//    /**
//     * 持久化到redis中，在分布式系统中使用
//     *
//     * @return
//     */
//    @Bean(name = "stateMachineRedisPersister")
//    public RedisStateMachinePersister<E, S> getRedisPersister() {
//        RedisStateMachineContextRepository<E, S> repository = new RedisStateMachineContextRepository<>(redisConnectionFactory);
//        RepositoryStateMachinePersist<E, S> p = new RepositoryStateMachinePersist<>(repository);
//        return new RedisStateMachinePersister<>(p);
//    }
}
