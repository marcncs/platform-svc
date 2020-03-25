/*
 * Created on 2005-7-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.util.pager;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.hbm.util.pager.SimplePageInfo;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PaginationFacility {
	public static final String PAGINATION_ELE_NAME="com.winsafe.drp.util.pager.simpleInfo";
	public static final String PAGINATION_PARA_NAME="pageno";
	public static final String PAGINATION_PARA_PAGE_SIZE="pageSize";

	public static SimplePageInfo setSimplePaginationInfo(HttpServletRequest pRequst,int pCurPageNo,int pPgSize,int pTotRcdNum){
		SimplePageInfo tmpPgInfo=new SimplePageInfo(pCurPageNo,pPgSize,pTotRcdNum);
		setSimplePaginationInfo(pRequst,tmpPgInfo);
		return tmpPgInfo;
	}

	public static SimplePageInfo setSimplePaginationInfo(HttpServletRequest pRequest){
		return setSimplePaginationInfo(pRequest,new SimplePageInfo());
	}


	public static SimplePageInfo setSimplePaginationInfo(HttpServletRequest pRequst,SimplePageInfo pPgInfo){

		pRequst.setAttribute(PAGINATION_ELE_NAME,pPgInfo);
		return pPgInfo;
	}



}
