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
import com.winsafe.drp.dao.AppStockPileAgeing;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.StockPileAgeing;
import com.winsafe.drp.dao.StockPileReportService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ExcPutAgeingTotalReportAction extends BaseAction {
	AppStockPileAgeing aspa = new AppStockPileAgeing();
	StockPileReportService sprs = new StockPileReportService();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//int pagesize = 20;
		super.initdata(request);
		
		String isshowBatch = request.getParameter("isShowbatch");
		
		 String orderSql = request.getParameter("orderbySql");
		 //String orderSqlName = request.getParameter("orderbySqlShowName");
		 Map<String, StockPileAgeing> ageingMap = aspa.getAgeingMap();
		 String ageingSql = sprs.getSql(ageingMap, "ceil(sysdate-to_date(batch,'yyyymmdd'))");
		 StockPileAgeing spa =null;
		try {
			String condition = " s.productid=p.id and ps.structcode = p.psid ";
			condition += " and s.warehouseid in (select wv.wid from  Warehouse_Visit  wv where wv.userid=" + userid + ")";
			
			WarehouseService aw = new WarehouseService();
			String[] tablename = { "PurchaseSalesReport" };
			String whereSql = getWhereSql(tablename);
			//String timeCondition = getTimeCondition("recorddate");
			String color = request.getParameter("ageing");
			if(!StringUtil.isEmpty(color)){
				spa =ageingMap.get(color);
				condition+=" and ceil(sysdate-to_date(batch,'yyyymmdd')) >="+spa.getTagMinValue()+" and ceil(sysdate-to_date(batch,'yyyymmdd'))<="+spa.getTagMaxValue();
			}
			
			String blur = getKeyWordCondition("s.batch");
	        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			//whereSql = whereSql +leftblur +  timeCondition + blur  + condition;
			whereSql = whereSql +leftblur + blur  + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			
			List sals = new ArrayList();
			if(isshowBatch!=null){
				sals = sprs.getAgeingTotal(whereSql, ageingSql);
			}else{
				if(orderSql!=null){
					orderSql = orderSql.replaceAll("batch", "productid");
				}
				
				sals = sprs.getAgeingTotalNoBatch(whereSql, ageingSql);
			}
			List alp = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				Map p = (Map) sals.get(i);
				Double quantity = Double.valueOf(p.get("quantity").toString());

				psf.setWarehouseid(p.get("warehouseid").toString());
				psf.setWarehourseidname(aw.getWarehouseName(p.get("warehouseid").toString()));
				psf.setProductid(p.get("productid").toString());
				psf.setBarcode(p.get("nccode")==null?"":p.get("nccode").toString());
				psf.setPsproductname(p.get("productname").toString());
				psf.setPsspecmode(p.get("specmode").toString());
				psf.setSortName(p.get("sortname").toString());
				psf.setTagColor(p.get("color").toString());
				
				psf.setMinValue(ageingMap.get(psf.getTagColor()).getTagMinValue());
				psf.setMaxValue(ageingMap.get(psf.getTagColor()).getTagMaxValue());
				if(isshowBatch!=null){
					psf.setBatch(p.get("batch").toString());
				}
				Integer sunit = Integer.valueOf(p.get("countunit").toString());
				psf.setCountunit(sunit);
				//psf.setDate(p.get("recorddate").toString().substring(0, 10));
				psf.setStockpile(quantity);
				alp.add(psf);
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=AgeingTotalReport.xls");
			response.setContentType("application/msexcel");
			writeXls(alp, os, request,isshowBatch, spa);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,7,"库存报表>>导出库龄汇总报表");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request,String isshowBatch, StockPileAgeing spa) throws NumberFormatException, Exception {
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
			sheets[j].addCell(new Label(0, start, "库龄汇总报表(导出)",wchT));
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
			sheets[j].addCell(new Label(4, start+2, "库龄范围（天）:",seachT));
			sheets[j].addCell(new Label(5, start+2, spa == null ? "全部" :spa.getTagMinValue()+"-"+spa.getTagMaxValue()+"天"));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "库龄范围（天）",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "仓库编号",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "仓库名称",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "内部编码",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "产品名称",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "规格",wcfFC));
			
			if(isshowBatch!=null){
				sheets[j].addCell(new Label(6, start+4, "批次",wcfFC));
				sheets[j].addCell(new Label(7, start+4, "单位",wcfFC));
				sheets[j].addCell(new Label(8, start+4, "库存数量",wcfFC));
				
			}else{
				sheets[j].addCell(new Label(6, start+4, "单位",wcfFC));
				sheets[j].addCell(new Label(7, start+4, "库存数量",wcfFC));
				
			}
			
			int row = 0;
			double count = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
//				Map p = (Map) list.get(i);
				ProductStockpileForm psf = (ProductStockpileForm) list.get(i);
				
//				sheets[j].addCell(new Label(0, row, p.get("warehouseid").toString()));
//				sheets[j].addCell(new Label(1, row, ws.getWarehouseName(p.get("warehouseid").toString())));
//				sheets[j].addCell(new Label(2, row, p.get("nccode").toString()));
//				sheets[j].addCell(new Label(3, row, p.get("productname").toString()));
//				sheets[j].addCell(new Label(4, row, p.get("specmode").toString()));
//				sheets[j].addCell(new Label(5, row, p.get("ageing").toString()));
				sheets[j].addCell(new Label(0, row, psf.getMinValue()+"-"+psf.getMaxValue()));
				sheets[j].addCell(new Label(1, row, psf.getWarehouseid()));
				sheets[j].addCell(new Label(2, row, ws.getWarehouseName(psf.getWarehouseid())));
				sheets[j].addCell(new Label(3, row, psf.getBarcode()));
				sheets[j].addCell(new Label(4, row, psf.getPsproductname()));
				sheets[j].addCell(new Label(5, row, psf.getPsspecmode()));
	        	//库存
//	        	Double IBoxNum = Double.valueOf(p.get("quantity").toString());
				Double IBoxNum = Double.valueOf(psf.getStockpile());
				count += IBoxNum;
//				if(isshowBatch!=null){
//					sheets[j].addCell(new Label(6, row, p.get("batch").toString()));
//					sheets[j].addCell(new Label(7, row, "支"));
//					sheets[j].addCell(new Number(8, row, IBoxNum,QWCF));
//				
//					
//				}else{
//					sheets[j].addCell(new Label(6, row, "支"));
//					sheets[j].addCell(new Number(7, row, IBoxNum,QWCF));
//					
//				}
				if(isshowBatch!=null){
					sheets[j].addCell(new Label(6, row, psf.getBatch()));
					sheets[j].addCell(new Label(7, row, "支"));
					sheets[j].addCell(new Number(8, row, IBoxNum,QWCF));
				
					
				}else{
					sheets[j].addCell(new Label(6, row, "支"));
					sheets[j].addCell(new Number(7, row, IBoxNum,QWCF));
					
				}
			}
			if(isshowBatch!=null){
				sheets[j].addCell(new Label(7, row+1, "总数量："));
				sheets[j].addCell(new Number(8, row+1, count,QWCF));
			
				
			}else{
				sheets[j].addCell(new Label(6, row+1, "总数量："));
				sheets[j].addCell(new Number(7, row+1, count,QWCF));
				
			}
			
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
