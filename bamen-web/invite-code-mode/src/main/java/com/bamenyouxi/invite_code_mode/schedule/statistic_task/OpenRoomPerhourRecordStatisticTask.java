package com.bamenyouxi.invite_code_mode.schedule.statistic_task;

import com.bamenyouxi.core.constant.FieldConstant;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Component
@Order(SysConstant.CommandLineOrder.OPEN_ROOM_PERHOUR_RECORD_TASk)
class OpenRoomPerhourRecordStatisticTask extends AbstractDataSynTask<UserFangKaCost, OpenRoomPerhourRecord> {
    @Autowired
    private UserFangKaCostMapper userFangKaCostMapper;
    @Autowired
    private OpenRoomPerhourRecordMapper openRoomPerhourRecordMapper;

    @Override
    protected List<OpenRoomPerhourRecord> dataScanForRun() {
        PageHelper.startPage(1,1, FieldConstant.SortConstant.CREATE_TIME_DESC);
        List<OpenRoomPerhourRecord> list = openRoomPerhourRecordMapper.get(new HashMap<>());
        String startTime = list.isEmpty() ? null : list.get(0).getCreateTime().toLocalDateTime().toString();
        String endTime = LocalDateTime.now().minusHours(1).toString();
        return
                userFangKaCostMapper.findOpenRoomPerhourRecord(new HashMap<String,Object>() {{
                    put(FieldConstant.CommonFieldConstant.startTime.name(), startTime);
                    put(FieldConstant.CommonFieldConstant.endTime.name(), endTime);
                }});
    }

    @Override
    protected List<OpenRoomPerhourRecord> dataScanForTask() {
        String costTime = LocalDateTime.now().minusHours(1).toString();
        return
                userFangKaCostMapper.findOpenRoomPerhourRecord(new HashMap<String,Object>() {{
                    put(FieldConstant.CommonFieldConstant.startTime.name(), costTime);
                    put(FieldConstant.CommonFieldConstant.endTime.name(), costTime);
                }});
    }

    @Override
    protected void dataSyn(List<OpenRoomPerhourRecord> list) {
        if(dataSynBefore(list))
            openRoomPerhourRecordMapper.saveMul(list);
    }


    @Override
    @Scheduled(cron = "0 5 * * * ?")
    public void doTask() {
        super.doTask();
    }
}
