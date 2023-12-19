package com.mudxx.demo.boot.jpa.modules.table.service.vo;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/20 10:59
 */
public class SysDictDataSimpleVo implements Serializable {
    private static final long serialVersionUID = -41753217443687619L;
    /**
     * 主键
     */
    private String id;
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
    /**
     * 启用状态 启用状态 （0：禁用；1：启用）
     */
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
