/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class UrlEncodeTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(UrlEncodeTagHandle.class);
	protected String key;
	

	public int doStartTag()throws JspException{
		try{
			outPagination();
		}catch(Exception exc){
			exc.printStackTrace();
		}

		return super.doStartTag();
	}

	protected void outPagination()throws JspException{
		try{
			String url=URLEncoder.encode(key, "utf-8");
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.println(url);

		}catch(Exception exc){
			throw new JspException("<data:encode tag  error"+exc.getMessage());
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
