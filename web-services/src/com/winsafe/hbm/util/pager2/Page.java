package com.winsafe.hbm.util.pager2;


public class Page {

	private int startIndex = 0;
	

	private int lastIndex = 0;
    
  
    public static int DEFAULT_PAGE_SIZE = 10;
   
 
    private int pageSize = DEFAULT_PAGE_SIZE;

 
    private int totalCount = 0;
    

    private int totalPage = 0;

//    public Page() {
//        this(0, 0, DEFAULT_PAGE_SIZE);
//    }


    public Page(int currentPage, int pageSize, int totalCount) {
    	this.pageSize = pageSize;       
        this.totalCount = totalCount;   
        this.lastIndex = currentPage * pageSize;
    	setTotalPage();
    	setStartIndex(currentPage);    	
    }


    private void setTotalPage() {
    	totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;       
    }    
 

    public int getCurrentPageNo() {
        return (startIndex / pageSize) + 1;
    }


    public boolean hasNextPage() {       
    	return this.getCurrentPageNo() < totalPage;
    }


    public boolean hasPreviousPage() {
        return (this.getCurrentPageNo() > 1);
    }


    private void setStartIndex(int currentPage) {    	
    	if ( currentPage <= totalPage ){
    		startIndex = (currentPage - 1) * pageSize;
    	}else{
    		startIndex = (totalPage - 1) * pageSize;
    	}
    	
    }

	public int getPageSize() {
		return pageSize;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getLastIndex() {
		return lastIndex;
	}
	
	
}
