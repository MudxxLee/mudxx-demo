package com.mudxx.demo.boot.jpa.modules.table.service;

import com.mudxx.demo.boot.jpa.modules.table.dao.SysDictData;
import com.mudxx.demo.boot.jpa.modules.table.model.SysDictDataGroupModel;
import com.mudxx.demo.boot.jpa.modules.table.service.bo.SysDictDataBo;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataSaveDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataUpdateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataUpdateStateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.vo.SysDictDataSimpleVo;

import java.util.List;
import java.util.Map;

public interface ISysDictDataService {

    SysDictData findById(String id, boolean exists);

    SysDictDataBo findAndCheckById(String id, boolean enable);

    /**
     * 获取数据列表
     *
     * @param sysDictCodeId 字典编码ID
     * @param enable        是否启用
     * @return List
     */
    List<SysDictDataSimpleVo> findBySysDictCodeId(String sysDictCodeId, boolean enable);

    /**
     * 获取所有可用的字典数据
     * <p>
     * 按code分组,并取value成为列表
     *
     * @return Map
     */
    Map<String, List<SysDictDataGroupModel>> findAllUsableGroupData();

    /**
     * 新增
     *
     * @param saveDto  表单
     * @param createBy 创建人
     */
    void saveData(SysDictDataSaveDto saveDto, String createBy);

    /**
     * 修改
     *
     * @param updateDto 表单
     * @param updateBy  修改人
     */
    void updateData(SysDictDataUpdateDto updateDto, String updateBy);

    /**
     * 启用|禁用 （Enable：启用；Disable：禁用）
     *
     * @param updateStateDto 表单
     * @param updateBy       修改人
     */
    void updateState(SysDictDataUpdateStateDto updateStateDto, String updateBy);

}
