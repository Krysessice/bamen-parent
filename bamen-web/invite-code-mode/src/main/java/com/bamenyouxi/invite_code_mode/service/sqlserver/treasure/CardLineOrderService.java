package com.bamenyouxi.invite_code_mode.service.sqlserver.treasure;

import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.CardLineOrderMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.CardLineOrder;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameIntegral;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CardLineOrderService {
    @Autowired
    private CardLineOrderMapper cardLineOrderMapper;

    @Transactional
    public PageInfo<CardLineOrder> getWxPay(int page, int size, Map<String, Object> params) {
        List<CardLineOrder> list=cardLineOrderMapper.getWxPay(params);
        return queryAllLists(page,size,list);
    }

    @Transactional
    private PageInfo<CardLineOrder> queryAllLists(int pageNum, int pageSize, List<CardLineOrder> cardLineOrders) {
        //分页之后的对象
        List<CardLineOrder> sysAgentVoNews = new ArrayList<>();

        int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < cardLineOrders.size() - currIdx; i++) {
            CardLineOrder appGame = cardLineOrders.get(currIdx + i);
            sysAgentVoNews.add(appGame);
        }

        PageInfo<CardLineOrder> page = new PageInfo<>(sysAgentVoNews);
        page.setSize(cardLineOrders.size());
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(cardLineOrders.size());
        page.setPages(cardLineOrders.size() % pageSize == 0 ? cardLineOrders.size() / pageSize
                : cardLineOrders.size() / pageSize + 1);

        page.setIsFirstPage(pageNum == 1 ? true : false);
        page.setIsLastPage(pageNum == page.getPages() ? true : false);
        page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
        page.setHasNextPage(pageNum < page.getPages() ? true : false);
        page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
        page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
        page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
        page.setLastPage(page.getPages());
        page.setStartRow(currIdx);
        page.setEndRow(cardLineOrders.size() - currIdx);
        return page;
    }

}
