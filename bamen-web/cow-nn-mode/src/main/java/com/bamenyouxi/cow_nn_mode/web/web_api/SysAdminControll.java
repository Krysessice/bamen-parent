package com.bamenyouxi.cow_nn_mode.web.web_api;


import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.core.util.FileUtil;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.RoleMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.SysAgentMapper;
import com.bamenyouxi.cow_nn_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.cow_nn_mode.mapper.sqlserver.treasure.GameScoreInfoMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4AgentRecord;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Role;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgentClearRecord;
import com.bamenyouxi.cow_nn_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.cow_nn_mode.service.excel.SysAgentClearExcelService;
import com.bamenyouxi.cow_nn_mode.service.mysql.*;
import com.bamenyouxi.cow_nn_mode.service.sqlserver.treasure.GameScoreInfoService;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin Controll
 * 2018 05 15
 */

@RestController
@RequestMapping("/admin")
public class SysAdminControll {

    @Autowired
    private SysAgentMapper sysAgentMapper;

    @Autowired
    private SysAgentService sysAgentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SysAgentClearRecordService sysAgentClearRecordService;

    @Autowired
    private WithdrawSerice withdrawSerice;

    @Autowired
    private SysAgentClearExcelService sysAgentClearExcelService;

    @Autowired
    private CardGift4AgentRecordService cardGift4AgentRecordService;

    @Autowired
    private CardGift4PlayerRecordService cardGift4PlayerRecordService;

    @Autowired
    private GameScoreInfoService gameScoreInfoService;
    @Autowired
    private  PayOrderSerice payOrderSerice;

    @Autowired
    private AccountsInfoMapper accountsInfoMapper;

    /**
     * 注册新的代理
     */
    @PostMapping("/getOffice/{name}/{office}/{account}")
    public WebResult getOffice(@PathVariable String name,@PathVariable String office,@PathVariable String account) throws IOException {
            List<SysAgent> agentList=sysAgentMapper.getAccount(account);
            Assert.isTrue(agentList.isEmpty(),TipMsgConstant.SYS_ACCOUNT);
//            List<SysAgent> gameIdAccountList=sysAgentMapper.getGameId(gameId);
//            Assert.isTrue(gameIdAccountList.isEmpty(),TipMsgConstant.SYS_GAME_ID);
            SysAgent sysAgent=new SysAgent();
            List<Role> list=roleMapper.get(new HashMap<String, Object>(){{
                put(FieldConstant.DBFieldConstant.ROLE_NAME.name(), office);
            }});
            /*List<AccountsInfo> gameIdList=accountsInfoMapper.get(new HashMap<String, Object>(){{
                put(FieldConstant.ModelFieldConstant.gameId.name(), gameId);
            }});
            Assert.isTrue(!gameIdList.isEmpty(),TipMsgConstant.SYS_PLAY);
            sysAgent.setGameId(gameId);*/
            Assert.isTrue(!list.isEmpty(),TipMsgConstant.PARAM_INVALID);
            String nickName=name+office;//名称
            sysAgent.setNickName(nickName);
            sysAgent.setAccount(account);
            sysAgent.setRoleId(list.get(0).getRoleId());
            SysAgent.Builder.defaultPwdInject(sysAgent);
            int n=sysAgentMapper.OpenOffice(sysAgent);
            Assert.isTrue(n>0,TipMsgConstant.OPERATION_FAILED);
            return WebResult.of();
    }

    @GetMapping("/clear/record/")
    private WebResult getClearRecord(String startDate, String endDate) {
        return WebResult.of(sysAgentClearRecordService.getClear(startDate, endDate));
    }

    @PostMapping("/clear/record/")
    private WebResult saveClearRecord(String startDate, String endDate) throws IOException {
        try {
            sysAgentClearRecordService.getCleanTime(startDate,endDate);
            sysAgentClearExcelService.makeExcel(startDate, endDate);
        } catch (IOException e) {
            throw new IOException(TipMsgConstant.REPORT_GENERATION_FAILED);
        }
        sysAgentClearRecordService.save(sysAgentClearRecordService.getClear(startDate, endDate));
        return WebResult.of();
    }

    @GetMapping("/download/excel/clear/{fileName}")
    private void downClearExcel(HttpServletResponse response, @PathVariable String fileName) throws Exception {
        FileUtil.read(response, FileConstant.ExcelConstant.CLEAR_EXCEL_PARENT + fileName + ".xlsx");
    }


    //能获取到登录的账号
    @GetMapping("/loginAgent/{account}/")
    private void loginAgent(@PathVariable String account ,HttpServletResponse response) throws IOException {
        UserDetails userDetails = UserDetailsUtil.getUserDetails();
        List<SysAgent> list=sysAgentMapper.get(new HashMap<String,Object>(){{
                put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(),account);
        }});
        SysAgent sysAgent=list.get(0);
        UserDetailsUtil.refresh(sysAgent);
        response.sendRedirect("/agent/index.html" );
    }

    @GetMapping("/getOfficeMessage/")
    public WebResult getOfficeMessage(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String ,Object> params){
        return WebResult.of(sysAgentService.getOfficeMessage(page,size,params));
    }

    @GetMapping("/getOfficeAgentPayMessage/")
    public WebResult getOfficeAgentPayMessage(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String ,Object> params){
        return WebResult.of(sysAgentService.getOfficeAgentPayMessage(page,size,params));
    }


    @GetMapping("/getOfficeAgentSumPayMessage/")
    @JsonSerialize
    public @ResponseBody JSONObject getOfficeAgentSumPayMessage(@RequestParam Map<String ,Object> params){
        return  sysAgentService.getOfficeAgentSumPayMessage(params);
    }


    /**
     * 管理员赠送代理房卡
     */
    @PostMapping("/saveCardGift")
    public WebResult saveCardGift(@RequestBody CardGift4AgentRecord cardGift4AgentRecord){
        cardGift4AgentRecordService.saves(cardGift4AgentRecord);
        return  WebResult.of();
    }
    /**
     * 管理员赠送代理房卡记录
     */
    @GetMapping("/querypayforList")
    public WebResult querypayforList(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(cardGift4AgentRecordService.querypayforList(page,size,params));
    }

    /**
     * 管理员给玩家赠卡
     */
    @PutMapping("/cardGift/")
    private WebResult cardGift(int presentee, int cardNum, String giftReason) {
        gameScoreInfoService.cardGift(presentee, cardNum, giftReason);
        return WebResult.of();
    }


    /**
     *管理员玩家赠卡记录
     */
    @GetMapping("/queryCardPlayer")
    public WebResult queryCardPlayer(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return  WebResult.of(cardGift4PlayerRecordService.queryCardPlayer(page,size,params));
    }

    @GetMapping("/cardCost/User/list/")
    private WebResult cardCostUserRecord(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String, Object> params) {
        return WebResult.of(sysAgentService.getNewInsert(page,size,params));
    }

    @GetMapping("/payOrder/list/")
    private WebResult payOrderSerice(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String, Object> params) {
        return WebResult.of(payOrderSerice.getPayDay(page,size,params));
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







}
