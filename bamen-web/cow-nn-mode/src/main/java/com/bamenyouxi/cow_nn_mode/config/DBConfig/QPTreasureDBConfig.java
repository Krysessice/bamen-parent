package com.bamenyouxi.cow_nn_mode.config.DBConfig;

import com.bamenyouxi.cow_nn_mode.constant.DBConstant;
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
@MapperScan(basePackages = DBConstant.QPTreasureDB.MAPPER_PACKAGE, sqlSessionFactoryRef = DBConstant.QPTreasureDB.FACTORY_NAME)
class QPTreasureDBConfig {

	@Bean(name = DBConstant.QPTreasureDB.DATASOURCE_NAME)
	@ConfigurationProperties(prefix = DBConstant.QPTreasureDB.PREFIX)
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = DBConstant.QPTreasureDB.FACTORY_NAME)
	SqlSessionFactory sqlSessionFactory(@Qualifier(value = DBConstant.QPTreasureDB.DATASOURCE_NAME) DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DBConstant.QPTreasureDB.XML_PATH));
		bean.setTypeAliasesPackage(DBConstant.QPTreasureDB.MODEL_PACKAGE);
		return bean.getObject();
	}

	@Bean(name = DBConstant.QPTreasureDB.TEMPLATE_NAME)
	SqlSessionTemplate sqlSessionTemplate(@Qualifier(DBConstant.QPTreasureDB.FACTORY_NAME) SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = DBConstant.QPTreasureDB.TRANSACTION_MANAGER_NAME)
	DataSourceTransactionManager mysqlKaixinTransactionManager(@Qualifier(DBConstant.QPTreasureDB.DATASOURCE_NAME) DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
