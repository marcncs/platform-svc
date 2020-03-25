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

import com.winsafe.hbm.util.pager.SelectTagHandle;
import com.winsafe.hbm.util.HtmlSelect;


public class SelectTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(SelectTagHandle.class);
	protected String key;
	protected String name;
	private String p;
	private String value;
	private String HAS_CHOOSE = "y";
	private String NO_CHOOSE = "n";
	private String BASE_RESOURCE = "d";
	private String SYSTEM_RESOURCE = "f";
	private String i18n;

	public int doStartTag()throws JspException{
		try{
			outPagination();
		}catch(Exception exc){
			exc.printStackTrace();
		}

		return super.doStartTag();
	}

	protected void outPagination()throws JspException{
		String htmlselect = "";
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		try{
			if ( key.equals("") ){
				throw new JspException("<data:select tag the key parameter error");
			}			
			if ( name.equals("") ){
				throw new JspException("<data:select tag the name parameter error");
			}
			if ( p.equals("") ){
				throw new JspException("<data:select tag the p parameter error");
			}
			String[] parameter = p.split("\\|");
			if ( parameter.length != 2 ){
				throw new JspException("<data:select tag the p parameter error");
			}
			if ( BASE_RESOURCE.equals(parameter[1]) ){
				if ( HAS_CHOOSE.equals(parameter[0]) ){
					if ( null == value ){
						htmlselect = HtmlSelect.getResourceCSelect(request, key, name);
					}else{
						if ( "".equals(value) ){
							htmlselect = HtmlSelect.getResourceCSelect(request, key, name);
						}else{
							int defaultvalue = Integer.valueOf(value);
							htmlselect = HtmlSelect.getDefaultResourceCSelect(request, key, name, defaultvalue);
						}
					}
				}else if ( NO_CHOOSE.equals(parameter[0]) ){
					if ( null == value ){
						htmlselect = HtmlSelect.getResourceSelect(request, key, name);
					}else{
						if ( "".equals(value) ){
							htmlselect = HtmlSelect.getResourceSelect(request, key, name);
						}else{
							int defaultvalue = Integer.valueOf(value);
							htmlselect = HtmlSelect.getDefaultResourceSelect(request, key, name, defaultvalue);
						}
					}
				}else{
					throw new JspException("<data:select tag the p parameter error");
				}
			}else if ( SYSTEM_RESOURCE.equals(parameter[1]) ){
				if ( HAS_CHOOSE.equals(parameter[0]) ){
					if ( null == value ){
						htmlselect = HtmlSelect.getCSelect(request, key, name, i18n);
					}else{
						if ( "".equals(value) ){
							htmlselect = HtmlSelect.getCSelect(request, key, name, i18n);
						}else{
							int defaultvalue = Integer.valueOf(value);
							htmlselect = HtmlSelect.getDefaultCSelect(request, key, name, defaultvalue, i18n);
						}
					}
				}else if ( NO_CHOOSE.equals(parameter[0]) ){
					if ( null == value ){
						htmlselect = HtmlSelect.getSelect(request, key, name, i18n);
					}else{
						if ( "".equals(value) ){
							htmlselect = HtmlSelect.getSelect(request, key, name, i18n);
						}else{
							int defaultvalue = Integer.valueOf(value);
							htmlselect = HtmlSelect.getDefaultSelect(request, key, name, defaultvalue, i18n);
						}
					}
				}else{
					throw new JspException("<data:select tag the p parameter error");
				}					
			}else{
				throw new JspException("<data:select tag the p parameter error");
			}
			
			PrintWriter tmpPrint = new PrintWriter(pageContext.getOut());
			tmpPrint.println(htmlselect);

		}catch(Exception exc){
			exc.printStackTrace();
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

	public String getI18n() {
		return i18n;
	}

	public void setI18n(String i18n) {
		this.i18n = i18n;
	}

}
