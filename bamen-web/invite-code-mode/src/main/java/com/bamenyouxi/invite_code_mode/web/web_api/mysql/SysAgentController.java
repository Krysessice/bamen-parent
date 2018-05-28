package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.core.util.FileUtil;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.service.mysql.CardGiftRecordService;
import com.bamenyouxi.invite_code_mode.service.mysql.SysAgentService;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 后台代理 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/agent")
final class SysAgentController extends AbstractCrudController<SysAgent, Long> {
	@Autowired
	private SysAgentService sysAgentService;

	@Autowired
	private SysAgentMapper sysAgentMapper;

	@Autowired
	private CardGiftRecordService cardGiftRecordService;

	@Override
	public AbstractCrudService<SysAgent, Long> getService() {
		return sysAgentService;
	}

	@PutMapping("/authorize/{id}/")
	private WebResult authorize(@PathVariable Long id) {
		sysAgentService.authorize(id);
		return WebResult.of();
	}

	@PutMapping("/info/")
	private WebResult updateInfo(String realName, String tel, String openingBank, String bankAccount, String province, String city) {
		Assert.isTrue(!UserDetailsUtil.isFinishInfo(), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
		SysAgent sysAgent = sysAgentService.update(new SysAgent.Builder()
				.id(UserDetailsUtil.getId())
				.realName(realName).tel(tel)
				.openingBank(openingBank)
				.bankAccount(bankAccount)
				.province(province)
				.city(city)
				.isFinishInfo(true).build());
		UserDetailsUtil.refresh(sysAgent);
		return WebResult.of();
	}

	@PutMapping("/authorizeAgent/{gameId}/")
	private WebResult authorizeAgent(@PathVariable String gameId) {
		sysAgentService.authorizeAgent(gameId);
		return WebResult.of();
	}

	/**
	 * 合伙人进代理后台
	 */
	@GetMapping("/loginAgent/{gameId}/")
	private void loginAgent(@PathVariable Integer gameId ,HttpServletResponse response) throws IOException {
		UserDetails userDetails = UserDetailsUtil.getUserDetails();
		SysAgent listSubset=sysAgentMapper.querySubset(gameId);
		UserDetailsUtil.refresh(listSubset);
		response.sendRedirect("/agent/index.html" );
	}

	@PutMapping("/authorizeNo/{gameId}/")
	private WebResult authorizeNo(@PathVariable String gameId) {
		sysAgentService.authorizeNo(gameId);
		return WebResult.of();
	}

	@GetMapping("/pages/")
	@JsonSerialize
	public @ResponseBody JSONObject pages(){
		return  sysAgentService.pages();
	}


	@PutMapping("/pwd/")
	private WebResult updatePwd(String oldPwd, String newPwd) {
		sysAgentService.changePassword(oldPwd, newPwd);
		return WebResult.of();
	}

	@PutMapping("/announce/close/")
	private WebResult closeAnnounce() {
		Assert.isTrue(UserDetailsUtil.isShowAnnounce(), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
		SysAgent sysAgent = sysAgentService.update(new SysAgent.Builder()
				.id(UserDetailsUtil.getId())
				.showAnnounce(false).build());
		UserDetailsUtil.refresh(sysAgent);
		return WebResult.of();
	}

	@GetMapping("/getSumCard")
	public WebResult queryAgentModel(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params){
		return WebResult.of(sysAgentService.querysubclass(page,size,params));
	}

	@GetMapping("/User/list/")
	private WebResult queryGoldsubclass(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(sysAgentService.queryGoldsubclass(page,size,params));
	}

	@GetMapping("/queryGoldSums/")
	@JsonSerialize
	public @ResponseBody JSONObject queryGoldSums(@RequestParam Map<String, Object> params){
		return  sysAgentService.queryGoldSums(params);
	}


	@GetMapping("/cardGift/list/")
	public WebResult queryAgent(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params){
		return WebResult.of(cardGiftRecordService.queryAgent(page,size,params));
	}

	@GetMapping("/queryTimeSumMoney/")
	@JsonSerialize
	public @ResponseBody JSONObject queryTimeSumMoney(@RequestParam Map<String, Object> params){
		return  sysAgentService.queryTimeSumMoney(params);
	}

	@GetMapping("/queryAgentOne/")
	@JsonSerialize
	public @ResponseBody JSONObject queryAgentOne(){
		return  cardGiftRecordService.queryAgentOne();
	}

	@GetMapping("/queryAgentOneTime/")
	@JsonSerialize
	public @ResponseBody JSONObject queryAgentOneTime(@RequestParam Map<String, Object> params){
		return  cardGiftRecordService.queryAgentOneTime(params);
	}


	@GetMapping("/file/default/")
	private void read(HttpServletResponse response) throws Exception {
		FileUtil.read(response, FileConstant.DEFAULT_FILE_PATH);
	}
}
