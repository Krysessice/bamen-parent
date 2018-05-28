package com.bamenyouxi.invite_code_mode.service.sqlserver.record;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.UseridScoreRoomnumSaveMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RedeemCode;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RoomParnterData;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.UseridScoreRoomnumSave;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UseridScoreRoomnumSaveService  extends AbstractCrudService<UseridScoreRoomnumSave, Integer> {

    @Autowired
    private UseridScoreRoomnumSaveMapper useridScoreRoomnumSaveMapper;


    @Override
    public CrudMapper<UseridScoreRoomnumSave, Integer> getMapper() {
        return useridScoreRoomnumSaveMapper;
    }


    @Transactional
    public JSONObject getUser(Map<String,Object> params){
        if(params.get("gameid")!=null&&params.get("gameid")!=""){
            List<UseridScoreRoomnumSave> list=useridScoreRoomnumSaveMapper.getUser(params);
            JSONObject result=new JSONObject();
            if (!list.isEmpty()){
                result.put("count",list.get(0).getGameid());
                result.put("score",list.get(0).getScore());
                return  result;
            }
        }
        return null;
    }


    @Transactional
    public PageInfo<UseridScoreRoomnumSave> getUserScore(int page, int size, Map<String,Object> params){
        if(params.get("gameid")!=null&&params.get("gameid")!=""){
            List<UseridScoreRoomnumSave> list=useridScoreRoomnumSaveMapper.getUserScore(params);
            return queryAllLists(page,size,list);
        }
        return null;
    }

    @Transactional
   public PageInfo<UseridScoreRoomnumSave> countKind(int page, int size, Map<String,Object> params){
        List<UseridScoreRoomnumSave> list=useridScoreRoomnumSaveMapper.countKind(params);
        if (list.isEmpty() || list.get(0) == null)
           return new PageInfo<>();
        return new PageInfo<>(list);
   }

    @Transactional
   public PageInfo<UseridScoreRoomnumSave> queryAllRoom(int page, int size, Map<String,Object> params){
       List<UseridScoreRoomnumSave> list=useridScoreRoomnumSaveMapper.queryAllRoom(params);
       return queryAllLists(page,size,list);
   }

    @Transactional
   public PageInfo<UseridScoreRoomnumSave> queryPartnerAgent(int page, int size, Map<String,Object> params){
        List<UseridScoreRoomnumSave> list=useridScoreRoomnumSaveMapper.queryPartnerAgent(params);
        return queryAllLists(page,size,list);
   }


    @Transactional
    public JSONObject querySumPartnerAgent(Map<String,Object> params){
        UseridScoreRoomnumSave useridScoreRoomnumSave=useridScoreRoomnumSaveMapper.querySumPartnerAgent(params);
        int n=useridScoreRoomnumSave.getParnerid();
        JSONObject result=new JSONObject();
        result.put("n",n);
        return  result;
    }


    @Transactional
    private PageInfo<UseridScoreRoomnumSave> queryAllLists(int pageNum, int pageSize, List<UseridScoreRoomnumSave> useridScoreRoomnumSaves) {
        //分页之后的对象
        List<UseridScoreRoomnumSave> sysAgentVoNews = new ArrayList<>();

        int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < useridScoreRoomnumSaves.size() - currIdx; i++) {
            UseridScoreRoomnumSave appGame = useridScoreRoomnumSaves.get(currIdx + i);
            sysAgentVoNews.add(appGame);
        }

        PageInfo<UseridScoreRoomnumSave> page = new PageInfo<>(sysAgentVoNews);
        page.setSize(useridScoreRoomnumSaves.size());
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(useridScoreRoomnumSaves.size());
        page.setPages(useridScoreRoomnumSaves.size() % pageSize == 0 ? useridScoreRoomnumSaves.size() / pageSize
                : useridScoreRoomnumSaves.size() / pageSize + 1);

        page.setIsFirstPage(pageNum == 1 ? true : false);
        page.setIsLastPage(pageNum == page.getPages() ? true : false);
        page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
        page.setHasNextPage(pageNum < page.getPages() ? true : false);
        page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
        page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
        page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
        page.setLastPage(page.getPages());
        page.setStartRow(currIdx);
        page.setEndRow(useridScoreRoomnumSaves.size() - currIdx);
        return page;
    }

}
