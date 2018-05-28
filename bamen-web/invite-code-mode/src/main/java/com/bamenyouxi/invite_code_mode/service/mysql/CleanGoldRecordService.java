package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.CleanGoldRecordMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CleanGoldRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.TeamPayOrder;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserGold;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CleanGoldRecordService  extends AbstractCrudService<CleanGoldRecord, Long> {

    @Autowired
    private CleanGoldRecordMapper cleanGoldRecordMapper;

    @Override
    public CrudMapper<CleanGoldRecord, Long> getMapper() {
        return cleanGoldRecordMapper;
    }

    @Transactional
    public PageInfo<CleanGoldRecord> getCleanGoldAll(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
        params.put("agentGameid",UserDetailsUtil.getGameId());
        List<CleanGoldRecord> list = cleanGoldRecordMapper.getCleanGoldAll(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<CleanGoldRecord> getCleanGoldRecord(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
        List<CleanGoldRecord> list = cleanGoldRecordMapper.getCleanGoldAll(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

}
