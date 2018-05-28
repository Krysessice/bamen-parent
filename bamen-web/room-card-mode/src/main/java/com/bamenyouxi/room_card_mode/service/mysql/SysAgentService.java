package com.bamenyouxi.room_card_mode.service.mysql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.util.UUIDUtil;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgentVo;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SystemInfo;
import com.bamenyouxi.room_card_mode.util.RedisUtil;
import com.github.pagehelper.PageInfo;
import com.bamenyouxi.room_card_mode.constant.SysConstant;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentExtendMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgentExtend;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
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
	private SysAgentExtendMapper sysAgentExtendMapper;
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
	 * 合伙人业绩
	 */
	@Transactional
	public PageInfo<SysAgentVo> groupPartnerMessage(int page, int size,Map<String,Object> params) {
		List<SysAgentVo> sysAgentsRe = new ArrayList<>();
		List<SysAgentVo> gameId = sysAgentMapper.query4list(params);
		if(gameId.size()==0){
			Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
		}else{
			// 一级代理ong
			sysAgentsRe.addAll(gameId);
			Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
			long sub = 2;
			List<String> strs = gameId.stream().map(SysAgent::getAccount).collect(Collectors.toList());
			List<SysAgentVo> sysAgents = new ArrayList<>();

			Map<Long, Map<String, SysAgentVo>> map = new HashMap();
			convertData(1l, gameId, map);
			do {
				sysAgents = getSubSyAgent(strs,(String)params.get("startDate"),(String)params.get("endDate"));
				strs = sysAgents.stream().map(SysAgent::getAccount).collect(Collectors.toList());
				sysAgentsRe.addAll(sysAgents);
				//System.out.println(sub + "级代理" + sysAgents.toString());
				//convertData(sub, sysAgents, map);
				sub++;
			} while (sysAgents.size() > 0 && strs.size() > 0 && sub<=20000);
			if (sysAgentsRe.isEmpty()) {
				return null;
			}
			//BigDecimal sumBig=calculatedAmount(map);
			//System.out.println(sumBig);
			if (sysAgentsRe.isEmpty() || sysAgentsRe.get(0) == null)
				return new PageInfo<>();
		}

		removeNullPayPrice(sysAgentsRe);
		//组装pageinfo对象
		return assemblePage(page,size,sysAgentsRe);
	}
	/**
	 * 合伙人总金额
	 */
	@Transactional
	public JSONObject queryPartnerSumMoney(Map<String,Object> params) {
		JSONObject mapRe=new JSONObject();
		List<SysAgentVo> sysAgentsRe = new ArrayList<>();
		List<SysAgentVo> gameId = sysAgentMapper.query4list(params);
		JSONObject result = new JSONObject();
		if(gameId.size()==0){
			Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
		}else {
			// 一级代理
			sysAgentsRe.addAll(gameId);
			Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
			long sub = 2;
			List<String> strs = gameId.stream().map(SysAgent::getAccount).collect(Collectors.toList());
			List<SysAgentVo> sysAgents = new ArrayList<>();

			Map<Long, Map<String, SysAgentVo>> map = new HashMap();
			Map<Long, Map<String, SysAgentVo>> voNew=new HashMap<>();

			try {
				Map<Long, Map<String, SysAgentVo>>  mapNew =(Map<Long, Map<String, SysAgentVo>>)deepCopy(map);
				List<SysAgentVo> vosNew=(List<SysAgentVo>)deepCopy(gameId);
				convertData(1l, gameId, map,mapNew,vosNew);

				do {
					//System.out.println(strs);
					sysAgents = getSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
					strs = sysAgents.stream().map(SysAgent::getAccount).collect(Collectors.toList());
			/*
			 * for(SysAgentVo vo:sysAgents){ vo.setLeve(sub); }
			 */
					sysAgentsRe.addAll(sysAgents);
					vosNew.addAll(sysAgents);
					//System.out.println(sub + "级代理" + sysAgents.toString());
					convertData(sub, sysAgents, map,mapNew,vosNew);


					sub++;
				} while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
				if (sysAgentsRe.isEmpty()) {
					return null;
				}
				BigDecimal sumBig = calculatedAmounts(map,mapNew);
				//System.out.println("sumBig:"+sumBig);

				result.put("sumBig", sumBig);
				//System.out.println(result);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		return result;
	}


	/**
	 * 查询总金额
	 */
	@Transactional
	public JSONObject queryTimeSumMoney(Map<String,Object> params) {
		JSONObject mapRe=new JSONObject();
		List<SysAgentVo> sysAgentsRe = new ArrayList<>();
		params.put("account",UserDetailsUtil.getAccoutnt());
		List<SysAgentVo> gameId = sysAgentMapper.query4list(params);
		JSONObject result = new JSONObject();
		if(gameId.size()==0){
			Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
		}else {
			// 一级代理
			sysAgentsRe.addAll(gameId);
			Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
			long sub = 2;
			List<String> strs = gameId.stream().map(SysAgent::getAccount).collect(Collectors.toList());
			List<SysAgentVo> sysAgents = new ArrayList<>();

			Map<Long, Map<String, SysAgentVo>> map = new HashMap();
			Map<Long, Map<String, SysAgentVo>> voNew=new HashMap<>();

			try {
				Map<Long, Map<String, SysAgentVo>>  mapNew =(Map<Long, Map<String, SysAgentVo>>)deepCopy(map);
				List<SysAgentVo> vosNew=(List<SysAgentVo>)deepCopy(gameId);
				convertData(1l, gameId, map,mapNew,vosNew);

				do {
					//System.out.println(strs);
					sysAgents = getSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
					strs = sysAgents.stream().map(SysAgent::getAccount).collect(Collectors.toList());
			/*
			 * for(SysAgentVo vo:sysAgents){ vo.setLeve(sub); }
			 */
					sysAgentsRe.addAll(sysAgents);
					vosNew.addAll(sysAgents);
					//System.out.println(sub + "级代理" + sysAgents.toString());
					convertData(sub, sysAgents, map,mapNew,vosNew);


					sub++;
				} while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
				if (sysAgentsRe.isEmpty()) {
					return null;
				}
				BigDecimal sumBig = calculatedAmounts(map,mapNew);
				//System.out.println("sumBig:"+sumBig);

				result.put("sumBig", sumBig);
				//System.out.println(result);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		return result;
	}


	/**
	 * 授权代理
	 */
	@Transactional
	public PageInfo<SysAgentVo> querysubclass(int page, int size,Map<String,Object> params) {
		List<SysAgentVo> sysAgentsRe = new ArrayList<>();
		params.put("account",UserDetailsUtil.getAccoutnt());
		List<SysAgentVo> gameId = sysAgentMapper.query4list(params);
		if(gameId.size()==0){
			Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
		}else{
			// 一级代理ong
			sysAgentsRe.addAll(gameId);
			Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
			long sub = 2;
			List<String> strs = gameId.stream().map(SysAgent::getAccount).collect(Collectors.toList());
			List<SysAgentVo> sysAgents = new ArrayList<>();

			Map<Long, Map<String, SysAgentVo>> map = new HashMap();
			convertData(1l, gameId, map);
			do {
				sysAgents = getSubSyAgent(strs,(String)params.get("startDate"),(String)params.get("endDate"));
				strs = sysAgents.stream().map(SysAgent::getAccount).collect(Collectors.toList());
				sysAgentsRe.addAll(sysAgents);
				//System.out.println(sub + "级代理" + sysAgents.toString());
				//convertData(sub, sysAgents, map);
				sub++;
			} while (sysAgents.size() > 0 && strs.size() > 0 && sub<=20000);
			if (sysAgentsRe.isEmpty()) {
				return null;
			}
			//BigDecimal sumBig=calculatedAmount(map);
			//System.out.println(sumBig);
			if (sysAgentsRe.isEmpty() || sysAgentsRe.get(0) == null)
				return new PageInfo<>();
		}

		removeNullPayPrice(sysAgentsRe);
		//组装pageinfo对象
		return assemblePage(page,size,sysAgentsRe);
	}

	/**]
	 * 去掉null值
	 */
	public void removeNullPayPrice(List<SysAgentVo> sysAgentsRe) {
		if(sysAgentsRe.isEmpty()){
			return;
		}
		List<SysAgentVo> listTemp = new ArrayList();
		for (int i = 0;i < sysAgentsRe.size(); i++) {
			SysAgentVo sysAgentVo=sysAgentsRe.get(i);
			if(sysAgentVo.getPayPrice()==null){
				listTemp.add(sysAgentVo);
				continue;
			}
			if (BigDecimal.ZERO.compareTo(sysAgentVo.getPayPrice())>=0) {
				listTemp.add(sysAgentVo);
			}
		}
		sysAgentsRe.removeAll(listTemp);
	}


	/**
	 * 分页
	 */
	private PageInfo<SysAgentVo> assemblePage(int pageNum, int pageSize, List<SysAgentVo> sysAgentVos) {
		//分页之后的对象
		List<SysAgentVo> sysAgentVoNews = new ArrayList<>();

		int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
		for (int i = 0; i < pageSize && i < sysAgentVos.size() - currIdx; i++) {
			SysAgentVo appGame = sysAgentVos.get(currIdx + i);
			sysAgentVoNews.add(appGame);
		}

		PageInfo<SysAgentVo> page = new PageInfo<>(sysAgentVoNews);
		page.setSize(sysAgentVos.size());
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		page.setTotal(sysAgentVos.size());
		page.setPages(sysAgentVos.size() % pageSize == 0 ? sysAgentVos.size() / pageSize
				: sysAgentVos.size() / pageSize + 1);

		page.setIsFirstPage(pageNum == 1 ? true : false);
		page.setIsLastPage(pageNum == page.getPages() ? true : false);
		page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
		page.setHasNextPage(pageNum < page.getPages() ? true : false);
		page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
		page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
		page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
		page.setLastPage(page.getPages());
		page.setStartRow(currIdx);
		page.setEndRow(sysAgentVos.size() - currIdx);
		return page;
	}


	private List<SysAgentVo> getSubSyAgent(List<String> params,String startDate,String endDate){
		return sysAgentMapper.querysubclass(params,startDate,endDate);
	}

	/**
	 * 组装path 为总金额赋值
	 *
	 * @param map
	 */
	public BigDecimal calculatedAmount(Map<Long, Map<String, SysAgentVo>> map) {
		SystemInfo systemInfo = redisUtil.getSystemInfo();

		BigDecimal sumBig= BigDecimal.ZERO;
		if(map!=null) {
			for (Long key : map.keySet()) {
				Map<String, SysAgentVo> sysAgentVos = map.get(key);
				if(sysAgentVos!=null) {
					for (String str : sysAgentVos.keySet()) {
						SysAgentVo sysAgentVo = sysAgentVos.get(str);
						sysAgentVo.setLeve(key); // 代理等级
						//System.out.println(key + "级代理" + sysAgentVo.toString());
						setPath(key, sysAgentVo, map); // path 路径获取
					}
				}
			}
		}
		if(map!=null) {
			for (Long key : map.keySet()) {
				Map<String, SysAgentVo> sysAgentVos = map.get(key);
				if(sysAgentVos!=null) {
					for (String str : sysAgentVos.keySet()) {
						SysAgentVo sysAgentVo = sysAgentVos.get(str);
						if(sysAgentVo==null){
							continue;
						}
						//setSumAmount(key, sysAgentVo, map); // 金额计算
						//计算总金额
						sumBig=sumBig.add(sysAgentVo.getPayPrice() == null ? BigDecimal.ZERO :sysAgentVo.getPayPrice());
					}
				}
			}
		}

		return sumBig;
	}

	public BigDecimal calculatedAmounts(Map<Long, Map<String, SysAgentVo>> map,Map<Long, Map<String, SysAgentVo>> voNew) {
		SystemInfo systemInfo = redisUtil.getSystemInfo();

		BigDecimal sumBig= BigDecimal.ZERO;
		if(map!=null) {
			for (Long key : map.keySet()) {
				Map<String, SysAgentVo> sysAgentVos = map.get(key);

				Map<String, SysAgentVo> sysAgentVosSum = voNew.get(key);
				if(sysAgentVos!=null) {
					for (String str : sysAgentVos.keySet()) {
						SysAgentVo sysAgentVo = sysAgentVos.get(str);
						sysAgentVo.setLeve(key); // 代理等级
						//System.out.println(key + "级代理" + sysAgentVo.toString());
						setPath(key, sysAgentVo, map); // path 路径获取


						setPath(key, sysAgentVosSum.get(str), voNew); // path 路径获取
					}
				}
			}
		}
		if(map!=null) {
			for (Long key : map.keySet()) {
				Map<String, SysAgentVo> sysAgentVos = map.get(key);

				Map<String, SysAgentVo> sysAgentVosNew = voNew.get(key);
				if(sysAgentVos!=null) {
					for (String str : sysAgentVos.keySet()) {
						SysAgentVo sysAgentVo = sysAgentVos.get(str);


						SysAgentVo sysAgentVoNew = sysAgentVosNew.get(str);
						if(sysAgentVoNew==null){
							continue;
						}
						setSumAmount(key, sysAgentVo, map); // 金额计算

						setSumAmount(key, sysAgentVoNew, voNew);
						//计算总金额

						sumBig=sumBig.add(sysAgentVoNew.getSumAmount() == null ? BigDecimal.ZERO :sysAgentVoNew.getSumAmount());
					}
				}
			}
		}

		return sumBig;
	}

	/**
	 * 获取路径
	 */
	public void setPath(Long size, SysAgentVo vo, Map<Long, Map<String, SysAgentVo>> map) {
		String path = new String("");
		// String superAccountpath = "";
		String getSuperAgentGameId = vo.getSuperAgentId();
		//System.out.println(getSuperAgentGameId);
		// signFor:for(Long key:map.keySet()){
		// 往后退 2级代理只查询 1级 3级查询 2 1
		for (int i = size.intValue(); i > 0; i--) {
			if (size != 1) { // 不是一级代理
				int upperLevel = i - 1;
				Map<String, SysAgentVo> sysAgentVos = map.get(Long.valueOf(upperLevel));
				if(sysAgentVos==null){
					continue;
				}
				SysAgentVo sysAgentVo = sysAgentVos.get(getSuperAgentGameId);
				if (upperLevel > 1) {
					if(sysAgentVo== null){
						continue;
					}
					if (StringUtils.isEmpty(path)) {
						path = path+(sysAgentVo.getAccount());
					} else {
						path = path+(",")+(sysAgentVo.getAccount());
					}
					getSuperAgentGameId = sysAgentVo.getSuperAgentId();

					if (StringUtils.isEmpty(path)) {
						break;
					}
				}else if(upperLevel == 1){
					if (StringUtils.isEmpty(path)) {
						path = sysAgentVo.getSuperAgentId()+","+sysAgentVo.getAccount();
					} else {
						path = sysAgentVo.getSuperAgentId()+","+sysAgentVo.getAccount()+","+path;
					}
				}

			}else{
				path =  getSuperAgentGameId.toString();

			}
		}

		vo.setPath(path);

		//System.out.println("账号:" + vo.getAccount() + "path路径为:" + vo.getPath());

		// }
	}

	/**
	 * 计算金额
	 */
	public void setSumAmount(Long size, SysAgentVo vo, Map<Long, Map<String, SysAgentVo>> map) {

		BigDecimal sumAmount = vo.getPayPrice() == null ? BigDecimal.ZERO :vo.getPayPrice() ; // 自己的金额

		for (int i = size.intValue(); i < size.intValue(); i++) {

			//System.out.println(vo.getAccount() +"计算几级代理："+(i + 1));
			Map<String, SysAgentVo> vomap = map.get(Long.valueOf((i + 1)));

			if (vomap == null) {
				break;
			}

			for (String str : vomap.keySet()) {
				SysAgentVo sysvo = vomap.get(str);
				if (StringUtils.isEmpty(sysvo.getPath()) || StringUtils.isEmpty(sysvo)) {
					continue;
				}
				String[] pathStrs = sysvo.getPath().split(",");

				List<String> pathList = Arrays.asList(pathStrs);

				if (pathList.contains(vo.getAccount())) {
					sumAmount = sumAmount .add(
							sysvo.getPayPrice() == null ? BigDecimal.ZERO :sysvo.getPayPrice());
				}

			}
			//System.out.println(strSum+"==================");

		}
		vo.setSumAmount(sumAmount);

		//System.out.println("账号:" + vo.getAccount() + "总金额为:" + sumAmount);
	}

	/**
	 * 数据转换
	 * @param sub
	 * @param map
	 */
	public void convertData(Long sub, List<SysAgentVo> vos, Map<Long, Map<String, SysAgentVo>> map,
	Map<Long, Map<String, SysAgentVo>>  mapNew, List<SysAgentVo> vosNew) {
		Map<String, SysAgentVo> mapVo = new HashMap();

		Map<String, SysAgentVo> mapVoNew = new HashMap();

			for (SysAgentVo vo : vos) {
				if(mapVoNew.containsKey(vo.getAccount())){

					for(SysAgentVo sysAgentVo:vosNew){
						if(vo.getAccount().equals(sysAgentVo.getAccount())){
							SysAgentVo voOld=mapVoNew.get(vo.getAccount());
							voOld.setPayPrice(vo.getPayPrice().add(voOld.getPayPrice()));
							break;
						}
					}


				}else {

					for (SysAgentVo sysAgentVo : vosNew) {
						if (vo.getAccount().equals(sysAgentVo.getAccount()) && vo.getPayPrice().compareTo(sysAgentVo.getPayPrice()) == 0) {
							mapVoNew.put(sysAgentVo.getAccount(), sysAgentVo);
							break;
						}
					}
				}


				mapVo.put(vo.getAccount(), vo);
			}

			map.put(sub, mapVo);
			mapNew.put(sub, mapVoNew);

	}


	public void convertData(Long sub, List<SysAgentVo> vos, Map<Long, Map<String, SysAgentVo>> map) {
		Map<String, SysAgentVo> mapVo = new HashMap();

		Map<String, SysAgentVo> mapVoNew = new HashMap();

		for (SysAgentVo vo : vos) {
			mapVo.put(vo.getAccount(), vo);
		}
		map.put(sub, mapVo);

	}



	/**
	 * ni 到时候把这段代码写到工具类里面去
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
	@Override
	public SysAgent save(SysAgent sysAgent) {
		this.saveBefore(sysAgent);
		SysAgent.Builder.defaultPwdInject(sysAgent);
		int i = getMapper().save(sysAgent);
		if (i > 0) {
			int j = sysAgentExtendMapper.save(new SysAgentExtend.Builder().sysAgentId(sysAgent.getId()).build());
			Assert.isTrue(j > 0, TipMsgConstant.OPERATION_FAILED);
		}
		return sysAgent;
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

	@Transactional
	public JSONObject pages(String account){
		SysAgent sysAgent=sysAgentMapper.pages(UserDetailsUtil.getAccoutnt());
		int n=sysAgent.getPartner();
		JSONObject result=new JSONObject();
		result.put("n",n);
		return result;
	}

	@Transactional
	public PageInfo<SysAgent> downAgent(int page, int size, String account){
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESCS);
		List<SysAgent> list=sysAgentMapper.downAgent(UserDetailsUtil.getAccoutnt());
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	@Transactional
	public PageInfo<SysAgent> downAgents(int page, int size, String account){
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
		List<SysAgent> list=sysAgentMapper.downAgents(UserDetailsUtil.getAccoutnt());
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	@Transactional
	public PageInfo<SysAgent> queryAgentDown(int page, int size, String teamNo){
		PageHelper.startPage(page,size,FieldConstant.SortConstant.CREATE_TIME_DESCS);
		List<SysAgent> list=sysAgentMapper.queryAgentDown(teamNo);
		if(list.isEmpty() || list.get(0)==null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}


	/***
	 * 管理员查看未被授权的代理
	 */
	@Transactional
	public PageInfo<SysAgent> queryAgentList(int page,int size,Map<String,Object>params){
		super.listBefore(params);
		PageHelper.startPage(page,size,FieldConstant.GroupConstant.CREATE_DATED);
		params.put(FieldConstant.CommonFieldConstant.groupBy.name(),FieldConstant.GroupConstant.CREATE_DATED);
		List<SysAgent> list=sysAgentMapper.queryAgentList(params);
		if(list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	@Transactional
	public PageInfo<SysAgent> queryAlls(int page, int size, Map<String,Object> params){
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
		params.put(FieldConstant.CommonFieldConstant.groupBy.name(),FieldConstant.GroupConstant.CREATE_DATED);
		List<SysAgent> list=sysAgentMapper.queryAlls(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	/***
	 * 管理员查看已被代理
	 */
	@Transactional
	public PageInfo<SysAgent> queryPartner(int page,int size,Map<String,Object>params){
		super.listBefore(params);
		PageHelper.startPage(page,size,FieldConstant.GroupConstant.CREATE_DATED);
		params.put(FieldConstant.CommonFieldConstant.groupBy.name(),FieldConstant.GroupConstant.CREATE_DATED);
		List<SysAgent> list=sysAgentMapper.queryPartner(params);
		if(list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
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
		int n=getMapper().update(new SysAgent.Builder().account(account).sysFlag(sysFlag).build());
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
