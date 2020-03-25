package com.winsafe.drp.keyretailer.util;


public class BonusAfficheMsg {
	/**
	 * 积分信息
	 */ 
	public static final String TITLE = "积分信息";
	public static final String ADJUST_TITLE = "积分调整";
	public static final String DELIVERY_OUT = "您发货给%s,获得%s积分,请在\"积分查询\"查看您的积分,积分详情:\r\n%s";
	public static final String DELIVERY_IN = "%s发货给您,获得%s积分,请在\"积分查询\"查看您的积分,积分详情:\r\n%s";
	public static final String RETURN_OUT = "%s退货给您,扣除%s积分,请在\"积分查询\"查看您的积分,积分详情:\r\n%s";
	public static final String RETURN_IN = "您退货给%s,扣除%s积分,请在\"积分查询\"查看您的积分,积分详情:\r\n%s";
	public static final String ADJUST = "%s调整%s积分,发生交易机构[%s,%s],请在\"积分查询\"查看您的积分,积分详情:\r\n%s";
	
	public static final String DELIVERY_OUT_REMOVE = "您发货给%s的%s";
	public static final String DELIVERY_IN_REMOVE = "%s发货给您的%s";
	
	
	public static final String AUDIT_CREATE_TITLE = "审核信息";
	public static final String AUDIT_CREATE= "客户[%s]已创建,需要审核";
	
	public static final String AUDIT_UPDATE_TITLE = "审核信息";
	public static final String AUDIT_UPDATE= "客户[%s]的信息已更新,需要审核";
	
	public static final String AUDIT_RESPONSE_TITLE = "审核信息";
	public static final String AUDIT_RESPONSE= "机构[%s]已审核,%s%s";
	
	public static final String BONUS_CONFIRM_TITLE = "返利确认";
	public static final String BONUS_CONFIRM= "%s:%s年度最终积分已确定,达成目标，请与上级供货商[%s]联系返利事宜，并在收到返利后点击\"返利确认\"按钮，确认返利已收到";
	/**
	 * 格式化字符信息
	 * @param error
	 * @param args
	 * @return
	 */
	public static String getError(String error,Object... args) {
		return String.format(error, args);
	}
}
