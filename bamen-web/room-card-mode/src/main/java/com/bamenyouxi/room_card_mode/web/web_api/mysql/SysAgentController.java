package com.bamenyouxi.room_card_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.util.FileUtil;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.service.mysql.SysAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 后台代理控制层
 * Created by 13477 on 2017/8/26.
 */
@RestController
@RequestMapping("/agent")
final class SysAgentController extends AbstractCrudController<SysAgent, Long> {
	@Autowired
	private SysAgentService sysAgentService;

	@Override
	public AbstractCrudService<SysAgent, Long> getService() {
		return sysAgentService;
	}


	@GetMapping("/file/default/")
	private void  read(HttpServletResponse response) throws Exception {
		FileUtil.read(response, FileConstant.DEFAULT_FILE_PATH);
	}
}
