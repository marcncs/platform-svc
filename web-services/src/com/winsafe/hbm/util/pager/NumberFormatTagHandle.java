/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.pager.NumberFormatTagHandle;


public class NumberFormatTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(NumberFormatTagHandle.class);	
	private String value;
	private String n;
	private String p;
	private String[] matstr = new String[]{"0","0.0","0.00","0.000","0.0000","0.00000","0.000000","0.0000000","0.00000000"}; 
	private int defaultnum = 2;
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
			if ( value == null || value.equals("") ){
				value = "0";
			}
			if ( n != null && !"".equals(n) ){
				defaultnum = Integer.valueOf(n);
				if ( defaultnum > 8 ){
					defaultnum = 2;
				}
			}
			DecimalFormat df1 = new DecimalFormat(matstr[defaultnum]);
			if ( p != null && !"".equals(p) ){
				df1 = new DecimalFormat(p);
			}
			double dv = Double.valueOf(value);			
		    String result = df1.format(dv);
			
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.print(result);

		}catch(Exception exc){
			throw new JspException("<data:format error "+exc.getMessage());
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

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	

	

}
