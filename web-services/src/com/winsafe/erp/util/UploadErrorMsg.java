package com.winsafe.erp.util;

import org.apache.log4j.Logger;



public class UploadErrorMsg {
	
	private static final Logger logger = Logger.getLogger(UploadErrorMsg.class);
	/**
	 * 错误信息
	 */
	public static final String E10001 = "E10001: 单据 编号为空";
	public static final String E10002 = "E10002: 收货机构为空";
	public static final String E10003 = "E10003: 产品编码为空";
	public static final String E10004 = "E10004: 订单数量为空";
	public static final String E10005 = "E10005: 机构 [%s]不存在名称为 [%s]的下级机构";
	public static final String E10006 = "E10006: 机构 [%s]不存在可用仓库";
	
	public static final String E10007 = "E10007: 未找到产品编码 [%s] 所对应的物料号";
	public static final String E10008 = "E10008: 物料号为 [%s] 的产品不存在或不可用";
	
	public static final String E10009 = "E10009: 数量无法转化为件数";
	
	
	public static final String E10010 = "E10010: [%s]日期格式不正确,支持的格式为[%s]";
	
	
	public static final String E10011 = "E10011: 出货机构名称为空";
	public static final String E10012 = "E10012: 出货机构名称与所选机构不一致";
	public static final String E10013 = "E10013: 出货数量应该为整数（件）";
	public static final String E10014 = "E10014: 发货单出货数量不能为负数";
	
	public static final String E10015 = "E10015: 出货仓库名称与所选仓库不一致";
	public static final String E10016 = "E10016: 出货仓库名称不能为空";
	public static final String E10017 = "E10017: 机构[%s]下已存在ERP单号为[%s]的发货单";
	public static final String E10018 = "E10018: 机构[%s]下已存在ERP单号为[%s]的退货单";
	public static final String E10019 = "E10019: 单据号为[%s]的单据包含多个客户";
	
	
	
	
	
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
