/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.pager.UsersSelectTagHandle;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UsersService;


public class UsersSelectTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(UsersSelectTagHandle.class);
	protected String name;
	private String p="n";
	private String value;
	private String onchange;
	private String HAS_CHOOSE = "y";
	private String NO_CHOOSE = "n";
	
	private UsersService os = new UsersService();
	
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
				throw new JspException("datausers tag the name parameter is null");
			}
			if ( value == null ){
				value = "";
			}else{
				value = value.trim();
			}
			
			List<UsersBean> dlist = os.getIDAndLoginNameByOID2(usersBean.getMakeorganid());
			StringBuffer sb = new StringBuffer();
			for ( UsersBean ub : dlist ){				
				if ( ub.getUserid().toString().equalsIgnoreCase(value) ){				
					sb.append("<option value=\""+ub.getUserid()+"\" selected>").append(ub.getRealname()).append("</option>");
				}else{
					sb.append("<option value=\""+ub.getUserid()+"\">").append(ub.getRealname()).append("</option>");
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
			throw new JspException("datausers tag  error"+exc.getMessage());
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
	
	


	

}
