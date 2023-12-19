package com.mudxx.demo.boot.statemachine.dao;

import com.baomidou.mybatisplus.annotation.*;
import com.mudxx.common.utils.json.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 测试-订单表
 * </p>
 *
 * @author laiwen
 * @since 2023-10-16 16:07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tb_test_order")
@ApiModel(value = "TestOrder对象", description = "测试-订单表")
public class Order {

    public static void main(String[] args) {
        System.out.println(JsonUtil.toString(new Order()));
    }

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单编码")
    @TableField("order_code")
    private String orderCode;

    @ApiModelProperty("订单状态")
    @TableField("`status`")
    private Integer status;

    @ApiModelProperty("订单名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("删除标记，0未删除  1已删除")
    @TableField("delete_flag")
    private Integer deleteFlag;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty("创建人")
    @TableField("create_user_code")
    private String createUserCode;

    @ApiModelProperty("更新人")
    @TableField("update_user_code")
    private String updateUserCode;

    @ApiModelProperty("版本号")
    @TableField("version")
    @Version
    private Integer version;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
