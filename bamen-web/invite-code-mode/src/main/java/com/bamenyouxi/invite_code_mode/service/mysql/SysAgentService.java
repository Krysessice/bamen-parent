package com.bamenyouxi.invite_code_mode.service.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.util.UUIDUtil;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.*;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.*;
import com.bamenyouxi.invite_code_mode.model.spring.PropertiesMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.service._impl.AbstractSysAgentService;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.bamenyouxi.invite_code_mode.util.SysResourceUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 后台代理 service
 * Created by 13477 on 2017/7/10.
 */
@Service
@Order(SysConstant.CommandLineOrder.RESET_SHOW_ANNOUNCE)
public class SysAgentService extends AbstractSysAgentService implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysAgentService.class);

    @Autowired
    private SysAgentMapper sysAgentMapper;
    @Autowired
    private AccountsInfoMapper accountsInfoMapper;
    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private PayOrderPerdayStatisticMapper payOrderPerdayStatisticMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PropertiesMapper propertiesMapper;
    @Autowired
    private SysResourceUtil sysResourceUtil;
    @Autowired
    private UserGoldMapper userGoldMapper;
    @Autowired
    private GoldInfoMapper goldInfoMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public CrudMapper<SysAgent, Long> getMapper() {
        return sysAgentMapper;
    }

    /**
     * 代理授权
     * <p>1.检查用户权限</p>
     * <p>2.授权</p>
     * <p>3.更新游戏用户信息</p>
     *
     * @param id 待授权代理id
     */
    @Transactional
    public void authorize(Long id) {
        sysResourceUtil.exists(UserDetailsUtil.getId(), id, SysConstant.SysResourceConstant.RESOURCE_USER);

        int i =
                getMapper().update(
                        new SysAgent.Builder()
                                .isAuthorized(true)
                                .authorizedTime(Timestamp.from(Instant.now()))
                                .id(id).build()
                );

        SysAgent sysAgent =
                super.getSingleSysAgent(
                        new HashMap<String, Object>() {{
                            put(FieldConstant.DBFieldConstant.F_ID.name(), id);
                        }}
                );
        int j =
                accountsInfoMapper.update(
                        new AccountsInfo.Builder()
                                .agentLevel(SysConstant.SysFlagConstant.ENABLE)
                                .gameId(sysAgent.getGameId())
                                .build()
                );

        Assert.isTrue(i > 0 && j > 0, TipMsgConstant.OPERATION_FAILED);
        LOGGER.info("{} 授权代理: {}", UserDetailsUtil.getGameId(), sysAgent.getGameId());
    }

    @Override
    protected SysAgent getSingleSysAgent(Map<String, Object> params) {
        return super.getSingleSysAgent(params);
    }

    /**
     * 密码修改
     * <p>1.校验密码格式</p>
     * <p>2.获取redis中盐值</p>
     * <p>3.验证原密码</p>
     * <p>4.更新密码</p>
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     */
    @Transactional
    public void changePassword(String oldPwd, String newPwd) {
        // 新旧密码相同
        Assert.isTrue(!oldPwd.equals(newPwd), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
        // 新密码不符合格式
        Assert.isTrue(Pattern.compile("^[a-zA-Z0-9_]{6,16}$").matcher(newPwd).matches(), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
        // redis中对应用户盐值存在
        Assert.isTrue(stringRedisTemplate.hasKey(UserDetailsUtil.getRedisKey_Salt()), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
        String salt = stringRedisTemplate.opsForValue().get(UserDetailsUtil.getRedisKey_Salt());
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        // 原密码验证
        Assert.isTrue(UserDetailsUtil.getPassword().equals(md5PasswordEncoder.encodePassword(oldPwd, salt)), TipMsgConstant.ORIGIN_PWD_WRONG);
        // 密码修改
        int i =
                getMapper().update(
                        new SysAgent.Builder()
                                .id(UserDetailsUtil.getId())
                                .password(md5PasswordEncoder.encodePassword(newPwd, salt))
                                .build()
                );

        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
        LOGGER.info("{} 修改密码: {} -> {}", UserDetailsUtil.getGameId(), oldPwd, newPwd);
    }

    @Transactional
    public JSONObject getService(String game) {
        SysAgent sysAgent = sysAgentMapper.getService(UserDetailsUtil.getGameId());
        int n = sysAgent.getGameId();
        JSONObject result = new JSONObject();
        result.put("n", n);
        return result;
    }

    @Transactional
    public PageInfo<SysAgent> querySubsets(int page, int size, Map<String, Object> params) {
        List<SysAgent> list = sysAgentMapper.querySubsets(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }



    @Transactional
    public JSONObject pages() {
        SysAgent sysAgent = sysAgentMapper.pages(UserDetailsUtil.getGameId());
        //System.out.println(UserDetailsUtil.getGameId());
        int n = sysAgent.getPartner();
        int s=sysAgent.getLookGold();
        JSONObject result = new JSONObject();
        result.put("n", n);
        result.put("s", s);
        return result;
    }

    /**
     * 授权合伙人
     */
    @Transactional
    public PageInfo<SysAgent> getPartner(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        if (StringUtils.isEmpty(params.get("gameId")))
            params = null;
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        //params.put(FieldConstant.CommonFieldConstant.groupBy.name(),FieldConstant.GroupConstant.CREATE_DATE);
        List<SysAgent> list = sysAgentMapper.getPartner(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    /**
     * 查询没有授权人
     */
    @Transactional
    public PageInfo<SysAgent> cancelPartner(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
        List<SysAgent> list = sysAgentMapper.cancelPartner(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    /**
     * 授权合伙人
     */
    @Transactional
    public void authorizeAgent(String gameId) {
        int i = sysAgentMapper.updateAgentId(gameId);
        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
    }

    /**
     * 取消授权
     */
    @Transactional
    public void authorizeNo(String gameId) {
        int i = sysAgentMapper.updateAgentIds(gameId);
        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
    }

    /**
     * 合伙人业绩
     */
    @Transactional
    public PageInfo<SysAgentVo> partnerQuerysubclass(int page, int size, Map<String, Object> params) {
        List<SysAgentVo> sysAgentsRe = new ArrayList<>();
        List<SysAgentVo> gameId = this.queryFirstLevelAgent(params);
        if (gameId.size() == 0) {
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
        } else {
            // 一级代理
            sysAgentsRe.addAll(gameId);
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
            long sub = 2;
            List<Integer> strs = gameId.stream().map(SysAgent::getGameId).collect(Collectors.toList());
            List<SysAgentVo> sysAgents = new ArrayList<>();

            Map<Long, Map<Integer, SysAgentVo>> map = new HashMap();
            convertData(1l, gameId, map);
            do {
                sysAgents = getSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
                strs = sysAgents.stream().map(SysAgent::getGameId).collect(Collectors.toList());
                sysAgentsRe.addAll(sysAgents);
                //System.out.println(sub + "级代理" + sysAgents.toString());
                convertData(sub, sysAgents, map);
                sub++;
            } while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
            if (sysAgentsRe.isEmpty()) {
                return null;
            }
            calculatedAmount(map);
        }

        //移除玩家
        filtSysAgentsRe(sysAgentsRe);
        //组装pageinfo对象
        return assemblePage(page, size, sysAgentsRe);
    }

    /**
     * 合伙人业绩总金额
     */
    @Transactional
    public JSONObject partnerQueryTimeSumMoney(Map<String, Object> params) {
        JSONObject mapRe = new JSONObject();
        List<SysAgentVo> sysAgentsRe = new ArrayList<>();
        List<SysAgentVo> gameId = this.queryFirstLevelAgent(params);
        if (gameId.size() == 0) {
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
        } else {
            // 一级代理
            sysAgentsRe.addAll(gameId);
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
            long sub = 2;
            List<Integer> strs = gameId.stream().map(SysAgent::getGameId).collect(Collectors.toList());
            List<SysAgentVo> sysAgents = new ArrayList<>();

            Map<Long, Map<Integer, SysAgentVo>> map = new HashMap();
            convertData(1l, gameId, map);
            do {
                sysAgents = getSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
                strs = sysAgents.stream().map(SysAgent::getGameId).collect(Collectors.toList());
                sysAgentsRe.addAll(sysAgents);
                //System.out.println(sub + "级代理" + sysAgents.toString());
                convertData(sub, sysAgents, map);
                sub++;
            } while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
            if (sysAgentsRe.isEmpty()) {
                return null;
            }
            calculatedAmount(map);
        }

        SystemInfo systemInfo = redisUtil.getSystemInfo();
        //一级比例
        BigDecimal t1 = systemInfo.getT1_commission();

        PayOrder sysAgentMyselfSum=payOrderMapper.queryMyself(params);
        BigDecimal sums=sysAgentMyselfSum.getPayPrice();
        BigDecimal lastSum=sums.multiply(t1);

        //移除玩家
        filtSysAgentsRe(sysAgentsRe);
        JSONObject result = new JSONObject();
        BigDecimal bigDecimalSum= java.math.BigDecimal.ZERO;
        for(SysAgentVo sysAgentVo:sysAgentsRe) bigDecimalSum = bigDecimalSum.add(sysAgentVo.getSumAmount());

        bigDecimalSum=bigDecimalSum.add(sums);

        result.put("sumBig", bigDecimalSum);
        result.put("sums", sums);
        result.put("lastSum", lastSum);
        return result;
    }


    /**
     * 查询总金额
     */
    @Transactional
    public JSONObject queryTimeSumMoney(Map<String, Object> params) {
        JSONObject mapRe = new JSONObject();
        List<SysAgentVo> sysAgentsRe = new ArrayList<>();
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), null);
        params.put(FieldConstant.ModelFieldConstant.gameId.name(), UserDetailsUtil.getGameId());
        List<SysAgentVo> gameId = this.queryFirstLevelAgent(params);
        if (gameId.size() == 0) {
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
        } else {
            // 一级代理
            sysAgentsRe.addAll(gameId);
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
            long sub = 2;
            List<Integer> strs = gameId.stream().map(SysAgent::getGameId).collect(Collectors.toList());
            List<SysAgentVo> sysAgents = new ArrayList<>();

            Map<Long, Map<Integer, SysAgentVo>> map = new HashMap();
            convertData(1l, gameId, map);
            do {
                sysAgents = getSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
                strs = sysAgents.stream().map(SysAgent::getGameId).collect(Collectors.toList());
                sysAgentsRe.addAll(sysAgents);
                //System.out.println(sub + "级代理" + sysAgents.toString());
                convertData(sub, sysAgents, map);
                sub++;
            } while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
            if (sysAgentsRe.isEmpty()) {
                return null;
            }
            calculatedAmount(map);
        }

        SystemInfo systemInfo = redisUtil.getSystemInfo();
        //一级比例
        BigDecimal t1 = systemInfo.getT1_commission();

        PayOrder sysAgentMyselfSum=payOrderMapper.queryMyself(params);
        BigDecimal sums=sysAgentMyselfSum.getPayPrice();
        BigDecimal lastSum=sums.multiply(t1);

        //移除玩家
        filtSysAgentsRe(sysAgentsRe);
        JSONObject result = new JSONObject();
        BigDecimal bigDecimalSum= java.math.BigDecimal.ZERO;
        for(SysAgentVo sysAgentVo:sysAgentsRe)  bigDecimalSum= bigDecimalSum.add(sysAgentVo.getSumAmount());

        bigDecimalSum=bigDecimalSum.add(sums);

        result.put("sumBig", bigDecimalSum);
        result.put("sums", sums);
        result.put("lastSum", lastSum);
        return result;
    }

    /**
     * 查询一级代理
     *
     * @param params
     * @return
     */
    public List<SysAgentVo> queryFirstLevelAgent(Map<String, Object> params) {
        List<SysAgentVo> gameId = sysAgentMapper.query4list(params);
        List<SysAgentVo> gameIdAllAgent = sysAgentMapper.queryAllAgent(params);
        Map<Long, SysAgentVo> agentVoMap = new HashMap<>();

        for (SysAgentVo vo : gameIdAllAgent) {
            agentVoMap.put(vo.getId(), vo);
        }
        for (SysAgentVo vo : gameId) {
            agentVoMap.put(vo.getId(), vo);
        }

        return agentVoMap.values().stream().collect(Collectors.toList());

    }


    /**
     * 授权代理
     */
    @Transactional
    public PageInfo<SysAgentVo> querysubclass(int page, int size, Map<String, Object> params) {
        List<SysAgentVo> sysAgentsRe = new ArrayList<>();
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), null);
        params.put(FieldConstant.ModelFieldConstant.gameId.name(), UserDetailsUtil.getGameId());
        List<SysAgentVo> gameId = this.queryFirstLevelAgent(params);
        if (gameId.size() == 0) {
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
        } else {
            // 一级代理
            sysAgentsRe.addAll(gameId);
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
            long sub = 2;
            List<Integer> strs = gameId.stream().map(SysAgent::getGameId).collect(Collectors.toList());
            List<SysAgentVo> sysAgents = new ArrayList<>();

            Map<Long, Map<Integer, SysAgentVo>> map = new HashMap();
            convertData(1l, gameId, map);
            do {
                sysAgents = getSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
                strs = sysAgents.stream().map(SysAgent::getGameId).collect(Collectors.toList());
                sysAgentsRe.addAll(sysAgents);
                //System.out.println(sub + "级代理" + sysAgents.toString());
                convertData(sub, sysAgents, map);
                sub++;
            } while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
            if (sysAgentsRe.isEmpty()) {
                return null;
            }
            calculatedAmount(map);
        }

        //移除玩家
        filtSysAgentsRe(sysAgentsRe);
        //组装pageinfo对象
        return assemblePage(page, size, sysAgentsRe);

    }

    //是玩家就移除
    private void filtSysAgentsRe(List<SysAgentVo> vos) {
        Iterator<SysAgentVo> it = vos.iterator();
        while (it.hasNext()) {
            SysAgentVo x = it.next();
            if (!x.getAuthorized() || (x.getSumAmount() == null || x.getSumAmount() .compareTo(BigDecimal.ZERO)<=0 )) {
                it.remove();
            }
        }

    }

    //是玩家金币就移除
    private void filtGoldSysAgentsRe(List<SysAgentVo> vos) {
        Iterator<SysAgentVo> it = vos.iterator();
        while (it.hasNext()) {
            SysAgentVo x = it.next();
            if (!x.getAuthorized()) {
                it.remove();
            }
        }

    }


    /**
     * 分页
     */
    private PageInfo<SysAgentVo> assemblePage(int pageNum, int pageSize, List<SysAgentVo> sysAgentVos) {
        //分页之后的对象
        List<SysAgentVo> sysAgentVoNews = new ArrayList<>();

        int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < sysAgentVos.size() - currIdx; i++) {
            SysAgentVo appGame = sysAgentVos.get(currIdx + i);
            sysAgentVoNews.add(appGame);
        }

        PageInfo<SysAgentVo> page = new PageInfo<>(sysAgentVoNews);
        page.setSize(sysAgentVos.size());
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(sysAgentVos.size());
        page.setPages(sysAgentVos.size() % pageSize == 0 ? sysAgentVos.size() / pageSize
                : sysAgentVos.size() / pageSize + 1);

        page.setIsFirstPage(pageNum == 1 ? true : false);
        page.setIsLastPage(pageNum == page.getPages() ? true : false);
        page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
        page.setHasNextPage(pageNum < page.getPages() ? true : false);
        page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
        page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
        page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
        page.setLastPage(page.getPages());
        page.setStartRow(currIdx);
        page.setEndRow(sysAgentVos.size() - currIdx);
        return page;
    }
    
    private List<SysAgentVo> getSubSyAgent(List<Integer> params, String startDate, String endDate) {
        List<SysAgentVo> vos = sysAgentMapper.querysubclass(params, startDate, endDate);
        List<SysAgentVo> vosAll = sysAgentMapper.querysubclassAll(params);
        Map<Long, SysAgentVo> agentVoMap = new HashMap<>();

        for (SysAgentVo vo : vosAll) {
            agentVoMap.put(vo.getId(), vo);
        }
        for (SysAgentVo vo : vos) {
            agentVoMap.put(vo.getId(), vo);
        }

        return agentVoMap.values().stream().collect(Collectors.toList());
    }

    /**
     * 组装path 为总金额赋值
     *
     * @param map
     */
    public void calculatedAmount(Map<Long, Map<Integer, SysAgentVo>> map) {
        SystemInfo systemInfo = redisUtil.getSystemInfo();

        BigDecimal sumBig = BigDecimal.ZERO;
        if (map != null) {
            for (Long key : map.keySet()) {
                Map<Integer, SysAgentVo> sysAgentVos = map.get(key);
                if (sysAgentVos != null) {
                    for (Integer str : sysAgentVos.keySet()) {
                        SysAgentVo sysAgentVo = sysAgentVos.get(str);
                        sysAgentVo.setLeve(key); // 代理等级
                        //System.out.println(key + "级代理" + sysAgentVo.toString());
                        if (!sysAgentVo.getAuthorized()) {//只计算玩家的充值
                            setPath(key, sysAgentVo, map); // path 路径获取
                        }
                    }
                }
            }
        }
        if (map != null) {
            for (Long key : map.keySet()) {
                Map<Integer, SysAgentVo> sysAgentVos = map.get(key);
                if (sysAgentVos != null) {
                    for (Integer str : sysAgentVos.keySet()) {
                        SysAgentVo sysAgentVo = sysAgentVos.get(str);
                        if (sysAgentVo == null) {
                            continue;
                        }
                        setSumAmount(key, sysAgentVo, map); // 金额计算
                        //计算总金额

                       /* if (!sysAgentVo.getAuthorized()) {
                            sumBig = sumBig.add(sysAgentVo.getSumAmount() == null ? BigDecimal.ZERO : sysAgentVo.getSumAmount());
                        }*/
                        //sysAgentVo.setSums(sumBig.doubleValue());
                    }
                }
            }
        }

        //return sumBig;
    }

    /**
     * 获取路径
     */
    public void setPath(Long size, SysAgentVo vo, Map<Long, Map<Integer, SysAgentVo>> map) {
        String path = new String("");
        // String superAccountpath = "";
        Integer getSuperAgentGameId = vo.getSuperAgentGameId();
        // signFor:for(Long key:map.keySet()){
        // 往后退 2级代理只查询 1级 3级查询 2 1
        for (int i = size.intValue(); i > 0; i--) {
            if (size != 1) { // 不是一级代理
                int upperLevel = i - 1;
                Map<Integer, SysAgentVo> sysAgentVos = map.get(Long.valueOf(upperLevel));
                if (sysAgentVos == null) {
                    continue;
                }
                SysAgentVo sysAgentVo = sysAgentVos.get(getSuperAgentGameId);
                if (upperLevel > 1) {
                    if (sysAgentVo == null) {
                        continue;
                    }
                    if (StringUtils.isEmpty(path)) {
                        path = path + (sysAgentVo.getGameId());
                    } else {
                        path = path + (",") + (sysAgentVo.getGameId());
                    }
                    getSuperAgentGameId = sysAgentVo.getSuperAgentGameId();

                    if (StringUtils.isEmpty(path)) {
                        break;
                    }
                } else if (upperLevel == 1) {
                    if (StringUtils.isEmpty(path)) {
                        path = sysAgentVo.getSuperAgentGameId() + "," + sysAgentVo.getGameId();
                    } else {
                        path = sysAgentVo.getSuperAgentGameId() + "," + sysAgentVo.getGameId() + "," + path;
                    }
                }

            } else {
                path = getSuperAgentGameId.toString();

            }
        }

        vo.setPath(path);

        //System.out.println("账号:" + vo.getGameId() + "path路径为:" + vo.getPath());

        // }
    }

    /**
     * 计算金额
     */
    public void setSumAmount(Long size, SysAgentVo vo, Map<Long, Map<Integer, SysAgentVo>> map) {
        //查询比例
        SystemInfo systemInfo = redisUtil.getSystemInfo();
        //一级比例
        BigDecimal t1 = systemInfo.getT1_commission();
        //2级比例
        BigDecimal t2 = systemInfo.getT2_commission();
        //3级比例
        BigDecimal t3 = systemInfo.getT3_commission();

        BigDecimal sumAmount = vo.getPayPrice() == null ? BigDecimal.ZERO : vo.getPayPrice(); // 自己的金额
        BigDecimal priceSum = BigDecimal.ZERO;
        BigDecimal strSum = BigDecimal.ZERO;
        BigDecimal oneselfSum = BigDecimal.ZERO;
        BigDecimal t1PriceSum = BigDecimal.ZERO;
        //BigDecimal  t2PriceSum=BigDecimal.ZERO ;
        //BigDecimal  t3PriceSum=BigDecimal.ZERO ;
        for (int i = size.intValue(); i < size.intValue() + 1; i++) {

            //System.out.println(vo.getGameId() +"计算几级代理："+(i + 1));
            Map<Integer, SysAgentVo> vomap = map.get(Long.valueOf((i + 1)));

            if (vomap == null) {
                break;
            }

            for (Integer str : vomap.keySet()) {
                SysAgentVo sysvo = vomap.get(str);
                if (StringUtils.isEmpty(sysvo.getPath()) || StringUtils.isEmpty(sysvo)) {
                    continue;
                }
                String[] pathStrs = sysvo.getPath().split(",");

                List<String> pathList = Arrays.asList(pathStrs);

                if (pathList.contains(vo.getGameId() + "")) {
                    priceSum = priceSum.add(
                            sysvo.getPayPrice() == null ? BigDecimal.ZERO : sysvo.getPayPrice());
                    sumAmount = sumAmount.add(
                            sysvo.getPayPrice() == null ? BigDecimal.ZERO : sysvo.getPayPrice());
                }

            }

            oneselfSum = vo.getPayPrice() == null ? BigDecimal.ZERO : vo.getPayPrice();
            //System.out.println(vo.getGameId() +"自己："+oneselfSum);
            BigDecimal oneselfSum1 = vo.getPayPrice() == null ? BigDecimal.ZERO : vo.getPayPrice();
            BigDecimal oneselfSums = oneselfSum.multiply(t1);
            //System.out.println(vo.getGameId()+"自己结算后："+oneselfSums);
            if (i == size.intValue()) {
                BigDecimal t1PriceSum1 = priceSum;
                //System.out.println(vo.getGameId() +"T1："+t1PriceSum1);
                t1PriceSum = priceSum.multiply(t1);
                //System.out.println(vo.getGameId() +"T1结算后："+t1PriceSum);
            } else if (i == size.intValue() + 1) {
                //BigDecimal t2PriceSum2=priceSum;
                //System.out.println(vo.getGameId() +"T2："+t2PriceSum);
                //t2PriceSum= priceSum.multiply(t2);
                //System.out.println(vo.getGameId() +"T2结算后："+t2PriceSum);
            } else {
                //BigDecimal t3PriceSum3=priceSum;
                //System.out.println(vo.getGameId() +"T3："+t3PriceSum);
                //t3PriceSum= priceSum.multiply(t3);
                //System.out.println(vo.getGameId() +"T3结算后："+t3PriceSum);
            }

            priceSum = BigDecimal.ZERO;
            strSum = oneselfSums.add(t1PriceSum);
            //.add(t2PriceSum).add(t3PriceSum);
            //System.out.println(strSum+"==================");

        }
        vo.setScaleMoney(strSum.doubleValue());
        vo.setSumAmount(sumAmount);
       // if(sumAmount.compareTo(BigDecimal.ZERO)>0)
         //   System.out.println("账号:" + vo.getGameId() + "总金额为:" + sumAmount);

    }


    /**
     * 数据转换
     *
     * @param sub
     * @param map
     */
    public void convertData(Long sub, List<SysAgentVo> vos, Map<Long, Map<Integer, SysAgentVo>> map) {
        Map<Integer, SysAgentVo> mapVo = new HashMap();
        for (SysAgentVo vo : vos) {
            mapVo.put(vo.getGameId(), vo);
        }
        map.put(sub, mapVo);

    }



    /**
     * 修改用户真实资料与邀请码
     *
     * @param sysAgent 待修改信息
     */
    @Transactional
    public void updateRealInfoAndInvitedCode(SysAgent sysAgent) {
        this.changeRealInfo(sysAgent);
        this.changeSuperAgentGameId(sysAgent.getId(), sysAgent.getSuperAgentGameId());
    }

    /**
     * 代理冻结
     *
     * @param gameId  游戏id
     * @param sysFlag 是否冻结的标识
     */
    @Transactional
    public void sysAgentFreeze(int gameId, boolean sysFlag) {
        SysAgent sysAgent =
                super.getSingleSysAgent(
                        new HashMap<String, Object>() {{
                            put(FieldConstant.DBFieldConstant.F_GAME_ID.name(), gameId);
                        }}
                );
        Assert.isTrue(sysAgent.getSysFlag() != sysFlag, TipMsgConstant.PARAM_INVALID);
        int i =
                getMapper().update(
                        new SysAgent.Builder()
                                ._gameId(gameId)
                                .sysFlag(sysFlag)
                                .build()
                );
        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
    }

    /**
     * 密码重置
     *
     * @param gameId 游戏id
     */
    @Transactional
    public void pwdReset(int gameId) {
        SysAgent sysAgent =
                super.getSingleSysAgent(
                        new HashMap<String, Object>() {{
                            put(FieldConstant.DBFieldConstant.F_GAME_ID.name(), gameId);
                        }}
                );
        String salt = UUIDUtil.genUUID();
        String password = new Md5PasswordEncoder().encodePassword(AuthConstant.SysAgentConstant.DEFAULT_PWD, salt);
        int i =
                getMapper().update(
                        new SysAgent.Builder()
                                ._gameId(gameId)
                                .secretKey(salt)
                                .password(password)
                                .build()
                );
        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
    }

    /**
     * 修改代理真实资料
     *
     * @param sysAgent 代理信息
     */
    @Transactional
    private void changeRealInfo(SysAgent sysAgent) {
        SysAgent record =
                super.getSingleSysAgent(
                        new HashMap<String, Object>() {{
                            put(FieldConstant.DBFieldConstant.F_ID.name(), sysAgent.getId());
                        }}
                );
        if (!record.getFinishInfo()) return;
        Assert.isTrue(
                !(sysAgent.getRealName().isEmpty() || sysAgent.getTel().isEmpty() ||
                        sysAgent.getOpeningBank().isEmpty() || sysAgent.getBankAccount().isEmpty()),
                TipMsgConstant.CONDITION_UNMET
        );
        int i =
                getMapper().update(
                        new SysAgent.Builder()
                                .id(sysAgent.getId())
                                .realName(sysAgent.getRealName())
                                .tel(sysAgent.getTel())
                                .openingBank(sysAgent.getOpeningBank())
                                .bankAccount(sysAgent.getBankAccount())
                                .province(sysAgent.getProvince())
                                .city(sysAgent.getCity())
                                .build()
                );
        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
    }

    /**
     * 统计每天新增
     */
    @Transactional
    public PageInfo<SysAgent> getNewInsert(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
        List<SysAgent> list = sysAgentMapper.getNewInsert(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }


    /**
     * 雷总授权合伙人
     */
    @Transactional
    public PageInfo<SysAgent> giveAgentList(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
        List<SysAgent> list = sysAgentMapper.giveAgentList(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<SysAgent> queryGiveAgentList(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
        List<SysAgent> list = sysAgentMapper.queryGiveAgentList(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }


    /**
     * 10003 授权合伙人
     */
    @Transactional
    public void updateAiveAgentList(Integer gameId) {
        int i = sysAgentMapper.updateAiveAgentList(gameId);
        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
    }

    /**
     * 10003 取消授权
     */
    @Transactional
    public void updateNoAiveAgentList(Integer gameId) {
        int i = sysAgentMapper.updateNoAiveAgentList(gameId);
        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
    }


    //start 金币消耗

    /**
     * 授权管理员金币代理
     */
    @Transactional
    public PageInfo<SysAgentVo> partnerGoldQuerysubclass(int page, int size, Map<String, Object> params) {
        List<SysAgentVo> sysAgentsRe = new ArrayList<>();
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), null);
        List<SysAgentVo> gameId = this.queryGoldFirstLevelAgent(params);
        if (gameId.size() == 0) {
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
        } else {
            // 一级代理
            sysAgentsRe.addAll(gameId);
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
            long sub = 2;
            List<Integer> strs = gameId.stream().map(SysAgent::getGameId).collect(Collectors.toList());
            List<SysAgentVo> sysAgents = new ArrayList<>();

            Map<Long, Map<Integer, SysAgentVo>> map = new HashMap();
            convertData(1l, gameId, map);
            do {
                sysAgents = getGoldSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
                strs = sysAgents.stream().map(SysAgent::getGameId).collect(Collectors.toList());
                sysAgentsRe.addAll(sysAgents);
                //System.out.println(sub + "级代理" + sysAgents.toString());
                convertData(sub, sysAgents, map);
                sub++;
            } while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
            if (sysAgentsRe.isEmpty()) {
                return null;
            }
            GoldcalculatedAmount(map);
        }

        //移除玩家
        filtSysAgentsRe(sysAgentsRe);
        //组装pageinfo对象
        return assemblePage(page, size, sysAgentsRe);

    }

    /**
     * 查询管理员总金币消耗
     */
    @Transactional
    public JSONObject querysGoldSums(Map<String, Object> params) {
        JSONObject mapRe = new JSONObject();
        List<SysAgentVo> sysAgentsRe = new ArrayList<>();
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), null);
        List<SysAgentVo> gameId = this.queryGoldFirstLevelAgent(params);
        if (gameId.size() == 0) {
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
        } else {
            // 一级代理
            sysAgentsRe.addAll(gameId);
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
            long sub = 2;
            List<Integer> strs = gameId.stream().map(SysAgent::getGameId).collect(Collectors.toList());
            List<SysAgentVo> sysAgents = new ArrayList<>();

            Map<Long, Map<Integer, SysAgentVo>> map = new HashMap();
            convertData(1l, gameId, map);
            do {
                sysAgents = getGoldSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
                strs = sysAgents.stream().map(SysAgent::getGameId).collect(Collectors.toList());
                sysAgentsRe.addAll(sysAgents);
                //System.out.println(sub + "级代理" + sysAgents.toString());
                convertData(sub, sysAgents, map);
                sub++;
            } while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
            if (sysAgentsRe.isEmpty()) {
                return null;
            }
            GoldcalculatedAmount(map);
        }

        SystemInfo systemInfo = redisUtil.getSystemInfo();
        //一级比例
        BigDecimal t1 = systemInfo.getT1_commission();

        UserGold userGold=userGoldMapper.queryGoldMyself(params);
        BigDecimal sums=userGold.getSystemcost();
        BigDecimal lastSum=sums.multiply(t1);

        //移除玩家
        filtSysAgentsRe(sysAgentsRe);
        JSONObject result = new JSONObject();
        BigDecimal bigDecimalSum= java.math.BigDecimal.ZERO;
        for(SysAgentVo sysAgentVo:sysAgentsRe)  bigDecimalSum= bigDecimalSum.add(sysAgentVo.getSumAmount());

        bigDecimalSum=bigDecimalSum.add(sums);

        result.put("sumBig", bigDecimalSum);
        result.put("sums", sums);
        result.put("lastSum", lastSum);
        return result;
    }


    /**
     * 查询总金币消耗
     */
    @Transactional
    public JSONObject queryGoldSums(Map<String, Object> params) {
        JSONObject mapRe = new JSONObject();
        List<SysAgentVo> sysAgentsRe = new ArrayList<>();
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), null);
        params.put(FieldConstant.ModelFieldConstant.gameId.name(), UserDetailsUtil.getGameId());
        List<SysAgentVo> gameId = this.queryGoldFirstLevelAgent(params);
        if (gameId.size() == 0) {
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
        } else {
            // 一级代理
            sysAgentsRe.addAll(gameId);
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
            long sub = 2;
            List<Integer> strs = gameId.stream().map(SysAgent::getGameId).collect(Collectors.toList());
            List<SysAgentVo> sysAgents = new ArrayList<>();

            Map<Long, Map<Integer, SysAgentVo>> map = new HashMap();
            convertData(1l, gameId, map);
            do {
                sysAgents = getGoldSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
                strs = sysAgents.stream().map(SysAgent::getGameId).collect(Collectors.toList());
                sysAgentsRe.addAll(sysAgents);
                //System.out.println(sub + "级代理" + sysAgents.toString());
                convertData(sub, sysAgents, map);
                sub++;
            } while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
            if (sysAgentsRe.isEmpty()) {
                return null;
            }
            GoldcalculatedAmount(map);
        }

        SystemInfo systemInfo = redisUtil.getSystemInfo();
        //一级比例
        BigDecimal t1 = systemInfo.getT1_commission();

        UserGold userGold=userGoldMapper.queryGoldMyself(params);
        BigDecimal sums=userGold.getSystemcost();
        BigDecimal lastSum=sums.multiply(t1);

        //移除玩家
        filtSysAgentsRe(sysAgentsRe);
        JSONObject result = new JSONObject();
        BigDecimal bigDecimalSum= java.math.BigDecimal.ZERO;
        for(SysAgentVo sysAgentVo:sysAgentsRe)  bigDecimalSum= bigDecimalSum.add(sysAgentVo.getSumAmount());

        bigDecimalSum=bigDecimalSum.add(sums);

        result.put("sumBig", bigDecimalSum);
        result.put("sums", sums);
        result.put("lastSum", lastSum);
        return result;
    }

    /**
     * 查询金币一级代理
     *
     * @param params
     * @return
     */
    public List<SysAgentVo> queryGoldFirstLevelAgent(Map<String, Object> params) {
        List<SysAgentVo> gameId = sysAgentMapper.queryGoldlist(params);
        List<SysAgentVo> gameIdAllAgent = sysAgentMapper.queryGoldAllAgent(params);
        Map<Long, SysAgentVo> agentVoMap = new HashMap<>();

        for (SysAgentVo vo : gameIdAllAgent) {
            agentVoMap.put(vo.getId(), vo);
        }
        for (SysAgentVo vo : gameId) {
            agentVoMap.put(vo.getId(), vo);
        }

        return agentVoMap.values().stream().collect(Collectors.toList());

    }


    /**
     * 授权金币代理
     */
    @Transactional
    public PageInfo<SysAgentVo> queryGoldsubclass(int page, int size, Map<String, Object> params) {
        List<SysAgentVo> sysAgentsRe = new ArrayList<>();
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), null);
        params.put(FieldConstant.ModelFieldConstant.gameId.name(), UserDetailsUtil.getGameId());
        List<SysAgentVo> gameId = this.queryGoldFirstLevelAgent(params);
        if (gameId.size() == 0) {
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
        } else {
            // 一级代理
            sysAgentsRe.addAll(gameId);
            Assert.notNull(gameId, TipMsgConstant.PARAM_INVALID);
            long sub = 2;
            List<Integer> strs = gameId.stream().map(SysAgent::getGameId).collect(Collectors.toList());
            List<SysAgentVo> sysAgents = new ArrayList<>();

            Map<Long, Map<Integer, SysAgentVo>> map = new HashMap();
            convertData(1l, gameId, map);
            do {
                sysAgents = getGoldSubSyAgent(strs, (String) params.get("startDate"), (String) params.get("endDate"));
                strs = sysAgents.stream().map(SysAgent::getGameId).collect(Collectors.toList());
                sysAgentsRe.addAll(sysAgents);
                //System.out.println(sub + "级代理" + sysAgents.toString());
                convertData(sub, sysAgents, map);
                sub++;
            } while (sysAgents.size() > 0 && strs.size() > 0 && sub <= 20000);
            if (sysAgentsRe.isEmpty()) {
                return null;
            }
            GoldcalculatedAmount(map);
        }

        //移除玩家
        filtSysAgentsRe(sysAgentsRe);
        //组装pageinfo对象
        return assemblePage(page, size, sysAgentsRe);

    }


    private List<SysAgentVo> getGoldSubSyAgent(List<Integer> params, String startDate, String endDate) {
        List<SysAgentVo> vos = sysAgentMapper.queryGoldSubclassAll(params, startDate, endDate);
        List<SysAgentVo> vosAll = sysAgentMapper.queryGoldSubclass(params);
        Map<Long, SysAgentVo> agentVoMap = new HashMap<>();

        for (SysAgentVo vo : vosAll) {
            agentVoMap.put(vo.getId(), vo);
        }
        for (SysAgentVo vo : vos) {
            agentVoMap.put(vo.getId(), vo);
        }

        return agentVoMap.values().stream().collect(Collectors.toList());
    }

    /**
     * 组装path 为总金币赋值
     *
     * @param map
     */
    public void GoldcalculatedAmount(Map<Long, Map<Integer, SysAgentVo>> map) {
        SystemInfo systemInfo = redisUtil.getSystemInfo();

        BigDecimal sumBig = BigDecimal.ZERO;
        if (map != null) {
            for (Long key : map.keySet()) {
                Map<Integer, SysAgentVo> sysAgentVos = map.get(key);
                if (sysAgentVos != null) {
                    for (Integer str : sysAgentVos.keySet()) {
                        SysAgentVo sysAgentVo = sysAgentVos.get(str);
                        sysAgentVo.setLeve(key); // 代理等级
                        //System.out.println(key + "级代理" + sysAgentVo.toString());
                        if (!sysAgentVo.getAuthorized()) {//只计算玩家的充值
                            setGoldPath(key, sysAgentVo, map); // path 路径获取
                        }
                    }
                }
            }
        }
        if (map != null) {
            for (Long key : map.keySet()) {
                Map<Integer, SysAgentVo> sysAgentVos = map.get(key);
                if (sysAgentVos != null) {
                    for (Integer str : sysAgentVos.keySet()) {
                        SysAgentVo sysAgentVo = sysAgentVos.get(str);
                        if (sysAgentVo == null) {
                            continue;
                        }
                        setGoldSumAmount(key, sysAgentVo, map); // 金额计算
                        //计算总金额

                       /* if (!sysAgentVo.getAuthorized()) {
                            sumBig = sumBig.add(sysAgentVo.getSumAmount() == null ? BigDecimal.ZERO : sysAgentVo.getSumAmount());
                        }*/
                        //sysAgentVo.setSums(sumBig.doubleValue());
                    }
                }
            }
        }

        //return sumBig;
    }

    /**
     * 获取金币路径
     */
    public void setGoldPath(Long size, SysAgentVo vo, Map<Long, Map<Integer, SysAgentVo>> map) {
        String path = new String("");
        // String superAccountpath = "";
        Integer getSuperAgentGameId = vo.getSuperAgentGameId();
        // signFor:for(Long key:map.keySet()){
        // 往后退 2级代理只查询 1级 3级查询 2 1
        for (int i = size.intValue(); i > 0; i--) {
            if (size != 1) { // 不是一级代理
                int upperLevel = i - 1;
                Map<Integer, SysAgentVo> sysAgentVos = map.get(Long.valueOf(upperLevel));
                if (sysAgentVos == null) {
                    continue;
                }
                SysAgentVo sysAgentVo = sysAgentVos.get(getSuperAgentGameId);
                if (upperLevel > 1) {
                    if (sysAgentVo == null) {
                        continue;
                    }
                    if (StringUtils.isEmpty(path)) {
                        path = path + (sysAgentVo.getGameId());
                    } else {
                        path = path + (",") + (sysAgentVo.getGameId());
                    }
                    getSuperAgentGameId = sysAgentVo.getSuperAgentGameId();

                    if (StringUtils.isEmpty(path)) {
                        break;
                    }
                } else if (upperLevel == 1) {
                    if (StringUtils.isEmpty(path)) {
                        path = sysAgentVo.getSuperAgentGameId() + "," + sysAgentVo.getGameId();
                    } else {
                        path = sysAgentVo.getSuperAgentGameId() + "," + sysAgentVo.getGameId() + "," + path;
                    }
                }

            } else {
                path = getSuperAgentGameId.toString();

            }
        }

        vo.setPath(path);

        //System.out.println("账号:" + vo.getGameId() + "path路径为:" + vo.getPath());

        // }
    }

    /**
     * 计算金币消耗
     */
    public void setGoldSumAmount(Long size, SysAgentVo vo, Map<Long, Map<Integer, SysAgentVo>> map) {
        //查询比例
        List<GoldInfo> list = goldInfoMapper.get(new HashMap<String, Object>() {{
            put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
        }});
        //一级比例
        BigDecimal t1=null;
        if(list.size() != 0){
            t1=list.get(0).getT1_commission();
        }

        BigDecimal sumAmount = vo.getSystemcost()== null ? BigDecimal.ZERO : vo.getSystemcost(); // 自己的金额
        BigDecimal priceSum = BigDecimal.ZERO;
        BigDecimal strSum = BigDecimal.ZERO;
        BigDecimal oneselfSum = BigDecimal.ZERO;
        BigDecimal t1PriceSum = BigDecimal.ZERO;
        //BigDecimal  t2PriceSum=BigDecimal.ZERO ;
        //BigDecimal  t3PriceSum=BigDecimal.ZERO ;
        for (int i = size.intValue(); i < size.intValue() + 1; i++) {

            //System.out.println(vo.getGameId() +"计算几级代理："+(i + 1));
            Map<Integer, SysAgentVo> vomap = map.get(Long.valueOf((i + 1)));

            if (vomap == null) {
                break;
            }

            for (Integer str : vomap.keySet()) {
                SysAgentVo sysvo = vomap.get(str);
                if (StringUtils.isEmpty(sysvo.getPath()) || StringUtils.isEmpty(sysvo)) {
                    continue;
                }
                String[] pathStrs = sysvo.getPath().split(",");

                List<String> pathList = Arrays.asList(pathStrs);

                if (pathList.contains(vo.getGameId() + "")) {
                    priceSum = priceSum.add(
                            sysvo.getSystemcost() == null ? BigDecimal.ZERO : sysvo.getSystemcost());
                    sumAmount = sumAmount.add(
                            sysvo.getSystemcost() == null ? BigDecimal.ZERO : sysvo.getSystemcost());
                }

            }

            oneselfSum = vo.getSystemcost() == null ? BigDecimal.ZERO : vo.getSystemcost();
//            System.out.println(vo.getGameId() +"自己："+oneselfSum);
            BigDecimal oneselfSum1 = vo.getSystemcost() == null ? BigDecimal.ZERO : vo.getSystemcost();
            BigDecimal oneselfSums = oneselfSum.multiply(t1);
//            System.out.println(vo.getGameId()+"自己结算后："+oneselfSums);
            if (i == size.intValue()) {
                BigDecimal t1PriceSum1 = priceSum;
//                System.out.println(vo.getGameId() +"T1："+t1PriceSum1);
                t1PriceSum = priceSum.multiply(t1);
//                System.out.println(vo.getGameId() +"T1结算后："+t1PriceSum);
            } else if (i == size.intValue() + 1) {
                //BigDecimal t2PriceSum2=priceSum;
                //System.out.println(vo.getGameId() +"T2："+t2PriceSum);
                //t2PriceSum= priceSum.multiply(t2);
                //System.out.println(vo.getGameId() +"T2结算后："+t2PriceSum);
            } else {
                //BigDecimal t3PriceSum3=priceSum;
                //System.out.println(vo.getGameId() +"T3："+t3PriceSum);
                //t3PriceSum= priceSum.multiply(t3);
                //System.out.println(vo.getGameId() +"T3结算后："+t3PriceSum);
            }

            priceSum = BigDecimal.ZERO;
            strSum = oneselfSums.add(t1PriceSum);
            //.add(t2PriceSum).add(t3PriceSum);
            //System.out.println(strSum+"==================");

        }
        vo.setScaleMoney(strSum.doubleValue());
        vo.setSumAmount(sumAmount);
        // if(sumAmount.compareTo(BigDecimal.ZERO)>0)
        //   System.out.println("账号:" + vo.getGameId() + "总金额为:" + sumAmount);

    }


    //end 金币消耗



    /**
     * 修改代理邀请码
     * <p>1. 查询代理、上级代理是否存在</p>
     * <p>2. 修改邀请码</p>
     * <p>3. 更新招募人数</p>
     * <p>4. 修改用户资源</p>
     *
     * @param id                   代理id
     * @param destSuperAgentGameId 待修改的上级邀请码
     */
    @Transactional
    private void changeSuperAgentGameId(Long id, Integer destSuperAgentGameId) {
        SysAgent sysAgent =
                super.getSingleSysAgent(
                        new HashMap<String, Object>() {{
                            put(FieldConstant.DBFieldConstant.F_ID.name(), id);
                        }}
                );
        SysAgent destSuperAgent =
                super.getSingleSysAgent(
                        new HashMap<String, Object>() {{
                            put(FieldConstant.DBFieldConstant.F_GAME_ID.name(), destSuperAgentGameId);
                        }}
                );
        Assert.isTrue(!sysAgent.getGameId().equals(destSuperAgentGameId), TipMsgConstant.SYS_AGENT_INFO_INVALID);

        Integer srcSuperAgentGameId = sysAgent.getSuperAgentGameId();
        if (destSuperAgentGameId.equals(srcSuperAgentGameId)) return;
        Assert.notNull(srcSuperAgentGameId, TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
        SysAgent srcSuperAgent =
                super.getSingleSysAgent(
                        new HashMap<String, Object>() {{
                            put(FieldConstant.DBFieldConstant.F_GAME_ID.name(), srcSuperAgentGameId);
                        }}
                );

        // 修改邀请码
        int i =
                getMapper().update(
                        new SysAgent.Builder()
                                .superAgentGameId(destSuperAgentGameId)
                                .id(id)
                                .build()
                );
        int j =
                accountsInfoMapper.update(
                        new AccountsInfo.Builder()
                                .gameId(sysAgent.getGameId())
                                .playingGame(destSuperAgentGameId)
                                .build()
                );

        // 更新招募人数
        int m =
                getMapper().update(
                        new SysAgent.Builder()
                                .recruitNumInt(SysConstant.SysFlagConstant.MINUS)
                                ._gameId(srcSuperAgentGameId)
                                .build()
                );
        int n =
                getMapper().update(
                        new SysAgent.Builder()
                                .recruitNumInt(SysConstant.SysFlagConstant.ENABLE)
                                ._gameId(destSuperAgentGameId)
                                .build()
                );

        // 修改用户资源
        int k = sysResourceMapper.updateMul(
                new SysResource.Builder().userId(srcSuperAgent.getId()).resourceId(sysAgent.getId()).build(),
                new SysResource.Builder().userId(destSuperAgent.getId()).sysFlag(true).build()
        );

        Assert.isTrue(i > 0 && j > 0 && m > 0 && n > 0 && k > 0, TipMsgConstant.OPERATION_FAILED);
        LOGGER.info("{} 修改上级邀请码: 将 {} 的 {} 修改为 {}", UserDetailsUtil.getGameId(), sysAgent.getGameId(), srcSuperAgentGameId, destSuperAgentGameId);
    }

    /**
     * 赠送靓号，刷新代理信息
     *
     * @param srcGameId  源gameId
     * @param destGameId 赠送的gameId
     */
    @Transactional
    public void refresh(Integer srcGameId, Integer destGameId) {
        Assert.isTrue(!srcGameId.equals(destGameId), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
        SysAgent sysAgent =
                super.getSingleSysAgent(
                        new HashMap<String, Object>() {{
                            put(FieldConstant.DBFieldConstant.F_GAME_ID.name(), srcGameId);
                        }}
                );
        if (destGameId.equals(sysAgent.getSuperAgentGameId())) return;
        Assert.notEmpty(accountsInfoMapper.get(new HashMap<String, Object>() {{
            put(FieldConstant.DBFieldConstant.UserID.name(), sysAgent.getUserId());
            put(FieldConstant.DBFieldConstant.GameID.name(), destGameId);
        }}), TipMsgConstant.NOT_GIVING_UP);

        int i =
                getMapper().update(
                        new SysAgent.Builder()
                                .id(sysAgent.getId())
                                .gameId(destGameId)
                                .build()
                );
        getMapper().update(
                new SysAgent.Builder()
                        .superAgentGameId(destGameId)
                        ._superAgentGameId(srcGameId)
                        .build()
        );
        accountsInfoMapper.update(new AccountsInfo.Builder().playingGame(destGameId)._playingGame(srcGameId).build());
        payOrderMapper.update(new PayOrder.Builder()._gameId(srcGameId).gameId(destGameId).build());
        payOrderPerdayStatisticMapper.update(new PayOrderPerdayStatistic.Builder()._gameId(srcGameId).gameId(destGameId).build());

        String saltKey = UserDetailsUtil.getRedisKey_Salt(srcGameId.toString());
        if (stringRedisTemplate.hasKey(saltKey))
            stringRedisTemplate.delete(saltKey);

        Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
        LOGGER.info("{} 赠送靓号后，刷新代理信息: {} -> {}", UserDetailsUtil.getGameId(), srcGameId, destGameId);
    }

    @Override
    public void run(String... strings) throws Exception {
        // 重置代理公告显示
        if (propertiesMapper.getResetShowAnnounce()) {
            this.sysAgentMapper.resetShowAnnounce();
            LOGGER.info("重置代理公告显示...");
        }

        if (propertiesMapper.getResetIsFinishInfo()) {
            this.sysAgentMapper.resetIsFinishInfo();
        }

    }
}
