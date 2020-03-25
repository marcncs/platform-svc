package com.winsafe.drp.action.newreport;

import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppSalesConsumHistory;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.SalesConsumMonthReportForm;
import com.winsafe.drp.dao.SalesConsumeReportForm;
import com.winsafe.drp.dao.SalesConsumptionInventory;
import com.winsafe.drp.server.SalesConsumeInventoryReportService;
import com.winsafe.drp.server.SalesConsumeReportService;
import com.winsafe.drp.service.report.SalesConsumptionInventoryMonthlyVolumeReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;

import common.Logger;

/**
 * 销售消耗库存月数量报表 Create Time 2015-10-8 下午02:00:23
 * 
 * @param scrForm
 * @return
 * @author Jacky.Chen
 */
public class SalesMonthlyReportAction extends BaseAction {
	private static Logger logger = Logger
			.getLogger(SalesMonthlyReportAction.class);
	private SalesMonthlyReportService scService = new SalesMonthlyReportService();
	private AppRegion appRegion = new AppRegion();
	private SalesConsumptionInventoryMonthlyVolumeReportService service = new SalesConsumptionInventoryMonthlyVolumeReportService();
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化参数
		super.initdata(request);
		// 初始化分页信息
		int pageSize = 15;
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		SalesConsumMonthReportForm scrForm = new SalesConsumMonthReportForm();
		// 将map中对应的值赋值给对应的实体类
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
		// 默认开始时间与结束时间
		if(StringUtil.isEmpty((String)map.get("beginDate")) && StringUtil.isEmpty((String)map.get("endDate"))){
			request.setAttribute("beginDate", Dateutil.formatDate(Dateutil.addMonth2Date(-1, Dateutil.getCurrentDate())));
			request.setAttribute("endDate", Dateutil.formatDate(Dateutil.getCurrentDate()));
		}
		//动态获取时间并动态添加至页面
		List<String> titleList = service.getTitleList(request);
		List<SalesConsumptionInventory> titleBeginList = new ArrayList<SalesConsumptionInventory>();
		
		// 判断页面是否初始化显示
		List<SalesConsumMonthReportForm> list = new ArrayList<SalesConsumMonthReportForm>();
		if (!checkInitState(request, pageSize)) {
			list = scService.queryReport(request, pageSize, scrForm, users);
			
//			scForm.setEndInventory(endInventory);
//			list.add(scForm);
			
			request.setAttribute("prompt", "view");
		}
		// 初始化区域
		List<Region> regions = appRegion.getAllRegion();
		request.setAttribute("titleList", titleList);
		request.setAttribute("titleBeginList", titleBeginList);
		request.setAttribute("regions", regions);
		
		request.setAttribute("list", list);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}

	/**
	 * 判断页面是否初始化页面 Create Time 2015-10-8 下午02:10:55
	 * 
	 * @param scrForm
	 * @return
	 * @author Jacky.Chen
	 */
	private Boolean checkInitState(HttpServletRequest request, Integer pageSize) {
		String queryFlag = request.getParameter("queryFlag");
		if (queryFlag == null) {
			request.setAttribute("queryFlag", 1);
			BasePage bp = new BasePage(request, pageSize);
			bp.getPageSite();
			bp.setPage(new Page(0, pageSize, 0));
			return true;
		} else {
			return false;
		}

	}

}
