package com.mudxx.demo.boot.jpa.modules.table.service.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author laiw
 * @date 2023/11/20 10:59
 */
public class SysDictDataBo implements Serializable {
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
     * 排序
     */
    private Integer sort;
    /**
     * 启用状态 启用状态 （0：禁用；1：启用）
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateDate;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
