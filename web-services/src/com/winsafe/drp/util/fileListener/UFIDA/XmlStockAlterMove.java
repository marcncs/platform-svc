package com.winsafe.drp.util.fileListener.UFIDA;

import org.w3c.dom.NodeList;

import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.Users;





public class XmlStockAlterMove {
	//用友处给出的编号
	private String nccode;
	//调拨入库单据的对象
	private StockAlterMove stockAlterMove;
	//保存entry的node的集合
	private NodeList nodeList;
	//调拨入库的单据DAO
	private AppStockAlterMove appStockAlterMove;
	//调拨入库的货物DAO
	private AppStockAlterMoveDetail appStockAlterMoveDetail;
	//交易类型
	private String opt_type;
	
	private Users users;

	public  XmlStockAlterMove(String nccode,  StockAlterMove stockAlterMove,
			NodeList nodeList, AppStockAlterMove appStockAlterMove,
			AppStockAlterMoveDetail appStockAlterMoveDetail, String opt_type,Users users) {
		this.nccode = nccode;
		this.stockAlterMove = stockAlterMove;
		this.nodeList = nodeList;
		this.appStockAlterMove = appStockAlterMove;
		this.appStockAlterMoveDetail = appStockAlterMoveDetail;
		this.opt_type = opt_type;
		this.users=users;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public StockAlterMove getStockAlterMove() {
		return stockAlterMove;
	}

	public void setStockAlterMove(StockAlterMove stockAlterMove) {
		this.stockAlterMove = stockAlterMove;
	}

	public NodeList getNodeList() {
		return nodeList;
	}

	public void setNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	public AppStockAlterMove getAppStockAlterMove() {
		return appStockAlterMove;
	}

	public void setAppStockAlterMove(AppStockAlterMove appStockAlterMove) {
		this.appStockAlterMove = appStockAlterMove;
	}

	public AppStockAlterMoveDetail getAppStockAlterMoveDetail() {
		return appStockAlterMoveDetail;
	}

	public void setAppStockAlterMoveDetail(
			AppStockAlterMoveDetail appStockAlterMoveDetail) {
		this.appStockAlterMoveDetail = appStockAlterMoveDetail;
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
