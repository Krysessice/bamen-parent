package com.bamenyouxi.room_card_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.room_card_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4PlayerRecord;
import com.bamenyouxi.room_card_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.room_card_mode.service.mysql.CardGift4PlayerRecordService;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/cardPlay/")
public class CardGift4PlayerRecordController extends AbstractCrudController<CardGift4PlayerRecord,Long>{

    @Autowired
    private CardGift4PlayerRecordService cardGift4PlayerRecordService;

    @Autowired
    private AccountsInfoMapper accountsInfoMapper;

    @Override
    public AbstractCrudService<CardGift4PlayerRecord, Long> getService() {
        return cardGift4PlayerRecordService;
    }


    @PostMapping("/saveCardPlay")
    public WebResult saveCardPlay(@RequestBody CardGift4PlayerRecord cardGift4PlayerRecord){
        List<AccountsInfo> list=accountsInfoMapper.getUser(cardGift4PlayerRecord.getPresentee().toString());
        System.out.print(cardGift4PlayerRecord.getPresentee().toString());
        if(list!=null&&!list.isEmpty()){
                cardGift4PlayerRecord.setPresenter(Long.valueOf(UserDetailsUtil.getAccoutnt()));
                int n=cardGift4PlayerRecordService.getMapper().save(cardGift4PlayerRecord);
                return  WebResult.of(list.get(0));
        }
        return  WebResult.of(0, TipMsgConstant.SYS_NOTAGENT,null);
    }




}
