package com.mudxx.demo.boot.statemachine.common;

/**
 * @author laiw
 * @date 2023/10/16 15:38
 */
public enum OrderStatusChangeEvent {
    // 创建，支付，发货，确认收货
    CREATE, PAYED, DELIVERY, RECEIVED;
}
