package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.GameScoreLockerMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.UserStatusMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreLocker;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class GameScoreLockerService extends AbstractCrudService<GameScoreLocker, Integer> {

	@Autowired
	private GameScoreLockerMapper gameScoreLockerMapper;
	@Autowired
	private UserStatusMapper userStatusMapper;

	@Override
	public CrudMapper<GameScoreLocker, Integer> getMapper() {
		return gameScoreLockerMapper;
	}

	@Override
	protected String sort() {
		return FieldConstant.SortConstant.COLLECT_DATE_DESC;
	}

	@Override
	protected void deleteBefore(GameScoreLocker gameScoreLocker) {
		Assert.notNull(gameScoreLocker, TipMsgConstant.PARAM_INVALID);
		Assert.notNull(gameScoreLocker.getUserId(), TipMsgConstant.PARAM_INVALID);
		super.deleteBefore(gameScoreLocker);
	}

	/**
	 * 删除卡线信息
	 * <p>删除卡线时，需关联删除两个表，{@link GameScoreLocker} 和 {@link UserStatus}</p>
	 * @param gameScoreLocker   卡线实体
	 */
	@Transactional
	@Override
	public void delete(GameScoreLocker gameScoreLocker) {
		super.delete(gameScoreLocker);
		int i = userStatusMapper.delete(UserStatus.of(gameScoreLocker.getUserId()));
		Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
	}
}
