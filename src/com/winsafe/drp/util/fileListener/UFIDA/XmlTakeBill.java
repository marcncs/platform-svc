package com.winsafe.drp.util.fileListener.UFIDA;


import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.TakeBill;



public class XmlTakeBill {

	
	//用友处给出的编号
	private String nccode;
	//订购出库单据的对象
	private TakeBill takeBill;
	//订购出库的货物DAO
	private AppTakeBill appTakeBill;
	//交易类型
	private String opt_type;
	
}
