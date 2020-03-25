package com.winsafe.drp.action.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.ProductVlidateReportForm;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.SalesConsumeReportForm;
import com.winsafe.drp.server.ProductVlidataReportServer;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;
import common.Logger;

public class ProductValidateReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ProductValidateReportAction.class);
	
	private ProductVlidataReportServer pvServer = new ProductVlidataReportServer();
	
	private AppRegion appRegion = new AppRegion();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 初始化分页信息
		int pageSize = 10;
		// 获取查询条件
		Map map = new HashMap(request.getParameterMap());
		ProductVlidateReportForm pvrForm = new ProductVlidateReportForm();
		MapUtil.mapToObject(MapUtil.changeKeyLow(map), pvrForm);
		//页面图的数据
		String categories = "[]";
		String totleData = "[]";
		String trueData = "[]";
		String cateType = "";
		// 判断页面是否初始化显示
		List<ProductVlidateReportForm> list = null;
		Properties systemPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		String pvQueryCount = systemPro.getProperty("productValiQueryCount");
		if(!checkInitState(request, pageSize)){
			// 查询数据报表
			list = pvServer.queryReport(request, pageSize, pvrForm,pvQueryCount,users);
			request.setAttribute("prompt", "view");
			// 查询地图报表
			Map<String, String> charDateMap = pvServer.queryChartReport(pvrForm, users);
			categories = charDateMap.get("categories");
			totleData = charDateMap.get("totleData");
			trueData = charDateMap.get("trueData");
			cateType = "("+charDateMap.get("cateType")+")";
		}
		// 默认开始时间与结束时间
		if(StringUtil.isEmpty(pvrForm.getBeginDate()) && StringUtil.isEmpty(pvrForm.getEndDate())){
			String dateStr = Dateutil.formatDate(Dateutil.getCurrentDate());
			request.setAttribute("beginDate", dateStr);
			request.setAttribute("endDate", dateStr);
		}
		// 初始化区域
		List<Region> regions = appRegion.getAllRegion();
		request.setAttribute("regions", regions);
		request.setAttribute("list", list);
		request.setAttribute("categories",categories);
		request.setAttribute("totleData",totleData);
		request.setAttribute("trueData",trueData);
		request.setAttribute("cateType",cateType);
		request.setAttribute("pvQueryCount", pvQueryCount);
		
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
