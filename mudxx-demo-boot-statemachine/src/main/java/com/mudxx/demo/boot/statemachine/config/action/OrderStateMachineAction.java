package com.mudxx.demo.boot.statemachine.config.action;

import com.mudxx.demo.boot.statemachine.common.OrderStatus;
import com.mudxx.demo.boot.statemachine.common.OrderStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * @author laiw
 * @date 2023/10/17 11:48
 */
@Slf4j
public class OrderStateMachineAction implements Action<OrderStatus, OrderStatusChangeEvent> {

    @Override
    public void execute(StateContext<OrderStatus, OrderStatusChangeEvent> context) {
        log.info("status: {}, event: {}", context.getTarget(), context.getEvent());
    }

}
