package com.mudxx.demo.boot.redis.web.api.test;

import lombok.Data;

import java.util.Map;

/**
 * @author laiw
 * @date 2023/8/15 13:38
 */
@Data
public class UserDto {

    private String name;

    private Map<String, String> extInfo;

    private String test;

}
