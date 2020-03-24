package com.winsafe.drp.action.warehouse;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutStockWasteBookTotalAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String isshowBatch = request.getParameter("isShowbatch");
		try {
//			String condition = " s.productid=p.id ";			
			
			String condition = " s.productid=p.id ";
			condition += " and s.warehouseid in (select wv.wid from  Warehouse_Visit as wv where wv.userid=" + userid + ")";

			String[] tablename = { "StockWasteBook" };
			String whereSql = getWhereSql(tablename);

			String timeCondition = getTimeCondition("RecordDate");
			String blur = getKeyWordCondition("s.batch");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			whereSql = whereSql +leftblur +  timeCondition + blur  + condition;
//			whereSql = whereSql + timeCondition + blur +condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppStockWasteBook aswb = new AppStockWasteBook();
			
			List sals = new ArrayList();
			if(isshowBatch!=null){
				sals = aswb.getStockWasteBookTotal2(whereSql);
			}else{
				sals = aswb.getStockWasteBookTotalNoBatch(whereSql);
			}

			if (sals.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListStockWasteBook.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request,isshowBatch);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,7,"库存管理>>导出库存报表");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request,String isshowBatch) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WarehouseService ws = new WarehouseService();
		Map<String,Double> boxUnitMap  = new HashMap<String, Double>();
		Map<String,Double> scatterUnitMap  = new HashMap<String, Double>();
		 
		Map<String,Product> pMap = new HashMap<String, Product>();
		AppProduct ap =new AppProduct();
		
		
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
			sheets[j].addCell(new Label(0, start, "库存报表(导出)",wchT));
			sheets[j].addCell(new Label(0, start+1, "机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "仓库:",seachT));
			sheets[j].addCell(new Label(3, start+1, ws.getWarehouseName(request.getParameter("WarehouseID").toString())));
			sheets[j].addCell(new Label(4, start+1, "批次:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+2, "产品类别:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("psname")));
			sheets[j].addCell(new Label(2, start+2, "产品:",seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("ProductName")));			
			sheets[j].addCell(new Label(4, start+2, "日期:",seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));			
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "仓库编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "仓库名称",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "产品类别",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "内部编码",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "产品名称",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "规格",wcfFC));
			if(isshowBatch!=null){
				sheets[j].addCell(new Label(6, start+4, "批次",wcfFC));
				sheets[j].addCell(new Label(7, start+4, "单位",wcfFC));
				sheets[j].addCell(new Label(8, start+4, "入库数量(箱)",wcfFC));
				sheets[j].addCell(new Label(9, start+4, "入库数量(EA)",wcfFC));
				sheets[j].addCell(new Label(10, start+4, "出库数量(箱)",wcfFC));
				sheets[j].addCell(new Label(11, start+4, "出库数量(EA)",wcfFC));
			}else{
				sheets[j].addCell(new Label(6, start+4, "单位",wcfFC));
				sheets[j].addCell(new Label(7, start+4, "入库数量(箱)",wcfFC));
				sheets[j].addCell(new Label(8, start+4, "入库数量(EA)",wcfFC));
				sheets[j].addCell(new Label(9, start+4, "出库数量(箱)",wcfFC));
				sheets[j].addCell(new Label(10, start+4, "出库数量(EA)",wcfFC));
			}
			
//			sheets[j].addCell(new Label(10, start+4, "结存数量",wcfFC));
			int row = 0;
//			double totalst=0.00;
//			double totalcost=0.00;
//			double allcost=0.00;
			AppFUnit af = new AppFUnit();
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("warehouseid").toString()));
				sheets[j].addCell(new Label(1, row, ws.getWarehouseName(p.get("warehouseid").toString())));
				String productid = p.get("productid").toString();
				
				sheets[j].addCell(new Label(2, row, p.get("sortname").toString()));
				
				sheets[j].addCell(new Label(3, row, p.get("nccode").toString()));
				sheets[j].addCell(new Label(4, row, p.get("productname").toString()));
				sheets[j].addCell(new Label(5, row, p.get("specmode").toString()));
				
				
				//每箱多少kg
	        	Double Xquantity = boxUnitMap.get(productid);
	        	if(Xquantity == null){
	        		Xquantity = af.getXQuantity(productid, 2);
	        		boxUnitMap.put(productid, Xquantity);
	        	}
	            
	          //每小包装kg数
	        	Double scaq = scatterUnitMap.get(productid);
	        	if(scaq == null){
	        		Product product = pMap.get(productid);
	        		if(product==null){
	        			product = ap.getProductByID(productid);
	        			if(product==null){
	        				//说明产品不存在
	        				product = new Product();
	        			}
	        			pMap.put(productid, product);
	        		}
	        		
	        		scaq = af.getXQuantity(productid, product.getScatterunitid());
	        		scatterUnitMap.put(productid, scaq);
	        	}
	        	
	        	//收入箱
	        	Double IBoxNum = (double)((Double)ArithDouble.div(Double.valueOf(p.get("inquantity").toString()), Xquantity)).intValue();
	        	Double other = ArithDouble.sub(Double.valueOf(p.get("inquantity").toString()), ArithDouble.mul(Xquantity, IBoxNum));
	        	//收入EA
	        	Double IScatterNum = ArithDouble.div(other, scaq);
	        	
	        	//支出箱
	        	Double OBoxNum = (double)((Double)ArithDouble.div(Double.valueOf(p.get("outquantity").toString()), Xquantity)).intValue();
				other = ArithDouble.sub(Double.valueOf(p.get("outquantity").toString()), ArithDouble.mul(Xquantity, OBoxNum));
				//支出EA
				Double OScatterNum = ArithDouble.div(other, scaq);
				
				if(isshowBatch!=null){
					sheets[j].addCell(new Label(6, row, p.get("batch").toString()));
					sheets[j].addCell(new Label(7, row,"kg"));
					sheets[j].addCell(new Number(8, row, IBoxNum,QWCF));
					sheets[j].addCell(new Number(9, row, IScatterNum,QWCF));
					sheets[j].addCell(new Number(10, row, OBoxNum,QWCF));
					sheets[j].addCell(new Number(11, row, OScatterNum,QWCF));
				}else{
					sheets[j].addCell(new Label(6, row,"kg"));
					sheets[j].addCell(new Number(7, row, IBoxNum,QWCF));
					sheets[j].addCell(new Number(8, row, IScatterNum,QWCF));
					sheets[j].addCell(new Number(9, row, OBoxNum,QWCF));
					sheets[j].addCell(new Number(10, row, OScatterNum,QWCF));
					
				}
				
				//Integer sunit = Integer.valueOf(p.get("sunit").toString());
				
				
//				double inquantity = af.getStockpileQuantity2(productid, sunit,Double.valueOf(p.get("inquantity").toString()));
//				double outquantity = af.getStockpileQuantity2(productid, sunit,Double.valueOf(p.get("outquantity").toString()));
//				double quantity = af.getStockpileQuantity2(productid, sunit,Double.valueOf(p.get("quantity").toString()));
//				
//				sheets[j].addCell(new Number(8, row, inquantity,QWCF));
//				sheets[j].addCell(new Number(9, row, outquantity,QWCF));
//				sheets[j].addCell(new Number(10, row, quantity,QWCF));
//				totalst += inquantity;
//				totalcost += outquantity;
//				allcost += quantity;
			}
			
//			sheets[j].addCell(new Label(0, row + 1, "合计："));			
//			sheets[j].addCell(new Number(8, row + 1,totalst,wcfN));
//			sheets[j].addCell(new Number(9, row + 1,totalcost,wcfN));
//			sheets[j].addCell(new Number(10, row + 1,allcost,wcfN));

		}
		workbook.write();
		workbook.close();
		os.close();
	}

	
}
