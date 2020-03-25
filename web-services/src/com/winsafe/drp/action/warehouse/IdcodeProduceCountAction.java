package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.util.Dateutil;

/**
 * 条码统计
* @Title: IdcodeProduceCountAction.java
* @author: wenping 
* @CreateTime: Feb 19, 2013 2:26:14 PM
* @version:
 */
public class IdcodeProduceCountAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppUploadProduceReport apr = new AppUploadProduceReport();
		try{
			String beginDate  =  request.getParameter("BeginDate");
			String endDate  =  request.getParameter("EndDate");
			if("".equals(beginDate)){
				beginDate =null;
			}
			if("".equals(endDate)){
				endDate =null;
			}
			
			int count = apr.getUploadProduceReportCount(beginDate,endDate);
			request.setAttribute("count", count);
			
			if(beginDate==null || endDate ==null){
				request.setAttribute("BeginDate", Dateutil.getPreThreeMonDayStr("yyyy-MM-dd"));
				request.setAttribute("EndDate", Dateutil.getCurrentDateString());
			}else{
				request.setAttribute("BeginDate", beginDate);
				request.setAttribute("EndDate", endDate);
			}
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
}
