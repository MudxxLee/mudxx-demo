package com.mudxx.demo.boot.web.api.demo.param;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonAlias(value = {"name_", "xm", "x_m"})
    @JsonProperty(value = "Name")
    private String name;
    @ApiModelProperty(value = "年龄", required = true)
    private int age;
}


