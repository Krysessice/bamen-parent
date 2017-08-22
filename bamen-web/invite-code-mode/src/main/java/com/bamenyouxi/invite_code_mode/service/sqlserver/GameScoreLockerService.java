package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.GameScoreLockerMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameScoreLockerService extends AbstractCrudService<GameScoreLocker,Integer>{

    @Autowired
    private GameScoreLockerMapper gameScoreLockerMapper;

    @Override
    public CrudMapper<GameScoreLocker, Integer> getMapper() {
        return gameScoreLockerMapper;
    }

    /**
     * 分页排序
     * @return
     */
    @Override
    protected String sort() {
        return FieldConstant.SortConstant.COLLECT_DATE_DESC;
    }





}
