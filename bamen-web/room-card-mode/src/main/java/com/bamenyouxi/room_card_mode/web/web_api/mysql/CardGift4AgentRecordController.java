package com.bamenyouxi.room_card_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4AgentRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.service.mysql.CardGift4AgentRecordService;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class CardGift4AgentRecordController extends AbstractCrudController<CardGift4AgentRecord ,Long> {

    @Autowired
    private CardGift4AgentRecordService cardGift4AgentRecordService;

    @Autowired
    private SysAgentMapper sysAgentMapper;

    @Override
    public AbstractCrudService<CardGift4AgentRecord, Long> getService() {
        return cardGift4AgentRecordService;
    }


    /**
     * 管理员赠送代理房卡
     */
    @PostMapping("/saveCardgift")
    public WebResult saveCardgift(@RequestBody CardGift4AgentRecord cardGift4AgentRecord){
        List<SysAgent> list=sysAgentMapper.getAll(cardGift4AgentRecord.getPresentee().toString());
        if(list!=null&&!list.isEmpty()) {
            cardGift4AgentRecord.setPresenter(Long.valueOf(UserDetailsUtil.getAccoutnt()));
            int n=cardGift4AgentRecordService.getMapper().save(cardGift4AgentRecord);
            return WebResult.of(list.get(0));
        }
        return WebResult.of( 0, TipMsgConstant.SYS_NOTAGENT,null);
    }




}
