package com.bamenyouxi.invite_code_mode.service.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.CardGiftRecordMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CardGiftRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 房卡赠送service
 * Created by 13477 on 2017/8/8.
 */
@Service
public class CardGiftRecordService extends AbstractCrudService<CardGiftRecord, Long> {
	@Autowired
	private CardGiftRecordMapper cardGiftRecordMapper;

	@Override
	public CrudMapper<CardGiftRecord, Long> getMapper() {
		return cardGiftRecordMapper;
	}


	@Transactional
	public PageInfo<CardGiftRecord> selectedName(int page, int size,Map<String,Object> params){
		if(StringUtils.isEmpty(params.get("selected"))) {
			params.get("selected");
		}
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
		List<CardGiftRecord> list=cardGiftRecordMapper.selectedName(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	@Transactional
	public PageInfo<CardGiftRecord> queryAgent(int page, int size,Map<String,Object> params){
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
		params.put("selected",UserDetailsUtil.getGameId().toString());
		List<CardGiftRecord> list=cardGiftRecordMapper.queryAgent(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}


	//总金额
	@Transactional
	public JSONObject queryPrice(Map<String,Object> params){
		if(StringUtils.isEmpty(params.get("selected"))) {
			params.get("selected");
		}
		CardGiftRecord cardGiftRecord=cardGiftRecordMapper.queryPrice(params);
		String sumBig="";
		if(StringUtils.isEmpty(cardGiftRecord)){
			sumBig="0";
			JSONObject result=new JSONObject();
			result.put("sumBig",sumBig);
			return result;
		}
		sumBig=cardGiftRecord.getCardPrice();
		JSONObject result=new JSONObject();
		result.put("sumBig",sumBig);
		return result;
	}



	//日期金额
	@Transactional
	public JSONObject queryPriceSum(){
		CardGiftRecord cardGiftRecord=cardGiftRecordMapper.queryPriceSum();
		String sumBig="";
		if(StringUtils.isEmpty(cardGiftRecord)){
			sumBig="0";
			JSONObject result=new JSONObject();
			result.put("sumBig",sumBig);
			return result;
		}
		sumBig=cardGiftRecord.getCardPrice();
		JSONObject result=new JSONObject();
		result.put("sumBig",sumBig);
		return result;
	}

	//总金额
	@Transactional
	public JSONObject queryAgentOne(){
		CardGiftRecord cardGiftRecord=cardGiftRecordMapper.queryAgentOne(UserDetailsUtil.getGameId());
		String sumBig="";
		if(StringUtils.isEmpty(cardGiftRecord)){
			sumBig="0";
			JSONObject result=new JSONObject();
			result.put("sumBig",sumBig);
			return result;
		}
		sumBig=cardGiftRecord.getCardPrice();
		JSONObject result=new JSONObject();
		result.put("sumBig",sumBig);
		return result;
	}

	//日期金额
	@Transactional
	public JSONObject queryAgentOneTime(Map<String,Object> params){
		params.put("selected",UserDetailsUtil.getGameId());
		CardGiftRecord cardGiftRecord=cardGiftRecordMapper.queryAgentOneTime(params);
		String sumBig="";
		if(StringUtils.isEmpty(cardGiftRecord)){
			sumBig="0";
			JSONObject result=new JSONObject();
			result.put("sumBig",sumBig);
			return result;
		}
		sumBig=cardGiftRecord.getCardPrice();
		JSONObject result=new JSONObject();
		result.put("sumBig",sumBig);
		return result;
	}


}
