package com.bamenyouxi.cow_nn_mode.service.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.core.util.UUIDUtil;
import com.bamenyouxi.cow_nn_mode.constant.SysConstant;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.SysAgentMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgentVo;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SystemInfo;
import com.bamenyouxi.cow_nn_mode.util.RedisUtil;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.Null;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 后台代理 service
 * Created by 13477 on 2017/7/10.
 */
@Service
public class SysAgentService extends AbstractCrudService<SysAgent, Long> implements CommandLineRunner {

	@Autowired
	private SysAgentMapper sysAgentMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public CrudMapper<SysAgent, Long> getMapper() {
		return sysAgentMapper;
	}

	/**
	 * 检验账号是否存在
	 * @param account   账号
	 * @return  是否存在的标识
	 */
	private boolean exist(String account) {
		long count = PageHelper.count(
				() -> getMapper().get(new HashMap<String, Object>() {{
					put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(), account);
				}})
		);
		return count > 0;
	}

	@Override
	protected void saveBefore(SysAgent sysAgent) {
		Assert.notNull(sysAgent, TipMsgConstant.PARAM_INVALID);
		Assert.isTrue(sysAgent.getAccount() != null && sysAgent.getAccount().length() == SysConstant.getAccountLength(), TipMsgConstant.PARAM_INVALID);
		Assert.notNull(sysAgent.getNickName(), TipMsgConstant.PARAM_INVALID);
		super.saveBefore(sysAgent);
	}

	/**
	 * 办事处信息
	 */
	@Transactional
	public PageInfo<SysAgent> getOfficeMessage(int page, int size, Map<String,Object> params){
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
		List<SysAgent> list=sysAgentMapper.get(new HashMap<String,Object>(){{
			put(FieldConstant.DBFieldConstant.F_ROLE_ID.name(),1);
		}});
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	@Transactional
	public PageInfo<SysAgent> getOfficeAgentPayMessage(int page, int size, Map<String,Object> params){
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESCB);
		List<SysAgent> list=sysAgentMapper.getOfficeAgentPayMessage(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	@Transactional
	public JSONObject getOfficeAgentSumPayMessage(Map<String ,Object> params){
		SysAgent sysAgent=sysAgentMapper.getOfficeAgentSumPayMessage(params);
		BigDecimal n=sysAgent.getPayPrice();
		JSONObject result=new JSONObject();
		result.put("n",n);
		return result;
	}


	//业务员统计
	@Transactional
	public PageInfo<SysAgent> getSalesmanPayOrder(int page, int size, Map<String,Object> params){
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESCB);
		List<SysAgent> list=sysAgentMapper.salesmanPayOrderModel(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	@Transactional
	public JSONObject getSalesmanPayOrderSum(Map<String ,Object> params){
		SysAgent sysAgent=sysAgentMapper.getSalesmanPayOrderSum(params);
		BigDecimal n=sysAgent.getPayPrice();
		JSONObject result=new JSONObject();
		result.put("n",n);
		return result;
	}

	/**
	 * 统计每天新增
	 */
	@Transactional
	public PageInfo<SysAgent> getNewInsert(int page, int size, Map<String, Object> params) {
		super.listBefore(params);
		PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
		params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
		List<SysAgent> list = sysAgentMapper.getNewInsert(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}







	/**
	 *
	 * @param src
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object deepCopy(Object src) throws IOException, ClassNotFoundException{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in =new ObjectInputStream(byteIn);
		if(src instanceof  List){
			List dest = (List)in.readObject();
			return dest;
		}else if(src instanceof  Map) {
			Map dest = (Map)in.readObject();
			return dest;
		}
		return null;

	}


	@Transactional
	public void authorize(String account){
			int i=sysAgentMapper.updateAgentId(account);
			Assert.isTrue(i>0,TipMsgConstant.OPERATION_FAILED);
	}

	@Transactional
	public void authorizes(String account){
		int i=sysAgentMapper.updateAgentIds(account);
		Assert.isTrue(i>0,TipMsgConstant.OPERATION_FAILED);
	}

	/***
	 * 修改密码
	 * @param oldPwd 验证旧密码
	 * @param newPwd 获取新密码
	 */
	@Transactional
	public void changePassword(String oldPwd,String newPwd){
		//新密码旧密码相同
		Assert.isTrue(!oldPwd.equals(newPwd),TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
		//新密码格式不正确
		Assert.isTrue(Pattern.compile("^[a-zA-Z0-9_]{6,16}$").matcher(newPwd).matches(),TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
		// redis中对应用户盐值存在
		Assert.isTrue(stringRedisTemplate.hasKey(UserDetailsUtil.getRedisKey_Salt()),TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
		String salt=stringRedisTemplate.opsForValue().get(UserDetailsUtil.getRedisKey_Salt());
		Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
		//原密码验证
		Assert.isTrue(UserDetailsUtil.getPassword().equals(md5PasswordEncoder.encodePassword(oldPwd,salt)),TipMsgConstant.ORIGIN_PWD_WRONG);
		//修改密码
		int i=getMapper().updated(new SysAgent.Builder().id(UserDetailsUtil.getId()).password(md5PasswordEncoder.encodePassword(newPwd,salt)).build());
		Assert.isTrue(i>0,TipMsgConstant.OPERATION_FAILED);

	}

	/**
	 * 办事处权限
	 */
	@Transactional
	public JSONObject getJurisdiction(String account){
		SysAgent sysAgent=sysAgentMapper.getJurisdiction(UserDetailsUtil.getAccoutnt());
		int n=sysAgent.getRoleId();
		JSONObject result=new JSONObject();
		result.put("n",n);
		return result;
	}



	/**
	 * 冻结代理号
	 * @param account 代理账号
	 * @param sysFlag  标识
	 */
	@Transactional
	public void sysAgentFreeze(String account,boolean sysFlag){
		SysAgent sysAgent=this.getSingleSysAgent(new HashMap<String,Object>(){{
					put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(),account);
		}});
		Assert.isTrue(sysAgent.getSysFlag()!=sysFlag,TipMsgConstant.PARAM_INVALID);
		int n=sysAgentMapper.updateFreeze(new SysAgent.Builder().account(account).sysFlag(sysFlag).build());
		Assert.isTrue(n>0,TipMsgConstant.OPERATION_FAILED);
	}

	@Transactional
	public void queryHasNums(){
		List<SysAgent> list=sysAgentMapper.queryMun(UserDetailsUtil.getAccoutnt());
		System.out.println(list.get(0).getCardNum());
		Assert.isTrue(list.size()>0, TipMsgConstant.OPERATION_FAILED);
	}

	/**
	 *重置密码
	 * @param account 代理账号
	 */
	@Transactional
	public void pwdReset(String account){
		SysAgent sysAgent=this.getSingleSysAgent(new HashMap<String, Object>() {{
				put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(),account);
		}});
		String salt= UUIDUtil.genUUID();//获取uuid
		String password = new Md5PasswordEncoder().encodePassword(AuthConstant.SysAgentConstant.DEFAULT_PWD, salt);
		int n=sysAgentMapper.updates(new SysAgent.Builder().account(account).secretKey(salt).password(password).build());
		Assert.isTrue(n>0,TipMsgConstant.OPERATION_FAILED);
	}

	/**
	 * 查询代理
	 */
	protected SysAgent getSingleSysAgent(Map<String, Object> params) {
		List<SysAgent> list = getMapper().get(params);
		Assert.notEmpty(list, TipMsgConstant.SYS_AGENT_INFO_INVALID);
		return list.get(0);
	}



	/**
	 * 初始化密码
	 */
	@Override
	public void run(String... strings) throws Exception {
		// 管理员账号初始化
		SysAgent sysAgent;
		for (String account : AuthConstant.AdminAuthInfo.ADMIN_ACCOUNTS) {
			if (account.length() == SysConstant.getAccountLength()) {
				if (this.exist(account)) continue;

				sysAgent =
						new SysAgent.Builder()
								.account(account)
								.nickName(AuthConstant.AdminAuthInfo.ADMIN_NICK_NAME)
								.build();
				SysAgent.Builder.defaultPwdInject(sysAgent);
				this.save(sysAgent);
			}

		}
	}
}
