package com.mudxx.demo.boot.web.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/4/3 13:37
 */
@ApiModel("用户-新增表单对象")
@Data
public class PersonForm implements Serializable {
    private static final long serialVersionUID = 864969630125235709L;
    @ApiModelProperty(name = "姓名", required = true)
    private String name;
    @ApiModelProperty(value = "年龄", required = true)
    private int age;
}
