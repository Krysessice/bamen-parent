package com.bamenyouxi.room_card_mode.web.web_api;


import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardBonusRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardGift4AgentRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardGift4PlayerRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardBonusRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4AgentRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4PlayerRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.service.mysql.*;
import com.bamenyouxi.room_card_mode.service.sqlserver.treasure.GameScoreInfoService;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin Controll
 * 2017 08 30
 */

@RestController
@RequestMapping("/admin")
public class SysAdminControll {

    @Autowired
    private SysAgentMapper sysAgentMapper;

    @Autowired
    private SysAgentService sysAgentService;

    @Autowired
    private  PayOrderService payOrderService;

    @Autowired
    private CardBonusRecordService cardBonusRecordService;

    @Autowired
    private CardGift4AgentRecordService cardGift4AgentRecordService;

    @Autowired
    private CardGift4PlayerRecordService cardGift4PlayerRecordService;

    @Autowired
    private CardGift4AgentRecordMapper cardGift4AgentRecordMapper;

    @Autowired
    private GameScoreInfoService gameScoreInfoService;

    @Autowired
    private CardBonusRecordMapper cardBonusRecordMapper;

    /**
     * 注册新的代理
     */
    @PostMapping("/saveSysAget")
    public WebResult saveSysAget(@RequestBody SysAgent sysAgent) throws IOException {
            SysAgent.Builder.defaultPwdInject(sysAgent);
            List<SysAgent> list=sysAgentMapper.getAccount(sysAgent.getAccount());
            if(sysAgent.getSuperAgentId()==null){
                sysAgentMapper.saveOpenAgency(sysAgent);
                return WebResult.of();
            }
            if(list.size()<=0){
                List<SysAgent> lists=sysAgentMapper.getAccount(sysAgent.getSuperAgentId().toString());
                if(lists.size()>0){
                    sysAgentMapper.saveOpenAgency(sysAgent);
                }else{
                    return WebResult.of(0,TipMsgConstant.SYS_SUPERAGENT,null);
                }
            }else {
                return WebResult.of(0,TipMsgConstant.SYS_ACCOUNT,null);
            }
            return WebResult.of();
    }

    /**
     * 看已被授权的代理
     */
    @GetMapping("/queryPartner/")
    public WebResult queryPartner(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            Map<String ,Object> params){
        return WebResult.of(sysAgentService.queryPartner(page,size,params));
    }



    /**
     * 根据代理号查询代理信息
     */
    @GetMapping("/getSysAget")
    public WebResult getSysAget(String F_ACCOUNT) throws IOException {
        List list=sysAgentMapper.getId(F_ACCOUNT);
        if(list!=null&&!list.isEmpty()) {
            return WebResult.of(list.get(0));
        }
        return WebResult.of( 0,TipMsgConstant.SYS_AGENT,null);
        }

    /**
     * 查询代理号
     */
    @GetMapping("/getAccount")
    public WebResult getAccount(String F_ACCOUNT){
        List list=sysAgentMapper.getAccount(F_ACCOUNT);
        if(list!=null&&!list.isEmpty()){
            return WebResult.of(list.get(0));
        }
        return WebResult.of(0,TipMsgConstant.SYS_AGENT,null);
    }

    /**
     * 冻结代理号
     */
    @PutMapping("/sysAgent/Freeze/{account}")
    public WebResult getSysFreeze(@PathVariable String account,boolean sysFlag){
        sysAgentService.sysAgentFreeze(account,sysFlag);
        return WebResult.of();
    }

    /**
     * 重置密码
     */
    @PutMapping("/sysAgent/pwdReset/{account}")
    public WebResult pwdReset(@PathVariable String account){
        sysAgentService.pwdReset(account);
        return WebResult.of();
    }

    /**
     * 查询所有代理的购卡记录
     */
    @GetMapping("/queryPayOrder")
    public WebResult queryPayOrder(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
            return  WebResult.of(payOrderService.getAgentSum(page,size,params));
    }

    @GetMapping("/queryAgentModel")
    public WebResult queryAgentModel(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(payOrderService.getAgent(page,size,params));
    }

    @GetMapping("/queryAll/")
    public WebResult queryAll(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(sysAgentService.queryAlls(page,size,params));
    }

    @GetMapping("/queryCardSum/")
    public WebResult queryCardSum(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(payOrderService.queryCardSum(page,size,params));
    }

    @GetMapping("/queryAgentList/")
    public WebResult queryAgentList(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(sysAgentService.queryAgentList(page,size,params));
    }


    @GetMapping("/queryRebate")
    public WebResult queryRebate(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
            return  WebResult.of(cardBonusRecordService.queryAll(page,size,params));
    }

    @GetMapping("/queryCardforList")
    public WebResult queryCardforList(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(payOrderService.queryCardforList(page,size,params));
    }

    @GetMapping("/querypayforList")
    public WebResult querypayforList(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(cardGift4AgentRecordService.querypayforList(page,size,params));
    }

    @GetMapping("/queryCardPlayer")
    public WebResult queryCardPlayer(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(cardGift4PlayerRecordService.queryCardPlayer(page,size,params));
    }

    @GetMapping("/groupPartnerMessage")
    public WebResult groupPartnerMessage(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(sysAgentService.groupPartnerMessage(page,size,params));
    }

    @GetMapping("/queryPartnerSumMoney/")
    @JsonSerialize
    public @ResponseBody JSONObject queryPartnerSumMoney(@RequestParam Map<String, Object> params){
        return  sysAgentService.queryPartnerSumMoney(params);//这个
    }


    /**
     * 代理给玩家赠卡
     */
    @PutMapping("/cardGift/")
    private WebResult cardGift(int presentee, int cardNum, String giftReason) {
        gameScoreInfoService.cardGift(presentee, cardNum, giftReason);
        return WebResult.of();
    }


    /**
     * 代理赠送代理房卡
     */
    @PostMapping("/saveCardGift")
    public WebResult saveCardGift(@RequestBody CardGift4AgentRecord cardGift4AgentRecord){
            cardGift4AgentRecordService.saves(cardGift4AgentRecord);
            return  WebResult.of();
    }




}
