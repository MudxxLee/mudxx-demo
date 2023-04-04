package com.mudxx.demo.mq.rocketmq.consumer.common.processor;


import com.mudxx.demo.mq.rocketmq.common.RocketMqCommonMessageExt;

/**
 * @author laiwen
 */
public interface IBizCommonSampleProcessor {

	/**
	 * 消费消息
	 * @param messageExt 消息
	 * @return
	 */
	boolean idempotentConsume(RocketMqCommonMessageExt messageExt);

	/**
	 * 消费消息
	 * @param messageExt 消息
	 * @return
	 */
	boolean commonConsume(RocketMqCommonMessageExt messageExt);

}