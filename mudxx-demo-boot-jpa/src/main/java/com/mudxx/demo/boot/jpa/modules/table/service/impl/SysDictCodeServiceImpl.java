package com.mudxx.demo.boot.jpa.modules.table.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mudxx.common.constants.enums.IsDelEnum;
import com.mudxx.common.constants.enums.StateEnum;
import com.mudxx.common.constants.enums.StatusEnum;
import com.mudxx.common.exception.code.biz.BizErrorCode;
import com.mudxx.common.exception.code.biz.BizException;
import com.mudxx.common.exception.utils.VUtils;
import com.mudxx.common.utils.domain.EnumUtils;
import com.mudxx.demo.boot.jpa.modules.table.dao.SysDictCode;
import com.mudxx.demo.boot.jpa.modules.table.dao.mapper.SysDictCodeRepository;
import com.mudxx.demo.boot.jpa.modules.table.service.ISysDictCodeService;
import com.mudxx.demo.boot.jpa.modules.table.service.bo.SysDictCodeBo;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeSaveDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeUpdateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.SysDictCodeUpdateStateDto;
import com.mudxx.demo.boot.jpa.modules.table.service.vo.SysDictCodeSimpleVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author laiw
 * @date 2023/11/20 10:55
 */
@Service
public class SysDictCodeServiceImpl implements ISysDictCodeService {

    private static final String ROOT_PID = "";
    private static final Integer ROOT_GRADE = 0;

    @Autowired
    private SysDictCodeRepository repository;

    private SysDictCode getById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        Optional<SysDictCode> optional = repository.findById(id);
        return optional.orElse(null);
    }

    private SysDictCode getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return repository.findByCode(code);
    }

    private List<SysDictCode> selectByPid(String pid) {
        return repository.findByPidAndIsDel(
                StringUtils.defaultIfBlank(pid, ROOT_PID),
                IsDelEnum.NOT_DEL.getStatus()
        );
    }

    private int countByPid(String pid) {
        return ObjectUtils.defaultIfNull(repository.countByPidAndIsDel(
                StringUtils.defaultIfBlank(pid, ROOT_PID),
                IsDelEnum.NOT_DEL.getStatus()
        ), 0);
    }

    private void checkPresent(SysDictCode entity, boolean exists) {
        if (exists) {
            VUtils.isEmpty(entity).throwMessage(BizErrorCode.NOT_EXIST, "字典编码");
            VUtils.isTrue(!IsDelEnum.NOT_DEL.getStatus().equals(entity.getDel()))
                    .throwMessage(BizErrorCode.NOT_EXIST, "字典编码");
        }
    }

    private void checkStatus(SysDictCode entity, boolean enable) {
        if (enable) {
            VUtils.isTrue(!StatusEnum.Enable.getStatus().equals(entity.getStatus()))
                    .throwMessage(BizErrorCode.DISABLED, "字典编码");
        }
    }

    private boolean isRootNode(String pid) {
        return ROOT_PID.equals(pid);
    }

    @Override
    public SysDictCode findById(String id, boolean exists) {
        SysDictCode entity = this.getById(id);
        this.checkPresent(entity, exists);
        return entity;
    }

    @Override
    public SysDictCode findByCode(String code, boolean exists) {
        SysDictCode entity = this.getByCode(code);
        this.checkPresent(entity, exists);
        return entity;
    }

    @Override
    public SysDictCodeBo findAndCheckById(String id, boolean enable) {
        SysDictCode entity = this.findById(id, true);
        this.checkStatus(entity, enable);
        return BeanUtil.copyProperties(entity, SysDictCodeBo.class);
    }

    @Override
    public SysDictCodeBo findAndCheckByCode(String code, boolean enable) {
        SysDictCode entity = this.findByCode(code, true);
        this.checkStatus(entity, enable);
        return BeanUtil.copyProperties(entity, SysDictCodeBo.class);
    }

    @Override
    public List<SysDictCodeSimpleVo> lazyLoading(String pid) {
        // 获取所有未删除的字典编码
        List<SysDictCode> list = this.selectByPid(pid);
        return list.stream()
                // 排序
                .sorted(Comparator.comparing(SysDictCode::getSort, Comparator.nullsFirst(Comparator.naturalOrder())))
                .map(e -> BeanUtil.copyProperties(e, SysDictCodeSimpleVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveCode(SysDictCodeSaveDto saveDto, String createBy) {
        // 验证编码是否已存在
        SysDictCode entity = this.getByCode(saveDto.getCode());
        VUtils.isNotEmpty(entity).throwMessage(BizErrorCode.EXISTS, "字典编码");
        // 新增
        SysDictCode insert = BeanUtil.copyProperties(saveDto, SysDictCode.class);
        // 是否根节点
        boolean root = this.isRootNode(saveDto.getPid());
        if (root) {
            insert.setGrade(ROOT_GRADE);
        } else {
            SysDictCode parent = this.findById(saveDto.getPid(), true);
            insert.setGrade(parent.getGrade() + 1);
        }
        // 排序
        int count = this.countByPid(saveDto.getPid());
        insert.setSort(count + 1);
        // 默认
        insert.setStatus(StatusEnum.Enable.getStatus());
        insert.setDel(IsDelEnum.NOT_DEL.getStatus());
        insert.setCreateBy(createBy);
        insert.setCreateDate(new Date());
        repository.save(insert);
    }

    @Override
    public void updateCode(SysDictCodeUpdateDto updateDto, String updateBy) {
        SysDictCode update = this.findById(updateDto.getId(), true);
        // 验证编码是否已存在
        if (!update.getCode().equals(updateDto.getCode())) {
            SysDictCode entity = this.getByCode(updateDto.getCode());
            VUtils.isNotEmpty(entity).throwMessage(BizErrorCode.EXISTS, "字典编码");
            // 设置新的编码
            update.setCode(updateDto.getCode());
        }
        update.setName(updateDto.getName());
        update.setNameEn(updateDto.getNameEn());
        update.setUpdateBy(updateBy);
        update.setUpdateDate(new Date());
        repository.save(update);
    }

    @Override
    public void updateState(SysDictCodeUpdateStateDto updateStateDto, String updateBy) {
        SysDictCode update = this.findById(updateStateDto.getId(), true);
        StateEnum anEnum = EnumUtils.getEnumBy(StateEnum::getStatus, updateStateDto.getState());
        VUtils.isEmpty(anEnum).throwMessage(BizErrorCode.UNSUPPORTED, updateStateDto.getState());
        switch (anEnum) {
            case Enable:
                VUtils.isFalse(StatusEnum.Disable.getStatus().equals(update.getStatus()))
                        .throwMessage(BizErrorCode.BIZ_ERROR, "字典编码非禁用状态");
                update.setStatus(StatusEnum.Enable.getStatus());
                break;
            case Disable:
                VUtils.isFalse(StatusEnum.Enable.getStatus().equals(update.getStatus()))
                        .throwMessage(BizErrorCode.BIZ_ERROR, "字典编码非启用状态");
                update.setStatus(StatusEnum.Disable.getStatus());
                break;
            case Delete:
                VUtils.isFalse(StatusEnum.Disable.getStatus().equals(update.getStatus()))
                        .throwMessage(BizErrorCode.BIZ_ERROR, "字典编码非禁用状态");
                update.setDel(IsDelEnum.Deleted.getStatus());
                update.setDelBy(updateBy);
                update.setDelDate(new Date());
                break;
            default:
                throw new BizException(BizErrorCode.UNSUPPORTED, updateStateDto.getState());
        }
        update.setUpdateBy(updateBy);
        update.setUpdateDate(new Date());
        repository.save(update);
    }

}
