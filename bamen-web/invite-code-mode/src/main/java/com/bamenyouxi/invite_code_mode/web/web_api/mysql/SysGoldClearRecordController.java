package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgentClearRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysGoldClearRecord;
import com.bamenyouxi.invite_code_mode.service.mysql.SysAgentClearRecordService;
import com.bamenyouxi.invite_code_mode.service.mysql.SysGoldClearRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台代理结算记录 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/clear/gold/record")
final class SysGoldClearRecordController extends AbstractCrudController<SysGoldClearRecord, Long> {
	@Autowired
	private SysGoldClearRecordService sysGoldClearRecordService;

	@Override
	public AbstractCrudService<SysGoldClearRecord, Long> getService() {
		return sysGoldClearRecordService;
	}
}
