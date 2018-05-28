package com.bamenyouxi.cow_nn_mode;

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
 * Created by 13477 on 2018/4/10.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class CowNnModeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CowNnModeApplication.class, args);
	}
}
