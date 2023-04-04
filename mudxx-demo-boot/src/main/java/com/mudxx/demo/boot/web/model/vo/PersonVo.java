package com.mudxx.demo.boot.web.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/4/3 13:39
 */
@ApiModel("用户-返回对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonVo implements Serializable {
    private static final long serialVersionUID = 864969630125235709L;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "年龄")
    private int age;
}
