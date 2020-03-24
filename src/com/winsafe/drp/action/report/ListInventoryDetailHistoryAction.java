package com.winsafe.drp.action.report;

import java.util.ArrayList;
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
import com.winsafe.drp.dao.InventoryDetailReport;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.server.SalesConsumeInventoryHistoryService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;

public class ListInventoryDetailHistoryAction extends BaseAction{
	private SalesConsumeInventoryHistoryService scihs = new SalesConsumeInventoryHistoryService();
	private AppRegion appRegion = new AppRegion();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		int pageSize = 15;
		try {
			List<InventoryDetailReport> reports = new ArrayList<InventoryDetailReport>();
			Map map = new HashMap(request.getParameterMap());
			if(!checkInitState(request, pageSize)){
				reports = scihs.queryInventoryDetailHistory(request, pageSize, map, users);
				request.setAttribute("prompt", "view");
			}else {
				//默认按升,千克统计
				request.setAttribute("countByUnit", true);
			}
			// 默认结束时间
			/*if(StringUtil.isEmpty((String)map.get("EndDate"))){
				String dateStr = Dateutil.formatDate(Dateutil.getCurrentDate());
				request.setAttribute("EndDate", dateStr);
			}*/
			List<Region> regions = appRegion.getAllRegion();
			request.setAttribute("regions", regions);
		    request.setAttribute("rls", reports);
		    DBUserLog.addUserLog(request, "列表");
		    return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
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
