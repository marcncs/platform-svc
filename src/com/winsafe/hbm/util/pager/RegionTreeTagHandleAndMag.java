package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRegionUsers;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.RegionUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;


public class RegionTreeTagHandleAndMag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected Logger logger=Logger.getLogger(RegionTreeTagHandleAndMag.class);
	private String id;
	private String name;
	private String value;
	private String height;
	

	public int doStartTag()throws JspException{
		try{
			outPagination();
		}catch(Exception exc){
			exc.printStackTrace();
		}

		return super.doStartTag();
	}

	protected void outPagination()throws Exception{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();	
		AppUsers appusers = new AppUsers();
		AppRegion appregion = new AppRegion();
	    String regionname = "" ;
	  	String regionid = "1" ;
		
		 UsersBean  users = UserManager.getUser(request);
	     Integer userid= users.getUserid();
	     
	    Users us = appusers.getUsersByid(userid);
		Region r = appregion.getRegionByUserid(userid.toString());
		
		if(r!=null && r.getParentid().equals("1") && us.getUserType()==5 ){
			regionid=r.getRegioncode();
			regionname=r.getSortname();
		}
		 try{
			if ( id.equals("") ){
				throw new JspException("<data:select tag the id parameter error");
			}			
			if ( name.equals("") ){
				throw new JspException("<data:select tag the name parameter error");
			}
			String defaultvalue="";
			if ( value != null ){
				defaultvalue = value;
			}
			int divheight = 250;
			if ( height != null && !"".equals(height) ){
				divheight = Integer.valueOf(height);
			}
			String webpath = getWebRealPath(request);			
			StringBuffer tree = new StringBuffer();
			tree.append("<SCRIPT language=\"javascript\" src=\"").append(webpath).append("/js/xtree.js\"></SCRIPT>")
			.append("<SCRIPT language=\"javascript\" src=\"").append(webpath).append("/js/xmlextras.js\"></SCRIPT>")
			.append("<SCRIPT language=\"javascript\" src=\"").append(webpath).append("/js/xloadtree.js\"></SCRIPT>")
			.append("<SCRIPT language=\"javascript\" src=\"").append(webpath).append("/js/treeselect.js\"></SCRIPT>")
			.append("<link rel=\"stylesheet\" href=\"").append(webpath).append("/css/xtree.css\" type=\"text/css\">")
			.append("<script>")
			.append("var regiontree = new WebFXLoadTree(\""+regionname+"\",\"../regionAction.do?id="+regionid+"\",\"javascript:show('"+regionid+"')\");")
			.append("CreateTreeSelect(\"").append(id).append("\", \"").append(name).append("\",regiontree")
			.append(", \"").append(defaultvalue).append("\",").append(divheight).append(");</script>");
						
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.println(tree.toString());

		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public static String getWebRealPath(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("http://");
		sb.append(request.getServerName());
		if (request.getServerPort() != 80) {
			sb.append(":");
			sb.append(request.getServerPort());
		}
		sb.append(request.getContextPath());
		return sb.toString();
	}

	public void test(){
		try{
			PrintWriter tmpWriter= new PrintWriter(pageContext.getOut());
			tmpWriter.println("only a test ! ");
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}


}
