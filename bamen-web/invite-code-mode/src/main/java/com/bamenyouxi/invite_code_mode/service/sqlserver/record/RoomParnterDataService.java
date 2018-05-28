package com.bamenyouxi.invite_code_mode.service.sqlserver.record;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.RoomParnterDataMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RoomParnterData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RoomParnterDataService extends AbstractCrudService<RoomParnterData, Integer> {

    @Autowired
    RoomParnterDataMapper roomParnterDataMapper;

    @Override
    public CrudMapper<RoomParnterData, Integer> getMapper() {
        return roomParnterDataMapper;
    }


    @Transactional
    public PageInfo<RoomParnterData> queryPartnerMessage(int page, int size, Map<String,Object> params){
        List<RoomParnterData> list=roomParnterDataMapper.queryPartnerMessage(params);
        if (list.isEmpty() || list.get(0) == null)
                return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<RoomParnterData> queryAgentRoom(int page, int size, Map<String,Object> params){
        List<RoomParnterData> list=roomParnterDataMapper.queryAgentRoom(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

//    @Transactional
//    public JSONObject querySumInnings(Map<String,Object> params){
//        RoomParnterData sysAgent=roomParnterDataMapper.querySumInnings(params);
//        int n=sysAgent.getParnterid();
//        JSONObject result=new JSONObject();
//        result.put("n",n);
//        return  result;
//    }



}
