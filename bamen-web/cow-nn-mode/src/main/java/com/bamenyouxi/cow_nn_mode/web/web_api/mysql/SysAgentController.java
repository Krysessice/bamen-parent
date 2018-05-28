package com.bamenyouxi.cow_nn_mode.web.web_api.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.core.util.FileUtil;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.CardGift4AgentRecordMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.CardGift4PlayerRecordMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.RoleMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.SysAgentMapper;
import com.bamenyouxi.cow_nn_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.cow_nn_mode.mapper.sqlserver.treasure.GameScoreInfoMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4AgentRecord;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4PlayerRecord;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Role;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.sqlserver.account.AccountsInfo;
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
 * 后台代理控制层
 * Created by 13477 on 2018/5/12.
 */
@RestController
@RequestMapping("/agent")
final class SysAgentController extends AbstractCrudController<SysAgent, Long> {
	@Autowired
	private SysAgentService sysAgentService;

	@Autowired
	private SysAgentMapper sysAgentMapper;

	@Autowired
	private GameScoreInfoService gameScoreInfoService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private CardGift4AgentRecordService cardGift4AgentRecordService;
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private PayOrderSerice payOrderSerice;

	@Autowired
	private RebateSerice rebateSerice;
	@Autowired
	private CardGift4AgentRecordMapper cardGift4AgentRecordMapper;

	@Autowired
	private AccountsInfoMapper accountsInfoMapper;

	@Autowired
	private GameScoreInfoMapper gameScoreInfoMapper;

	@Autowired
	private CardGift4PlayerRecordService cardGift4PlayerRecordService;
	@Autowired
	private CardGift4PlayerRecordMapper gift4PlayerRecordMapper;

	@Override
	public AbstractCrudService<SysAgent, Long> getService() {
		return sysAgentService;
	}


	@GetMapping("/file/default/")
	private void  read(HttpServletResponse response) throws Exception {
		FileUtil.read(response, FileConstant.DEFAULT_FILE_PATH);
	}

	@PutMapping("/info/")
	private WebResult updateInfo(String realName, String tel, String openingBank, String bankAccount, String province, String city) {
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

	/**
	 * 注册新的代理
	 */
	@PostMapping("/getOffice/{name}/{office}/{account}")
	public WebResult getOffice(@PathVariable String name,@PathVariable String office,@PathVariable String account) throws IOException {
		List<SysAgent> agentList=sysAgentMapper.getAccount(account);
		Assert.isTrue(agentList.isEmpty(),TipMsgConstant.SYS_ACCOUNT);
	/*	List<SysAgent> gameIdAccountList=sysAgentMapper.getGameId(gameId);
		Assert.isTrue(gameIdAccountList.isEmpty(),TipMsgConstant.SYS_GAME_ID);*/
		SysAgent sysAgent=new SysAgent();
		List<Role> list=roleMapper.get(new HashMap<String, Object>(){{
			put(FieldConstant.DBFieldConstant.ROLE_NAME.name(), office);
		}});
		Assert.isTrue(!list.isEmpty(),TipMsgConstant.PARAM_INVALID);
		/*List<AccountsInfo> gameIdList=accountsInfoMapper.get(new HashMap<String, Object>(){{
			put(FieldConstant.ModelFieldConstant.gameId.name(), gameId);
		}});
		Assert.isTrue(!gameIdList.isEmpty(),TipMsgConstant.SYS_PLAY);
		sysAgent.setGameId(gameId);*/
		String nickName=name+office;//名称
		sysAgent.setNickName(nickName);
		sysAgent.setAccount(account);
		sysAgent.setRoleId(list.get(0).getRoleId());
		List<SysAgent> sysAgentList=sysAgentMapper.get(new HashMap<String, Object>(){{
			put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(), UserDetailsUtil.getAccoutnt());
		}});
		String str=null;
		String strName=null;
		if(sysAgentList.get(0).getSuperAgentId()==null&&sysAgentList.get(0).getSuperName()==null){
			str=sysAgentList.get(0).getAccount();
			strName=sysAgentList.get(0).getNickName();
		}else{
			str=sysAgentList.get(0).getSuperAgentId()+","+sysAgentList.get(0).getAccount();
			strName=sysAgentList.get(0).getSuperName()+","+sysAgentList.get(0).getNickName();
		}
		System.out.println("上级ID："+str);
		System.out.println("上级名称："+strName);
		sysAgent.setSuperAgentId(str);
		sysAgent.setSuperName(strName);
		sysAgent.setSuperRoleId(sysAgentList.get(0).getRoleId());
		SysAgent.Builder.defaultPwdInject(sysAgent);
		int n=sysAgentMapper.OpenOffice(sysAgent);
		Assert.isTrue(n>0,TipMsgConstant.OPERATION_FAILED);
		return WebResult.of();
	}

	@PutMapping("/pwd/")
	public WebResult updatePwd(String oldPwd,String newPwd){
		sysAgentService.changePassword(oldPwd,newPwd);
		return  WebResult.of();
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

	@GetMapping("/getJurisdiction/")
	@JsonSerialize
	public @ResponseBody JSONObject getJurisdiction(String account){
		return  sysAgentService.getJurisdiction(account);
	}


	@GetMapping("/getSalesmanPayOrder/")
	public WebResult salesmanPayOrderModel(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String ,Object> params){
		return WebResult.of(sysAgentService.getSalesmanPayOrder(page,size,params));
	}

	@GetMapping("/getSalesmanPayOrderSum/")
	@JsonSerialize
	public @ResponseBody JSONObject getSalesmanPayOrderSum(@RequestParam Map<String ,Object> params){
		return  sysAgentService.getSalesmanPayOrderSum(params);
	}

	/**
	 * 查看用户剩余房卡
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
	 * 代理赠送代理房卡
	 */
	@PostMapping("/saveAgentCardgift")
	public WebResult saveAgentCardgift(@RequestBody CardGift4AgentRecord cardGift4AgentRecord){
		List<SysAgent> list=sysAgentMapper.getAll(cardGift4AgentRecord.getPresentee().toString());
		List<SysAgent> lists=sysAgentMapper.queryMun(UserDetailsUtil.getAccoutnt());
		System.out.println(lists.get(0).getCardNum());
		int sumCard=lists.get(0).getCardNum();

		CardGift4AgentRecord cardGift4AgentRecords=cardGift4AgentRecordMapper.queryNumOne(UserDetailsUtil.getAccoutnt());
		CardGift4PlayerRecord cardGift4PlayerRecord=gift4PlayerRecordMapper.queryNumTwo(UserDetailsUtil.getAccoutnt());
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


	@GetMapping("/pay/list/")
	private WebResult payOrderSerice(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(payOrderSerice.getPayList(page,size,params));
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









}
