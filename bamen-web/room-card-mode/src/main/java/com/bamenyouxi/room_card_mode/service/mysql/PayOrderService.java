package com.bamenyouxi.room_card_mode.service.mysql;


import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.PayOrderMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.PayOrder;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.util.CreateOrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
public class PayOrderService extends AbstractCrudService<PayOrder,Long>{


    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
    public CrudMapper<PayOrder, Long> getMapper() {
        return payOrderMapper;
    }


    @Override
    protected String sort() {
        return FieldConstant.SortConstant.CREATE_TIME_DESC;
    }


}
