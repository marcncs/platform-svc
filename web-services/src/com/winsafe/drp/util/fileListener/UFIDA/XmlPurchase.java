package com.winsafe.drp.util.fileListener.UFIDA;

import org.w3c.dom.NodeList;

import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.Users;

public class XmlPurchase {
	//用友处给出的编号
	private String nccode;
	//采购入库单据的对象
	private PurchaseIncome purchaseIncome;
	//保存entry的node的集合
	private NodeList nodeList;
	//采购入库的单据DAO
	private AppPurchaseIncome appPurchaseIncome;
	//采购入库的货物DAO
	private AppPurchaseIncomeDetail appPurchaseIncomeDetail;
	//交易类型
	private String opt_type;
	
	private Users users;

	public XmlPurchase(String nccode, PurchaseIncome purchaseIncome,
			NodeList nodeList, AppPurchaseIncome appPurchaseIncome,
			AppPurchaseIncomeDetail appPurchaseIncomeDetail, String opt_type,Users users) {
		this.nccode = nccode;
		this.purchaseIncome = purchaseIncome;
		this.nodeList = nodeList;
		this.appPurchaseIncome = appPurchaseIncome;
		this.appPurchaseIncomeDetail = appPurchaseIncomeDetail;
		this.opt_type = opt_type;
		this.users = users;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public PurchaseIncome getPurchaseIncome() {
		return purchaseIncome;
	}

	public void setPurchaseIncome(PurchaseIncome purchaseIncome) {
		this.purchaseIncome = purchaseIncome;
	}

	public NodeList getNodeList() {
		return nodeList;
	}

	public void setNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	public AppPurchaseIncome getAppPurchaseIncome() {
		return appPurchaseIncome;
	}

	public void setAppPurchaseIncome(AppPurchaseIncome appPurchaseIncome) {
		this.appPurchaseIncome = appPurchaseIncome;
	}

	public AppPurchaseIncomeDetail getAppPurchaseIncomeDetail() {
		return appPurchaseIncomeDetail;
	}

	public void setAppPurchaseIncomeDetail(
			AppPurchaseIncomeDetail appPurchaseIncomeDetail) {
		this.appPurchaseIncomeDetail = appPurchaseIncomeDetail;
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
