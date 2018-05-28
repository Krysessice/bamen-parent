package com.bamenyouxi.cow_nn_mode.service.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamen.IpUtil;
import com.bamen.WXPaySend;
import com.bamen.WXPayUtil;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.*;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.*;
import com.bamenyouxi.cow_nn_mode.util.RedisUtil;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PriceService  extends AbstractCrudService<Price, Long> {

    @Autowired
    private PriceMapper priceMapper;
    @Autowired
    private SysAgentMapper sysAgentMapper;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private ScaleMapper scaleMapper;
    @Autowired
    private RebateMapper rebateMapper;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public CrudMapper<Price, Long> getMapper() {
        return priceMapper;
    }

    @Transactional
    public PageInfo<Price> getPrice(int page,int size,Map<String,Object> params){
        List<Price> list=getMapper().get(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional//h5支付
    public JSONObject payZs(HttpServletRequest request, HttpServletResponse response, String rmb) throws Exception {
        HttpSession sess = request.getSession();
        Map<String, String> reqDatas=new HashMap<>();
        //商品描述
        String body="牛牛";
        System.out.println("商品描述:"+body);
        //gameId
        String GameID= UserDetailsUtil.getAccoutnt();
        String date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Random random=new Random();
        int num=100 + random.nextInt(900);
        //  String names="wz";
        String names="nn";
        String no=names+date+num;

        String out_trade_no=no+GameID;
        System.out.println("编号加id:"+out_trade_no);
        //钱
        String total_fee=rmb;
        String strMoney="";
        int str=Integer.parseInt(total_fee);
        System.out.println("金额之前："+str);
        int moneys=str*100;
        strMoney=Integer.toString(moneys);
        System.out.println("支付金额转换之后："+strMoney);

        //游戏类型id
        //String detail=request.getParameter("detail");
        //ip
        String ip= IpUtil.getRealIp(request);
        System.out.println("ip:"+ip);

        if(body==null || out_trade_no ==null
                || total_fee ==null || ip == null
                ){
            response.getWriter().print("error");
            return null;
        }

        reqDatas.put("appid","wx3b9f6a5ac5117337");
        reqDatas.put("body",body);
        reqDatas.put("mch_id","1488244722");
        reqDatas.put("nonce_str", WXPayUtil.generateNonceStr());
        reqDatas.put("notify_url", "xy.bamenkeji.com/Price/notify");
        reqDatas.put("out_trade_no", out_trade_no);
//        reqDatas.put("spbill_create_ip", "42.48.110.18");
        reqDatas.put("spbill_create_ip", ip);
        reqDatas.put("total_fee", strMoney+"");
        reqDatas.put("trade_type", "MWEB");
        reqDatas.put("scene_info", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"xy.bamenkeji.com\",\"wap_name\": \"充值\"}}");
        reqDatas.put("sign",WXPayUtil.generateSignature(reqDatas, "wangzheqipailaobahudong201710241"));
        System.out.println("接收参数成功");

        String prePayXml = new String(body.toString().getBytes("utf-8"), "iso8859-1");
        System.out.println("商品描述:"+prePayXml);

        String param = WXPayUtil.mapToXml(reqDatas);

        System.out.println(param);

        String GETTOKKEN_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        String tokenResultJSON= WXPaySend.sendPost(GETTOKKEN_URL, param);
        System.out.println("发给微信端成功");

        System.out.println(tokenResultJSON);

        Map<String, String> m=WXPayUtil.xmlToMap(tokenResultJSON);

        /* String h5Url1="";
         h5Url1= m.get("mweb_url");
         String result = "http://xy.bamenkeji.com/agent/result.html";
         h5Url1+="&redirect_url="+ URLEncoder.encode(result, "UTF-8");*/

         String h5Url1 = m.get("mweb_url");
         JSONObject results = new JSONObject();
         results.put("h5Url1", h5Url1);
         System.out.println("跳转地址-------："+h5Url1);
         return results;
    }

    @Transactional
    public void weixin_notify_url(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("进回调-----");
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String str ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((str = in.readLine()) != null){
            sb.append(str);
        }
        in.close();
        inputStream.close();

        Map<String, String> m = new HashMap<String, String>();
        m = WXPayUtil.xmlToMap(sb.toString());
        System.out.println(m.get("out_trade_no"));

        String key="wangzheqipailaobahudong201710241";
        if(WXPayUtil.isSignatureValid(m,key)){
            System.out.println("秘钥成功");

            String GameId=m.get("out_trade_no");
           /* String GameId="nn2018051610075654613141111111";*/
            String out_trade_no=GameId.substring(19,30);
            System.out.println(out_trade_no);

            String no=m.get("out_trade_no");
            /*String no="nn2018051610075654613141111111";*/
            System.out.println(no);

            String total_fee=m.get("total_fee");
            /*String total_fee="100";*/
            int _money = 0;
            try
            {
                _money = Integer.parseInt(total_fee);
                if (_money != 0 && _money > 0)
                {
                    _money /= 100;
                }

            }catch(Exception e)
            {
                response.getWriter().print("金额转换失败");
                return;
            }
            BigDecimal money=new BigDecimal(_money);
            System.out.println("金额转换之后："+money);

            String result_code=m.get("result_code");

            System.out.println("接收参数成功------该处理逻辑");
        	/*逻辑*/
            List<SysAgent> list=sysAgentMapper.get(new HashMap<String, Object>(){{
                put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(),out_trade_no);
            }});
            Assert.isTrue(!list.isEmpty(), TipMsgConstant.SYS_AGENT);
            System.out.println("用户查询成功");

            List<Price> pricesList=priceMapper.get(new HashMap<String, Object>(){{
                put(FieldConstant.DBFieldConstant.RMB.name(),money);
            }});
            Assert.isTrue(!pricesList.isEmpty(), TipMsgConstant.CONDITION_UNMET);
            System.out.println("金额查询成功");

            List<PayOrder> orderNoList=payOrderMapper.get(new HashMap<String, Object>(){{
                put(FieldConstant.DBFieldConstant.F_ORDER_NO.name(),no);
            }});
            System.out.println("订单号查询成功");
            if(!orderNoList.isEmpty()){
                System.out.print("成功");
                String strXml=WXPayUtil.setXML("SUCCESS", "Ok");
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(strXml.getBytes());
                out.flush();
                out.close();
                System.out.println(strXml.getBytes().toString());
                return;
            }else{
                PayOrder payOrder=new PayOrder();
                payOrder.setOrderNo(no);//订单号
                payOrder.setSysAgentId(out_trade_no);//代理账号
                payOrder.setSysAgentRoleId(list.get(0).getRoleId());//代理角色ID
                payOrder.setPayPrice(money);//金额
                payOrder.setCardNum(pricesList.get(0).getDrill());//购买的钻石
                int n=payOrderMapper.save(payOrder);
                Assert.isTrue(n>0,TipMsgConstant.OPERATION_FAILED);
                System.out.println("充值成功");

                String superAgentId=list.get(0).getSuperAgentId();
//                String str="";
                List<SysAgent> superAgentIdAll =new  ArrayList<SysAgent>();//所有上级
                SystemInfo systemInfo= redisUtil.getSystemInfo();
                if(!superAgentId.isEmpty()){
                    String[] resultSuperAccount = superAgentId.split(",");
                    SysAgent flag=list.get(0);
                    for (int i=resultSuperAccount.length-1;i>=0;i--){
                        List<SysAgent> getSuperAgentID=sysAgentMapper.getSuperAgentID(resultSuperAccount[i]);
                        //superAgentIdAll.addAll(getSuperAgentID);
                        if(!CollectionUtils.isEmpty(getSuperAgentID)){
                            if(getSuperAgentID.get(0).getNickName().contains("办事处")){
                                continue;
                            }
                            BigDecimal rebate=getRebate(flag,getSuperAgentID.get(0),systemInfo);
                            flag=getSuperAgentID.get(0);
                            int s=rebateMapper.saves(no,list.get(0).getAccount(),list.get(0).getNickName(), String.valueOf(money),rebate,money.multiply(rebate),flag.getAccount(),getSuperAgentID.get(0).getRoleId());
                        }else {
                            break;
                        }
                    }
                }
                String strXml=WXPayUtil.setXML("SUCCESS", "Ok");
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(strXml.getBytes());
                out.flush();
                out.close();
                System.out.println(strXml.getBytes().toString());
            }

        }else{
            System.out.println("秘钥失败");
        }

    }

    public BigDecimal getRebate(SysAgent account,SysAgent superAccount,SystemInfo systemInfo){
        BigDecimal n=new BigDecimal(0);
        if(account.getNickName().contains("二级代理") && superAccount.getNickName().contains("一级代理")){
            return systemInfo.getRatelv4();
        }
        if(account.getNickName().contains("一级代理") && superAccount.getNickName().contains("总代")){
            return systemInfo.getRatelv2();
        }
        if(account.getNickName().contains("总代") && superAccount.getNickName().contains("业务员")){
            return systemInfo.getRatelv1();
        }
        if(account.getNickName().contains("二级代理") && superAccount.getNickName().contains("总代")){
            return systemInfo.getRatelv3();
        }
        if(account.getNickName().contains("一级代理") && superAccount.getNickName().contains("业务员")){
            return systemInfo.getRatelv2();
        }
        if(account.getNickName().contains("二级代理") && superAccount.getNickName().contains("业务员")){
            return systemInfo.getRatelv3();
        }
        return n;
    }


}
