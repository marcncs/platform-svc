package com.winsafe.hbm.util;

public interface Constants {

  public static final String PACKAGE = "com.winsafe.drp";

//user management related constant
  public static final String USER_MANAGE = PACKAGE + ".util";


  public static final String CHECKIMG_NUMBER=USER_MANAGE+"checkImg";
  public static final String USER=USER_MANAGE+".user";

  public static final String GENERAL_ICON_SUCCESSFUL = "/images/ok.gif";
  public static final String GENERAL_ICON_FAILURE = "/images/no.gif";

  public static final String COUNT_KEY = "userCounter";

  public static final String USERS_KEY = "userNames";

  public static final String CURRENT_SESSION_USER_KEY = "users";

  public static final String IS_BATCH = "readonly";  

  public static final int EXECL_MAXCOUNT = 40000;
  

  public static final String[] IDCODE_UPLOAD_PATH = new String[]{"/upload/pi/","/upload/tt/","/upload/sc/","/upload/pe/","/upload/om/",
	  "/upload/sm/","/upload/dm/","/upload/pc/","/upload/ow/","/upload/ot/",
	  "/upload/ot/","/upload/pk/","/upload/py/","/upload/pt/","/upload/wd/",
	  "/upload/st/"};
  

  public static final String FAIL_PATH = "/upload/fail/";
  

  public static final String[] TT_MAIN_TABLE = new String[]{"sale_order","stock_alter_move","stock_move","supply_sale_move","draw_shipment_bill","harm_shipment_bill",
	  "product_interconvert","organ_withdraw","organ_trades","organ_trades","purchase_withdraw","sale_trades","integral_order","purchase_trades"};


  public static final String[] TT_DETAIL_TABLE = new String[]{"sale_order_detail","stock_alter_move_detail","stock_move_detail","supply_sale_move_detail","draw_shipment_bill_detail","harm_shipment_bill_detail",
	  "product_interconvert_detail", "organ_withdraw_detail","organ_trades_detail","organ_trades_detail","purchase_withdraw_detail","sale_trades_detail","integral_order_detail","purchase_trades_detail"};
 

  public static final String[] TT_MAIN_COLUMN = new String[]{"soid","samid","smid","ssmid","dsid","hsid",
	  "piid", "owid", "otid", "otid", "pwid", "stid", "ioid", "ptid"};

  public static final String[] ZERO_PREFIX = new String[] { "",
		"0", "00", "000",
		"0000", "00000", "000000",
		"0000000", "00000000", "000000000",
		"0000000000", "00000000000", "000000000000", "0000000000000", "00000000000000"};

  public static final int TXT_DL_BILLNO	= 15;

  public static final int TXT_DL_ORGAN = 10;

  public static final int TXT_DL_ORGANNAME = 50;

  public static final int TXT_DL_PRODUCTID = 20;

  public static final int TXT_DL_PRODUCTNAME = 30;

  public static final int TXT_DL_BATCH = 10;

  public static final int TXT_DL_ISBATCH = 1;

  public static final int TXT_DL_QUANTITY = 5;

  public static final String TXT_FILL_STRING = " ";

  public static final String ORGANID="00001";
}
