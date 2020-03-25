package com.winsafe.drp.action.report;

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
import com.winsafe.drp.server.SalesConsumeInventoryHistoryService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;

import common.Logger;

public class ListConsumeDetailHistoryAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListConsumeDetailHistoryAction.class);
	
	private AppRegion appRegion = new AppRegion();
	private SalesConsumeInventoryHistoryService scihs = new SalesConsumeInventoryHistoryService();
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 初始化分页信息
		int pageSize = 15;
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		SalesConsumeDetailReportForm scrForm = new SalesConsumeDetailReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		// 判断页面是否初始化显示
		List<SalesConsumeDetailReportForm> list = null;
		if(!checkInitState(request, pageSize)){
			list = scihs.queryConsumeDetailHistory(request, pageSize, scrForm, users);
			request.setAttribute("prompt", "view");
		}else {
			//默认按升,千克统计
			request.setAttribute("countByUnit", true);
		}
		// 默认开始时间与结束时间
		/*if(StringUtil.isEmpty(scrForm.getBeginDate()) && StringUtil.isEmpty(scrForm.getEndDate())){
			String dateStr = Dateutil.formatDate(Dateutil.getCurrentDate());
			request.setAttribute("beginDate", dateStr);
			request.setAttribute("endDate", dateStr);
		}*/
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
