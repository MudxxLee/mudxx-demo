package com.mudxx.demo.boot.statemachine.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mudxx.demo.boot.statemachine.dao.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 测试-订单表 Mapper 接口
 * </p>
 *
 * @author laiwen
 * @since 2023-10-16 16:07
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
