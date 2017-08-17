package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * mapper for table: t_sys_resource, model: {@link SysResource}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface SysResourceMapper extends CrudMapper<SysResource, Long> {

	int updateMul(@Param("src") SysResource src, @Param("dest") SysResource dest);
}
