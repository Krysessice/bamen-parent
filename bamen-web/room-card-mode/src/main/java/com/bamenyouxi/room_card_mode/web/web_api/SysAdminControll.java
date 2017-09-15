package com.bamenyouxi.room_card_mode.web.web_api;


import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.service.mysql.CardBonusRecordService;
import com.bamenyouxi.room_card_mode.service.mysql.PayOrderService;
import com.bamenyouxi.room_card_mode.service.mysql.SysAgentService;
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

    /**
     * 注册新的代理
     */
    @PostMapping("/saveSysAget")
    public WebResult saveSysAget(@RequestBody SysAgent sysAgent) throws IOException {
            SysAgent.Builder.defaultPwdInject(sysAgent);
            List<SysAgent> list1 = sysAgentMapper.get(new HashMap<String, Object>() {{
                put(FieldConstant.DBFieldConstant.F_ID.name(), sysAgent.getSuperAgentId());
                sysAgentMapper.saveOpenAgency(sysAgent);
            }});
            Assert.notEmpty(list1, TipMsgConstant.SYS_AGENT);
            return WebResult.of();

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
            return  WebResult.of(payOrderService.list(page,size,params));
    }


    /**
     *查询所有代理的返利记录
     */
    @GetMapping("/queryRebate")
    public WebResult queryRebate(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
            return  WebResult.of(cardBonusRecordService.list(page,size,params));
    }



}
