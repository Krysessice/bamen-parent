package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CardGiftRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * mapper for t_card_gift_record
 * Created by 13477 on 2017/8/8.
 */
@Mapper
public interface CardGiftRecordMapper extends CrudMapper<CardGiftRecord,Long> {


    List<CardGiftRecord> selectedName(Map<String, Object> params);

    List<CardGiftRecord> queryAgent(Map<String, Object> params);

    CardGiftRecord queryPrice(Map<String, Object> params);

    CardGiftRecord queryPriceSum();

    CardGiftRecord queryAgentOne(Integer selected);

    CardGiftRecord queryAgentOneTime(Map<String, Object> params);
}
