package com.winsafe.drp.util.fileListener.UFIDA;

import org.w3c.dom.NodeList;

import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncome;



public class XmlOther {
	//用友处给出的编号
	private String nccode;
	//其他入库单据的对象
	private OtherIncome otherIncome;
	//保存entry的node的集合
	private NodeList nodeList;
	//其他入库的单据DAO
	private AppOtherIncome appOtherIncome;
	//其他入库的货物DAO
	private AppOtherIncomeDetail appOtherIncomeDetail;
	//交易类型
	private String opt_type;

	public XmlOther(String nccode, OtherIncome otherIncome,
			NodeList nodeList, AppOtherIncome appOtherIncome,
			AppOtherIncomeDetail appOtherIncomeDetail, String opt_type) {
		this.nccode = nccode;
		this.otherIncome = otherIncome;
		this.nodeList = nodeList;
		this.appOtherIncome = appOtherIncome;
		this.appOtherIncomeDetail = appOtherIncomeDetail;
		this.opt_type = opt_type;

	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public OtherIncome getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(OtherIncome otherIncome) {
		this.otherIncome = otherIncome;
	}

	public NodeList getNodeList() {
		return nodeList;
	}

	public void setNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	public AppOtherIncome getAppOtherIncome() {
		return appOtherIncome;
	}

	public void setAppOtherIncome(AppOtherIncome appOtherIncome) {
		this.appOtherIncome = appOtherIncome;
	}

	public AppOtherIncomeDetail getAppOtherIncomeDetail() {
		return appOtherIncomeDetail;
	}

	public void setAppOtherIncomeDetail(AppOtherIncomeDetail appOtherIncomeDetail) {
		this.appOtherIncomeDetail = appOtherIncomeDetail;
	}

	public String getOpt_type() {
		return opt_type;
	}

	public void setOpt_type(String opt_type) {
		this.opt_type = opt_type;
	}
	
	
}
