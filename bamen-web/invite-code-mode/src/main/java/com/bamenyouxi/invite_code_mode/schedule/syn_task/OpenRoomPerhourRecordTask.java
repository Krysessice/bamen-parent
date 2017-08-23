package com.bamenyouxi.invite_code_mode.schedule.syn_task;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.AbstractDataSynTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.OpenRoomPerhourRecordMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.UserFangKaCostMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.OpenRoomPerhourRecord;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.UserFangKaCost;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Component
@Order(SysConstant.CommandLineOrder.OPEN_ROOM_PERHOUR_RECORD_TASk)
public class OpenRoomPerhourRecordTask extends AbstractDataSynTask<UserFangKaCost,OpenRoomPerhourRecord>{

    @Autowired
    private UserFangKaCostMapper userFangKaCostMapper;

    @Autowired
    private OpenRoomPerhourRecordMapper openRoomPerhourRecordMapper;

    @Override
    protected List<OpenRoomPerhourRecord> dataScanForRun() {
        PageHelper.startPage(1,1, FieldConstant.SortConstant.CREATE_TIME_DESC);
        List<OpenRoomPerhourRecord> list=this.openRoomPerhourRecordMapper.get(new HashMap<>());
        String startDate=list.isEmpty() ? null:list.get(0).getCreateTime().toLocalDateTime().toLocalDate().toString();
        int endDate= LocalDateTime.now().getHour();
        return
                this.userFangKaCostMapper.findOpenRoomPerhourRecord(new HashMap<String,Object>(){{
            put(FieldConstant.CommonFieldConstant.startDate.name(),startDate);
            put(FieldConstant.CommonFieldConstant.endDate.name(),endDate);
        }});
    }

    @Override
    protected List<OpenRoomPerhourRecord> dataScanForTask() {
        String createDate=LocalDate.now().minusDays(1).toString();
        return this.userFangKaCostMapper.findOpenRoomPerhourRecord(new HashMap<String,Object>(){{
                put(FieldConstant.CommonFieldConstant.startDate.name(),createDate);
                put(FieldConstant.CommonFieldConstant.endDate.name(),createDate);
        }});
    }

    @Override
    protected void dataSyn(List<OpenRoomPerhourRecord> list) {
        if(dataSynBefore(list)){
            this.openRoomPerhourRecordMapper.saveMul(list);
        }
    }


    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void doTask() {
        super.doTask();
    }


}
