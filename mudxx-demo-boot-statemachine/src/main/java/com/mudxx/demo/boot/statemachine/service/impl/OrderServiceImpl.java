package com.mudxx.demo.boot.statemachine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mudxx.demo.boot.statemachine.common.OrderStatus;
import com.mudxx.demo.boot.statemachine.common.OrderStatusChangeEvent;
import com.mudxx.demo.boot.statemachine.dao.Order;
import com.mudxx.demo.boot.statemachine.dao.mapper.OrderMapper;
import com.mudxx.demo.boot.statemachine.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author laiw
 * @date 2023/10/16 15:45
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;
    @Resource
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, String> stateMachineMemPersister;
    @Resource
    private OrderMapper orderMapper;

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @Override
    public Order create(Order order) {
        order.setStatus(OrderStatus.WAIT_DELIVER.getKey());
        orderMapper.insert(order);
        return order;
    }

    /**
     * 对订单进行支付
     *
     * @param id
     * @return
     */
    @Override
    public Order pay(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},尝试支付，订单号：{}", Thread.currentThread().getName(), id);
        if (!sendEvent(OrderStatusChangeEvent.PAYED, order)) {
            log.error("线程名称：{},支付失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("支付失败, 订单状态异常");
        }
        return order;
    }

    /**
     * 对订单进行发货
     *
     * @param id
     * @return
     */
    @Override
    public Order deliver(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},尝试发货，订单号：{}", Thread.currentThread().getName(), id);
        if (!sendEvent(OrderStatusChangeEvent.DELIVERY, order)) {
            log.error("线程名称：{},发货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("发货失败, 订单状态异常");
        }
        return order;
    }

    /**
     * 对订单进行确认收货
     *
     * @param id
     * @return
     */
    @Override
    public Order receive(Long id) {
        Order order = orderMapper.selectById(id);
        log.info("线程名称：{},尝试收货，订单号：{}", Thread.currentThread().getName(), id);
        if (!sendEvent(OrderStatusChangeEvent.RECEIVED, order)) {
            log.error("线程名称：{},收货失败, 状态异常，订单信息：{}", Thread.currentThread().getName(), order);
            throw new RuntimeException("收货失败, 订单状态异常");
        }
        return order;
    }

    /**
     * 发送订单状态转换事件 这里不要使用synchronized锁方法，效率比较低，分布式系统优先采用分布式锁，下单锁userId，订单状态流转锁orderId根据业务考虑使用什么。
     *
     * @param changeEvent
     * @param order
     * @return
     */
    private synchronized boolean sendEvent(OrderStatusChangeEvent changeEvent, Order order) {
        boolean result = false;
        try {
            orderStateMachine.start();
            //尝试恢复状态机状态
            stateMachineMemPersister.restore(orderStateMachine, String.valueOf(order.getId()));
            Message<OrderStatusChangeEvent> message = MessageBuilder.withPayload(changeEvent).setHeader("order", order).build();
            result = orderStateMachine.sendEvent(message);
            //持久化状态机状态
            stateMachineMemPersister.persist(orderStateMachine, String.valueOf(order.getId()));
        } catch (Exception e) {
            log.error("订单操作失败", e);
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}
