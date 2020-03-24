package com.winsafe.hbm.util.pager2;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.hbm.util.StringUtil; 
import com.winsafe.hbm.util.pager2.Page;


public class BasePage {
	private HttpServletRequest request;
	private int pageNo;
	private int v_pagesize=0;
	
	public BasePage(HttpServletRequest request){
		this.request = request;
		setPageNo();
	}
	
	public BasePage(HttpServletRequest request, int v_pagesize){
		this.request = request;
		setPageNo();
		this.v_pagesize=v_pagesize;
	}
	
	private void setPageNo(){
		String cp = request.getParameter("pageNo");
		if(StringUtil.isEmpty(cp)) {
			cp = request.getParameter("current_page");
		}
		if ( cp == null || "".equals(cp) ){
			cp = "1";
		}else{		
			try{
				pageNo = Integer.parseInt(cp);				
			}catch ( NumberFormatException e ){
				pageNo = 1;
			}
		}
		if ( pageNo < 1 ){
			pageNo = 1;
		}		
	}
	
		
	public int getPageNo() {		
		return pageNo;
	}
	
	public int getPageSite(){		
		String pagesite = request.getParameter("pagesize");		
		if(StringUtil.isEmpty(pagesite)) {
			pagesite = request.getParameter("page_size");
		}
		int psite = v_pagesize<=0 ? Page.DEFAULT_PAGE_SIZE : v_pagesize;
		int result = psite;
		if ( pagesite != null && !"".equals(pagesite) ){	
			try{
				result = Integer.parseInt(pagesite);
				if ( result > 100 ){
					result = psite;
				}
			}catch ( NumberFormatException e ){
				result = psite;
			}			
		}	
		setPagesize(result);
		return result;
	}
	
	private void setPagesize(int pagesize){
		request.setAttribute("page_size", pagesize);
		request.setAttribute("pagesize", pagesize);
	}
	
	public void setPage(Page page){
		request.setAttribute("page", page);
	}
}
