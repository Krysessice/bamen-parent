package com.bamenyouxi.room_card_mode.config.DBConfig;

import com.bamenyouxi.room_card_mode.constant.DBConstant;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 邀请码数据源配置
 * Created by 13477 on 2017/8/16.
 */
@Configuration
@MapperScan(basePackages = DBConstant.RoomCardMode.MAPPER_PACKAGE, sqlSessionFactoryRef = DBConstant.RoomCardMode.FACTORY_NAME)
class RoomCardModeConfig {

	@Primary
	@Bean(name = DBConstant.RoomCardMode.DATASOURCE_NAME)
	@ConfigurationProperties(prefix = DBConstant.RoomCardMode.PREFIX)
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = DBConstant.RoomCardMode.FACTORY_NAME)
	SqlSessionFactory sqlSessionFactory(@Qualifier(value = DBConstant.RoomCardMode.DATASOURCE_NAME) DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DBConstant.RoomCardMode.XML_PATH));
		bean.setTypeAliasesPackage(DBConstant.RoomCardMode.MODEL_PACKAGE);

		// 分页插件
		bean.setPlugins(new Interceptor[]{ new PageHelper() });

		return bean.getObject();
	}

	@Primary
	@Bean(name = DBConstant.RoomCardMode.TEMPLATE_NAME)
	SqlSessionTemplate sqlSessionTemplate(@Qualifier(DBConstant.RoomCardMode.FACTORY_NAME) SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Primary
	@Bean(name = DBConstant.RoomCardMode.TRANSACTION_MANAGER_NAME)
	DataSourceTransactionManager mysqlKaixinTransactionManager(@Qualifier(DBConstant.RoomCardMode.DATASOURCE_NAME) DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
