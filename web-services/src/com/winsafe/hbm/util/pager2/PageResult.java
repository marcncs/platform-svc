package com.winsafe.hbm.util.pager2;

import java.util.List;

import com.winsafe.hbm.util.pager2.Page;


public class PageResult {
	private Page page;
	private List content;
	
	public PageResult(){
		super();
	}
	
	public PageResult(Page page, List content){
		this.page = page;
		this.content = content;
	}
	
	public List getContent() {
		return content;
	}
	public void setContent(List content) {
		this.content = content;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}
