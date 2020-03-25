package com.winsafe.drp.base.dao.db;

import java.util.List;

public class PageBean<T> { 
	private List<T> list; // 要返回的某一页的记录列表

	private int currentPage; // 当前页
	private int pageSize; // 每页记录数
	private int totalRows; // 总记录数
	private int totalPage; // 总页数
	
	private int current_page; // 当前页
	private int page_size; // 每页记录数
	private int total_rows; // 总记录数
	private int total_page; // 总页数

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 以下判断页的信息,只需getter方法(is方法)即可
	 * @return
	 */

	/** 是否有前一页 */
	public boolean hasPreviousPage() {
		return currentPage > 0 && currentPage != 1; // 只要当前页不是第1页
	}

	/** 是否有下一页 */
	public boolean hasNextPage() {
		return currentPage > 0 && currentPage < totalPage; // 只要当前页比总页数小
	}

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public int getTotal_rows() {
		return total_rows;
	}

	public void setTotal_rows(int total_rows) {
		this.total_rows = total_rows;
	}

	public int getTotal_page() {
		return total_page;
	}

	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}

}
