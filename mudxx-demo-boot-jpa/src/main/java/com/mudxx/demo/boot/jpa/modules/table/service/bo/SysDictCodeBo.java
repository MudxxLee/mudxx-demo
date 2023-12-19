package com.mudxx.demo.boot.jpa.modules.table.service.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author laiw
 * @date 2023/11/20 10:50
 */
public class SysDictCodeBo implements Serializable {
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
    /**
     * 等级 等级（0：根、1：一级、2：二级、3：三级依次类推）
     */
    private Integer grade;
    /**
     * 上级ID
     */
    private String pid;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 启用状态 启用状态 （0：禁用；1：启用）
     */
    private Integer status;
    /**
     * 删除状态 删除状态 （0：正常；1：删除）
     */
    private Integer isDel;
    /**
     * 删除人
     */
    private String delBy;
    /**
     * 删除时间
     */
    private Date delDate;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public Integer getDel() {
        return isDel;
    }

    public void setDel(Integer del) {
        isDel = del;
    }

    public String getDelBy() {
        return delBy;
    }

    public void setDelBy(String delBy) {
        this.delBy = delBy;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
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
