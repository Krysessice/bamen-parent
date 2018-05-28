package com.bamenyouxi.invite_code_mode.service.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.RecordPlayerReceiveAwardMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RecordPlayerReceiveAward;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameIntegral;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecordPlayerReceiveAwardService extends AbstractCrudService<RecordPlayerReceiveAward, Integer> {

    @Autowired
    private RecordPlayerReceiveAwardMapper recordPlayerReceiveAwardMapper;


    @Override
    public CrudMapper<RecordPlayerReceiveAward, Integer> getMapper() {
        return recordPlayerReceiveAwardMapper;
    }

    @Transactional
    public PageInfo<RecordPlayerReceiveAward> getCashRecord(int page, int size, Map<String, Object> params) {
        List<RecordPlayerReceiveAward> list=recordPlayerReceiveAwardMapper.getCashRecord(params);
        return queryAllLists(page,size,list);
    }


    @Transactional
    private PageInfo<RecordPlayerReceiveAward> queryAllLists(int pageNum, int pageSize, List<RecordPlayerReceiveAward> recordPlayerReceiveAwards) {
        //分页之后的对象
        List<RecordPlayerReceiveAward> sysAgentVoNews = new ArrayList<>();

        int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < recordPlayerReceiveAwards.size() - currIdx; i++) {
            RecordPlayerReceiveAward appGame = recordPlayerReceiveAwards.get(currIdx + i);
            sysAgentVoNews.add(appGame);
        }

        PageInfo<RecordPlayerReceiveAward> page = new PageInfo<>(sysAgentVoNews);
        page.setSize(recordPlayerReceiveAwards.size());
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(recordPlayerReceiveAwards.size());
        page.setPages(recordPlayerReceiveAwards.size() % pageSize == 0 ? recordPlayerReceiveAwards.size() / pageSize
                : recordPlayerReceiveAwards.size() / pageSize + 1);

        page.setIsFirstPage(pageNum == 1 ? true : false);
        page.setIsLastPage(pageNum == page.getPages() ? true : false);
        page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
        page.setHasNextPage(pageNum < page.getPages() ? true : false);
        page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
        page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
        page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
        page.setLastPage(page.getPages());
        page.setStartRow(currIdx);
        page.setEndRow(recordPlayerReceiveAwards.size() - currIdx);
        return page;
    }

}
