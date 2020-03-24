/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.pager.PaymentModeSelectTagHandle;
import com.winsafe.drp.dao.PaymentMode;
import com.winsafe.drp.server.PaymentModeService;


public class PaymentModeSelectTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(PaymentModeSelectTagHandle.class);
	protected String name;
	private String p="n";
	private String value;
	private String onchange;
	private String HAS_CHOOSE = "y";
	private String NO_CHOOSE = "n";
	
	private PaymentModeService os = new PaymentModeService();
	
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

		try{				
			if ( name.equals("") ){
				throw new JspException("<data:PaymentMode tag the name parameter is null");
			}
			if ( value == null ){
				value = "";
			}else{
				value = value.trim();
			}
			
			List<PaymentMode> dlist = os.getAllPaymentMode();
			StringBuffer sb = new StringBuffer();
			for ( PaymentMode PaymentMode : dlist ){				
				if ( PaymentMode.getId().toString().equalsIgnoreCase(value) ){				
					sb.append("<option value=\""+PaymentMode.getId()+"\" selected>").append(PaymentMode.getPaymentname()).append("</option>");
				}else{
					sb.append("<option value=\""+PaymentMode.getId()+"\">").append(PaymentMode.getPaymentname()).append("</option>");
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
			throw new JspException("<data:PaymentMode tag  error"+exc.getMessage());
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
