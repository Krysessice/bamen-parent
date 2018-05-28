package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.GoldInfo;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * mapper for table: t_gold_info, model: {@link GoldInfo}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface GoldInfoMapper extends CrudMapper<GoldInfo, Long> {

	@Insert("INSERT INTO t_gold_info VALUES()")
	int systemInfoInit();

	GoldInfo queryInfo();

	int updateInfo(@Param("t1_commission")BigDecimal t1_commission,@Param("t2_commission")BigDecimal t2_commission,@Param("t3_commission")BigDecimal t3_commission);
}
