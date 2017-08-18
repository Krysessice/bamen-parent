package com.bamenyouxi.invite_code_mode.model.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件映射
 * Created by 13477 on 2017/6/28.
 */
@Component
public final class PropertiesMapper {

	@Value("${custom.SysAgent.resetShowAnnounce}")
	private boolean resetShowAnnounce;

	public boolean getResetShowAnnounce() {
		return resetShowAnnounce;
	}


	@Value("${custom.SysAgent.resetIsFinishInfo}")
	private boolean resetIsFinishInfo;

	public boolean getResetIsFinishInfo(){return resetIsFinishInfo;}
}
