package com.winsafe.hbm.util.pager2;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;

import com.winsafe.hbm.util.StringFilters;
import com.winsafe.hbm.util.pager2.Page;


public class PageTag extends TagSupport{
	private String action;
	private Page page;
	private String imagePath;
	private String onsubmit;
	private String language;

	private String[] psize = new String[]{"5","10","15","20","25","30","40","50"};
	
	
	public int doStartTag() throws JspException {		
		page = (Page)pageContext.findAttribute("page");
		String tmpContextPath=((HttpServletRequest)pageContext.getRequest()).getContextPath();		
		imagePath = tmpContextPath + "/";
		try {
			PrintWriter out = getWriter();
			Map map = pageContext.getRequest().getParameterMap();		
			String  hiddenstr = createHiddenObj(map);		
			StringBuffer html = new StringBuffer();		
			html.append("<form name='pageform'  method='post' action='" + action + "' style='padding:0px;margin:0px;'>")
			.append(hiddenstr)
			.append(outHtml())
			.append("</form>");
			out.print(html.toString());		
		} catch (Exception e) {		
			throw new JspException("PageTag doStartTag() error:"+e.getMessage());
		}
		
		return super.doStartTag();		
	}
	
	
	
	private String createHiddenObj(Map map){
		StringBuffer hidObjSb = new StringBuffer();
		Set keyset = map.keySet();
		for ( Iterator it = keyset.iterator(); it.hasNext(); ){
			Object keyobj = it.next();
			Object valueobj = map.get(keyobj);
			if ( valueobj instanceof Object[] ){
				valueobj = ((Object[])valueobj)[0];
			}
			if ( "pageNo".equals(keyobj) || "topage".equals(keyobj) 
					|| "submit".equalsIgnoreCase(keyobj.toString())
					|| "pagesize".equalsIgnoreCase(keyobj.toString()) ){
				continue;
			}

			String value=StringFilters.filterHtml(valueobj.toString());
			hidObjSb.append("<input type='hidden' name='").append(keyobj)
			.append("' value='").append(value).append("'>");
		}
		return hidObjSb.toString();
	}
	
	
	public String outHtml() throws Exception{
		HttpServletRequest pRequest = (HttpServletRequest)pageContext.getRequest();
		Locale locale=getCurUserLocale(pRequest);
		StringBuffer pagehtml = new StringBuffer();	
		try{
			ResourceBundle resBundle= null;
			if("en".equals(language)) {
				resBundle=ResourceBundle.getBundle("global.sys.presentation",locale);
			} else {
				resBundle=ResourceBundle.getBundle("global.sys.presentation_zh",locale);
			}
			MessageFormat mf = new MessageFormat(""); 
			String pfirst = resBundle.getString("pagination.first");
			String pfirstNone = resBundle.getString("pagination.first.none");
			String pprev = resBundle.getString("pagination.prev");
			String pprevNone = resBundle.getString("pagination.prev.none");
			String pnext = resBundle.getString("pagination.next");		
			String pnextNone = resBundle.getString("pagination.next.none");	
			String plast = resBundle.getString("pagination.last");
			String plastNone = resBundle.getString("pagination.last.none");
			String pinfo = resBundle.getString("pagination.info");
			String pbutton = resBundle.getString("pagination.go.button.label");
			String pgo = resBundle.getString("pagination.go.label");
			String per = resBundle.getString("pagination.per");
			
			pagehtml.append(createPagesize(pRequest, per));
			
			mf.applyPattern(pinfo);
			pagehtml.append(mf.format(new Integer[]{page.getTotalCount(), page.getCurrentPageNo(), page.getTotalPage()})).append("&nbsp;");	
			if ( !page.hasPreviousPage() ){
				mf.applyPattern(pfirstNone);
				pagehtml.append(mf.format(new String[]{imagePath})).append("&nbsp;");
				mf.applyPattern(pprevNone);
				pagehtml.append(mf.format(new String[]{imagePath})).append("&nbsp;");
			}else{
				mf.applyPattern(pfirst);
				pagehtml.append(createHref(1, mf.format(new String[]{imagePath})));
				mf.applyPattern(pprev);
				pagehtml.append(createHref((page.getCurrentPageNo() -1), mf.format(new String[]{imagePath})));
			}
			if ( !page.hasNextPage() ){
				mf.applyPattern(pnextNone);
				pagehtml.append(mf.format(new String[]{imagePath})).append("&nbsp;");
				mf.applyPattern(plastNone);
				pagehtml.append(mf.format(new String[]{imagePath})).append("&nbsp;");
			}else{
				mf.applyPattern(pnext);
				pagehtml.append(createHref((page.getCurrentPageNo()+1), mf.format(new String[]{imagePath})));
				mf.applyPattern(plast);
				pagehtml.append(createHref(page.getTotalPage(), mf.format(new String[]{imagePath}))+"&nbsp;");
			}
			
			mf.applyPattern(pgo);
			String topage = "<input type='text' name='topage' style='width:25px' >";
			pagehtml.append("&nbsp;"+mf.format(new String[]{topage}));
			pagehtml.append("<input type='image' src='../images/CN/gopage.gif' align='absbottom' border='0' title='查询' onclick='pageform.pageNo.value=pageform.topage.value;pageform.submit(");
			if(onsubmit != null && onsubmit.length() > 0){
				pagehtml.append("[" + onsubmit +  "]");
			}
			pagehtml.append(")'/>");
			pagehtml.append("<input type='hidden' name='pageNo' style='width:20px' value='"+page.getCurrentPageNo()+"' >");
			
		}catch ( Exception e ){
			throw new Exception("outhtme() method error:"+e.getMessage());
		}
		return pagehtml.toString();
	}
	
	private PrintWriter getWriter()throws IOException{
		PrintWriter tmpWriter;
		tmpWriter=new PrintWriter(pageContext.getOut());
		return tmpWriter;
	}
	
	private String createPagesize(HttpServletRequest pRequest, String perPage){		
		Integer curPagesize = (Integer)pageContext.findAttribute("pagesize");
		StringBuffer pagesizehtml = new StringBuffer();	
		pagesizehtml.append(perPage+"<select name='pagesize' id='pagesize' onchange='pageform.submit(");	
		if(onsubmit != null && onsubmit.length() > 0){
			pagesizehtml.append("[" + onsubmit +  "]");
		}
		pagesizehtml.append(")'>");
		for(int i=0; i<psize.length; i++){
			if ( Integer.parseInt(psize[i]) == curPagesize.intValue() ){
				pagesizehtml.append("<option value='").append(psize[i]).append("' selected>").append(psize[i]).append("</option>");
			}else{
				pagesizehtml.append("<option value='").append(psize[i]).append("'>").append(psize[i]).append("</option>");
			}
		}
		pagesizehtml.append("</select>");
		return pagesizehtml.toString();
	}

	private String createHref(int num, String image){
		StringBuffer href = new StringBuffer();	
		href.append("<a href='javascript:pageform.pageNo.value=" + num);
		href.append(";pageform.submit(");
		if(onsubmit != null && onsubmit.length() > 0){
			href.append("[" + onsubmit +  "]");
		}
		href.append(");' >");
		href.append(image+"</a>&nbsp;");
		return href.toString();
	}
	
	public static Locale getCurUserLocale(HttpServletRequest pRequest){
		HttpSession session = pRequest.getSession();
		Locale tmpLocale=(Locale) session
        .getAttribute(Globals.LOCALE_KEY);
		return tmpLocale;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}



	public String getOnsubmit() {
		return onsubmit;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}
}
