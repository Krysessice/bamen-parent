package com.bamenyouxi.invite_code_mode.config.DBConfig;

import com.bamenyouxi.invite_code_mode.constant.DBConstant;
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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * sqlserver 游戏用户数据源
 * Created by 13477 on 2017/8/16.
 */
@Configuration
@MapperScan(basePackages = DBConstant.QPRecordDb.MAPPER_PACKAGE, sqlSessionFactoryRef = DBConstant.QPRecordDb.FACTORY_NAME)
class QPRecordDbConfig {

	@Bean(name = DBConstant.QPRecordDb.DATASOURCE_NAME)
	@ConfigurationProperties(prefix = DBConstant.QPRecordDb.PREFIX)
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = DBConstant.QPRecordDb.FACTORY_NAME)
	SqlSessionFactory sqlSessionFactory(@Qualifier(value = DBConstant.QPRecordDb.DATASOURCE_NAME) DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DBConstant.QPRecordDb.XML_PATH));
		bean.setTypeAliasesPackage(DBConstant.QPRecordDb.MODEL_PACKAGE);

		// 分页插件
		bean.setPlugins(new Interceptor[]{ new PageHelper() });

		return bean.getObject();
	}

	@Bean(name = DBConstant.QPRecordDb.TEMPLATE_NAME)
	SqlSessionTemplate sqlSessionTemplate(@Qualifier(DBConstant.QPRecordDb.FACTORY_NAME) SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = DBConstant.QPRecordDb.TRANSACTION_MANAGER_NAME)
	DataSourceTransactionManager mysqlKaixinTransactionManager(@Qualifier(DBConstant.QPRecordDb.DATASOURCE_NAME) DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
