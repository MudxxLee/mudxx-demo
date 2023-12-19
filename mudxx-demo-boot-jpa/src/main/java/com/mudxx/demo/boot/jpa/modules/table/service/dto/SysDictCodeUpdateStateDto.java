package com.mudxx.demo.boot.jpa.modules.table.service.dto;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/20 10:50
 */
public class SysDictCodeUpdateStateDto implements Serializable {
    private static final long serialVersionUID = -831213034020392900L;
    /**
     * ID
     */
    private String id;
    /**
     * 启用|禁用|删除 （Enable：启用；Disable：禁用；Delete：删除）
     */
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
