/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import java.io.PrintWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.pager.ShowIdcodeBitTagHandle;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;


public class ShowIdcodeBitTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(ShowIdcodeBitTagHandle.class);
	private String value;
	private String idcodeclick;
	private String bitclick;
	
	
	private AppProduct ap = new AppProduct();
	
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
			if ( value.equals("") ){
				throw new JspException("<data:idcodebit tag the value parameter is null");
			}
			
			Product p = ap.getProductByID(value.trim());
			//判断产品是否是条件管理
			if ( 1 == 1 ){
				htmlselect.append("<a href=\"javascript:"+idcodeclick+"\"><img src=\"../images/CN/code.gif\"  border=\"0\" title=\"录入条码\"></a>");
			}
//			else{
//				htmlselect.append("<a href=\"javascript:"+bitclick+"\"><img src=\"../images/CN/cang.gif\"   border=\"0\" title=\"分配仓位\"></a>");
//			}
			
			
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.println(htmlselect.toString());

		}catch(Exception exc){
			throw new JspException("<data:idcodebit tag  error"+exc.getMessage());
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIdcodeclick() {
		return idcodeclick;
	}

	public void setIdcodeclick(String idcodeclick) {
		this.idcodeclick = idcodeclick;
	}

	public String getBitclick() {
		return bitclick;
	}

	public void setBitclick(String bitclick) {
		this.bitclick = bitclick;
	}

	
	
	
	
	


	

}
