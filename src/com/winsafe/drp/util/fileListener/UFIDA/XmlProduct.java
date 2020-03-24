package com.winsafe.drp.util.fileListener.UFIDA;

import org.w3c.dom.NodeList;


import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.Users;


public class XmlProduct {
	//用友处给出的编号
	private String nccode;
	//产成品入库单据的对象
	private ProductIncome productIncome;
	//保存entry的node的集合
	private NodeList nodeList;
	//产成品入库的单据DAO
	private AppProductIncome appProductIncome;
	//产成品入库的货物DAO
	private AppProductIncomeDetail appProductIncomeDetail;
	//交易类型
	private String opt_type;
	
	private Users users;

	public XmlProduct(String nccode, ProductIncome productIncome,
			NodeList nodeList,  AppProductIncome appProductIncome,
			AppProductIncomeDetail appProductIncomeDetail, String opt_type,Users users) {
		this.nccode = nccode;
		this.productIncome = productIncome;
		this.nodeList = nodeList;
		this.appProductIncome = appProductIncome;
		this.appProductIncomeDetail = appProductIncomeDetail;
		this.opt_type = opt_type;
		this.users=users;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public ProductIncome getProductIncome() {
		return productIncome;
	}

	public void setProductIncome(ProductIncome productIncome) {
		this.productIncome = productIncome;
	}

	public NodeList getNodeList() {
		return nodeList;
	}

	public void setNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	public AppProductIncome getAppProductIncome() {
		return appProductIncome;
	}

	public void setAppProductIncome(AppProductIncome appProductIncome) {
		this.appProductIncome = appProductIncome;
	}

	public AppProductIncomeDetail getAppProductIncomeDetail() {
		return appProductIncomeDetail;
	}

	public void setAppProductIncomeDetail(
			AppProductIncomeDetail appProductIncomeDetail) {
		this.appProductIncomeDetail = appProductIncomeDetail;
	}

	public String getOpt_type() {
		return opt_type;
	}

	public void setOpt_type(String opt_type) {
		this.opt_type = opt_type;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
}
