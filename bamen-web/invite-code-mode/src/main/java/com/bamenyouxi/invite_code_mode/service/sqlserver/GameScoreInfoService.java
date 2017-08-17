package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.CardGiftRecordMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.GameScoreInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CardGiftRecord;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreInfo;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * GameScoreInfoService
 * Created by 13477 on 2017/8/8.
 */
@Service
public class GameScoreInfoService {
	@Autowired
	private GameScoreInfoMapper gameScoreInfoMapper;
	@Autowired
	private CardGiftRecordMapper cardGiftRecordMapper;

	@Transactional
	public void cardGift(int presentee, int cardNum, String giftReason) {
		if (StringUtils.isEmptyOrWhitespaceOnly(giftReason))
			giftReason = null;
		List<GameScoreInfo> list = gameScoreInfoMapper.get(new HashMap<String, Object>() {{
			put(FieldConstant.ModelFieldConstant.gameId.name(), presentee);
		}});
		Assert.isTrue(!(list.isEmpty() || list.get(0) == null), TipMsgConstant.PARAM_INVALID);
		Assert.isTrue(list.get(0).getInsureScore() + cardNum >= 0, TipMsgConstant.PARAM_INVALID);
		int i = gameScoreInfoMapper.updateCardNum(GameScoreInfo.ofGameId(presentee, (long) cardNum));
		int j = cardGiftRecordMapper.save(CardGiftRecord.of(UserDetailsUtil.getGameId(), presentee, cardNum, giftReason));
		Assert.isTrue(i > 0 && j > 0, TipMsgConstant.OPERATION_FAILED);
	}
}
