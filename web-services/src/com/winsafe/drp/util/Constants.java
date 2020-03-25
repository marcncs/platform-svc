package com.winsafe.drp.util;


public interface Constants {
	public static final boolean isDebug = false;
	//登陆方式
//	public static final String LOGIN_NORMAL = "normal";
//	public static final String LOGIN_LDAP = "LDAP";
	
	//部署环境
	public static final String ENV_INTERNAL="internal";
	public static final String ENV_EXTERNAL="external";
	
	public static final int PAGE_SIZE = 10;
	/** 缓存时间（毫秒） */
	public static final long CACHE_TIME = 60000;
	
	public static final String MENUS_SEPARATE = "&gt;&gt;";
	
	public static final String MENUS_HTML_SEPARATE = ">>";

	public static final String WAREHOUSE_BIT_DEFAULT = "000";
	public static final String PACKAGE = "com.winsafe.drp";

	// user management related constant
	public static final String USER_MANAGE = PACKAGE + ".util";

	public static final String CHECKIMG_NUMBER = USER_MANAGE + "checkImg";
	public static final String USER = USER_MANAGE + ".user";

	public static final String GENERAL_ICON_SUCCESSFUL = "/images/ok.gif";
	public static final String GENERAL_ICON_FAILURE = "/images/no.gif";

	public static final String COUNT_KEY = "userCounter";

	public static final String USERS_KEY = "userNames";

	public static final String CURRENT_SESSION_USER_KEY = "users";

	public static final String IS_BATCH = "readonly";

	public static final String NO_BATCH = "9999999999";
	//
	public static final String CODE_SUCCESS = "0";
	public static final String CODE_SUCCESS_MSG = "正确";
	public static final String CODE_SUCCESS_MSG_SIZE = "正确,共{count}条";

	public static final String CODE_ERROR= "-2";
	public static final String CODE_ERROR_MSG = "失败";
	
	public static final String CODE_LOGIN_FAIL = "-1";
	public static final String CODE_LOGIN_FAIL_MSG = "用户名或密码错误";
	public static final String CODE_SCANNER_FAIL_MSG = "采集器未注册";
	public static final String CODE_LOGIN_UPDATE = "update";
	
	
	public static final String CODE_NO_DATA = "-2";
	public static final String CODE_NO_DATA_MSG = "无相关的信息";
	public static final String CODE_NO_DATA_MSG_SEC = "暂未获取到相关产品信息，请您检查输入的查询码是否正确。";
	
	public static final String QUERY_ERROR_CODE2 = "-2";
	public static final String QUERY_ERROR_CODE2_DATA_MSG = "不存在该查询码";
	public static final String QUERY_ERROR_CODE3 = "-3";
	public static final String QUERY_ERROR_CODE3_DATA_MSG = "没有权限";
	public static final String QUERY_ERROR_CODE4 = "-4";
	public static final String QUERY_ERROR_CODE4_DATA_MSG = "没有物流信息";
	
	public static final String APPLYWLIDCODE_ERROR_CODE2 = "-2";
	public static final String APPLYWLIDCODE_ERROR_CODE2_DATA_MSG = "该机构已提交申请";

	public static final String CODE_METHOD_ERROR = "-3";
	public static final String CODE_METHOD_ERROR_MSG = "no authority operate this interface";

	public static final String QUERY_MODE_WEB = "1";
	public static final String QUERY_MODE_APP = "2";
	public static final String QUERY_MODE_MOBILE = "3";
	public static final String QUERY_MODE_OFFICEWEB = "4";
	
	public static final int CASE_QUERY_MODE_WEB = 1;
	public static final int CASE_QUERY_MODE_APP = 2;
	public static final int CASE_QUERY_MODE_MOBILE = 3;
	public static final int CASE_QUERY_MODE_OFFICEWEB = 4;
	
	public static final int EXECL_MAXCOUNT = 40000;
	//有单billsort
	public static final Integer[] HAVE_TICKET=  new Integer[]{4,5,16,8,20};
	//无单billsort
	public static final Integer[] NO_TICKET=  new Integer[]{17,18,19,21,23,24};
	
	public static final int CARTON_BEGIN_INDEX = 38;
	
	//小码截取起始位数
	public static final int PRIMARY_BEGIN_INDEX = 40;
	
	//小码位数
	public static final int PRIMARY_CODE_V_I = 10;
	public static final int PRIMARY_CODE_V_II = 13;
	public static final int PRIMARY_CODE_V_III = 50;
	public static final int PRIMARY_CODE_V_IV = 53;
	public static final int PRIMARY_CODE_III_II = 32; 
	//小码后21位
	public static final int PRIMARY_CODE_II_I= 21;
	
	//箱码位数
	public static final int CARTON_CODE_V_I = 20;
	public static final int CARTON_CODE_V_II= 58;
	
	//24位码
	public static final int MPIN_CODE_V_II= 24;
	//12位码
	public static final int COVERT_CODE_V_II= 12;
	//11位暗码   
	public static final int COVERT_CODE_X_I= 11;
	
	
	//产品追溯查询分类
	//1表示查询码不在系统中
	public static final int WLM_QUERYSORT_I = 1;
	//2表示不在范围内查询
	public static final int WLM_QUERYSORT_II = 2;
	//3表示正常查询
	public static final int WLM_QUERYSORT_III = 3;
	
	public static final String[] IDCODE_UPLOAD_PATH = new String[] {
			"/upload/pi/", "/upload/tt/", "/upload/sc/", "/upload/pe/",
			"/upload/om/", "/upload/sm/", "/upload/dm/", "/upload/pc/",
			"/upload/ow/", "/upload/ot/", "/upload/ot/", "/upload/pk/",
			"/upload/py/", "/upload/pt/", "/upload/wd/", "/upload/st/", 
			"/upload/db/","/upload/wdom", "/upload/wdsm", "/upload/wdow",
			"/upload/pw/","/upload/wdpw/" ,"/upload/bi/","/upload/krom/","/upload/krow/"};

	public static final String FAIL_PATH = "/upload/fail/";

	public static final String[] TT_MAIN_TABLE = new String[] { "sale_order",
			"stock_alter_move", "stock_move", "supply_sale_move",
			"draw_shipment_bill", "harm_shipment_bill", "product_interconvert",
			"organ_withdraw", "organ_trades", "organ_trades",
			"purchase_withdraw", "sale_trades", "integral_order",
			"purchase_trades", "sample_bill"};

	public static final String[] TT_DETAIL_TABLE = new String[] {
			"sale_order_detail", "stock_alter_move_detail",
			"stock_move_detail", "supply_sale_move_detail",
			"draw_shipment_bill_detail", "harm_shipment_bill_detail",
			"product_interconvert_detail", "organ_withdraw_detail",
			"organ_trades_detail", "organ_trades_detail",
			"purchase_withdraw_detail", "sale_trades_detail",
			"integral_order_detail", "purchase_trades_detail",
			"sample_bill_detail"};

	public static final String[] TT_IDCODE_TABLE = new String[] {
			"sale_order_idcode", "stock_alter_move_idcode",
			"stock_move_idcode", "supply_sale_move_idcode",
			"draw_shipment_bill_idcode", "harm_shipment_bill_idcode",
			"product_interconvert_idcode", "organ_withdraw_idcode",
			"organ_trades_idcode", "organ_trades_idcode",
			"purchase_withdraw_idcode", "sale_trades_idcode",
			"integral_order_idcode", "purchase_trades_idcode",
			"sample_bill_idcode"};

	public static final String[] TT_MAIN_COLUMN = new String[] { "soid",
			"samid", "smid", "ssmid", "dsid", "hsid", "piid", "owid", "otid",
			"otid", "pwid", "stid", "ioid", "ptid" ,""};

	public static final String[] TT_WASTE_BOOK_MEMO = new String[] { "soid",
			"发货单签收-入库", "转仓签收-入库", "ssmid", "dsid", "hsid", "piid", "渠道退货签收-入库",
			"otid", "otid", "pwid", "stid", "ioid", "ptid",""};

	public static final int TXT_BOXTRANSFROM = 5;

	public static final int TXT_BATCH = 32;

	public static final int TXT_PRODUCTNAME = 32;

	public static final int TXT_DL_BILLNO = 14;

	public static final int TXT_DL_NCCODE2 = 20;
	public static final int TXT_DL_NCCODE = 32;

	public static final int TXT_DL_ORGAN = 10;

	public static final int TXT_DL_ORGANNAME = 60;

	public static final int TXT_DL_PRODUCTID = 20;
	public static final int TXT_DL_UNITID = 2;
	public static final int TXT_DL_ICODE = 4;

	public static final int TXT_DL_PRODUCTNAME = 30;

	public static final int TXT_DL_BATCH = 10;

	public static final int TXT_DL_ISBATCH = 1;

	public static final int TXT_DL_QUANTITY = 20;

	public static final String TXT_FILL_STRING = " ";

	public static final String ORGANID = "00001";

	public static final int BUS_NO = 15;

	public static final int BUS_WAY = 60;

	public static final int BOX_COUNT_IN_STOCK = 3;
	
	public static final int ORGAN_THIRD_RANK = 3;

	/** 散装标识 **/
	public static final String CODEUNIT_Z = "Z";
	/** 托标识 **/
	public static final String CODEUNIT_T = "T";
	/** 箱标识 **/
	public static final String CODEUNIT_B = "B";
	/** 否 **/
	public static final Integer NO = 0;
	/** 是 **/
	public static final Integer YES = 1;
	
	public static final String PS_CODE_1 = "10401";

	/**
	 * 经销商签收入库条码扫描类型
	 */
	public static final String SCAN_TYPE_RI = "6";
	public static final Integer DB_BULK_SIZE = 5000;
	public static final Integer DB_BULK_COUNT = 200000;

	public static final Integer POINT_NUMBER = 16;

	public static final Integer POINT_NUMBER_DATE = 12;

	/** 箱标识 **/
	public static final String CODEUNIT_X = "X";
	/** 盒标识 **/
	public static final String CODEUNIT_H = "H";
	/** 散长度 **/
	public static final Integer CODEUNIT_S_LENGTH = 16;
	//采集器用户验证
	public static final boolean SCANNNER_VALIDATE_USER = true;
	//采集器IMEI验证
	public static final boolean SCANNNER_VALIDATE_IMEI = true;
	//手机用户验证
	public static final boolean PHONE_VALIDATE_USER = true;
	//手机IMEI验证
	public static final boolean PHONE_VALIDATE_IMEI = true;
	//出库条码扫描类型
	public static final String SCAN_TYPE_OM = "4";
	public static final String SCAN_TYPE_OM_NOBILL = "17";
	public static final String SCAN_TYPE_OM_NOBILL_BKD = "23";
	//转仓条码扫描类型
	public static final String SCAN_TYPE_SM = "5";
	public static final String SCAN_TYPE_SM_NOBILL = "18";
	//调拨
	public static final String SCAN_TYPE_DB = "16";
	//退货条码扫描类型
	public static final String SCAN_TYPE_OW = "8";
	public static final String SCAN_TYPE_OW_NOBILL = "19";
	public static final String SCAN_TYPE_OW_NOBILL_BKR = "24";
	//工厂退回条码扫描类型
	public static final String SCAN_TYPE_PW = "20";
	public static final String SCAN_TYPE_PW_NOBILL = "21";
	public static final String SCAN_TYPE_BI = "99";
	
	public static final String DEFAULT_FILE_ENCODE = "UTF-8";
	//PD默认选择单位
	public static final int DEFAULT_UNIT_ID = 2 ;
	//补足位
	public static final String[] ZERO_PREFIX = new String[] { "",
		"0", "00", "000",
		"0000", "00000", "000000",
		"0000000", "00000000", "000000000",
		"0000000000", "00000000000", "000000000000", "0000000000000", "00000000000000"};
	
	public static final String CODE_LATEST_VERSION = "-2";
	public static final String CODE_LATEST_VERSION_MSG = "当前已是最新版本";
	public static final String CODE_HAS_UPDATE = "0";
	public static final String CODE_HAS_UPDATE_MSG = "发现新版本V，该版本更新如下：\r\n";
	
	// 管辖仓库权限
	public static final String AUTH_RULE_WAREHOUSE = "warehouse";
	// 管辖机构权限
	public static final String AUTH_RULE_ORGAN = "organ";
	
	// 业务往来仓库权限
	public static final String AUTH_VISIT_WAREHOUSE = "warehouse";
	// 业务往来机构权限
	public static final String AUTH_VISIT_ORGAN  = "organ";
	// 二维码长度(旧箱码)
	public static final int CARTON_OLD_LENGTH = 24;
	
	// 密码有效期 
	public static final int PWD_VAL_DATE = 49;
	
	public static final int CARTON_SEQ_LEN = 9; 
	public static final int PRIMARY_SEQ_LEN = 3;
	public static final int RANDOM_LEN = 8;
	
	//默认检验单位
	public static final String DEFAULT_INSPECT_INST = "拜耳作物科学（中国）有限公司"; 
}
