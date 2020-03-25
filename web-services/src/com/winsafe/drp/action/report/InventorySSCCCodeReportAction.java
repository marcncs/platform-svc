package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInventorySSCCCodeReport;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.InventorySSCCCodeReportForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;

public class InventorySSCCCodeReportAction extends BaseAction{
	
	//private static Logger logger = Logger.getLogger(InventorySSCCCodeReportAction.class);
	private AppInventorySSCCCodeReport aissr = new AppInventorySSCCCodeReport();
	private AppRegion appRegion = new AppRegion();
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		//初始化
		super.initdata(request);
		int pageSize=10;
		try{
			List<InventorySSCCCodeReportForm> reports = new ArrayList<InventorySSCCCodeReportForm>();
			if(!checkInitState(request, pageSize)){
				List resultList = aissr.getInventorySSCCCodeReport(request, pageSize, users);
				for(int i=0; i<resultList.size(); i++){
					Map map = (Map) resultList.get(i);
					InventorySSCCCodeReportForm report = new InventorySSCCCodeReportForm();
					MapUtil.mapToObject(map, report);
					if(report.getAuditDate() != null){
						report.setAuditDateStr(DateUtil.formatDate(report.getAuditDate()));
					}
					if(report.getProductionDate() != null){
						report.setProductionDateStr(DateUtil.formatDate(report.getProductionDate()));
					}
					if(report.getExpiryDate() != null){
						report.setExpiryDateStr(DateUtil.formatDate(report.getExpiryDate()));
					}
					reports.add(report);
				}
				resultList = null;
				
				request.setAttribute("prompt", "view");
			}
			
			request.setAttribute("regions", appRegion.getAllRegion());
		    request.setAttribute("rls", reports);
		    DBUserLog.addUserLog(request, "列表");
		    return mapping.findForward("list");
		}catch(Exception e){
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
