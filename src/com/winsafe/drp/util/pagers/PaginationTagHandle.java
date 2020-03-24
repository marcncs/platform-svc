/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.drp.util.pagers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PaginationTagHandle extends TagSupport {
	protected Logger logger=Logger.getLogger(PaginationTagHandle.class);
	protected String target;
	protected String name;

	public int doStartTag()throws JspException{
		try{
			outPagination();
		}catch(Exception exc){
			exc.printStackTrace();
		}

		return super.doStartTag();
	}

	protected void outPagination()throws JspException{
		//0.
		Locale tmpLocale=getCurUserLocale((HttpServletRequest)pageContext.getRequest());
		//Locale tmpLocale = Locale.getAvailableLocales();
		//String aa = tmpLocale.getLanguage();
		//System.out.println("==++++++======="+tmpLocale);
		StringBuffer tmpOutStr=new StringBuffer();
		String tmpStr=null;
		if(name==null||name.length()==0){
			name=PaginationFacility.PAGINATION_ELE_NAME;
		}
		SimplePageInfo tmpPgIf=(SimplePageInfo)pageContext.getRequest().getAttribute(name);
		if(tmpPgIf==null){
			//error, not specify page info.
			logger.debug("Note:Can't retrieve SimplePageInfo object by name '"+name+"'");
			tmpPgIf=new SimplePageInfo();
		}
		int tmpPgNum=tmpPgIf.getPageNum();
		try{
			String tmpContextPath=((HttpServletRequest)pageContext.getRequest()).getContextPath();
			if(tmpContextPath.length()>0){
				tmpContextPath=tmpContextPath+"/";
			}else{
				tmpContextPath="/";
			}
			String tmpTarget=tmpContextPath+target;
			if(target.startsWith("/")){
				tmpTarget=tmpContextPath+target.substring(1);
			}
			boolean tmpLkFirst=false,tmpLkPrev=false,tmpLkNext=false,tmpLkLast=false;
			ResourceBundle tmpRscBundle=ResourceBundle.getBundle("global.sys.presentation",tmpLocale);
			if(tmpPgIf.currentPageNo>1){
				tmpLkFirst=true;tmpLkPrev=true;
			}
			if(tmpPgIf.currentPageNo<tmpPgNum){
				tmpLkNext=true;tmpLkLast=true;
			}
			int tmpInsertPos=0;
			if(tmpLkFirst){
				tmpOutStr.append("<a href=\""+tmpTarget+"?"+PaginationFacility.PAGINATION_PARA_NAME+"=1"+"\">");
				tmpInsertPos=tmpOutStr.length();
				tmpOutStr.append("</a>");
			}
			MessageFormat tmpMsgFormat=new MessageFormat("");
			String tmpPatternStr="";

			tmpPatternStr=tmpRscBundle.getString("pagination.first");
			tmpMsgFormat.applyPattern(tmpPatternStr);

			tmpOutStr.insert(tmpInsertPos,tmpMsgFormat.format(new String[]{tmpContextPath}));
			tmpOutStr.append("&nbsp;");
			tmpInsertPos=tmpOutStr.length();

			if(tmpLkPrev){
				tmpOutStr.append("<a href=\""+tmpTarget+"?"+PaginationFacility.PAGINATION_PARA_NAME+"="+(tmpPgIf.getCurrentPageNo()-1)+"\">");
				tmpInsertPos=tmpOutStr.length();
				tmpOutStr.append("</a>");
			}
			tmpPatternStr=tmpRscBundle.getString("pagination.prev");
			tmpMsgFormat.applyPattern(tmpPatternStr);
			tmpOutStr.insert(tmpInsertPos,tmpMsgFormat.format(new String[]{tmpContextPath}));
			tmpOutStr.append("&nbsp;");
			tmpInsertPos=tmpOutStr.length();

			if(tmpLkNext){
				tmpOutStr.append("<a href=\""+tmpTarget+"?"+PaginationFacility.PAGINATION_PARA_NAME+"="+(tmpPgIf.getCurrentPageNo()+1)+"\">");
				tmpInsertPos=tmpOutStr.length();
				tmpOutStr.append("</a>");
			}
			tmpPatternStr=tmpRscBundle.getString("pagination.next");
			tmpMsgFormat.applyPattern(tmpPatternStr);
			tmpOutStr.insert(tmpInsertPos,tmpMsgFormat.format(new String[]{tmpContextPath}));
			tmpOutStr.append("&nbsp;");
			tmpInsertPos=tmpOutStr.length();

			if(tmpLkLast){
				tmpOutStr.append("<a href=\""+tmpTarget+"?"+PaginationFacility.PAGINATION_PARA_NAME+"="+tmpPgNum+"\">");
				tmpInsertPos=tmpOutStr.length();
				tmpOutStr.append("</a>");
			}
			tmpPatternStr=tmpRscBundle.getString("pagination.last");
			tmpMsgFormat.applyPattern(tmpPatternStr);
			tmpOutStr.insert(tmpInsertPos,tmpMsgFormat.format(new String[]{tmpContextPath}));
			tmpInsertPos=tmpOutStr.length();

			tmpOutStr.append("&nbsp;&nbsp;");

			tmpPatternStr=tmpRscBundle.getString("pagination.info");
			tmpMsgFormat.applyPattern(tmpPatternStr);
			tmpOutStr.append(tmpMsgFormat.format(new Object[]{new Integer(tmpPgIf.getTotalRcdNum()),new Integer(tmpPgIf.getCurrentPageNo()),new Integer(tmpPgNum)}));

			tmpOutStr.append("&nbsp;");

			tmpPatternStr=tmpRscBundle.getString("pagination.go.label");
			tmpMsgFormat.applyPattern(tmpPatternStr);
			tmpStr="<input type=\"text\" size=\"1\" name=\""+PaginationFacility.PAGINATION_PARA_NAME+"\" />";

			//tmpOutStr.append("&nbsp;&nbsp;");
			tmpOutStr.append(tmpMsgFormat.format(new Object[]{tmpStr}));
			//tmpOutStr.append("&nbsp;&nbsp;");
			tmpOutStr.append("<input type=\"submit\" value=\""+tmpRscBundle.getString("pagination.go.button.label")+"\" />");
			tmpOutStr.insert(0,"<form action=\""+tmpTarget+"\">");
			tmpOutStr.append("</form>");

			//System.out.println(tmpRscBundle.getString("pagination.last"));

			PrintWriter tmpPrint=getWriter();
			tmpPrint.println(tmpOutStr.toString() );

		}catch(Exception exc){
			exc.printStackTrace();
		}



	}

	public PrintWriter getWriter()throws IOException{
		PrintWriter tmpWriter;
		//tmpWriter=pageContext.getResponse().getWriter();
		tmpWriter=new PrintWriter(pageContext.getOut());
		return tmpWriter;
	}

	public void test(){
		try{
			PrintWriter tmpWriter=getWriter();
			tmpWriter.println("only a test ! ");
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}


	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the target.
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target The target to set.
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	public static Locale getCurUserLocale(HttpServletRequest pRequest){
		//Locale tmpLocale=pRequest.getLocale();
		HttpSession session = pRequest.getSession();
		Locale tmpLocale=(Locale) session
        .getAttribute(Globals.LOCALE_KEY);

		return tmpLocale;
	}
}
