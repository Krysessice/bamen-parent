package com.bamenyouxi.room_card_mode.config.DBConfig;

import com.bamenyouxi.room_card_mode.constant.DBConstant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * sqlserver 游戏用户数据源
 * Created by 13477 on 2017/8/16.
 */
//@Configuration
@MapperScan(basePackages = DBConstant.QPPlatformDB.MAPPER_PACKAGE, sqlSessionFactoryRef = DBConstant.QPPlatformDB.FACTORY_NAME)
class QPPlatformDBConfig {

	@Bean(name = DBConstant.QPPlatformDB.DATASOURCE_NAME)
	@ConfigurationProperties(prefix = DBConstant.QPPlatformDB.PREFIX)
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = DBConstant.QPPlatformDB.FACTORY_NAME)
	SqlSessionFactory sqlSessionFactory(@Qualifier(value = DBConstant.QPPlatformDB.DATASOURCE_NAME) DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DBConstant.QPPlatformDB.XML_PATH));
		bean.setTypeAliasesPackage(DBConstant.QPPlatformDB.MODEL_PACKAGE);
		return bean.getObject();
	}

	@Bean(name = DBConstant.QPPlatformDB.TEMPLATE_NAME)
	SqlSessionTemplate sqlSessionTemplate(@Qualifier(DBConstant.QPPlatformDB.FACTORY_NAME) SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = DBConstant.QPPlatformDB.TRANSACTION_MANAGER_NAME)
	DataSourceTransactionManager mysqlKaixinTransactionManager(@Qualifier(DBConstant.QPPlatformDB.DATASOURCE_NAME) DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
