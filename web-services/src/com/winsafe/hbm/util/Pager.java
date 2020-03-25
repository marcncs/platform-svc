package com.winsafe.hbm.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.hbm.util.pager2.Page;

/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class Pager {

    private int totalRows;

    private int pageSize = 10;

    private int currentPage;

    private int totalPages;

    private int startRow;

    public Pager() {
    }

    public Pager(int _totalRows) {
	totalRows = _totalRows;
	totalPages = totalRows / pageSize;
	int mod = totalRows % pageSize;
	if (mod > 0) {
	    totalPages++;
	}
	if (totalPages > 0)
	    currentPage = 1;
	else
	    currentPage = 0;
	startRow = 0;
    }

    public Pager(int _totalRows, int _pageSize) {
	totalRows = _totalRows;
	pageSize = _pageSize;
	totalPages = totalRows / pageSize;
	int mod = totalRows % pageSize;
	if (mod > 0) {
	    totalPages++;
	}
	if (totalPages > 0)
	    currentPage = 1;
	else
	    currentPage = 0;
	startRow = 0;
    }

    public int getStartRow() {
	return startRow;
    }

    public int getTotalPages() {
	return totalPages;
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public int getPageSize() {
	return pageSize;
    }

    public void setTotalRows(int totalRows) {
	this.totalRows = totalRows;
    }

    public void setStartRow(int startRow) {
	this.startRow = startRow;
    }

    public void setTotalPages(int totalPages) {
	this.totalPages = totalPages;
    }

    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
    }

    public int getTotalRows() {
	return totalRows;
    }

    public void first() {
	if (totalPages > 0)
	    currentPage = 1;
	else
	    currentPage = 0;
	startRow = 0;
    }

    public void previous() {
	if (currentPage == 1 || currentPage == 0) {
	    return;
	}
	currentPage--;
	startRow = (currentPage - 1) * pageSize;
    }

    public void next() {
	if (currentPage < totalPages) {
	    currentPage++;
	}
	startRow = (currentPage - 1) * pageSize;
    }

    public void last() {
	currentPage = totalPages;
	startRow = (currentPage - 1) * pageSize;
    }

    public void jump() {
	if (currentPage <= totalPages && currentPage > 0)
	    startRow = (currentPage - 1) * pageSize;
    }

    public void refresh(int _currentPage) {
	currentPage = _currentPage;
	if (currentPage > totalPages) {
	    last();
	}
    }

    /**
     * 主要功能：手动配置分页
     * @param request 
     * @param returnList 需分页的列表
     * @return 分页结果列表
     */
    public static List initPagerManually(HttpServletRequest request,
	    List returnList) {
	//每页显示条数
	String pagesize = String.valueOf(request.getParameter("pagesize"));
	//如果初始化默认为10条
	if (pagesize == null || "null".equals(pagesize)) {
	    pagesize = "10";
	}
	//当前页数(默认第一页)
	Integer pageNo = 1;
	String cp = request.getParameter("pageNo");
	if (cp == null || "".equals(cp)) {
	    cp = "1";
	} else {
	    try {
		pageNo = Integer.parseInt(cp);
	    } catch (NumberFormatException e) {
		pageNo = 1;
	    }
	}
	if (pageNo < 1) {
	    pageNo = 1;
	}
	//初始化分页对象
	Page page = new Page(pageNo, Integer.valueOf(pagesize), returnList
		.size());
	request.setAttribute("pagesize", Integer.valueOf(pagesize));
	request.setAttribute("page", page);
	//得到分页后结果列表
	if(returnList.size() > 0){
		if(page.getStartIndex() >= 0 && returnList.size() > page.getLastIndex()){
		    returnList = returnList.subList(page.getStartIndex(), page
				.getLastIndex());
		}else{
		    //最后一页
		    returnList = returnList.subList(page.getStartIndex(), returnList.size());
		}
	} 
	
	return returnList;
    }
}
