package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.CardGiftRecordMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.CleanGoldRecordMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayGoldRecordMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.GameScoreInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CardGiftRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CleanGoldRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayGoldRecord;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreInfo;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Autowired
	private PayGoldRecordMapper payGoldRecordMapper;
	@Autowired
	private CleanGoldRecordMapper cleanGoldRecordMapper;


	@Transactional
	public void cardGift(int presentee, String cardPrice, int cardNum, String selected,String giftReason) {
		if (StringUtils.isEmptyOrWhitespaceOnly(giftReason))
			giftReason = null;
		if(StringUtils.isEmptyOrWhitespaceOnly(cardPrice))
			cardPrice=null;
		if(StringUtils.isEmptyOrWhitespaceOnly(selected))
			selected=null;
		List<GameScoreInfo> list = gameScoreInfoMapper.get(new HashMap<String, Object>() {{
			put(FieldConstant.ModelFieldConstant.gameId.name(), presentee);
		}});
		Assert.isTrue(!(list.isEmpty() || list.get(0) == null), TipMsgConstant.PARAM_INVALID);
		Assert.isTrue(list.get(0).getInsureScore() + cardNum >= 0, TipMsgConstant.PARAM_INVALID);
		int i = gameScoreInfoMapper.updateCardNum(GameScoreInfo.ofGameId(presentee, (long) cardNum));
		int j = cardGiftRecordMapper.save(CardGiftRecord.of(UserDetailsUtil.getGameId(), presentee,cardPrice, cardNum , selected,giftReason));
		Assert.isTrue(i > 0 && j > 0, TipMsgConstant.OPERATION_FAILED);
	}

	@Transactional
	public void payGold(int presentee, String goldPrice, int goldNum, String selected,String payReason) {
		if (StringUtils.isEmptyOrWhitespaceOnly(payReason))
			payReason = null;
		if(StringUtils.isEmptyOrWhitespaceOnly(goldPrice))
			goldPrice=null;
		if(StringUtils.isEmptyOrWhitespaceOnly(selected))
			selected=null;
		List<GameScoreInfo> list = gameScoreInfoMapper.get(new HashMap<String, Object>() {{
			put(FieldConstant.ModelFieldConstant.gameId.name(), presentee);
		}});
		System.out.println(list.get(0).getScore() + goldNum);
		Assert.isTrue(!(list.isEmpty() || list.get(0) == null), TipMsgConstant.PARAM_INVALID);
		Assert.isTrue(list.get(0).getScore() + goldNum >= 0, TipMsgConstant.PARAM_INVALID);
		int i = gameScoreInfoMapper.updateGoldNum(presentee,goldNum);
		int j = payGoldRecordMapper.save(PayGoldRecord.of(UserDetailsUtil.getGameId(), presentee,goldPrice, goldNum ,selected,payReason));
		Assert.isTrue(i > 0 && j > 0, TipMsgConstant.OPERATION_FAILED);
	}


	@Transactional
	public PageInfo<GameScoreInfo> queryUserGoldScore(int page, int size, Map<String,Object> params){
		List<GameScoreInfo> list=gameScoreInfoMapper.queryUserGoldScore(params);
		return queryAllLists(page,size,list);
	}


	@Transactional
	public void updateUserGoldSocre(Integer score,Integer gameId){
			List<GameScoreInfo> list=gameScoreInfoMapper.queryUserGold(gameId);
			Assert.isTrue(!list.isEmpty(),TipMsgConstant.SYS_PLAY);
			int userId=list.get(0).getUserId();
			int ListScore=list.get(0).getScore();

			boolean n=ListScore>=score;
			Assert.isTrue(n,TipMsgConstant.SYS_USER_GOLD);
			int s=gameScoreInfoMapper.updateUserGoldSocre(score,userId);
			Assert.isTrue(s>0,TipMsgConstant.OPERATION_FAILED);

			List<GameScoreInfo> listAgent=gameScoreInfoMapper.queryUserGold(UserDetailsUtil.getGameId());
			Assert.isTrue(!listAgent.isEmpty(),TipMsgConstant.SYS_PLAY);
			int AgentUserID=listAgent.get(0).getUserId();
			int t=gameScoreInfoMapper.addUserGoldSocre(score,AgentUserID);
			Assert.isTrue(t>0,TipMsgConstant.OPERATION_FAILED);

			CleanGoldRecord cleanGoldRecord=new CleanGoldRecord();
			int playGameId=list.get(0).getGameId();
			cleanGoldRecord.setAgentGameid(UserDetailsUtil.getGameId());
			cleanGoldRecord.setPlayGameid(playGameId);
			cleanGoldRecord.setScore(score);
			int i=cleanGoldRecordMapper.saveScore(cleanGoldRecord);
			Assert.isTrue(i>0,TipMsgConstant.OPERATION_FAILED);
	}


	@Transactional
	private PageInfo<GameScoreInfo> queryAllLists(int pageNum, int pageSize, List<GameScoreInfo> gameScoreInfos) {
		//分页之后的对象
		List<GameScoreInfo> sysAgentVoNews = new ArrayList<>();

		int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
		for (int i = 0; i < pageSize && i < gameScoreInfos.size() - currIdx; i++) {
			GameScoreInfo appGame = gameScoreInfos.get(currIdx + i);
			sysAgentVoNews.add(appGame);
		}

		PageInfo<GameScoreInfo> page = new PageInfo<>(sysAgentVoNews);
		page.setSize(gameScoreInfos.size());
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		page.setTotal(gameScoreInfos.size());
		page.setPages(gameScoreInfos.size() % pageSize == 0 ? gameScoreInfos.size() / pageSize
				: gameScoreInfos.size() / pageSize + 1);

		page.setIsFirstPage(pageNum == 1 ? true : false);
		page.setIsLastPage(pageNum == page.getPages() ? true : false);
		page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
		page.setHasNextPage(pageNum < page.getPages() ? true : false);
		page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
		page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
		page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
		page.setLastPage(page.getPages());
		page.setStartRow(currIdx);
		page.setEndRow(gameScoreInfos.size() - currIdx);
		return page;
	}
}
