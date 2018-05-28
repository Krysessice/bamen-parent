package com.bamenyouxi.invite_code_mode.service.sqlserver.record;

import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;


import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.RedeemCodeMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgentVo;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RedeemCode;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RoomParnterData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RedeemCodeService extends AbstractCrudService<RedeemCode, Integer> {

    @Autowired
    private RedeemCodeMapper redeemCodeMapper;

    @Override
    public CrudMapper<RedeemCode, Integer> getMapper() {
        return redeemCodeMapper;
    }

    //md5加密32位
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString().toUpperCase();
            System.out.println(encryptStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }

    //md5加密16位
    public static String encrypt16(String encryptStr) {
        return encrypt32(encryptStr).substring(8, 24);
    }

    @Transactional
    public void RedeemCode(String code,Integer card,Timestamp end){
        Integer in=Integer.parseInt(code);
        //获取当前的分秒
        //String date=new SimpleDateFormat("mmss").format(new Date());
        for(int i = 1;i <= in;i++){
            //默认第一次
            aa(true,card,null,end);
        }

    }

    private void aa(boolean aa, Integer card, String md5date, Timestamp end){
        /*如果有重复*/
        if(aa){
            //获取当前的分秒
            String date=new SimpleDateFormat("mmss").format(new Date());
            //uuid
            String s = UUID.randomUUID().toString();
            //截取uuid
            s =  s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
            String subs=s.substring(0, 12);
            String subNums=subs+date;
            //System.out.println("加密前：-------"+subNums);
            //md5加密
            md5date = new String(RedeemCodeService.encrypt16(subNums));
            //System.out.println("加密后：-------"+md5date);
            List<RedeemCode> list=redeemCodeMapper.queryExistence(md5date);
            aa(list.size()>0,card,md5date,end);
        }else{
            //添加到数据库
            int n=redeemCodeMapper.saves(md5date,card,end);
            Assert.isTrue(n>0, TipMsgConstant.OPERATION_SUCCESS);
        }
    }


    @Transactional
    public PageInfo<RedeemCode> queryAllList(int page, int size,Map<String,Object> params){
        Object endDate=  params.get("endDate");
        Object card=params.get("card");
        List<RedeemCode> list=redeemCodeMapper.queryAllList(page,size,endDate,card);
        return queryAllLists(page,size,list);
    }
    @Transactional
    public PageInfo<RedeemCode> queryPastNoAllList(int page, int size,Map<String,Object> params){
        Object endDate=  params.get("endDate");
        List<RedeemCode> list=redeemCodeMapper.queryPastNoAllList(page,size,endDate);
        return queryAllLists(page,size,list);
    }



    @Transactional
    private PageInfo<RedeemCode> queryAllLists(int pageNum, int pageSize, List<RedeemCode> redeemCodes) {
        //分页之后的对象
        List<RedeemCode> sysAgentVoNews = new ArrayList<>();

        int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < redeemCodes.size() - currIdx; i++) {
            RedeemCode appGame = redeemCodes.get(currIdx + i);
            sysAgentVoNews.add(appGame);
        }

        PageInfo<RedeemCode> page = new PageInfo<>(sysAgentVoNews);
        page.setSize(redeemCodes.size());
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(redeemCodes.size());
        page.setPages(redeemCodes.size() % pageSize == 0 ? redeemCodes.size() / pageSize
                : redeemCodes.size() / pageSize + 1);

        page.setIsFirstPage(pageNum == 1 ? true : false);
        page.setIsLastPage(pageNum == page.getPages() ? true : false);
        page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
        page.setHasNextPage(pageNum < page.getPages() ? true : false);
        page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
        page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
        page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
        page.setLastPage(page.getPages());
        page.setStartRow(currIdx);
        page.setEndRow(redeemCodes.size() - currIdx);
        return page;
    }


}
