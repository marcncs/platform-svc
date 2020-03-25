/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.pager.DateFormatTagHandle;


public class DateFormatTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(DateFormatTagHandle.class);	
	private String value;	
	private String p;
	private String style = "yyyy-MM-dd HH:mm:ss";
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
			String result = "";
			
			if ( p != null && !"".equals(p) ){
				style = p;
			}
			Date date = null;
			DateFormat dateformat = null;
			if ( value == null || value.equals("") ){
				result = "";
			}else{
				if(value.length() <= 8) {
					dateformat = new SimpleDateFormat("yyyyMMdd");		
				} else if ( value.length() <= 10 ){
					dateformat = new SimpleDateFormat("yyyy-MM-dd");				
				}else{
					dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}
				date = dateformat.parse(value);
				dateformat = new SimpleDateFormat(style);
				result = dateformat.format(date);	
			}
			
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.println(result);

		}catch(Exception exc){
			throw new JspException("<data:dateformat error "+exc.getMessage());
		}
	}

	public void test(){
		try{
			PrintWriter tmpWriter= new PrintWriter(pageContext.getOut());
			tmpWriter.println("only a test ! ");
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	

	

}
