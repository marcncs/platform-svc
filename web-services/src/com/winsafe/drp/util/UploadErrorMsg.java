package com.winsafe.drp.util;

import org.apache.log4j.Logger;



public class UploadErrorMsg {
	
	private static final Logger logger = Logger.getLogger(UploadErrorMsg.class);
	/**
	 * 错误信息
	 */
	public static final String E00001 = "E00001: 单据 [ %s ] 不存在";
	public static final String E00002 = "E00002: 单据 [ %s ] 已复核";
	public static final String E00003 = "E00003: 条码 [ %s ] 不在系统中";
	public static final String E00004 = "E00004: 条码 [ %s ] 不可用";
	public static final String E00005 = "E00005: 条码 [ %s ] 上传成功,但是货物所属权不归当前机构所有";
	public static final String E00006 = "E00006: 条码 [ %s ] 已存在当前单据中";
	public static final String E00007 = "E00007: 单据 [ %s ] 已作废";
	public static final String E00008 = "E00008: 条码 [ %s ] 不符合规则";
	public static final String E00009 = "E00009: 单据 [ %s ] ,产品 [ %s ] 不存在";
	public static final String E00010 = "E00010: 扫描类型不正确";
	public static final String E00011 = "E00011: 条码 [ %s ] 与  产品 [ %s ] 不对应";
	public static final String E00012 = "E00012: 获取属性 [ %s ] 失败";
	public static final String E00013 = "E00013: [ %s ] 不符合规则";
	public static final String E00014 = "E00014: 扫描标识位不正确";
	public static final String E00015 = "E00015: 条码 [ %s ] 与产品不符";
	public static final String E00016 = "E00016: 条码 数量超过产品数量";
	public static final String E00017 = "E00017: 条码 [ %s ] 上传成功,但是退货机构未选择正确";
	  
	
	public static final String E00101 = "E00101: 出库仓库 [ %s ]不存在";
	public static final String E00102 = "E00102: 用户  [%s] 对出库仓库  [%s] 无管辖权限";
	public static final String E00103 = "E00103: 入库仓库 [ %s ] 不存在";
	public static final String E00104 = "E00104: 用户 [ %s ] 对入库仓库 [ %s ] 没有业务往来权限";
	public static final String E00105 = "E00105: 扫描类型错误";
	public static final String E00106 = "E00106: 当前文件中条码 [ %s ] 重复扫描";
	public static final String E00107 = "E00107: 条码 [ %s ] 格式不正确";
	public static final String E00108 = "E00108: 条码 [ %s ] 与产品不符";
	public static final String E00109 = "E00109: 条码 [ %s ] 从[ %s ]发出时被积分,不能退货到[ %s ]"; 
	public static final String E00110 = "E00110: 条码 [ %s ] 从[ %s ]发货到[ %s ]时被积分,不能从[ %s ]退货"; 
	public static final String E00111 = "E00111: 条码 [ %s ] 曾经发货给[ %s ],本次属于重复发货,不能累积积分"; 
	public static final String E00112 = "E00112: 条码 [ %s ] 上传成功"; 
	public static final String E00113 = "E00113: 条码 [ %s ] 在[ %s ]已由[ %s ]退回"; 
	
	public static final String E00201 = "E00201: 内部编号为 %s 的机构不存在或已被撤销"; 
	public static final String E00202 = "E00202: 内部编号为 %s 的机构不存在可用仓库";
	public static final String E00203 = "E00203: 内部编号为 %s 的机构或仓库均不存在";
	public static final String E00204 = "E00204: 物料号为 %s 的产品不存在或不可用";
	public static final String E00205 = "E00205: 物料号为 %s 的产品 %s 到最小包装的比例关系未设置";
	public static final String E00206 = "E00206: %s数据不全";
	public static final String E00207 = "E00207: 箱码为空";
	public static final String E00208 = "E00208: %s 格式不正确,可处理格式为yyyyMMdd,MM-dd-yyyy,yyyy-MM-dd,MM/dd/yyyy或者yyyy/MM/dd";
	public static final String E00209 = "E00209: 箱码 %s 不可用";
	public static final String E00210 = "E00210: 解析错误：文件类型不正确，请确认文件类型是否与工厂类型匹配，%s 文件应该包含[ %s ]个字段";
	public static final String E00211 = "E00211: 未从文件中读取到相关数据";
	public static final String E00212 = "E00212: 内部编号为 %s 的发货单不存在";
	public static final String E00213 = "E00213: 数据错误, SoldToPartyCode和ShipToPartyCode字段均为空";
	public static final String E00214 = "E00214: 未找到文件: %s";
	public static final String E00215 = "E00215: 读取文件时发生异常 %s";
	public static final String E00216 = "E00216: %s格式不正确,应该为整数";
	public static final String E00217 = "E00217: %s格式不正确";
	public static final String E00218 = "E00218: 处理时系统发生异常 %s";
	public static final String E00219 = "E00219: 系统中已存在SAP单号为 %s 的发货单";
	public static final String E00220 = "E00220: 系统中已存在从 %s %s 到 %s %s ,条码为 %s 单号为 %s 的发货单";
	public static final String E00221 = "E00221: 条码 %s 当前正在使用中，不可用";
	public static final String E00222 = "E00222: GTIN码长度超过14位";
	public static final String E00223 = "E00223: 内部编号为 %s 的机构不存在";
	public static final String E00224 = "E00224: 内部编号为 %s 的仓库不存在";
	
	public static final String E00301 = "E00301: 条码 [ %s ] 格式不正确";
	public static final String E00302 = "E00302: idcode中找不到箱号[ %s ]记录";
	public static final String E00303 = "E00303: 箱码[%s]重复上传";
	public static final String E00304 = "E00304: 找不到该箱码[%s]对应的批号记录";
	public static final String E00305 = "E00305: 与箱码[%s]对应的物料号不一致";
	public static final String E00306 = "E00306: 与箱码[%s]对应的批号不一致";
	public static final String E00307 = "E00307: 该箱码[%s]对应的单据已复核";
	
	public static final String E00308 = "E00308: 条码[%s]对应的产品[%s]不可用或不存在";
	
	public static final String E09001 = "E09001: 条码应为20位长度";
	public static final String E09002 = "E09002: 条码必须以0054052296开头";
	public static final String E09003 = "E09003: 条码已存在系统中";
	public static final String E09004 = "E09004: 未配置该工厂的码前缀";
	public static final String E09005 = "E09005: 非本工厂可使用的码前缀";
	public static final String E09006 = "E00102: 用户  [%s] 上传条码  [%s] 产品 [%s] 成功";
	public static final String E10001 = "E00004: 条码 [ %s ] 曾经发货给 [ %s ]，本次属于重复发货，不能累积积分";
	
	public static final String DATAIL_MSG = "产品名称:%s 包装规格:%s 状态:%s ";
	public static final String STATUS_FAIL = "失败";
	public static final String STATUS_WARN = "警告";
	public static final String STATUS_SUCCESS = "成功";
	
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
