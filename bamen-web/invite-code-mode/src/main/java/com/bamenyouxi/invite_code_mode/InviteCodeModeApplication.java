package com.bamenyouxi.invite_code_mode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * 　     ┏┓　　　┏┓
 *      ┏┛┻━━━━━┛┻┓
 *      ┃　　　　　 ┃ 　
 *      ┃　　 ━　  ┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　 ┃
 *      ┃　　┻　　  ┃
 *      ┃　　　　　 ┃
 *      ┗━┓　　　┏━┛
 *　　    ┃　　　┃
 *　　    ┃　　　┃
 *　　    ┃　　　┗━━━┓
 *　　    ┃　　　　　 ┣┓
 *　　    ┃　　　　　┏┛
 *　　    ┗┓┓┏━┳┓┏┛
 *　　　   ┃┫┫ ┃┫┫
 *　　　  ┗┻┛ ┗┻┛
 * Code is far away from bug with the animal protecting
 *      神兽保佑，代码无BUG！
 *
 * Created by 13477 on 2017/6/19.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class InviteCodeModeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InviteCodeModeApplication.class, args);
	}
}
