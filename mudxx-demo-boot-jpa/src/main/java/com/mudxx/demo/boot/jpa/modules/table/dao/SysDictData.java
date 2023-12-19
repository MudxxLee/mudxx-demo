package com.mudxx.demo.boot.jpa.modules.table.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author laiw
 * @date 2023/11/20 10:56
 */
@Entity
@ApiModel("SYS-字典数据")
@Table(name = "sys_dict_data")
public class SysDictData implements Serializable {
    private static final long serialVersionUID = -6754890523433756749L;
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    @ApiModelProperty(value = "主键", required = true, example = "5803d5840fbbcd42")
    @Column(length = 125, nullable = false)
    private String id;

    @ApiModelProperty(value = "字典编码ID", required = true, example = "字典编码1")
    @Column(name = "sys_dict_code_id", length = 125, nullable = false)
    private String sysDictCodeId;

    @ApiModelProperty(value = "名称", required = true, example = "名称1")
    @Column(length = 64, nullable = false)
    private String name;

    @ApiModelProperty(value = "名称（英文）", required = true, example = "名称（英文）1")
    @Column(name = "name_en", length = 64, nullable = false)
    private String nameEn;

    @ApiModelProperty(value = "值", required = true, example = "值1")
    @Column(length = 255, nullable = false)
    private String value;

    @ApiModelProperty(value = "排序", required = true, example = "123")
    @Column(nullable = false)
    private Integer sort;

    @ApiModelProperty(value = "启用状态", required = true, example = "1")
    @Column(nullable = false)
    private Integer status;

    @ApiModelProperty(value = "创建人", required = true, example = "创建人1")
    @Column(name = "create_by", length = 125, nullable = false)
    private String createBy;

    @ApiModelProperty(value = "创建时间", required = true, example = "2023-11-16 12:36:27")
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @ApiModelProperty(value = "修改人", example = "修改人1")
    @Column(name = "update_by", length = 125)
    private String updateBy;

    @ApiModelProperty(value = "修改时间", example = "2023-11-18 12:36:27")
    @Column(name = "update_date")
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
