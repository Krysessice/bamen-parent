package com.bamenyouxi.invite_code_mode.web.web_api;

import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.core.util.FileUtil;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import com.bamenyouxi.invite_code_mode.service.excel.SysAgentClearExcelService;
import com.bamenyouxi.invite_code_mode.service.mysql.*;
import com.bamenyouxi.invite_code_mode.service.sqlserver.GameScoreInfoService;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
	private SysAgentClearRecordService sysAgentClearRecordService;
	@Autowired
	private SysAgentClearExcelService sysAgentClearExcelService;
	@Autowired
	private UserOpenRoomPerdayRecordService userOpenRoomPerdayRecordService;
	@Autowired
	private CardGiftRecordService cardGiftRecordService;
	@Autowired
	private GameScoreInfoService gameScoreInfoService;
	@Autowired
	private RedisUtil redisUtil;

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

	@GetMapping("/download/excel/clear/{fileName}")
	private void downClearExcel(HttpServletResponse response, @PathVariable String fileName) throws Exception {
		FileUtil.download(response, FileConstant.ExcelConstant.CLEAR_EXCEL_PARENT + fileName + ".xlsx");
	}

	@GetMapping("/cardCost/perday/list/")
	private WebResult cardCostPerdayRecord(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(userOpenRoomPerdayRecordService.sumPerday(page, size, params));
	}

	@GetMapping("/cardGift/list/")
	private WebResult cardGiftRecord(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(cardGiftRecordService.list(page, size, params));
	}

	@PutMapping("/cardGift/")
	private WebResult cardGift(int presentee, int cardNum, String giftReason) {
		gameScoreInfoService.cardGift(presentee, cardNum, giftReason);
		return WebResult.of();
	}
}
