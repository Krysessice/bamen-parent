package com.bamenyouxi.room_card_mode.web.web_api.mysql;


import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardBonusRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.PayOrderMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SystemInfoMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardBonusRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.PayOrder;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SystemInfo;
import com.bamenyouxi.room_card_mode.service.mysql.PayOrderService;
import com.bamenyouxi.room_card_mode.util.CreateOrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 订单
 */
@RestController
@RequestMapping("/order")
public class PayOrderController extends AbstractCrudController<PayOrder,Long>{

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private SysAgentMapper sysAgentMapper;

    @Autowired
    private SystemInfoMapper systemInfoMapper;

    @Autowired
    private CardBonusRecordMapper cardBonusRecordMapper;

    @Override
    public AbstractCrudService<PayOrder, Long> getService() {
        return payOrderService;
    }

    /**
     * 出售房卡
     */
    @PostMapping("/payOrder")
    public WebResult savePayOrder(@RequestBody  PayOrder payOrder){
        List<SysAgent> list=sysAgentMapper.getAll(payOrder.getAccount());
        if(list!=null&&!list.isEmpty()) {
            payOrder.setOrderNo(new CreateOrderNumber().createOrder());
            payOrder.setSysAgentId(list.get(0).getAccount());
            int i=payOrderMapper.save(payOrder);

                    List<SysAgent> list2=sysAgentMapper.getOne(payOrder.getAccount().toString());//上级
                    if(list2.get(0).getSuperAgentId()==null){
                        return WebResult.of(list2.get(0));
                    }else{
                        List<SysAgent> list3=sysAgentMapper.getTwo(list2.get(0).getSuperAgentId().toString());//上上级
                        if(list3.get(0).getSuperAgentId()==null){
                            List<SystemInfo> list4=systemInfoMapper.get(new HashMap<String, Object>(){{
                                put(FieldConstant.DBFieldConstant.F_ID.name(),get(0));
                            }});

                            Integer one=0;
                            if(list2.get(0).getSuperAgentId()!=null||list3.get(0).getSuperAgentId()!=null){
                                one= (int) ( payOrder.getCardNum()* list4.get(0).getRatelv1().doubleValue());
                            }
                            Integer two=0;
                            if(list3.get(0).getSuperAgentId()!=null){
                                two=(int)(payOrder.getCardNum()* list4.get(0).getRatelv2().doubleValue());
                            }
                            if(list2!=null&&!list2.isEmpty()) {
                                if(list3!=null&&!list3.isEmpty()){
                                    int x=cardBonusRecordMapper.save2(new CardBonusRecord.Builder().sysAgentId(list2.get(0).getAccount()).
                                            payOrderId(payOrder.getId()).firSuperAgentId(list2.get(0).getSuperAgentId().toString()).firBonus(one)
                                            .build());
                                }
                            }
                            return WebResult.of(list.get(0));
                        }else{
                            List<SystemInfo> list4=systemInfoMapper.get(new HashMap<String, Object>(){{
                                put(FieldConstant.DBFieldConstant.F_ID.name(),get(0));
                            }});

                            Integer one=0;
                            if(list2.get(0).getSuperAgentId()!=null||list3.get(0).getSuperAgentId()!=null){
                                one= (int) ( payOrder.getCardNum()* list4.get(0).getRatelv1().doubleValue());
                            }
                            Integer two=0;
                            if(list3.get(0).getSuperAgentId()!=null){
                                two=(int)(payOrder.getCardNum()* list4.get(0).getRatelv2().doubleValue());
                            }
                            if(list2!=null&&!list2.isEmpty()) {
                                if(list3!=null&&!list3.isEmpty()){
                                    int x=cardBonusRecordMapper.saves(new CardBonusRecord.Builder().sysAgentId(list2.get(0).getAccount()).
                                            payOrderId(payOrder.getId()).firSuperAgentId(list2.get(0).getSuperAgentId().toString()).firBonus(one)
                                            .secSuperAgentId(list3.get(0).getSuperAgentId().toString()).secBonus(two).build());
                                }
                            }
                            return WebResult.of(list.get(0));
                        }

                    }
            }
        return WebResult.of( 0, TipMsgConstant.SYS_AGENT,null);
    }







}
