package com.mudxx.demo.boot.web.api.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/4/3 13:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {
    private static final long serialVersionUID = 864969630125235709L;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
}
