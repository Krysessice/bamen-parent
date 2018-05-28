package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.GoldInfoMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.UserGoldMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.GoldInfo;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserGold;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserGoldSerice extends AbstractCrudService<UserGold, Long> {

    @Autowired
    private UserGoldMapper userGoldMapper;
    @Autowired
    private GoldInfoMapper goldInfoMapper;

    @Override
    public CrudMapper<UserGold, Long> getMapper() {
        return userGoldMapper;
    }

    @Override
    protected void listBefore(Map<String, Object> params) {
        Object gameId = params.get(FieldConstant.DBFieldConstant.F_GAME_ID.name());
        if (gameId != null) {
            super.checkParamsType(new HashMap<Object, Class<?>>() {{
                put(gameId, Integer.class);
            }});
        } else {
            params.put(FieldConstant.ModelFieldConstant.superAgentGameId.name(), UserDetailsUtil.getGameId());
        }
        super.listBefore(params);
    }

    public PageInfo<UserGold> sumActual(int page, int size, Map<String, Object> params) {
        params.put(FieldConstant.ModelFieldConstant.superAgentGameId.name(), UserDetailsUtil.getGameId());
        GoldInfo goldInfo=goldInfoMapper.queryInfo();
        params.put(FieldConstant.CommonFieldConstant.t1.name(), goldInfo.getT1_commission());
        params.put(FieldConstant.CommonFieldConstant.t2.name(), goldInfo.getT2_commission());
        params.put(FieldConstant.CommonFieldConstant.t3.name(), goldInfo.getT3_commission());
        params.put("F_GAME_ID",UserDetailsUtil.getGameId());
        PageHelper.startPage(page, size, false);
        List<UserGold> list = userGoldMapper.sumActual(params);
        return new PageInfo<>(list);
    }


}
