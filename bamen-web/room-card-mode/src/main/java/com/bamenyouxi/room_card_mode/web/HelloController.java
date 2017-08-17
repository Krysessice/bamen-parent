package com.bamenyouxi.room_card_mode.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 * Created by 13477 on 2017/8/17.
 */
@RestController
public class HelloController {

	@GetMapping("/")
	private String hello() {
		return "hello world!";
	}
}
