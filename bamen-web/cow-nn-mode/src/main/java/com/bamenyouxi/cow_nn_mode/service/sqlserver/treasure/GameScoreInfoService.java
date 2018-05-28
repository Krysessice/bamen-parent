package com.bamenyouxi.cow_nn_mode.service.sqlserver.treasure;
import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.CardGift4AgentRecordMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.CardGift4PlayerRecordMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.SysAgentMapper;
import com.bamenyouxi.cow_nn_mode.mapper.sqlserver.treasure.GameScoreInfoMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4AgentRecord;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4PlayerRecord;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.sqlserver.treasure.GameScoreInfo;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
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
    public JSONObject hasCard(){
        List<SysAgent> lists=sysAgentMapper.queryMun(UserDetailsUtil.getAccoutnt());
        int sumCard=lists.get(0).getCardNum();

        CardGift4AgentRecord cardGift4AgentRecord=cardGift4AgentRecordMapper.queryNumOne(UserDetailsUtil.getAccoutnt());
        CardGift4PlayerRecord cardGift4PlayerRecord=gift4PlayerRecordMapper.queryNumTwo(UserDetailsUtil.getAccoutnt());
        int Num=cardGift4AgentRecord.getCardNum()+cardGift4PlayerRecord.getCardNum();
        int HasNum=sumCard-Num;
        System.out.println(HasNum);

        JSONObject result=new JSONObject();
        result.put("sum",HasNum);

        Assert.isTrue(!(lists.isEmpty() || lists.get(0) == null), TipMsgConstant.PARAM_INVALID);
        return result;
    }


    @Transactional
    public void agentCardGift(int presentee, int cardNum, String giftReason){
        if (StringUtils.isEmptyOrWhitespaceOnly(giftReason))
            giftReason = null;
        List<GameScoreInfo> list = gameScoreInfoMapper.get(new HashMap<String, Object>() {{
            put(FieldConstant.ModelFieldConstant.gameId.name(), presentee);
        }});
        List<SysAgent> lists=sysAgentMapper.queryMun(UserDetailsUtil.getAccoutnt());

        int sumCard=lists.get(0).getCardNum();

        CardGift4AgentRecord cardGift4AgentRecord=cardGift4AgentRecordMapper.queryNumOne(UserDetailsUtil.getAccoutnt());
        CardGift4PlayerRecord cardGift4PlayerRecord=gift4PlayerRecordMapper.queryNumTwo(UserDetailsUtil.getAccoutnt());
        int Num=cardGift4AgentRecord.getCardNum()+cardGift4PlayerRecord.getCardNum();
        System.out.println(Num);
        int HasNum=sumCard-Num;
        System.out.println(HasNum);
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
}
