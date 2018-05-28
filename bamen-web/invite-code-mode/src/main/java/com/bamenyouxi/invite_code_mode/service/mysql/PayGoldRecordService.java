package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayGoldRecordMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CardGiftRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayGoldRecord;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 金币充值service
 * Created by 13477 on 2018/4/28.
 */
@Service
public class PayGoldRecordService  {
	@Autowired
	private PayGoldRecordMapper payGoldRecordMapper;

	@Transactional
	public PageInfo<PayGoldRecord> selectedName(int page, int size, Map<String,Object> params){
		if(StringUtils.isEmpty(params.get("selected"))) {
			params.get("selected");
		}
		PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
		List<PayGoldRecord> list=payGoldRecordMapper.selectedName(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}




}
