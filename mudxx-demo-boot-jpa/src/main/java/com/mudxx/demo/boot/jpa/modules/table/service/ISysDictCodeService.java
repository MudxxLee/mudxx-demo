package com.mudxx.demo.boot.jpa.modules.table.service;

import com.mudxx.demo.boot.jpa.modules.table.dao.SysDictCode;
import com.mudxx.demo.boot.jpa.modules.table.service.bo.SysDictCodeBo;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeSaveDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeUpdateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeUpdateStateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.vo.SysDictCodeSimpleVo;

import java.util.List;

public interface ISysDictCodeService {

    SysDictCode findById(String id, boolean exists);

    SysDictCode findByCode(String code, boolean exists);

    SysDictCodeBo findAndCheckById(String id, boolean enable);

    SysDictCodeBo findAndCheckByCode(String code, boolean enable);

    /**
     * 懒加载
     *
     * @param pid 父ID
     * @return List
     */
    List<SysDictCodeSimpleVo> lazyLoading(String pid);

    /**
     * 新增
     *
     * @param saveDto  表单
     * @param createBy 创建人
     */
    void saveCode(SysDictCodeSaveDto saveDto, String createBy);

    /**
     * 修改
     *
     * @param updateDto 表单
     * @param updateBy  修改人
     */
    void updateCode(SysDictCodeUpdateDto updateDto, String updateBy);

    /**
     * 启用|禁用|删除 （Enable：启用；Disable：禁用；Delete：删除）
     *
     * @param updateStateDto 表单
     * @param updateBy       修改人
     */
    void updateState(SysDictCodeUpdateStateDto updateStateDto, String updateBy);

}
