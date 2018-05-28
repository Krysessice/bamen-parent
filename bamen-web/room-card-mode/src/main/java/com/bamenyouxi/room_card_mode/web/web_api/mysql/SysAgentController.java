package com.bamenyouxi.room_card_mode.web.web_api.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.core.util.FileUtil;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.*;
import com.bamenyouxi.room_card_mode.mapper.sqlserver.treasure.GameScoreInfoMapper;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 后台代理控制层
 * Created by 13477 on 2017/8/26.
 */
@RestController
@RequestMapping("/agent")
final class SysAgentController extends AbstractCrudController<SysAgent, Long> {
	@Autowired
	private SysAgentService sysAgentService;

	@Autowired
	private SysAgentMapper sysAgentMapper;

	@Autowired
	private CardGift4PlayerRecordMapper cardGift4PlayerRecordMapper;

	@Autowired
	private CardGift4PlayerRecordService cardGift4PlayerRecordService;

	@Autowired
	private GameScoreInfoService gameScoreInfoService;

	@Autowired
	private CardGift4AgentRecordService cardGift4AgentRecordService;

	@Autowired
	private GameScoreInfoMapper gameScoreInfoMapper;

	@Autowired
	private CardGift4PlayerRecordMapper gift4PlayerRecordMapper;

	@Autowired
	private CardGift4AgentRecordMapper cardGift4AgentRecordMapper;

	@Autowired
	private CardBonusRecordMapper cardBonusRecordMapper;

	@Autowired
	private PayOrderService payOrderService;

	@Autowired
	private CardBonusRecordService cardBonusRecordService;



	@Override
	public AbstractCrudService<SysAgent, Long> getService() {
		return sysAgentService;
	}


	@GetMapping("/file/default/")
	private void  read(HttpServletResponse response) throws Exception {
		FileUtil.read(response, FileConstant.DEFAULT_FILE_PATH);
	}

	@PutMapping("/authorize/{account}/")
	private WebResult authorize(@PathVariable String account) {
		sysAgentService.authorize(account);
		return WebResult.of();
	}

	@PutMapping("/authorizes/{account}/")
	private WebResult authorizes(@PathVariable String account) {
		sysAgentService.authorizes(account);
		return WebResult.of();
	}

	/**
	 * 代理开通代理
	 */
	@PostMapping("/RegisterAgent")
	public WebResult RegisterAgent(@RequestBody SysAgent sysAgent){
		SysAgent.Builder.defaultPwdInject(sysAgent);
		sysAgent.setSuperAgentId(UserDetailsUtil.getAccoutnt());
		int n=sysAgentMapper.saveAll(sysAgent);
		Assert.isTrue(n>0, TipMsgConstant.OPERATION_FAILED);
		return  WebResult.of();
	}

	@PutMapping("/pwd/")
	public WebResult updatePwd(String oldPwd,String newPwd){
		sysAgentService.changePassword(oldPwd,newPwd);
		return  WebResult.of();
	}

	/**
	 * 查看剩余房卡
	 */
	@GetMapping("/queryNum")
	@JsonSerialize
	public @ResponseBody JSONObject HasNum(){
		return  gameScoreInfoService.hasCard();
	}


	/**
	 *代理赠送游戏用户房卡
	 */
	@PutMapping("/agentCardGift/")
	public WebResult agentCardGift(int presentee, int cardNum, String giftReason){
		 gameScoreInfoService.agentCardGift(presentee,cardNum,giftReason);
		return WebResult.of();
	}

	@GetMapping("/pages/")
	@JsonSerialize
	public @ResponseBody JSONObject pages(String account){
		return  sysAgentService.pages(account);
	}


	/**
	 * 下级的代理
	 */
	@GetMapping("/downAgent/")
	public WebResult downAgent(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			String params){
		return WebResult.of(sysAgentService.downAgent(page,size,params));
	}


	@GetMapping("/getSumCard")
	public WebResult queryAgentModel(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params){
		return WebResult.of(sysAgentService.querysubclass(page,size,params));
	}

	@GetMapping("/queryTimeSumMoney/")
	@JsonSerialize
	public @ResponseBody JSONObject queryTimeSumMoney(@RequestParam Map<String, Object> params){
		return  sysAgentService.queryTimeSumMoney(params);
	}



	/**
	 * 下级的代理
	 */
	@GetMapping("/downAgents/")
	public WebResult downAgents(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			String params){
		return WebResult.of(sysAgentService.downAgents(page,size,params));
	}

	@GetMapping("/queryAgentDown/")
	public WebResult queryAgentDown(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			String teamNo){
				System.out.println(teamNo);
		return WebResult.of(sysAgentService.queryAgentDown(page,size,teamNo));
	}


	/**
	 * 代理购卡记录
	 */
	@GetMapping("/queryPayCard/")
	public WebResult queryPayCard(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			String params){
			return WebResult.of(payOrderService.queryPayCard(page,size,params));
	}

	/**
	 * 代理赠卡代理记录
	 */
	@GetMapping("/queryGiftAgent/")
	public WebResult queryGiftAgent(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			String params){
		return WebResult.of(cardGift4AgentRecordService.queryGiftAgent(page,size,params));
	}

	/**
	 * 代理赠卡玩家记录
	 */
	@GetMapping("/queryAgentPlayer/")
	public WebResult queryAgentPlayer(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			String params){
		return WebResult.of(cardGift4PlayerRecordService.queryAgentPlayer(page,size,params));
	}

	/**
	 * 代理返卡记录
	 */
	@GetMapping("/queryOne/")
	public WebResult queryOne(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			String params){
		return WebResult.of(cardBonusRecordService.queryOne(page,size,params));
	}

	/**
	 * 代理返卡记录
	 */
	@GetMapping("/queryTwo/")
	public WebResult queryTwo(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			String params){
		return WebResult.of(cardBonusRecordService.queryTwo(page,size,params));
	}


	/**
	 * 代理赠送代理房卡
	 */
	@PostMapping("/saveAgentCardgift")
	public WebResult saveAgentCardgift(@RequestBody CardGift4AgentRecord cardGift4AgentRecord){
		System.out.println(cardGift4AgentRecord.getCardNum());
		List<SysAgent> list=sysAgentMapper.getAll(cardGift4AgentRecord.getPresentee().toString());

		List<SysAgent> lists=sysAgentMapper.queryMun(UserDetailsUtil.getAccoutnt());
		System.out.println(lists.get(0).getCardNum());

		CardBonusRecord one=cardBonusRecordMapper.getOne(UserDetailsUtil.getAccoutnt());
		CardBonusRecord two=cardBonusRecordMapper.getTwo(UserDetailsUtil.getAccoutnt());
		int superCard=one.getFirBonus()+two.getSecBonus();
		int sumCard=lists.get(0).getCardNum()+superCard;


		CardGift4AgentRecord cardGift4AgentRecords=cardGift4AgentRecordMapper.queryNumOne(lists.get(0).getAccount());
		CardGift4PlayerRecord cardGift4PlayerRecord=gift4PlayerRecordMapper.queryNumTwo(lists.get(0).getAccount());
		int Num=cardGift4AgentRecords.getCardNum()+cardGift4PlayerRecord.getCardNum();

		int HasNum=sumCard-Num;

		JSONObject result=new JSONObject();
		result.put("sum",HasNum);

		Assert.isTrue(!(list.isEmpty() || list.get(0) == null), TipMsgConstant.SYS_NOTAGENT);
		Assert.isTrue(!(lists.isEmpty() || lists.get(0) == null), TipMsgConstant.SYS_NOTAGENT);
		if(HasNum>=cardGift4AgentRecord.getCardNum()){
			cardGift4AgentRecord.setPresenter(Long.valueOf(UserDetailsUtil.getAccoutnt()));
			int n=cardGift4AgentRecordService.getMapper().save(cardGift4AgentRecord);
			return WebResult.of(list.get(0));
		}else{
			Assert.isTrue(lists.size()<0,TipMsgConstant.PAY_NOTCARD);
		}
		return  WebResult.of();
	}



}
