package com.mudxx.demo.boot.jpa.modules.table.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author laiw
 * @date 2023/11/20 10:44
 */
@Entity
@ApiModel("SYS-字典编码")
@Table(name = "sys_dict_code")
public class SysDictCode implements Serializable {
    private static final long serialVersionUID = 1587166930039594207L;
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    @Column(length = 125, nullable = false)
    private String id;

    @ApiModelProperty(value = "编码", required = true, example = "编码1")
    @Column(length = 125, nullable = false)
    private String code;

    @ApiModelProperty(value = "名称", required = true, example = "名称1")
    @Column(length = 64, nullable = false)
    private String name;

    @ApiModelProperty(value = "名称（英文）", required = true, example = "名称（英文）1")
    @Column(name = "name_en", length = 64, nullable = false)
    private String nameEn;

    @ApiModelProperty(value = "等级", required = true, example = "123")
    @Column(nullable = false)
    private Integer grade;

    @ApiModelProperty(value = "上级ID", example = "e2acdb6dd8fc7b7b")
    @Column(length = 125)
    private String pid;

    @ApiModelProperty(value = "排序", example = "123")
    private Integer sort;

    @ApiModelProperty(value = "启用状态", required = true, example = "1")
    @Column(nullable = false)
    private Integer status;

    @ApiModelProperty(value = "删除状态", required = true, example = "1")
    @Column(name = "is_del", nullable = false)
    private Integer isDel;

    @ApiModelProperty(value = "删除人", example = "删除人1")
    @Column(name = "del_by", length = 125)
    private String delBy;

    @ApiModelProperty(value = "删除时间", example = "2023-11-13 23:59:46")
    @Column(name = "del_date")
    private Date delDate;

    @ApiModelProperty(value = "创建人", required = true, example = "创建人1")
    @Column(name = "create_by", length = 125, nullable = false)
    private String createBy;

    @ApiModelProperty(value = "创建时间", required = true, example = "2023-11-16 23:59:46")
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @ApiModelProperty(value = "修改人", example = "修改人1")
    @Column(name = "update_by", length = 125)
    private String updateBy;

    @ApiModelProperty(value = "修改时间", example = "2023-11-18 23:59:46")
    @Column(name = "update_date")
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
