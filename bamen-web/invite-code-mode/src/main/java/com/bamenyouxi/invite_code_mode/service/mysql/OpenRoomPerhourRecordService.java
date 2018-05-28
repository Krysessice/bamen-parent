package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.OpenRoomPerhourRecordMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.OpenRoomPerhourRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserOpenRoomPerdayRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OpenRoomPerhourRecordService extends AbstractCrudService<OpenRoomPerhourRecord, Long> {


    @Autowired
    private OpenRoomPerhourRecordMapper openRoomPerhourRecordMapper;

    @Override
    public CrudMapper<OpenRoomPerhourRecord, Long> getMapper() {
        return openRoomPerhourRecordMapper;
    }


}
