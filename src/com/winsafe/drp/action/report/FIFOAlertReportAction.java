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
import com.winsafe.drp.dao.AppFIFOAlertReport;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.FIFOAlertReport;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;

public class FIFOAlertReportAction extends BaseAction{
	private static Logger logger = Logger.getLogger(FIFOAlertReportAction.class);
	private AppFIFOAlertReport appFIFOAlertReport = new AppFIFOAlertReport();
	private AppRegion appRegion = new AppRegion();
	private AppProduct appProduct = new AppProduct();
	private AppFUnit appFUnit = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//初始化
		super.initdata(request);
		int pageSize=10;
		try{
			Map paraMap = new HashMap(request.getParameterMap());
			List<FIFOAlertReport> reports = new ArrayList<FIFOAlertReport>();
			if(!checkInitState(request, pageSize)){
				
				List resultList = appFIFOAlertReport.getFIFOAlertReportNew(request, pageSize, users);
				request.setAttribute("prompt", "view");
				Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
				for(int i=0;i<resultList.size();i++){
					Map map = (Map) resultList.get(i);
					FIFOAlertReport report = new FIFOAlertReport();
					MapUtil.mapToObject(map, report);
					
					Product product = appProduct.loadProductById(report.getProductId());
					report.setProductNameen(product.getProductnameen());
					report.setPackSizeNameEn(product.getPackSizeNameEn());
					report.setMatericalChDes(product.getMatericalChDes());
					
					if(StringUtil.isEmpty((String)paraMap.get("countByUnit"))) {
						if(checkRate(product.getId(),product.getSunit(),Constants.DEFAULT_UNIT_ID, funitMap)){
							// 获取库存数量
							report.setShipQuantity(changeUnit(product.getId(), product.getSunit(), report.getShipQuantity(), Constants.DEFAULT_UNIT_ID,funitMap));
							report.setStockPile(changeUnit(product.getId(), product.getSunit(), report.getStockPile(), Constants.DEFAULT_UNIT_ID,funitMap));
							report.setUnitId(Constants.DEFAULT_UNIT_ID);
						}
					} else {
						//转换成计量单位
						if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
							Double shipQuantity = ArithDouble.mul(report.getShipQuantity(), product.getBoxquantity());
							report.setShipQuantity(shipQuantity);
							Double stockQuantity = ArithDouble.mul(report.getStockPile(), product.getBoxquantity());
							report.setStockPile(stockQuantity);
							report.setUnitId(product.getCountunit());
						}
					}
					
					reports.add(report);
					
				}
				
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
			List<Region> regions = appRegion.getAllRegion();
			request.setAttribute("regions", regions);
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

}
