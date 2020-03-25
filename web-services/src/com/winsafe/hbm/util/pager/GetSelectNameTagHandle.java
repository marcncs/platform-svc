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

import com.winsafe.drp.dao.AppLeftMenu;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.PaymentModeService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.HtmlSelect;


public class GetSelectNameTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(GetSelectNameTagHandle.class);
	protected String key;
	private String p;
	private String value;
	private String BASE_RESOURCE = "d";
	private String SYSTEM_RESOURCE = "f";
	private String language;
	
	private OrganService os = new OrganService();
	private DeptService ds = new DeptService();
	private WarehouseService ws = new WarehouseService();
	private UsersService us = new UsersService();
	private PaymentModeService pms = new PaymentModeService();
	private CountryAreaService cas = new CountryAreaService();
	private AppLeftMenu appLeftMenu = new AppLeftMenu();
	private AppScanner as = new AppScanner();

	public int doStartTag()throws JspException{
		try{
			outPagination();
		}catch(Exception exc){
			exc.printStackTrace();
		}

		return super.doStartTag();
	}

	protected void outPagination()throws JspException{
		String name = "";
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		try{
			if ( key.equals("") ){
				throw new JspException("<data:getname tag the key parameter is null");
			}			

			if ( p.equals("") ){
				throw new JspException("<data:getname tag the p parameter is null");
			}
			
			if ( value == null || "".equals(value) ){
				name = "";
			}else{			
				if ( BASE_RESOURCE.equals(p) ){	
					if ( "organ".equalsIgnoreCase(key) ){
						name = os.getOrganName(value);
					}else if ( "dept".equalsIgnoreCase(key) ){
						name = ds.getDeptName(Integer.valueOf(value));
					}else if ( "warehouse".equalsIgnoreCase(key) ){
						name = ws.getWarehouseName(value);
					}else if ( "users".equalsIgnoreCase(key) ){
						name = us.getUsersName(Integer.valueOf(value));
					}else if ( "paymentmode".equalsIgnoreCase(key) ){
						name = pms.getPaymentModeName(Integer.valueOf(value));
					}else if ( "countryarea".equalsIgnoreCase(key) ){
						name = cas.getCountryAreaName(Integer.valueOf(value));
					}else if ( "leftMenu".equalsIgnoreCase(key) ){
						name = appLeftMenu.getNameById(value);
					}else if ("scanner".equalsIgnoreCase(key)) {
						name = as.getImeiNById(value);
						
					}else{
						name = HtmlSelect.getResourceName(request, key, Integer.parseInt(value));
					}
				}else if ( SYSTEM_RESOURCE.equals(p) ){				
					name = HtmlSelect.getNameByOrder(request, key, Integer.parseInt(value), language);			
				}else{
					throw new JspException("<data:getname tag the p parameter error");
				}
			}
			
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.println(name);

		}catch(Exception exc){
			throw new JspException("<data:getname tag  error"+exc.getMessage());
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
