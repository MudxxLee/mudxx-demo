package com.mudxx.demo.boot.statemachine.config.action;

import com.mudxx.demo.boot.statemachine.common.OrderStatus;
import com.mudxx.demo.boot.statemachine.common.OrderStatusChangeEvent;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * @author laiw
 * @date 2023/10/17 11:48
 */
public class OrderStateMachineErrorAction implements Action<OrderStatus, OrderStatusChangeEvent> {

    @Override
    public void execute(StateContext<OrderStatus, OrderStatusChangeEvent> context) {
        System.out.println(String.format("status: %s, event: %s", context.getTarget(), context.getEvent()));
    }

}
