package com.mudxx.demo.boot.jpa.modules.table.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mudxx.common.constants.enums.StateEnum;
import com.mudxx.common.constants.enums.StatusEnum;
import com.mudxx.common.exception.code.biz.BizErrorCode;
import com.mudxx.common.exception.code.biz.BizException;
import com.mudxx.common.exception.utils.VUtils;
import com.mudxx.common.utils.domain.EnumUtils;
import com.mudxx.demo.boot.jpa.modules.table.dao.SysDictCode;
import com.mudxx.demo.boot.jpa.modules.table.dao.SysDictData;
import com.mudxx.demo.boot.jpa.modules.table.dao.mapper.SysDictDataRepository;
import com.mudxx.demo.boot.jpa.modules.table.model.SysDictDataGroupModel;
import com.mudxx.demo.boot.jpa.modules.table.model.SysDictDataModel;
import com.mudxx.demo.boot.jpa.modules.table.service.ISysDictCodeService;
import com.mudxx.demo.boot.jpa.modules.table.service.ISysDictDataService;
import com.mudxx.demo.boot.jpa.modules.table.service.bo.SysDictDataBo;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataSaveDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataUpdateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictDataUpdateStateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.vo.SysDictDataSimpleVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author laiw
 * @date 2023/11/20 11:01
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

    @Autowired
    private SysDictDataRepository repository;
    @Autowired
    private ISysDictCodeService sysDictCodeService;


    private SysDictData getById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        Optional<SysDictData> optional = repository.findById(id);
        return optional.orElse(null);
    }

    private List<SysDictData> selectBySysDictCodeId(String sysDictCodeId) {
        return repository.findBySysDictCodeId(sysDictCodeId);
    }

    private int countBySysDictCodeId(String sysDictCodeId) {
        return ObjectUtils.defaultIfNull(repository.countBySysDictCodeId(sysDictCodeId), 0);
    }

    private List<SysDictDataModel> findAllUsableData() {
        List<Map<String, String>> list = repository.findUsableData();
        return list.stream().map(e -> new SysDictDataModel.Builder()
                .code(e.get("code"))
                .name(e.get("name"))
                .nameEn(e.get("name_en"))
                .value(e.get("value"))
                .build()).collect(Collectors.toList());
    }

    private void checkPresent(SysDictData entity, boolean exists) {
        if (exists) {
            VUtils.isEmpty(entity).throwMessage(BizErrorCode.NOT_EXIST, "字典数据");
        }
    }

    private void checkStatus(SysDictData entity, boolean enable) {
        if (enable) {
            VUtils.isTrue(!StatusEnum.Enable.getStatus().equals(entity.getStatus()))
                    .throwMessage(BizErrorCode.DISABLED, "字典数据");
        }
    }

    @Override
    public SysDictData findById(String id, boolean exists) {
        SysDictData entity = this.getById(id);
        this.checkPresent(entity, exists);
        return entity;
    }

    @Override
    public SysDictDataBo findAndCheckById(String id, boolean enable) {
        SysDictData entity = this.findById(id, true);
        this.checkStatus(entity, enable);
        return BeanUtil.copyProperties(entity, SysDictDataBo.class);
    }

    @Override
    public List<SysDictDataSimpleVo> findBySysDictCodeId(String sysDictCodeId, boolean enable) {
        // 获取所有未删除的字典编码
        List<SysDictData> list = this.selectBySysDictCodeId(sysDictCodeId);
        if (enable) {
            list = list.stream().filter(e -> StatusEnum.Enable.getStatus().equals(e.getStatus()))
                    .collect(Collectors.toList());
        }
        return list.stream()
                // 排序
                .sorted(Comparator.comparing(SysDictData::getSort, Comparator.nullsFirst(Comparator.naturalOrder())))
                .map(e -> BeanUtil.copyProperties(e, SysDictDataSimpleVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<SysDictDataGroupModel>> findAllUsableGroupData() {
        return this.findAllUsableData().stream().collect(Collectors.groupingBy(
                SysDictDataModel::getCode,
                Collectors.mapping(e -> new SysDictDataGroupModel.Builder()
                        .name(e.getName())
                        .nameEn(e.getNameEn())
                        .value(e.getValue())
                        .build(), Collectors.toList())
        ));
    }

    @Override
    public void saveData(SysDictDataSaveDto saveDto, String createBy) {
        // 验证编码是否已存在
        SysDictCode dictCode = sysDictCodeService.findById(saveDto.getSysDictCodeId(), true);
        // 新增
        SysDictData insert = BeanUtil.copyProperties(saveDto, SysDictData.class);
        insert.setSysDictCodeId(dictCode.getId());
        // 排序
        int count = this.countBySysDictCodeId(saveDto.getSysDictCodeId());
        insert.setSort(count + 1);
        // 默认
        insert.setStatus(StatusEnum.Enable.getStatus());
        insert.setCreateBy(createBy);
        insert.setCreateDate(new Date());
        repository.save(insert);
    }

    @Override
    public void updateData(SysDictDataUpdateDto updateDto, String updateBy) {
        SysDictData update = this.findById(updateDto.getId(), true);
        update.setName(updateDto.getName());
        update.setNameEn(updateDto.getNameEn());
        update.setValue(updateDto.getValue());
        update.setUpdateBy(updateBy);
        update.setUpdateDate(new Date());
        repository.save(update);
    }

    @Override
    public void updateState(SysDictDataUpdateStateDto updateStateDto, String updateBy) {
        SysDictData update = this.findById(updateStateDto.getId(), true);
        StateEnum anEnum = EnumUtils.getEnumBy(StateEnum::getStatus, updateStateDto.getState());
        VUtils.isEmpty(anEnum).throwMessage(BizErrorCode.UNSUPPORTED, updateStateDto.getState());
        switch (anEnum) {
            case Enable:
                VUtils.isFalse(StatusEnum.Disable.getStatus().equals(update.getStatus()))
                        .throwMessage(BizErrorCode.BIZ_ERROR, "字典数据非禁用状态");
                update.setStatus(StatusEnum.Enable.getStatus());
                break;
            case Disable:
                VUtils.isFalse(StatusEnum.Enable.getStatus().equals(update.getStatus()))
                        .throwMessage(BizErrorCode.BIZ_ERROR, "字典数据非启用状态");
                update.setStatus(StatusEnum.Disable.getStatus());
                break;
            default:
                throw new BizException(BizErrorCode.UNSUPPORTED, updateStateDto.getState());
        }
        update.setUpdateBy(updateBy);
        update.setUpdateDate(new Date());
        repository.save(update);
    }

}
