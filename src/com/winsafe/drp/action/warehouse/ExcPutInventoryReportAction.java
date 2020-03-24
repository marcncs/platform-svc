package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInventoryReport;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutInventoryReportAction extends BaseAction {
	AppInventoryReport air = new AppInventoryReport();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		
		String isshowBatch = request.getParameter("isShowbatch");
		
		 String orderSql = request.getParameter("orderbySql");
		
		try {
			String condition = " s.productid=p.id and ps.structcode=p.psid ";
			condition += " and s.warehouseid in (select wv.wid from  Warehouse_Visit  wv where wv.userid=" + userid + ")";
			WarehouseService aw = new WarehouseService();
			String[] tablename = { "InventoryReport" };
			String whereSql = getWhereSql(tablename);
			if(map.get("RecordDate")==null){
				map.put("RecordDate", DateUtil.getYestDay());
			}
			String timeCondition = "";
			if(map.get("RecordDate")!=null&&map.get("RecordDate").toString()!=""){
				timeCondition = DbUtil.getTimeCondition3(map, tmpMap, "RecordDate");
			}
			
			String blur = getKeyWordCondition("s.batch");
	        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			whereSql = whereSql +leftblur +  timeCondition + blur  + condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppStockWasteBook aswb = new AppStockWasteBook();
			
			List sals = new ArrayList();
			if(isshowBatch!=null){
				sals = air.getInventoryReport(whereSql);
			}else{
				sals = air.getInventoryReportNoBatch(whereSql);
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=InventoryReport.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request,isshowBatch);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,7,"进销存报表>>导出历史库存报表");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request,String isshowBatch) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WarehouseService ws = new WarehouseService();
		int snum = 1;
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
			sheets[j].mergeCells(0, start, 10, start);
			sheets[j].addCell(new Label(0, start, "历史库存报表(导出)",wchT));
			sheets[j].addCell(new Label(0, start+1, "机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "仓库:",seachT));
			sheets[j].addCell(new Label(3, start+1, ws.getWarehouseName(request.getParameter("WarehouseID").toString())));
			sheets[j].addCell(new Label(4, start+1, "批次:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+2, "产品:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("ProductName")));			
			sheets[j].addCell(new Label(2, start+2, "日期:",seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("RecordDate")));			
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "仓库编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "仓库名称",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "内部编码",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "产品名称",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "规格",wcfFC));
			if(isshowBatch!=null){
				sheets[j].addCell(new Label(5, start+4, "批次",wcfFC));
				sheets[j].addCell(new Label(6, start+4, "单位",wcfFC));
				sheets[j].addCell(new Label(7, start+4, "库存数量",wcfFC));
			}else{
				sheets[j].addCell(new Label(5, start+4, "单位",wcfFC));
				sheets[j].addCell(new Label(6, start+4, "库存数量",wcfFC));
			}
			
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("warehouseid").toString()));
				sheets[j].addCell(new Label(1, row, ws.getWarehouseName(p.get("warehouseid").toString())));
				sheets[j].addCell(new Label(2, row, p.get("nccode").toString()));
				sheets[j].addCell(new Label(3, row, p.get("productname").toString()));
				sheets[j].addCell(new Label(4, row, p.get("specmode").toString()));
				
	        	//库存
	        	Double IBoxNum = Double.valueOf(p.get("quantity").toString());
				if(isshowBatch!=null){
					sheets[j].addCell(new Label(5, row, p.get("batch").toString()));
					sheets[j].addCell(new Label(6, row, "支"));
					sheets[j].addCell(new Number(7, row, IBoxNum,QWCF));
					
				}else{
					sheets[j].addCell(new Label(5, row, "支"));
					sheets[j].addCell(new Number(6, row, IBoxNum,QWCF));
				}
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	
}
