package com.bamenyouxi.invite_code_mode.test;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * MainTest
 * Created by 13477 on 2017/8/16.
 */
final class MainTest {

	public static void main(String[] args) {
//		System.out.println(SystemInfo.class.getName());
		System.out.println(new Md5PasswordEncoder().encodePassword("123456", "9a2e926a-6c4a-4563-977a-379fea3a2c45").equals("9ac3e6ac7ceba951991487e3df2a0e4c"));
	}
}
