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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 邀请码数据源配置
 * Created by 13477 on 2017/8/16.
 */
@Configuration
@MapperScan(basePackages = DBConstant.InviteCodeMode.MAPPER_PACKAGE, sqlSessionFactoryRef = DBConstant.InviteCodeMode.FACTORY_NAME)
class InviteCodeModeConfig {

	@Primary
	@Bean(name = DBConstant.InviteCodeMode.DATASOURCE_NAME)
	@ConfigurationProperties(prefix = DBConstant.InviteCodeMode.PREFIX)
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = DBConstant.InviteCodeMode.FACTORY_NAME)
	SqlSessionFactory sqlSessionFactory(@Qualifier(value = DBConstant.InviteCodeMode.DATASOURCE_NAME) DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DBConstant.InviteCodeMode.XML_PATH));
		bean.setTypeAliasesPackage(DBConstant.InviteCodeMode.MODEL_PACKAGE);

		// 分页插件
		bean.setPlugins(new Interceptor[]{ new PageHelper() });

		return bean.getObject();
	}

	@Primary
	@Bean(name = DBConstant.InviteCodeMode.TEMPLATE_NAME)
	SqlSessionTemplate sqlSessionTemplate(@Qualifier(DBConstant.InviteCodeMode.FACTORY_NAME) SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Primary
	@Bean(name = DBConstant.InviteCodeMode.TRANSACTION_MANAGER_NAME)
	DataSourceTransactionManager mysqlKaixinTransactionManager(@Qualifier(DBConstant.InviteCodeMode.DATASOURCE_NAME) DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
