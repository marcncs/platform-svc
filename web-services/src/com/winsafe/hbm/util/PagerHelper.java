package com.winsafe.hbm.util;

/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import javax.servlet.http.HttpServletRequest;

import com.winsafe.hbm.util.Pager;


public class PagerHelper {

        public static Pager getPager(HttpServletRequest httpServletRequest,
                        int totalRows) {


                Pager pager = new Pager(totalRows);


                String currentPage = httpServletRequest.getParameter("currentPage");


                if (currentPage != null) {
                        pager.refresh(Integer.parseInt(currentPage));
                }


                String pagerMethod = httpServletRequest.getParameter("pageMethod");

                if (pagerMethod != null) {
                        if (pagerMethod.equals("first")) {
                                pager.first();
                        } else if (pagerMethod.equals("previous")) {
                                pager.previous();
                        } else if (pagerMethod.equals("next")) {
                                pager.next();
                        } else if (pagerMethod.equals("last")) {
                                pager.last();
                        }
                }
                return pager;
        }

        public static Pager getPager(HttpServletRequest httpServletRequest,
                        int totalRows,int pageSize) {


                Pager pager = new Pager(totalRows,pageSize);


                String currentPage = httpServletRequest.getParameter("currentPage");


                if (currentPage != null) {
                        pager.refresh(Integer.parseInt(currentPage));
                }


                String pagerMethod = httpServletRequest.getParameter("pageMethod");

                if (pagerMethod != null) {
                        if (pagerMethod.equals("first")) {
                                pager.first();
                        } else if (pagerMethod.equals("previous")) {
                                pager.previous();
                        } else if (pagerMethod.equals("next")) {
                                pager.next();
                        } else if (pagerMethod.equals("last")) {
                                pager.last();
                        } else if (pagerMethod.equals("jump")){
                          pager.jump();
                        }
                }
                return pager;
        }

}
