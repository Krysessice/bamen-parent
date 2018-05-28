package com.bamenyouxi.invite_code_mode.service.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.TurntableAwardTypeMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.TurntableAwardType;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.UseridScoreRoomnumSave;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class TurntableAwardTypeService extends AbstractCrudService<TurntableAwardType, Integer> {

    @Autowired
    private TurntableAwardTypeMapper turntableAwardTypeMapper;

    @Override
    public CrudMapper<TurntableAwardType, Integer> getMapper() {
        return turntableAwardTypeMapper;
    }


    @Transactional
    public PageInfo<TurntableAwardType> getTurntableAwardType(int page, int size, Map<String,Object> params){
        List<TurntableAwardType> list=turntableAwardTypeMapper.getTurntableAwardType(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

}
