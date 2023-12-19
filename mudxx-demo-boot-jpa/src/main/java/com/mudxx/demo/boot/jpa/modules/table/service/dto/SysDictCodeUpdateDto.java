package com.mudxx.demo.boot.jpa.modules.table.service.dto;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/20 10:50
 */
public class SysDictCodeUpdateDto implements Serializable {
    private static final long serialVersionUID = -831213034020392900L;
    /**
     * ID
     */
    private String id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 名称（英文）
     */
    private String nameEn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

}
