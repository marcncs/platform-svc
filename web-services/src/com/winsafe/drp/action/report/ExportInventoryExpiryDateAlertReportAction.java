package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppInventoryExpiryDateAlertReport;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.InventoryExpiryDateAlertReportForm;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class ExportInventoryExpiryDateAlertReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExportInventoryExpiryDateAlertReportAction.class);
	private AppInventoryExpiryDateAlertReport app = new AppInventoryExpiryDateAlertReport();
	private AppRegion appRegion = new AppRegion();
	private AppFUnit appFUnit = new AppFUnit();
	private AppBaseResource abr = new AppBaseResource();
	

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//初始化
		super.initdata(request);
		int pageSize = -1;
		
		List resultList = app.getInventoryExpiryDateAlertReport(request, pageSize, users);
		if (resultList.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		Map paraMap = new HashMap(request.getParameterMap());
		List<InventoryExpiryDateAlertReportForm> reports = new ArrayList<InventoryExpiryDateAlertReportForm>();
		Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
		Map<Integer, String> unitIdValueMap = new HashMap<Integer, String>();
		for(int i=0; i<resultList.size(); i++){
			Map map = (Map) resultList.get(i);
			InventoryExpiryDateAlertReportForm report = new InventoryExpiryDateAlertReportForm();
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
				if(checkRate(report.getProductId(), report.getsUnit(), Constants.DEFAULT_UNIT_ID, funitMap)){
					report.setDisplayUnit(getUnitNameByUnitId(Constants.DEFAULT_UNIT_ID, unitIdValueMap));
					report.setDisplayQuantity(report.getsUnitQuantity());
				}
			} else {
				report.setDisplayUnit(getUnitNameByUnitId(report.getCountUnit(), unitIdValueMap));
				report.setDisplayQuantity(report.getCountUnitQuantity());
			}
			
			reports.add(report);
		}
		resultList = null;
		
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=InventoryExpiryDateAlertReport"+Dateutil.getCurrentDateTimeString()+".xls");
		response.setContentType("application/msexcel");
		writeXls(reports, os, request);
		os.flush();
		os.close();
		DBUserLog.addUserLog(request, "列表");
		reports = null;
		return null;
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
	 * @param list
	 * @param os
	 * @param request
	 * @throws Exception
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeXls(List<InventoryExpiryDateAlertReportForm> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		int m = 0;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;

			sheets[j].mergeCells(0, start, 23, start);
			sheets[j].addCell(new Label(0, start, "库存有效期预警报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("oname2")));
			sheets[j].addCell(new Label(m++, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packSizeName")));
			sheets[j].addCell(new Label(m++, start + 2, "批次:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("batch")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "当前有效期(天):", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("startDate") + " - " + request.getParameter("endDate")));
			sheets[j].addCell(new Label(m++, start + 3, "发货有效期(天):", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("dStartDate") + " - " + request.getParameter("dEndDate")));
			sheets[j].addCell(new Label(m++, start + 3, "单位:", seachT));
			if (request.getParameter("countByUnit").equals("true")) {
				sheets[j].addCell(new Label(m++, start + 3, "升,千克"));
			} else {
				sheets[j].addCell(new Label(m++, start + 3, "件"));
			}
			
			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "区\nRegion", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "省份\nProvince", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码\nSAP Code", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称\nPD Name", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库\nWarehouse", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号\nMaterial Code", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料中文\nMaterial Description CN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料英文\nMaterial Description EN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称中文\nProduct Name CN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称英文\nProduct Name EN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文\nPack Size EN", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "批次\nBatch No", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "库存数量\nVolume", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位\nUnit ", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "生产日期\nProduction Date", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "过期日期\nExpiry Date", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "当前有效期(天)\nShelf Life Days", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "当前有效期(月)\nShelf Life Month", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货单号\nSAP Delivery No", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货日期\nDelivery Date ", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货有效期(天)\nShelf Life days on Delivery Date", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货有效期(月)\nShelf Life Month on Delivery Date ", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				InventoryExpiryDateAlertReportForm rs = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, rs.getInRegionName() == null ? "" : rs.getInRegionName()));
				sheets[j].addCell(new Label(m++, row, rs.getInProvinceName() == null ? "" : rs.getInProvinceName()));
				sheets[j].addCell(new Label(m++, row, rs.getInOrganOecode() == null ? "" : rs.getInOrganOecode()));
				sheets[j].addCell(new Label(m++, row, rs.getInOrganName() == null ? "" : ESAPIUtil.decodeForHTML(rs.getInOrganName())));
				sheets[j].addCell(new Label(m++, row, rs.getInWarehouseName() == null ? "" : rs.getInWarehouseName()));
				sheets[j].addCell(new Label(m++, row, rs.getMcode() == null ? "" : rs.getMcode()));
				sheets[j].addCell(new Label(m++, row, rs.getMatericalchdes() == null ? "" : rs.getMatericalchdes()));
				sheets[j].addCell(new Label(m++, row, rs.getMatericalendes() == null ? "" : rs.getMatericalendes()));
				sheets[j].addCell(new Label(m++, row, rs.getProductName() == null ? "" : rs.getProductName()));
				sheets[j].addCell(new Label(m++, row, rs.getProductNameEn() == null ? "" : rs.getProductNameEn()));
				sheets[j].addCell(new Label(m++, row, rs.getPacksizeNameEn() == null ? "" : rs.getPacksizeNameEn()));
				sheets[j].addCell(new Label(m++, row, rs.getBatch() == null ? "" : rs.getBatch()));
				sheets[j].addCell(new Number(m++, row, getDouble(rs.getDisplayQuantity())));
				sheets[j].addCell(new Label(m++, row, rs.getDisplayUnit() == null ? "" : rs.getDisplayUnit()));
				sheets[j].addCell(new Label(m++, row, rs.getProductionDateStr() == null ? "" : rs.getProductionDateStr()));
				sheets[j].addCell(new Label(m++, row, rs.getExpiryDateStr() == null ? "" : rs.getExpiryDateStr()));
				sheets[j].addCell(new Number(m++, row, rs.getShelfLifeDays() == null ? 0 : rs.getShelfLifeDays()));
				sheets[j].addCell(new Number(m++, row, getDouble(rs.getShelfLifeMonth())));
				sheets[j].addCell(new Label(m++, row, rs.getBillNccode() == null ? "" : rs.getBillNccode()));
				sheets[j].addCell(new Label(m++, row, rs.getAuditDateStr() == null ? "" : rs.getAuditDateStr()));
				sheets[j].addCell(new Number(m++, row, rs.getShelfLifeDaysOnDeliveryDate() == null ? 0 : rs.getShelfLifeDaysOnDeliveryDate()));
				sheets[j].addCell(new Number(m++, row, getDouble(rs.getShelfLifeMonthOnDeliveryDate())));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private Double getDouble(Double value) {
		if(value == null) {
			return 0d;
		}
		return value;
	}
}
