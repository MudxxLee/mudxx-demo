package com.mudxx.demo.boot.web.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/4/3 13:37
 */
@ApiModel("用户-分页查询对象")
@Data
public class PersonQuery implements Serializable {
    private static final long serialVersionUID = 864969630125235709L;
    @ApiModelProperty(value = "姓名", required = true)
    private String name;
}
