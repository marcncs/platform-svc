package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.SalesConsumptionInventory;
import com.winsafe.drp.dao.SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm;
import com.winsafe.drp.service.report.SalesConsumptionInventoryMonthlyVolumeReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;

public class SalesConsumptionInventoryMonthlyVolumeReportAction extends BaseAction{
	
	private static Logger logger = Logger.getLogger(SalesConsumptionInventoryMonthlyVolumeReportAction.class);
	private SalesConsumptionInventoryMonthlyVolumeReportService service = new SalesConsumptionInventoryMonthlyVolumeReportService();
	private AppRegion appRegion = new AppRegion();
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		//初始化
		super.initdata(request);
		int pageSize=10;
		try{
			Map paraMap = new HashMap(request.getParameterMap());
			
			// 默认开始时间与结束时间
			if(StringUtil.isEmpty((String)paraMap.get("beginDate")) && StringUtil.isEmpty((String)paraMap.get("endDate"))){
				request.setAttribute("beginDate", Dateutil.formatDate(Dateutil.addMonth2Date(-1, Dateutil.getCurrentDate())));
				request.setAttribute("endDate", Dateutil.formatDate(Dateutil.getCurrentDate()));
			}
			
			List<SalesConsumptionInventory> titleList = new ArrayList<SalesConsumptionInventory>();
			List<SalesConsumptionInventory> titleBeginList = new ArrayList<SalesConsumptionInventory>();
			service.getTitleList(request, titleBeginList, titleList);
			
			List<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm> displayList = new ArrayList<SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm>();
			if(!checkInitState(request, pageSize)){
				displayList = service.getSalesConsumptionInventoryMonthlyVolumeReportPageDisplayFormList(request, pageSize, users);
				request.setAttribute("prompt", "view");
			}
			
			request.setAttribute("titleList", titleList);
			request.setAttribute("titleBeginList", titleBeginList);
			request.setAttribute("regions", appRegion.getAllRegion());
		    request.setAttribute("rls", displayList);
		    DBUserLog.addUserLog(request, "列表");
		    return mapping.findForward("list");
		}catch(Exception e){
			logger.error("",e);
		}
		
		return new ActionForward(mapping.getInput());
	}
	
	/**
	 * 判断页面是否初始化页面
	 * Create Time 2015-1-30 下午05:44:55 
	 * @param scrForm
	 * @return
	 * @author lipeng
	 */
	private Boolean checkInitState(HttpServletRequest request,Integer pageSize){
		String queryFlag = request.getParameter("queryFlag");
		if(queryFlag == null){
			request.setAttribute("queryFlag", 1);
			BasePage bp = new BasePage(request, pageSize);
			bp.getPageSite();
			bp.setPage(new Page(0, pageSize, 0));
			return true;
		}else {
			return false;
		}
		
	}
}
