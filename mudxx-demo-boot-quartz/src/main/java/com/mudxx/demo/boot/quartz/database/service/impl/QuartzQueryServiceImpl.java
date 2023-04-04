package com.mudxx.demo.boot.quartz.database.service.impl;

import com.mudxx.common.utils.BeanCopyUtils;
import com.mudxx.common.web.response.CommonPage;
import com.mudxx.demo.boot.quartz.database.dao.QrtzJobDetails;
import com.mudxx.demo.boot.quartz.database.dao.mapper.QrtzJobDetailsRepository;
import com.mudxx.demo.boot.quartz.database.service.IQuartzQueryService;
import com.mudxx.demo.boot.quartz.database.service.bo.QrtzJobDetailsBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laiw
 * @date 2023/4/4 14:19
 */
@Slf4j
@Service
public class QuartzQueryServiceImpl implements IQuartzQueryService {

    @Autowired
    private QrtzJobDetailsRepository qrtzJobDetailsRepository;

    @Override
    public CommonPage<QrtzJobDetailsBo> selectPageJobDetails(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Specification<QrtzJobDetails> querySpecie = new Specification<QrtzJobDetails>() {
            private static final long serialVersionUID = 4655209094415264541L;

            @Override
            public Predicate toPredicate(Root<QrtzJobDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<QrtzJobDetails> page = qrtzJobDetailsRepository.findAll(querySpecie, pageable);
        CommonPage<QrtzJobDetailsBo> data = new CommonPage<>();
        List<QrtzJobDetailsBo> list = BeanCopyUtils.copyListProperties(page.getContent(), QrtzJobDetailsBo::new);
        data.setList(list);
        data.setPageNum(page.getNumber());
        data.setPageSize(page.getSize());
        data.setTotalPage(page.getTotalPages());
        data.setTotal(page.getTotalElements());
        return data;
    }
}
