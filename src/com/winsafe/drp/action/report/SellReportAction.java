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
import com.winsafe.drp.dao.ReportForm;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.StringUtil;
import common.Logger;

public class SellReportAction extends BaseAction {
	private Logger logger = Logger.getLogger(SellReportAction.class);
	private SellReportService srs = new SellReportService();
	private CountryAreaService aca = new CountryAreaService();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 分页
		int pageSize = 15;
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		
		if(!checkInitState(request)){
			List<ReportForm> list = srs.queryReport(request,pageSize,map,users);
			request.setAttribute("prompt", "view");
			request.setAttribute("alp", list);
		}else {
			request.setAttribute("prompt", "notview");
			request.setAttribute("dataPage", 1);
			//默认按升,千克统计
			request.setAttribute("countByUnit", true);
		}
		//获取大区
		List list = aca.getProvinceObj();
		request.setAttribute("cals", list);
		// 默认开始时间与结束时间
		if(map.isEmpty() || (StringUtil.isEmpty(map.get("beginDate").toString()) && StringUtil.isEmpty(map.get("endDate").toString()))){
			String dateStr = Dateutil.formatDate(Dateutil.getCurrentDate());
			request.setAttribute("beginDate", dateStr);
			request.setAttribute("endDate", dateStr);
		}
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("success");
	}
	
	private Boolean checkInitState(HttpServletRequest request){
		String dataPage = request.getParameter("dataPage");
		if(dataPage == null){
			return true;
		}else {
			return false;
		}
	}

}