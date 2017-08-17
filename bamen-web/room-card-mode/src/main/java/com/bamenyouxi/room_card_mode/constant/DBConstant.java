package com.bamenyouxi.room_card_mode.constant;

/**
 * 数据源常量
 * Created by 13477 on 2017/8/16.
 */
public final class DBConstant {
	private final static String BASE_PACKAGE = "com.bamenyouxi.room_card_mode";
	private final static String MYSQL_MODEL_BASE_PACKAGE = BASE_PACKAGE + ".model.mysql";
	private final static String SQLSERVER_MODEL_BASE_PACKAGE = BASE_PACKAGE + ".model.sqlserver";
	private final static String MYSQL_MAPPER_BASE_PACKAGE = BASE_PACKAGE + ".mapper.mysql";
	private final static String SQLSERVER_MAPPER_BASE_PACKAGE = BASE_PACKAGE + ".mapper.sqlserver";

	public static class RoomCardMode {
		private final static String PACKAGE = ".room_card_mode";
		public final static String DATASOURCE_NAME = "room_card_mode";

		public final static String MODEL_PACKAGE = MYSQL_MODEL_BASE_PACKAGE + PACKAGE;
		public final static String MAPPER_PACKAGE = MYSQL_MAPPER_BASE_PACKAGE + PACKAGE;

		public final static String XML_PATH = "classpath:/mapper/mysql/room_card_mode/*.xml";
		public final static String PREFIX = "spring.datasource.mysql.roomcardmode";
		public final static String FACTORY_NAME = DATASOURCE_NAME + "_SqlSessionFactory";
		public final static String TEMPLATE_NAME = DATASOURCE_NAME + "_SqlSessionTemplate";
		public final static String TRANSACTION_MANAGER_NAME = DATASOURCE_NAME + "_TransactionManager";
	}

	public static class QPAccountsDB {
		private final static String PACKAGE = ".account";
		public final static String DATASOURCE_NAME = "account";

		public final static String MODEL_PACKAGE = SQLSERVER_MODEL_BASE_PACKAGE + PACKAGE;
		public final static String MAPPER_PACKAGE = SQLSERVER_MAPPER_BASE_PACKAGE + PACKAGE;

		public final static String XML_PATH = "classpath:/mapper/sqlserver/account/*.xml";
		public final static String PREFIX = "spring.datasource.sqlserver.account";
		public final static String FACTORY_NAME = DATASOURCE_NAME + "_SqlSessionFactory";
		public final static String TEMPLATE_NAME = DATASOURCE_NAME + "_SqlSessionTemplate";
		public final static String TRANSACTION_MANAGER_NAME = DATASOURCE_NAME + "_TransactionManager";
	}

	public static class QPPlatformDB {
		private final static String PACKAGE = ".platform";
		public final static String DATASOURCE_NAME = "platform";

		public final static String MODEL_PACKAGE = SQLSERVER_MODEL_BASE_PACKAGE + PACKAGE;
		public final static String MAPPER_PACKAGE = SQLSERVER_MAPPER_BASE_PACKAGE + PACKAGE;

		public final static String XML_PATH = "classpath:/mapper/sqlserver/platform/*.xml";
		public final static String PREFIX = "spring.datasource.sqlserver.platform";
		public final static String FACTORY_NAME = DATASOURCE_NAME + "_SqlSessionFactory";
		public final static String TEMPLATE_NAME = DATASOURCE_NAME + "_SqlSessionTemplate";
		public final static String TRANSACTION_MANAGER_NAME = DATASOURCE_NAME + "_TransactionManager";
	}

	public static class QPTreasureDB {
		private final static String PACKAGE = ".treasure";
		public final static String DATASOURCE_NAME = "treasure";

		public final static String MODEL_PACKAGE = SQLSERVER_MODEL_BASE_PACKAGE + PACKAGE;
		public final static String MAPPER_PACKAGE = SQLSERVER_MAPPER_BASE_PACKAGE + PACKAGE;

		public final static String XML_PATH = "classpath:/mapper/sqlserver/treasure/*.xml";
		public final static String PREFIX = "spring.datasource.sqlserver.treasure";
		public final static String FACTORY_NAME = DATASOURCE_NAME + "_SqlSessionFactory";
		public final static String TEMPLATE_NAME = DATASOURCE_NAME + "_SqlSessionTemplate";
		public final static String TRANSACTION_MANAGER_NAME = DATASOURCE_NAME + "_TransactionManager";
	}

	public enum E_DBConstant {
		INVITE_CODE_MODE(MYSQL_MODEL_BASE_PACKAGE + ".room_card_mode", MYSQL_MAPPER_BASE_PACKAGE + ".room_card_mode");

		E_DBConstant(String modelPackage, String mapperPackage) {
			this.modelPackage = modelPackage;
			this.mapperPackage = mapperPackage;
		}

		private String modelPackage;
		private String mapperPackage;

		public String getModelPackage() {
			return modelPackage;
		}

		public String getMapperPackage() {
			return mapperPackage;
		}
	}
}
