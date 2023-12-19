package com.mudxx.demo.boot.statemachine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mudxx.demo.boot.statemachine.dao.Order;

/**
 * @author laiw
 * @date 2023/10/16 15:45
 */
public interface OrderService extends IService<Order> {
    Order create(Order order);

    Order pay(Long id);

    Order deliver(Long id);

    Order receive(Long id);
}