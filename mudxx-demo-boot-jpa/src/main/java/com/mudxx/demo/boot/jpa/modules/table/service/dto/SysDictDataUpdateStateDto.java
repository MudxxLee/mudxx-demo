package com.mudxx.demo.boot.jpa.modules.table.service.dto;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/20 10:59
 */
public class SysDictDataUpdateStateDto implements Serializable {
    private static final long serialVersionUID = -41753217443687619L;
    /**
     * 主键
     */
    private String id;
    /**
     * 启用|禁用 （Enable：启用；Disable：禁用）
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
