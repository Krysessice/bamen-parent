package com.bamenyouxi.invite_code_mode.service.sqlserver.treasure;

import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.RecordPlayerReceiveAwardMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.GameIntegralMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AgentPartner;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RedeemCode;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameIntegral;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GameIntegralService  extends AbstractCrudService<GameIntegral, Integer> {

    @Autowired
    private GameIntegralMapper gameIntegralMapper;

    @Autowired
    private RecordPlayerReceiveAwardMapper recordPlayerReceiveAwardMapper;

    @Override
    public CrudMapper<GameIntegral, Integer> getMapper() {
        return gameIntegralMapper;
    }

    @Transactional
    public PageInfo<GameIntegral> getWithrawal(int page, int size, Map<String, Object> params) {
        List<GameIntegral> list=gameIntegralMapper.getWithrawal(params);
        return queryAllLists(page,size,list);
    }

    @Transactional
    public void updateRMB(double rmb,int gameId){
        List<GameIntegral> gameIntegral=gameIntegralMapper.getWithrawalOne(gameId);
        Assert.isTrue(!gameIntegral.isEmpty(),TipMsgConstant.PLAYER_NOT_EXIST);
        int userId=gameIntegral.get(0).getUserid();
        double rmbsum=gameIntegral.get(0).getRmb();
        Assert.isTrue(rmb<=rmbsum,TipMsgConstant.SYS_MoMEYUSUMS);
        int n=gameIntegralMapper.updateRMB(rmb,userId);
        Assert.isTrue(n>0, TipMsgConstant.OPERATION_FAILED);
        int i=recordPlayerReceiveAwardMapper.addRMB(userId,rmb);
        Assert.isTrue(i>0, TipMsgConstant.OPERATION_FAILED);
    }


    @Transactional
    private PageInfo<GameIntegral> queryAllLists(int pageNum, int pageSize, List<GameIntegral> gameIntegrals) {
        //分页之后的对象
        List<GameIntegral> sysAgentVoNews = new ArrayList<>();

        int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < gameIntegrals.size() - currIdx; i++) {
            GameIntegral appGame = gameIntegrals.get(currIdx + i);
            sysAgentVoNews.add(appGame);
        }

        PageInfo<GameIntegral> page = new PageInfo<>(sysAgentVoNews);
        page.setSize(gameIntegrals.size());
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(gameIntegrals.size());
        page.setPages(gameIntegrals.size() % pageSize == 0 ? gameIntegrals.size() / pageSize
                : gameIntegrals.size() / pageSize + 1);

        page.setIsFirstPage(pageNum == 1 ? true : false);
        page.setIsLastPage(pageNum == page.getPages() ? true : false);
        page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
        page.setHasNextPage(pageNum < page.getPages() ? true : false);
        page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
        page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
        page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
        page.setLastPage(page.getPages());
        page.setStartRow(currIdx);
        page.setEndRow(gameIntegrals.size() - currIdx);
        return page;
    }

}
