package com.winsafe.hbm.util.pager;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SimplePageInfo {
	protected int currentPageNo=1;
	protected int pageSize=10;
	protected int totalRcdNum=0;

	public SimplePageInfo(){

	}

	/**
	 * @param pCurPageNo
	 * @param pPgSize
	 * @param pTotRcdNum
	 */
	public SimplePageInfo(int pCurPageNo,int pPgSize,int pTotRcdNum){
		currentPageNo=pCurPageNo;
		pageSize=pPgSize;
		totalRcdNum=pTotRcdNum;
	}



	/**
	 * Note: the page No. from 1 count
	 * @return
	 */
	public int getPageNum(){
		int tmpPageNum=0;
		if(totalRcdNum>0){
			tmpPageNum=totalRcdNum/pageSize;
			if(totalRcdNum%pageSize>0){
				tmpPageNum++;
			}
		}
		return tmpPageNum;

	}

	/**
	 * Note: the record No. from 1 count
	 * @return
	 */
	public int getStartRcdNo(){
		int tmpStartRcdNo=1;
		int tmpPageNum=getPageNum();
		if(currentPageNo>0&&currentPageNo<=tmpPageNum){
			tmpStartRcdNo=(currentPageNo-1)*pageSize+1;
		}else{
			//adust the current pageNo
			currentPageNo=1;
		}

		return tmpStartRcdNo;

	}


	/**
	 * @return Returns the currentPageNo.
	 */
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	/**
	 * @param currentPageNo The currentPageNo to set.
	 */
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		int tmpPgSize=1;
		if(tmpPgSize>0)
			tmpPgSize=pageSize;
		this.pageSize = tmpPgSize;
	}
	/**
	 * @return Returns the totalRcdNum.
	 */
	public int getTotalRcdNum() {
		return totalRcdNum;
	}
	/**
	 * @param totalRcdNum The totalRcdNum to set.
	 */
	public void setTotalRcdNum(int totalRcdNum) {
		this.totalRcdNum = totalRcdNum;
	}
}
