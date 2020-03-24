package com.winsafe.drp.base.dao.db;

public class PageUtil {
	
	/** 计算总页数,静态方法,供外部直接通过类名调用 */ 
	public static int countTotalPage(int pageSize, int totalRows) {
		if (totalRows < 0 || pageSize <= 0) {
			return 0;
		}
		int totalPage = totalRows % pageSize == 0 ? totalRows / pageSize : totalRows / pageSize + 1;
		return totalPage;
	}

	/** 计算当前页开始记录 */
	public static int countStartIndex(int pageSize, int currentPage) {
		if (currentPage <= 0) {
			throw new IllegalArgumentException("current page can't less than zero");
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException("pagesize can't less than zero");
		}
		return pageSize * (currentPage - 1);
	}

	/** 计算当前页,若为0或者请求的URL中没有"?page=",则用1代替 */
	public static int countCurrentPage(int page) {
		return (page == 0 ? 1 : page);
	}
}
