package com.bamenyouxi.invite_code_mode.schedule.syn_task;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.AbstractDataSynTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.UserGoldMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.UserGoldRecordMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserGold;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.UserGoldRecord;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

/**
 * 金币消耗同步
 * Created by 13477 on 2018/4/14.
 */
@Component
@Order(SysConstant.CommandLineOrder.SYS_Gold_Field_SYN_TASK)
public class UserGoldRecordSynTask extends AbstractDataSynTask<UserGoldRecord, UserGold> {
    private static Timestamp createTime;

    @Autowired
    private UserGoldMapper userGoldMapper;
    @Autowired
    private UserGoldRecordMapper userGoldRecordMapper;

    @Override
    protected List<UserGold> dataScanForRun() {
        createTime=Timestamp.from(Instant.now());
        PageHelper.startPage(1, 1, FieldConstant.SortConstant.CREATE_TIME_DESC);
        List<UserGold> list = this.userGoldMapper.get(new HashMap<>());
        Timestamp temp = list.isEmpty() ? null : list.get(0).getCreateTime();
        return
                userGoldRecordMapper.getUserGold(new HashMap<String, Object>() {{
                    put(FieldConstant.CommonFieldConstant.startTime.name(), temp);
                }});
    }

    @Override
    protected List<UserGold> dataScanForTask() {
        List<UserGold> userGold = userGoldRecordMapper.getUserGold(new HashMap<String, Object>() {{
            put(FieldConstant.CommonFieldConstant.startTime.name(), createTime);
        }});
        createTime = Timestamp.from(Instant.now());
        return userGold;
    }

    @Override
    protected void dataSyn(List<UserGold> list) {
        if (super.dataSynBefore(list))
            this.userGoldMapper.saveMul(list);
    }

    @Override
    @Scheduled(initialDelay = SysConstant.ScheduleTask.PAY_ORDER_SYN_DELAY, fixedDelay = SysConstant.ScheduleTask.PAY_ORDER_SYN_INTERVAL)
    public void doTask() {
        super.doTask();
    }
}
