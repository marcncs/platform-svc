package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMsgTemplate {

	
	public List searchMsgTemplate(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "from MsgTemplate  "
				+ pWhereClause + " order by id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addMsgTemplate(MsgTemplate mt) throws Exception{		
		EntityManager.save(mt);		
	}
	
	public void updMsgTemplate(MsgTemplate mt) throws Exception{		
		EntityManager.update(mt);		
	}
	
	
	public void delMsgTemplate(int id) throws Exception{		
		String sql="delete from Msg_Template where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public MsgTemplate getMsgTemplateById(int id) throws Exception{
		String sql="from MsgTemplate where id="+id;
		return (MsgTemplate)EntityManager.find(sql);
	}
	
	
	
	public String getMemberMsg(String name) throws Exception{
		MsgTemplate mt = getMsgTemplateById(1);
		return mt.getTemplatecontent().replace("name", name);
	}
	
	
	public String getSaleOrderMsg(String name, String product, String money) throws Exception{
		String sb = new String();
		MsgTemplate mt = getMsgTemplateById(2);
		sb = mt.getTemplatecontent().replace("name", name);
		sb = sb.replace("product", product);
		sb = sb.replace("money", money);
		return sb;
	}
	
	
	public String getIntegralMsg(String name, double integral) throws Exception{
		String sb = new String();
		MsgTemplate mt = getMsgTemplateById(3);
		sb = mt.getTemplatecontent().replace("name", name);
		sb = sb.replace("integral", ""+integral);
		return sb;
	}
	
	
	public String searchSaleOrderMsg(String name, String billno, String product, double money) throws Exception{
		String sb = new String();
		MsgTemplate mt = getMsgTemplateById(4);
		sb = mt.getTemplatecontent().replace("name", name);
		sb = sb.replace("billno", billno);
		sb = sb.replace("product", product);		
		sb = sb.replace("money", ""+money);
		return sb;
	}
	
	
	public String getRateMsg(String name, String rate) throws Exception{
		String sb = new String();
		MsgTemplate mt = getMsgTemplateById(5);
		sb = mt.getTemplatecontent().replace("name", name);
		sb = sb.replace("rate", ""+rate);
		return sb;
	}
	
	public String getMoneyMsg(String name, double money) throws Exception{
		String sb = new String();
		MsgTemplate mt = getMsgTemplateById(6);
		sb = mt.getTemplatecontent().replace("name", name);
		sb = sb.replace("money", ""+money);
		return sb;
	}
	
}
