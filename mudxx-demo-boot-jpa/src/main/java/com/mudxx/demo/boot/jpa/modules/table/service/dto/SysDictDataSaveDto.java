package com.mudxx.demo.boot.jpa.modules.table.service.dto;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/20 10:59
 */
public class SysDictDataSaveDto implements Serializable {
    private static final long serialVersionUID = -41753217443687619L;
    /**
     * 字典编码ID 关联字段
     */
    private String sysDictCodeId;
    /**
     * 名称
     */
    private String name;
    /**
     * 名称（英文）
     */
    private String nameEn;
    /**
     * 值
     */
    private String value;

    public String getSysDictCodeId() {
        return sysDictCodeId;
    }

    public void setSysDictCodeId(String sysDictCodeId) {
        this.sysDictCodeId = sysDictCodeId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
