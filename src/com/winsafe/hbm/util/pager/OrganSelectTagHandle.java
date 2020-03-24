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

import com.winsafe.hbm.util.pager.OrganSelectTagHandle;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;


public class OrganSelectTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(OrganSelectTagHandle.class);
	protected String name;
	private String p="n";
	private String value;
	private String onchange;
	private String HAS_CHOOSE = "y";
	private String NO_CHOOSE = "n";
	
	private OrganService os = new OrganService();
	
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
				throw new JspException("<data:organ tag the name parameter is null");
			}
			if ( value == null ){
				value = "";
			}else{
				value = value.trim();
			}
			
			List<Organ> olist = os.getOrganToDown(os.getOrganByID(usersBean.getMakeorganid()).getSysid());
			StringBuffer sb = new StringBuffer();
			for ( Organ organ : olist ){				
				if ( organ.getId().equals(value) ){				
					sb.append("<option value=\""+organ.getId()+"\" selected>").append(getLine(organ.getId())+organ.getOrganname()).append("</option>");
				}else{
					sb.append("<option value=\""+organ.getId()+"\">").append(getLine(organ.getId())+organ.getOrganname()).append("</option>");
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
			throw new JspException("<data:organ tag error"+exc.getMessage());
		}
	}
	
	private String getLine(String id){
		StringBuffer line = new StringBuffer();
		for ( int i=0; i<(id.length()/2); i++){
			line.append("--");
		}
		return line.toString();
	}

	public void test(){
		try{
			PrintWriter tmpWriter= new PrintWriter(pageContext.getOut());
			tmpWriter.println("only a test ! ");
		}catch(Exception exc){
			exc.printStackTrace();
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
