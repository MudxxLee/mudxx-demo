package com.mudxx.demo.boot.statemachine.config;

import com.mudxx.demo.boot.statemachine.common.OrderStatus;
import com.mudxx.demo.boot.statemachine.common.OrderStatusChangeEvent;
import com.mudxx.demo.boot.statemachine.config.action.OrderStateMachineAction;
import com.mudxx.demo.boot.statemachine.config.action.OrderStateMachineErrorAction;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;
import java.util.Optional;

/**
 * @author laiw
 * @date 2023/10/16 15:39
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderStatusChangeEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(false)
                .listener(listener());
    }

    /**
     * 配置状态
     *
     * @param states 状态
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 配置状态转换事件关系
     *
     * @param transitions 转换
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                //支付事件:待支付-》待发货
                .withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.WAIT_DELIVER).event(OrderStatusChangeEvent.PAYED)
                .and()
                //发货事件:待发货-》待收货
                .withExternal().source(OrderStatus.WAIT_DELIVER).target(OrderStatus.WAIT_RECEIVE).event(OrderStatusChangeEvent.DELIVERY)
                .and()
                //收货事件:待收货-》已完成
                .withExternal().source(OrderStatus.WAIT_RECEIVE).target(OrderStatus.FINISH).event(OrderStatusChangeEvent.RECEIVED);
    }

    private StateMachineListener<OrderStatus, OrderStatusChangeEvent> listener() {
        return new StateMachineListenerAdapter<OrderStatus, OrderStatusChangeEvent>() {
            @Override
            public void transition(Transition<OrderStatus, OrderStatusChangeEvent> transition) {
                System.out.println("move from:{" + ofNullableState(transition.getSource()) + "} " +
                        "to:{" + ofNullableState(transition.getTarget()) + "}");
            }

            @Override
            public void stateChanged(State<OrderStatus, OrderStatusChangeEvent> from, State<OrderStatus, OrderStatusChangeEvent> to) {
                if (null != from) {
                    System.out.println("State change from " + from.getId() + " to " + to.getId());
                } else {
                    System.out.println("State change to " + to.getId());
                }
            }

            @Override
            public void eventNotAccepted(Message<OrderStatusChangeEvent> event) {
                System.err.println("event not accepted: {" + event + "}");
            }

            private Object ofNullableState(State s) {
                return Optional.ofNullable(s)
                        .map(State::getId)
                        .orElse(null);
            }
        };
    }

}
