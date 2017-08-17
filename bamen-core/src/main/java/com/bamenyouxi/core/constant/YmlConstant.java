package com.bamenyouxi.core.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * 配置文件常量
 * Created by 13477 on 2017/6/28.
 */
public final class YmlConstant {
	private static final Logger log = LoggerFactory.getLogger(YmlConstant.class);
	private static final PropertySource<?> PROPERTY_SOURCES;

	/* 读取配置文件 */
	static {
		String path = "application.yml";
		YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
		Resource resource = new ClassPathResource(path, YmlConstant.class.getClassLoader());
		PropertySource<?> propertySources = null;
		try {
			propertySources = yamlPropertySourceLoader.load("application.yml", resource, null);
		} catch (IOException e) {
			log.warn("{} load failed: {}", path, e.getLocalizedMessage());
		}
		PROPERTY_SOURCES = propertySources;
	}

	/**
	 * 环境名
	 */
	/*public static class ProfileName {
		private static final String PREFIX = "custom.profile.name";
		public static final String DEV = String.valueOf(PROPERTY_SOURCES.getProperty(PREFIX + ".dev"));
		public static final String TEST = String.valueOf(PROPERTY_SOURCES.getProperty(PREFIX + ".test"));
		public static final String PROD = String.valueOf(PROPERTY_SOURCES.getProperty(PREFIX + ".prod"));
	}*/
	public static class ProfileName {
		public static final String DEV = "dev";
		public static final String TEST = "test";
		public static final String PROD = "prod";
	}
}
