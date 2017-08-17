package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.CardGiftRecordMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CardGiftRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
