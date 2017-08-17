package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.util.UUIDUtil;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderPerdayStatisticMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysResourceMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrderPerdayStatistic;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysResource;
import com.bamenyouxi.invite_code_mode.model.spring.PropertiesMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.service._impl.AbstractSysAgentService;
import com.bamenyouxi.invite_code_mode.util.SysResourceUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.regex.Pattern;

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

	@Override
	public CrudMapper<SysAgent, Long> getMapper() {
		return sysAgentMapper;
	}

	/**
	 * 代理授权
	 * <p>1.检查用户权限</p>
	 * <p>2.授权</p>
	 * <p>3.更新游戏用户信息</p>
	 * @param id	待授权代理id
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
						new HashMap<String, Object>(){{
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

	/**
	 * 密码修改
	 * <p>1.校验密码格式</p>
	 * <p>2.获取redis中盐值</p>
	 * <p>3.验证原密码</p>
	 * <p>4.更新密码</p>
	 * @param oldPwd    原密码
	 * @param newPwd    新密码
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

	/**
	 * 修改用户真实资料与邀请码
	 * @param sysAgent  待修改信息
	 */
	@Transactional
	public void updateRealInfoAndInvitedCode(SysAgent sysAgent) {
		this.changeRealInfo(sysAgent);
		this.changeSuperAgentGameId(sysAgent.getId(), sysAgent.getSuperAgentGameId());
	}

	/**
	 * 代理冻结
	 * @param gameId    游戏id
	 * @param sysFlag   是否冻结的标识
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
	 * @param gameId    游戏id
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
	 * @param sysAgent  代理信息
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
				!(sysAgent.getRealName().isEmpty() || sysAgent.getTel().isEmpty() || sysAgent.getOpeningBank().isEmpty() || sysAgent.getBankAccount().isEmpty())||sysAgent.getProvince().isEmpty()||sysAgent.getCity().isEmpty(),
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
								.Province(sysAgent.getProvince())
								.City(sysAgent.getCity())
								.build()
				);
		Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
	}

	/**
	 * 修改代理邀请码
	 * <p>1. 查询代理、上级代理是否存在</p>
	 * <p>2. 修改邀请码</p>
	 * <p>3. 更新招募人数</p>
	 * <p>4. 修改用户资源</p>
	 * @param id    代理id
	 * @param destSuperAgentGameId  待修改的上级邀请码
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
	 * @param srcGameId     源gameId
	 * @param destGameId    赠送的gameId
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
	}
}
