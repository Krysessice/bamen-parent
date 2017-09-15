package com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.PayOrder;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_sys_agent, model: {@link SysAgent}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface SysAgentMapper extends CrudMapper<SysAgent, Long> {

    int saveOpenAgency(SysAgent t);

    List<SysAgent> getId(String F_ACCOUNT);

    List<SysAgent> getAll(String F_ACCOUNT);

    List<T> getSuperAgentId(String SuperAgentId);

    List<SysAgent> getAccount(String F_ACCOUNT);

    List<SysAgent> getOne(String F_ID);

    List<SysAgent> getTwo(String superAccount);

    int updates(SysAgent t);
}
