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
import com.winsafe.drp.dao.AppBKDReturnSSCCCodeReport;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.BKDReturnSSCCCodeReportForm;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.FUnitUtil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;

public class BKDReturnSSCCCodeReportAction extends BaseAction{
	
	private static Logger logger = Logger.getLogger(BKDReturnSSCCCodeReportAction.class);
	private AppBKDReturnSSCCCodeReport abrssr = new AppBKDReturnSSCCCodeReport();
	private AppRegion appRegion = new AppRegion();
	private AppFUnit appFUnit = new AppFUnit();
	private AppBaseResource abr = new AppBaseResource();
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		//初始化
		super.initdata(request);
		int pageSize=10;
		try{
			Map paraMap = new HashMap(request.getParameterMap());
			List<BKDReturnSSCCCodeReportForm> reports = new ArrayList<BKDReturnSSCCCodeReportForm>();
			if(!checkInitState(request, pageSize)){
				List resultList = abrssr.getBKDReturnSSCCCodeReport(request, pageSize, users);
				Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
				Map<Integer, String> unitIdValueMap = new HashMap<Integer, String>();
				for(int i=0; i<resultList.size(); i++){
					Map map = (Map) resultList.get(i);
					BKDReturnSSCCCodeReportForm report = new BKDReturnSSCCCodeReportForm();
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
					
					if(StringUtil.isEmpty((String)paraMap.get("countByUnit"))) {
						report.setDisplayUnit(getUnitNameByUnitId(Constants.DEFAULT_UNIT_ID, unitIdValueMap));
						report.setDisplayQuantity(report.getsUnitQuantity());
					} else {
						report.setDisplayUnit(getUnitNameByUnitId(report.getCountUnit(), unitIdValueMap));
						report.setDisplayQuantity(report.getCountUnitQuantity());
					}
					
					reports.add(report);
				}
				unitIdValueMap = null;
				resultList = null;
				
				request.setAttribute("prompt", "view");
			}else {
				//默认按升,千克统计
				request.setAttribute("countByUnit", true);
			}
			// 默认开始时间与结束时间
			if(StringUtil.isEmpty((String)paraMap.get("beginDate")) && StringUtil.isEmpty((String)paraMap.get("endDate"))){
				String dateStr = Dateutil.formatDate(Dateutil.getCurrentDate());
				request.setAttribute("beginDate", dateStr);
				request.setAttribute("endDate", dateStr);
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
	
	/**
	 * 检查单位是否可以正常转化
	 * Create Time 2015-1-30 下午02:27:02 
	 * @param scForm
	 * @param funitMap
	 * @return
	 * @author lipeng
	 */
	private Boolean checkRate(String productId, Integer srcUnitId, Integer desUnitId, Map<String,FUnit>  rateMap){
		Boolean flag = true;
		try{
			String srcKey = productId + "_" + srcUnitId;
			FUnit srcFunit = rateMap.get(srcKey);
			if(srcFunit == null){
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + srcUnitId + ") ] not exist");
			}
			String desKey = productId + "_" + desUnitId;
			FUnit desFunit = rateMap.get(desKey);
			if(desFunit == null){
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + desUnitId + ") ] not exist");
			}
			Double desRate = desFunit.getXquantity();
			if(desRate == 0d){
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + desUnitId + ") ] is zero");
			}
		}catch (Exception e) {
			logger.error("", e);
			flag = false;
		}
		return flag;
	}
	/**
	 * 根据单位转化数量
	 * Create Time 2015-1-30 下午02:07:55 
	 * @param productId
	 * @param srcUnit
	 * @param srcQuantity
	 * @param desUnit
	 * @param rateMap
	 * @return
	 * @author lipeng
	 */
	private Double changeUnit(String productId,Integer srcUnit,Double srcQuantity,Integer desUnit,Map<String,FUnit> rateMap){
		// 先换算出最小单位数量
		String srcKey = productId + "_" + srcUnit;
		Double srcRate = rateMap.get(srcKey).getXquantity();
		Double minUnitQuantity = ArithDouble.mul(srcQuantity, srcRate);
		// 再换算成目标单位数量
		String desKey = productId + "_" + desUnit;
		Double desRate = rateMap.get(desKey).getXquantity();
		Double desQuantity = ArithDouble.div(minUnitQuantity, desRate);
		return desQuantity;
	}

	
	private String getUnitNameByUnitId(Integer unitid, Map<Integer, String> unitIdValueMap) throws Exception{
		if(unitIdValueMap == null || unitid == null){
			return "";
		}
		if(!unitIdValueMap.containsKey(unitid)){
			BaseResource br = abr.getBaseResourceValue("CountUnit", unitid);
			if(br != null && !StringUtil.isEmpty(br.getTagsubvalue())){
				unitIdValueMap.put(unitid, br.getTagsubvalue());
			}
		}
		return unitIdValueMap.get(unitid);
	}
}
