package com.bamenyouxi.invite_code_mode.service.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.GoldInfoMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SystemInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.GoldInfo;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 系统信息 service
 * Created by 13477 on 2017/7/10.
 */
@Service
@Order(SysConstant.CommandLineOrder.Gold_INFO_INIT)
public class GoldInfoService extends AbstractCrudService<GoldInfo, Long> implements CommandLineRunner {
	@Autowired
	private GoldInfoMapper goldInfoMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public CrudMapper<GoldInfo, Long> getMapper() {
		return goldInfoMapper;
	}

	@Transactional
	public JSONObject getGoldInfo(){
		List<GoldInfo> list = getMapper().get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
		}});
		if(list.size() != 0){
			BigDecimal t1_commission=list.get(0).getT1_commission();
			BigDecimal t2_commission=list.get(0).getT2_commission();
			BigDecimal t3_commission=list.get(0).getT3_commission();
			JSONObject result = new JSONObject();
			result.put("t1_commission", t1_commission);
			result.put("t2_commission", t2_commission);
			result.put("t3_commission", t3_commission);
			return result;
		}
		return null;
	}

	@Transactional
	public void updateInfo(BigDecimal t1_commission,BigDecimal t2_commission,BigDecimal t3_commission){
		int n=goldInfoMapper.updateInfo(t1_commission,t2_commission,t3_commission);
		Assert.isTrue(n>0,TipMsgConstant.OPERATION_FAILED);
	}

	@Override
	protected void saveBefore(GoldInfo goldInfo) {
		getMapper().update(new GoldInfo.Builder().sysFlag(false)._sysFlag(true).build());
		super.saveBefore(goldInfo);
	}

	@Override
	public void run(String... strings) throws Exception {
		RedisTemplate<String, GoldInfo> redisTemplate = redisUtil.getGoldInfoDB();
		if (redisTemplate.hasKey(GoldInfo.class.getName())) return;

		long count = PageHelper.count(
				() -> getMapper().get(new HashMap<String, Object>() {{
					put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
				}})
		);
		if (count <= 0)
			Assert.isTrue(goldInfoMapper.systemInfoInit() > 0, TipMsgConstant.RUN_EXCEPTION);

		PageHelper.startPage(1, 1, FieldConstant.SortConstant.MODIFY_TIME_DESC);
		List<GoldInfo> list = getMapper().get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
		}});
		Assert.notEmpty(list, TipMsgConstant.CONDITION_UNMET);

		redisUtil.saveGoldInfo(list.get(0));
	}


}
