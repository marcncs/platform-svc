package com.winsafe.drp.metadata;

import org.apache.commons.lang.StringUtils;

public enum EResourceViewsType {
	PRODUCT(100, "product"), //产品 
	PRODUCT_CONTENT(101, "product_content"), //产品图文资源
	PRODUCT_VIDEO(102, "product_video"), //产品视频资源
	PRODUCT_FEEDBACK(103, "product_feedback"), //产品评价资源
	PRODUCT_CASE(104, "product_case"), //产品的各地经验资源
	PRODUCT_CASE_LIST(105, "product_case_list"), //产品的各地经验资源列表
	MANUFACTURER_NEWS(201, "manufacturer_news"), //厂商新闻资源
	MANUFACTURER_SERVICE_TEL(202, "manufacturer_service_tel"), //厂家客服电话
	MANUFACTURER_PRODUCT_LIST(203, "manufacturer_product_list"), //热门产品列表
	PORTAL_NEWS(301, "portal_news"), //平台新闻
	FARM_PRODUCE(401, "farm_article"), //农产品
	SOLUTION(501, "solution") //方案
	;
	
	private int id;
	private String name;
	
	private EResourceViewsType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static EResourceViewsType getByName(String name) {
		if (StringUtils.isBlank(name)) return null;
		
		EResourceViewsType[] types = values();
		for (EResourceViewsType type : types) {
			if (name.equals(type.getName())) {
				return type;
			}
		}
		
		return null;
	}
	
	public static EResourceViewsType getById(int id) {
		EResourceViewsType[] types = values();
		for (EResourceViewsType type : types) {
			if (id == type.getId()) {
				return type;
			}
		}
		
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
