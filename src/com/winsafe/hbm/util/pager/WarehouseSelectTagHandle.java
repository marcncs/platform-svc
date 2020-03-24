/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.pager.WarehouseSelectTagHandle;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.WarehouseService;


public class WarehouseSelectTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(WarehouseSelectTagHandle.class);
	protected String name;
	/** 下拉框是否显示“请选择”*/
	private String p="n";
	private String value;
	private String onchange;
	private String c;
	
	private String HAS_CHOOSE = "y";
	private String NO_CHOOSE = "n";
	
	//2011.8.15 dufazuo--新增仓库类型属性，用仓库类型过滤下拉选择框要显示的仓库信息（注：新增功能不会影响系统原来下拉选择框的使用）
	private String wHType;
	
	private WarehouseService ws = new WarehouseService();
	
	public int doStartTag()throws JspException{
		try{
			outPagination();
		}catch(Exception exc){
			exc.printStackTrace();
		}

		return super.doStartTag();
	}

	protected void outPagination()throws JspException{
		StringBuffer htmlselect = new StringBuffer();
		
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		UsersBean usersBean = (UsersBean) request.getSession().getAttribute("users");
		try{				
			if ( name.equals("") ){
				throw new JspException("<data:warehouse tag the name parameter is null");
			}
			if ( value == null ){
				value = "";
			}else{
				value = value.trim();
			}
			
			List<Warehouse> wlist = new ArrayList();
			if ( c == null ){
				//2011.8.15 dufazuo
				//如果属性wHType不为空，则根据仓库属性
				if(null == wHType || "".equals(wHType))
				{
					wlist = ws.getEnableWarehouseByVisit(usersBean.getUserid(), usersBean.getMakeorganid());
				}
				else
				{
					wlist = ws.getEnableWarehouseByVisit(usersBean.getUserid(), Integer.valueOf(wHType.trim()), usersBean.getMakeorganid());
				}
			}else if ( "v".equals(c) ){	
				if(null == wHType || "".equals(wHType))
				{
					wlist = ws.getEnableWarehouseByVisit(usersBean.getUserid());
				}
				else
				{
					wlist = ws.getEnableWarehouseByVisit(usersBean.getUserid(), Integer.valueOf(wHType.trim()));
				}
			}else if ( "rw".equals(c) ){
				AppRuleUserWH apprw=new AppRuleUserWH();
				wlist=apprw.queryRuleUserWh(usersBean.getUserid());
	
			}
			
			StringBuffer sb = new StringBuffer();
			for ( Warehouse w : wlist ){				
				if ( w.getId().toString().equalsIgnoreCase(value) ){				
					sb.append("<option value=\""+w.getId()+"\" selected>").append(w.getWarehousename()).append("</option>");
				}else{
					sb.append("<option value=\""+w.getId()+"\">").append(w.getWarehousename()).append("</option>");
				}			
			}
			
			htmlselect.append("<select name=\""+name.trim()+"\" id=\""+name.trim()+"\" ");
			if ( onchange != null && !"".equals(onchange) ){
				htmlselect.append(" onChange=\""+onchange+"\" ");
			}
			htmlselect.append(">");
			if ( HAS_CHOOSE.equals(p) ){
				htmlselect.append("<option value=''>请选择</option>");
			}
			htmlselect.append(sb).append("</select>");
			
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.println(htmlselect.toString());

		}catch(Exception exc){
			throw new JspException("<data:warehouse tag  error"+exc.getMessage());
		}
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	/**
	 * @return Returns the wHType.
	 */
	public String getwHType()
	{
		return wHType;
	}

	/**
	 * @param wHType The wHType to set.
	 */
	public void setwHType(String wHType)
	{
		this.wHType = wHType;
	}
	
	
	


	

}
