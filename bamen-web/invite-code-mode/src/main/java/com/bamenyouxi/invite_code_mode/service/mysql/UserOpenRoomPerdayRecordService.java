package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.UserOpenRoomPerdayRecordMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserOpenRoomPerdayRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户每日房卡消耗service
 * Created by 13477 on 2017/8/7.
 */
@Service
public class UserOpenRoomPerdayRecordService extends AbstractCrudService<UserOpenRoomPerdayRecord, Long> {
	@Autowired
	private UserOpenRoomPerdayRecordMapper userOpenRoomPerdayRecordMapper;


	@Override
	public CrudMapper<UserOpenRoomPerdayRecord, Long> getMapper() {
		return userOpenRoomPerdayRecordMapper;
	}

	@Override
	protected String sort() {
		return FieldConstant.SortConstant.CREATE_TIME_DESC;
	}

	public PageInfo<UserOpenRoomPerdayRecord> sumPerday(int page, int size, Map<String, Object> params) {
		super.listBefore(params);
		PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
		params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATES);
		List<UserOpenRoomPerdayRecord> list = this.userOpenRoomPerdayRecordMapper.sum(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}



}
