/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.pager.SalesRegionTreeTagHandle;


public class SalesRegionTreeTagHandle extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected Logger logger=Logger.getLogger(SalesRegionTreeTagHandle.class);
	private String id;
	private String name;
	private String value;
	private String height;
	private String callBack ;
	

	public int doStartTag()throws JspException{
		try{
			outPagination();
		}catch(Exception exc){
			exc.printStackTrace();
		}

		return super.doStartTag();
	}

	protected void outPagination()throws JspException{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();		
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
			tree.append("")
			.append("<SCRIPT language=\"javascript\" src=\"").append(webpath).append("/js/xtree.js\"></SCRIPT>")
			.append("<SCRIPT language=\"javascript\" src=\"").append(webpath).append("/js/xmlextras.js\"></SCRIPT>")
			.append("<SCRIPT language=\"javascript\" src=\"").append(webpath).append("/js/xloadtree.js\"></SCRIPT>")
			.append("<SCRIPT language=\"javascript\" src=\"").append(webpath).append("/js/treeselect.js\"></SCRIPT>")
			.append("<link rel=\"stylesheet\" href=\"").append(webpath).append("/css/xtree.css\" type=\"text/css\">")
			.append("<script>")
			.append("var pstree = new WebFXLoadTree(\"All\",\"../sBonusAreaAction.do?id=-1\",\"javascript:show('-1')\");")
			.append("CreateTreeSelect(\"").append(id).append("\", \"").append(name).append("\",pstree")
			.append(", \"").append(defaultvalue).append("\",").append(divheight).append(",'" + callBack + "'").append(");</script>") ;
						
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.println(tree.toString());

		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public static String getWebRealPath(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append(request.getScheme());
		sb.append("://");
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

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	

	


	

}
