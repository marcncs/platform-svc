package com.winsafe.drp.util.fileListener.UFIDA;

import org.w3c.dom.NodeList;

import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.Users;



public class XmlStockMove {
	//用友处给出的编号
	private String nccode;
	//调拨入库单据的对象
	private StockMove stockMove;
	//保存entry的node的集合
	private NodeList nodeList;
	//调拨入库的单据DAO
	private AppStockMove appStockMove;
	//调拨入库的货物DAO
	private AppStockMoveDetail appStockMoveDetail;
	//交易类型
	private String opt_type;
	
	private Users users;

	public  XmlStockMove(String nccode,  StockMove stockMove,
			NodeList nodeList, AppStockMove appStockMove,
			AppStockMoveDetail appStockMoveDetail, String opt_type,Users users) {
		this.nccode = nccode;
		this.stockMove = stockMove;
		this.nodeList = nodeList;
		this.appStockMove = appStockMove;
		this.appStockMoveDetail = appStockMoveDetail;
		this.opt_type = opt_type;
		this.users=users;

	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public StockMove getStockMove() {
		return stockMove;
	}

	public void setStockMove(StockMove stockMove) {
		this.stockMove = stockMove;
	}

	public NodeList getNodeList() {
		return nodeList;
	}

	public void setNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	public AppStockMove getAppStockMove() {
		return appStockMove;
	}

	public void setAppStockMove(AppStockMove appStockMove) {
		this.appStockMove = appStockMove;
	}

	public AppStockMoveDetail getAppStockMoveDetail() {
		return appStockMoveDetail;
	}

	public void setAppStockMoveDetail(AppStockMoveDetail appStockMoveDetail) {
		this.appStockMoveDetail = appStockMoveDetail;
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
