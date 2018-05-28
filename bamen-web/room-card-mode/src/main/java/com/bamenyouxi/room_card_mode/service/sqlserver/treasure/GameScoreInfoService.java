package com.bamenyouxi.room_card_mode.service.sqlserver.treasure;


import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardBonusRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardGift4AgentRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardGift4PlayerRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.mapper.sqlserver.treasure.GameScoreInfoMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardBonusRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4AgentRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4PlayerRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.model.sqlserver.treasure.GameScoreInfo;

import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

@Service
public class  GameScoreInfoService{

    @Autowired
    private GameScoreInfoMapper gameScoreInfoMapper;

    @Autowired
    private SysAgentMapper sysAgentMapper;

    @Autowired
    private CardGift4PlayerRecordMapper gift4PlayerRecordMapper;

    @Autowired
    private CardGift4AgentRecordMapper cardGift4AgentRecordMapper;

    @Autowired
    private CardBonusRecordMapper cardBonusRecordMapper;

    @Transactional
    public void cardGift(int presentee, int cardNum, String giftReason) {
        if (StringUtils.isEmptyOrWhitespaceOnly(giftReason))
            giftReason = null;
        List<GameScoreInfo> list = gameScoreInfoMapper.get(new HashMap<String, Object>() {{
            put(FieldConstant.ModelFieldConstant.gameId.name(), presentee);
        }});
        Assert.isTrue(!(list.isEmpty() || list.get(0) == null), TipMsgConstant.SYS_PLAY);
        Assert.isTrue(list.get(0).getInsureScore() + cardNum >= 0, TipMsgConstant.PARAM_INVALID);
        int i = gameScoreInfoMapper.updateCardNum(GameScoreInfo.ofGameId(presentee, (long) cardNum));
        int j = gift4PlayerRecordMapper.save(CardGift4PlayerRecord.of(UserDetailsUtil.getAccoutnt(), presentee, cardNum, giftReason));
        Assert.isTrue(i > 0 && j > 0, TipMsgConstant.OPERATION_FAILED);
    }


    @Transactional
    public void agentCardGift(int presentee, int cardNum, String giftReason){
        if (StringUtils.isEmptyOrWhitespaceOnly(giftReason))
            giftReason = null;
        List<GameScoreInfo> list = gameScoreInfoMapper.get(new HashMap<String, Object>() {{
            put(FieldConstant.ModelFieldConstant.gameId.name(), presentee);
        }});
        List<SysAgent> lists=sysAgentMapper.queryMun(UserDetailsUtil.getAccoutnt());


        CardBonusRecord one=cardBonusRecordMapper.getOne(UserDetailsUtil.getAccoutnt());
        CardBonusRecord two=cardBonusRecordMapper.getTwo(UserDetailsUtil.getAccoutnt());
        int superCard=one.getFirBonus()+two.getSecBonus();
        int sumCard=lists.get(0).getCardNum()+superCard;


        CardGift4AgentRecord cardGift4AgentRecord=cardGift4AgentRecordMapper.queryNumOne(lists.get(0).getAccount());
        CardGift4PlayerRecord cardGift4PlayerRecord=gift4PlayerRecordMapper.queryNumTwo(lists.get(0).getAccount());
        int Num=cardGift4AgentRecord.getCardNum()+cardGift4PlayerRecord.getCardNum();


        int HasNum=sumCard-Num;


        JSONObject result=new JSONObject();
        result.put("sum",HasNum);

        Assert.isTrue(!(list.isEmpty() || list.get(0) == null), TipMsgConstant.SYS_PLAY);
        Assert.isTrue(!(lists.isEmpty() || lists.get(0) == null), TipMsgConstant.PARAM_INVALID);
        Assert.isTrue(list.get(0).getInsureScore() + cardNum >= 0, TipMsgConstant.PARAM_INVALID);
        if(HasNum>=cardNum){
            int i = gameScoreInfoMapper.updateCardNum(GameScoreInfo.ofGameId(presentee, (long) cardNum));
            int j = gift4PlayerRecordMapper.save(CardGift4PlayerRecord.of(UserDetailsUtil.getAccoutnt(), presentee, cardNum, giftReason));
            Assert.isTrue(i > 0 && j > 0, TipMsgConstant.OPERATION_FAILED);
        }else{
            Assert.isTrue(lists.size()<0,TipMsgConstant.PAY_NOTCARD);
        }
    }

    @Transactional
    public JSONObject hasCard(){
        List<SysAgent> lists=sysAgentMapper.queryMun(UserDetailsUtil.getAccoutnt());
        //System.out.println(lists.get(0).getCardNum());
        CardBonusRecord one=cardBonusRecordMapper.getOne(UserDetailsUtil.getAccoutnt());
        CardBonusRecord two=cardBonusRecordMapper.getTwo(UserDetailsUtil.getAccoutnt());
        int superCard=one.getFirBonus()+two.getSecBonus();
        int sumCard=lists.get(0).getCardNum()+superCard;
       //System.out.println(sumCard);

        CardGift4AgentRecord cardGift4AgentRecord=cardGift4AgentRecordMapper.queryNumOne(UserDetailsUtil.getAccoutnt());
        CardGift4PlayerRecord cardGift4PlayerRecord=gift4PlayerRecordMapper.queryNumTwo(UserDetailsUtil.getAccoutnt());
        int Num=cardGift4AgentRecord.getCardNum()+cardGift4PlayerRecord.getCardNum();


        int HasNum=sumCard-Num;
        //System.out.println(HasNum);

        JSONObject result=new JSONObject();
        result.put("sum",HasNum);

        Assert.isTrue(!(lists.isEmpty() || lists.get(0) == null), TipMsgConstant.PARAM_INVALID);
        return result;
    }

}
