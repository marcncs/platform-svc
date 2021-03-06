package com.winsafe.drp.action.newreport;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.SalesConsumeDetailReportForm;
import com.winsafe.drp.dao.SalesConsumeReportForm;
import com.winsafe.drp.server.SalesConsumeReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;

import common.Logger;

public class SalesLnventoryReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(SalesLnventoryReportAction.class);
	
	private SalesLnventoryReportService scService = new SalesLnventoryReportService();
	private AppRegion appRegion = new AppRegion();
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 初始化分页信息
		int pageSize = 15;
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		
		SalesLnventoryReportForm scrForm = new SalesLnventoryReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		// 判断页面是否初始化显示
		List<SalesLnventoryReportForm> list = null;
		if(!checkInitState(request, pageSize)){
			list = scService.querySalesDetailReport(request, pageSize, scrForm,users);
			request.setAttribute("prompt", "view");
		} else {
			request.setAttribute("organType", 2);
			//默认按升,千克统计
			request.setAttribute("countByUnit", true);
		}
		
		// 初始化区域
		List<Region> regions = appRegion.getAllRegion();
		request.setAttribute("regions", regions);
	
		request.setAttribute("list", list);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("success");
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
