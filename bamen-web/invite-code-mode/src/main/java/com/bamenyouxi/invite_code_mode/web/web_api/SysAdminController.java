package com.bamenyouxi.invite_code_mode.web.web_api;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.core.util.FileUtil;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.GoldInfo;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreLocker;
import com.bamenyouxi.invite_code_mode.service.excel.SysAgentClearExcelService;
import com.bamenyouxi.invite_code_mode.service.excel.SysGoldClearExcelService;
import com.bamenyouxi.invite_code_mode.service.mysql.*;
import com.bamenyouxi.invite_code_mode.service.sqlserver.GameScoreInfoService;
import com.bamenyouxi.invite_code_mode.service.sqlserver.GameScoreLockerService;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/admin")
final class SysAdminController {
	@Autowired
	private SysAgentService sysAgentService;
	@Autowired
	private SystemInfoService systemInfoService;
	@Autowired
	private GoldInfoService goldInfoService;
	@Autowired
	private SysAgentClearRecordService sysAgentClearRecordService;
	@Autowired
	private SysAgentClearExcelService sysAgentClearExcelService;
	@Autowired
	private SysGoldClearExcelService  sysGoldClearExcelService;
	@Autowired
	private UserOpenRoomPerdayRecordService userOpenRoomPerdayRecordService;
	@Autowired
	private CardGiftRecordService cardGiftRecordService;
	@Autowired
	private GameScoreInfoService gameScoreInfoService;
	@Autowired
	private GameScoreLockerService gameScoreLockerService;
	@Autowired
	private PayOrderPerdayStatisticService payOrderPerdayStatisticService;
	@Autowired
	private OpenRoomPerhourRecordService openRoomPerhourRecordService;
	@Autowired
	private CleanGoldRecordService cleanGoldRecordService;
	@Autowired
	private SysGoldClearRecordService sysGoldClearRecordService;
	@Autowired
	private  PayGoldRecordService payGoldRecordService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private SysAgentMapper sysAgentMapper;

	/* 修改代理真实资料与邀请码 */
	@PutMapping("/sysAgent/realInfoAndInvitedCode/")
	private WebResult updateRealInfoAndInvitedCode(@RequestBody SysAgent sysAgent) {
		sysAgentService.updateRealInfoAndInvitedCode(sysAgent);
		return WebResult.of();
	}

	@PutMapping("/sysAgent/freeze/{gameId}/")
	private WebResult sysAgentFreeze(@PathVariable int gameId, boolean sysFlag) {
		sysAgentService.sysAgentFreeze(gameId, sysFlag);
		return WebResult.of();
	}

	@PutMapping("/sysAgent/pwdReset/{gameId}/")
	private WebResult pwdReset(@PathVariable int gameId) {
		sysAgentService.pwdReset(gameId);
		return WebResult.of();
	}

	/* 更新系统信息 */
	@PutMapping("/systemInfo/")
	private WebResult updateSystemInfo(@RequestBody SystemInfo systemInfo) {
		systemInfoService.save(systemInfo);
		redisUtil.saveSystemInfo(systemInfo);
		return WebResult.of();
	}

	/* 更新金币信息 */
	@PutMapping("/goldInfo/{t1_commission}/{t2_commission}/{t3_commission}")
	private WebResult updateGoldInfo(@PathVariable BigDecimal t1_commission , @PathVariable BigDecimal t2_commission, @PathVariable BigDecimal t3_commission) {
		goldInfoService.updateInfo(t1_commission,t2_commission,t3_commission);
		return WebResult.of();
	}

	@GetMapping("/getGoldInfo/")
	@JsonSerialize
	public @ResponseBody JSONObject getGoldInfo(){
		return  goldInfoService.getGoldInfo();
	}


	/* 赠送靓号，刷新代理信息 */
	@PutMapping("/refresh/")
	private WebResult refresh(Integer srcGameId, Integer destGameId) {
		sysAgentService.refresh(srcGameId, destGameId);
		return WebResult.of();
	}

	@GetMapping("/clear/record/")
	private WebResult getClearRecord(String startDate, String endDate) {
		return WebResult.of(sysAgentClearRecordService.getClear(startDate, endDate));
	}

	@PostMapping("/clear/record/")
	private WebResult saveClearRecord(String startDate, String endDate) throws IOException {
		try {
			sysAgentClearExcelService.makeExcel(startDate, endDate);
		} catch (IOException e) {
			throw new IOException(TipMsgConstant.REPORT_GENERATION_FAILED);
		}
		sysAgentClearRecordService.save(sysAgentClearRecordService.getClear(startDate, endDate));
		return WebResult.of();
	}

	@GetMapping("/clear/gold/record/")
	private WebResult getGoldClearRecord(String startDate, String endDate) {
		return WebResult.of(sysGoldClearRecordService.getClear(startDate, endDate));
	}

	@PostMapping("/clear/gold/record/")
	private WebResult saveGoldClearRecord(String startDate, String endDate) throws IOException {
		try {
			sysGoldClearRecordService.getCleanTime(startDate,endDate);
			sysGoldClearExcelService.makeExcel(startDate, endDate);
		} catch (IOException e) {
			throw new IOException(TipMsgConstant.REPORT_GENERATION_FAILED);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sysGoldClearRecordService.save(sysGoldClearRecordService.getClear(startDate, endDate));
		return WebResult.of();
	}


	@GetMapping("/download/excel/clear/{fileName}")
	private void downClearExcel(HttpServletResponse response, @PathVariable String fileName) throws Exception {
		FileUtil.read(response, FileConstant.ExcelConstant.CLEAR_EXCEL_PARENT + fileName + ".xlsx");
	}

	@GetMapping("/download/excel/gold/clear/{fileName}")
	private void downGoldClearExcel(HttpServletResponse response, @PathVariable String fileName) throws Exception {
		FileUtil.read(response, FileConstant.ExcelConstant.CLEAR_EXCEL_PARENT + fileName + ".xlsx");
	}

	@GetMapping("/getService/")
	@JsonSerialize
	public @ResponseBody JSONObject getService(String game){
		return sysAgentService.getService(game);
	}

	@GetMapping("/cardCost/perday/list/")
	private WebResult cardCostPerdayRecord(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(userOpenRoomPerdayRecordService.sumPerday(page, size, params));
	}

	@GetMapping("/cardCost/User/list/")
	private WebResult cardCostUserRecord(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(sysAgentService.getNewInsert(page,size,params));
	}





	@GetMapping("/clean/gold/list/")
	private WebResult getCleanGoldRecord(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(cleanGoldRecordService.getCleanGoldRecord(page,size,params));
	}

	@GetMapping("/getPartner/")
	private WebResult getPartner(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(sysAgentService.getPartner(page,size,params));
	}


	@GetMapping("/getGameType/")
	private WebResult getGameType(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(userOpenRoomPerdayRecordService.getGameType(page,size,params));
	}

	@GetMapping("/cancelPartner/")
	private WebResult cancelPartner(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(sysAgentService.cancelPartner(page,size,params));
	}

	@GetMapping("/cardGift/list/")
	private WebResult cardGiftRecord(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(cardGiftRecordService.selectedName(page, size, params));
	}

	@GetMapping("/payGold/list/")
	private WebResult payGoldRecord(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(payGoldRecordService.selectedName(page, size, params));
	}

	@GetMapping("/queryGiveAgentList/")
	private WebResult queryGiveAgentList(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(sysAgentService.queryGiveAgentList(page, size, params));
	}

	@GetMapping("/giveAgentList/")
	private WebResult giveAgentList(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(sysAgentService.giveAgentList(page, size, params));
	}


	@GetMapping("/querySubsets/")
	private WebResult querySubsets(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(sysAgentService.querySubsets(page, size, params));
	}

	@PutMapping("/cardGift/")
	private WebResult cardGift(int presentee,String cardPrice, int cardNum,String selected, String giftReason) {
		gameScoreInfoService.cardGift(presentee, cardPrice,cardNum,selected, giftReason);
		return WebResult.of();
	}

	@PutMapping("/payGold/")
	private WebResult payGold(int presentee,String goldPrice, int goldNum,String selected, String payReason) {
		gameScoreInfoService.payGold(presentee, goldPrice,goldNum,selected, payReason);
		return WebResult.of();
	}

	/* 卡线用户列表 */
	@GetMapping("/gameScoreLocker/list/")
	private WebResult gameScoreLockerList(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(gameScoreLockerService.list(page, size, params));
	}

	/* 清理卡线 */
	@DeleteMapping("/gameScoreLocker/{userId}/")
	private WebResult delGameScoreLocker(@PathVariable int userId) {
		gameScoreLockerService.delete(GameScoreLocker.of(userId));
		return WebResult.of();
	}

	/* 每日订单列表 */
	@GetMapping("/payOrder/perday/list/")
	private WebResult payOrderPerdayList(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size) {
		return WebResult.of(payOrderPerdayStatisticService.list(page, size, new HashMap<>()));
	}

	@GetMapping("/partnerQuerysubclass")
	public WebResult partnerQuerysubclass(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params){
		return WebResult.of(sysAgentService.partnerQuerysubclass(page,size,params));
	}

	@GetMapping("/partnerGoldQuerysubclass")
	public WebResult partnerGoldQuerysubclass(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params){
		return WebResult.of(sysAgentService.partnerGoldQuerysubclass(page,size,params));
	}

	@GetMapping("/querysGoldSums/")
	@JsonSerialize
	public @ResponseBody JSONObject querysGoldSums(@RequestParam Map<String, Object> params){
		return  sysAgentService.querysGoldSums(params);
	}


	@GetMapping("/partnerQueryTimeSumMoney/")
	@JsonSerialize
	public @ResponseBody JSONObject partnerQueryTimeSumMoney(@RequestParam Map<String, Object> params){
		return  sysAgentService.partnerQueryTimeSumMoney(params);
	}


	/**
	 * 10003授权合伙人
	 */
	@PutMapping("/updateAiveAgentList/{gameId}/")
	private WebResult authorizeAgent(@PathVariable Integer gameId) {
		sysAgentService.updateAiveAgentList(gameId);
		return WebResult.of();
	}
	/**
	 * 10003取消授权合伙人
	 */
	@PutMapping("/updateNoAiveAgentList/{gameId}/")
	private WebResult updateNoAiveAgentList(@PathVariable Integer gameId) {
		sysAgentService.updateNoAiveAgentList(gameId);
		return WebResult.of();
	}


	@GetMapping("/queryPrice/")
	@JsonSerialize
	public @ResponseBody JSONObject queryPrice(@RequestParam Map<String, Object> params){
		return  cardGiftRecordService.queryPrice(params);
	}


	@GetMapping("/queryPriceSum/")
	@JsonSerialize
	public @ResponseBody JSONObject queryPriceSum(){
		return  cardGiftRecordService.queryPriceSum();
	}


	/**
	 * 合伙人进代理后台
	 */
	@GetMapping("/getAccredit/{gameId}/")
	private void getAccredit(@PathVariable Integer gameId ,HttpServletResponse response) throws IOException {
		UserDetails userDetails = UserDetailsUtil.getUserDetails();
		SysAgent listSubset=sysAgentMapper.querySubset(gameId);
		UserDetailsUtil.refresh(listSubset);
		response.sendRedirect("/agent/index.html" );
	}

}
