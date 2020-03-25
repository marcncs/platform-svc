package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExportRepairByTaketicketAction extends BaseAction{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OutputStream os = null;
		//初始化参数
		super.initdata(request);
		//初始化各查询类
		AppWarehouse aw = new AppWarehouse();
		AppProduct ap = new AppProduct();
		AppTakeTicket att  = new AppTakeTicket();
		AppProductStruct aps = new AppProductStruct();
		AppProductStockpile apsa = new AppProductStockpile();
		//页面接收两个表中的属性值
		String[] tablename = { "TakeTicketDetailBatchBit", "WarehouseVisit" };
		
		//获取页面检索条件中的参数
		String productID = request.getParameter("ProductID");
		String outOrganID = request.getParameter("MakeOrganID");
		String keyWordLeft = request.getParameter("KeyWordLeft");
		String outWarehouseID = request.getParameter("WarehouseID");
		String isShowAssistQuantity = request.getParameter("isShowAssistQuantity");
		
		
		String whereSql1 = getWhereSql(tablename);
		//String whereSql2 = getWhereSql(tablename);
		//制单日期
		//String timeCondition = getTimeCondition("tt.makedate");
		String Condition = " tt.warehouseid = wv.wid and wv.userid=1 and tt.id = ttb.ttid and tt.IsAudit=1  and w.id=tt.warehouseid " +
				" and pstr.StructCode=p.PSID and p.id=ttb.productid ";
		String Condition1 = " and sam.id = tt.billno and sam.IsComplete = 0 and sam.isaudit = 1 ";
		
		if(null != outOrganID && !"".equals(outOrganID)){
			Condition1 += " and w.makeorganid = '"+outOrganID+"' ";
		}else{
			Condition1 += " and w.makeorganid = '"+users.getMakeorganid()+"' ";
		}
		if(null != outWarehouseID && !"".equals(outWarehouseID)){
			Condition1 += " and w.id = '"+outWarehouseID+"' ";
		}
		
		if(null != keyWordLeft && !"".equals(keyWordLeft)){
			Condition1 += " and p.psid = '"+keyWordLeft+"' ";
		}
		if(null != productID && !"".equals(productID)){
			Condition1 += " and ttb.productid = '"+productID+"' ";
		}
		
		//String Condition2 = " and sm.id = tt.billno and sm.IsComplete = 0 and sm.isaudit = 1 ";
		//绑定关键字
		String blur =DbUtil.getOrBlur(map, tmpMap, "w.warehousename","p.productname","p.nccode");
		whereSql1 = whereSql1 + blur+ Condition + Condition1; 
		whereSql1 = DbUtil.getWhereSql(whereSql1); 
		//whereSql2 = whereSql2 + blur+ Condition + Condition2; 
		//whereSql2 = DbUtil.getWhereSql(whereSql2); 
		//查询集合
		List ttlistBysam = att.getRepairListBySAMNoPage(whereSql1);
		//查询集合
		//List ttlistBysm = att.getRepairListBySM(request, pageSize, whereSql2);
		
		List alp = new ArrayList();
		
		for (int i = 0; i < ttlistBysam.size(); i++) {
			Map p = (Map) ttlistBysam.get(i);
			ProductStockpileForm psf = new ProductStockpileForm();
			Warehouse warehouse = aw.getWarehouseByID(p.get("warehouseid").toString());
			if(null != warehouse){
				psf.setWarehouseid(warehouse.getId());
				psf.setWarehourseidname(warehouse.getWarehousename());
			}
			Product product = ap.getProductByID(p.get("productid").toString());
			if(null != product){
				psf.setProductid(product.getId());
				psf.setNccode(product.getNccode());
				psf.setPsproductname(product.getProductname());
				psf.setPsspecmode(product.getSpecmode());
				ProductStruct pst = aps.getProductStructById(product.getPsid());
				if(null != pst){
					psf.setSortName(pst.getSortname());
				}
			}
			
			Double quantity, boxQuantity, scatterQuantity;
			//在途库存
			String q = (String) p.get("quantity");
			if(null == q){
				quantity = 0.0;
			}else{
				quantity = Double.valueOf(q);
			}
			//在途箱库存
			String b = (String) p.get("boxquantity");
			if(null == b){
				boxQuantity = 0.0;
			}else{
				boxQuantity = Double.valueOf(b);
			}
			String s = (String) p.get("scatterquantity");
			if(null == s){
				scatterQuantity = 0.0;
			}else{
				scatterQuantity = Double.valueOf(s);
			}
			
			double stockpile = apsa.getProductStockpileByProductID(product.getId(), 
					Long.valueOf(warehouse.getId()));
			psf.setStockpile(stockpile);
			psf.setRepairstockpile(quantity);
			psf.setTotalstockpile(psf.getStockpile() + psf.getRepairstockpile());
			
			if("1".equals(isShowAssistQuantity)){
				//箱到KG转换数
				double xtsQuantity = new AppFUnit().getXQuantity(psf.getProductid(), 2);
    			//散到KG转换数
    			double stsQuantity = new AppFUnit().getXQuantity(psf.getProductid(), product.getScatterunitid());
    			if(psf.getStockpile() <= 0.0){
    				psf.setAssistBoxStockpile(0.0);
					psf.setAssistEAStockpile(0.0);
    			}else{
    				//得到整箱数
	    			double box = ((Double)ArithDouble.div(psf.getStockpile(), xtsQuantity));
					//得到散数
					double tqu = ArithDouble.sub(psf.getStockpile(), ArithDouble.mul(xtsQuantity, (double)box));
					tqu = (Double)ArithDouble.div(tqu, stsQuantity);
					psf.setAssistBoxStockpile(box);
					if(tqu <= 0.0){
						psf.setAssistEAStockpile(0.0);
	    			}else{
	    				psf.setAssistEAStockpile(tqu);
	    			}
    			}
			}
			
			alp.add(psf);
		}

		if (alp.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=ExportReairByTaketicket.xls");
		response.setContentType("application/msexcel");
		writeXls(alp, isShowAssistQuantity, os, request);
		os.flush();
		return null;
	}
	
	public void writeXls(List<ProductStockpileForm> ttlist, String isFZfunitid, OutputStream os, HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organ = new OrganService();
		WarehouseService ws = new WarehouseService();
		int snum = 1;
		snum = ttlist.size() / 50000;
		if (ttlist.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];

		try {
			for (int j = 0; j < snum; j++) {
				sheets[j] = workbook.createSheet("sheet" + j, j);
				int currentnum = (j + 1) * 50000;
				if (currentnum >= ttlist.size()) {
					currentnum = ttlist.size();
				}
				int start = j * 50000;

				sheets[j].mergeCells(0, start, 12, start);
				sheets[j].addCell(new Label(0, start, "补货报表", wchT));
				sheets[j].addCell(new Label(0, start + 1, "发货机构:", seachT));
				sheets[j].addCell(new Label(1, start + 1, ""));
				sheets[j].addCell(new Label(2, start + 1, "发货仓库:", seachT));
				sheets[j].addCell(new Label(3, start + 1, ""));
				sheets[j].addCell(new Label(4, start + 1, "关键字:", seachT));
				sheets[j].addCell(new Label(5, start + 1, ""));

				sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
				sheets[j].addCell(new Label(1, start + 3, request.getAttribute("porganname").toString()));
				sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
				sheets[j].addCell(new Label(3, start + 3, request.getAttribute("pusername").toString()));
				sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
				sheets[j].addCell(new Label(5, start + 3, DateUtil.getCurrentDateTime()));

				sheets[j].addCell(new Label(0, start + 4, "仓库编号", wcfFC));
				sheets[j].addCell(new Label(1, start + 4, "仓库名称", wcfFC));
				sheets[j].addCell(new Label(2, start + 4, "产品类别", wcfFC));
				sheets[j].addCell(new Label(3, start + 4, "产品内部编号", wcfFC));
				sheets[j].addCell(new Label(4, start + 4, "产品名称", wcfFC));
				sheets[j].addCell(new Label(5, start + 4, "产品规格", wcfFC));
				sheets[j].addCell(new Label(6, start + 4, "单位", wcfFC));
				sheets[j].addCell(new Label(7, start + 4, "当前库存", wcfFC));
				sheets[j].addCell(new Label(8, start + 4, "在途库存", wcfFC));
				sheets[j].addCell(new Label(9, start + 4, "总库存", wcfFC));
				if("1".equals(isFZfunitid)){
					sheets[j].addCell(new Label(10, start + 4, "辅助单位(箱)", wcfFC));
					sheets[j].addCell(new Label(11, start + 4, "辅助单位(EA)", wcfFC));
				}
				Double quantity = 0.0, boxQuantity = 0.0, scatterQuantity = 0.0;
				int row = 0, count = 0;
				for (int i = start; i < currentnum; i++) {
					count++;
					row = i - start + 5;
					ProductStockpileForm wpsa = ttlist.get(i);
					sheets[j].addCell(new Label(0, row, (String) wpsa.getWarehouseid()));
					sheets[j].addCell(new Label(1, row, (String) wpsa.getWarehourseidname()));
					sheets[j].addCell(new Label(2, row, wpsa.getSortName()));
					sheets[j].addCell(new Label(3, row, wpsa.getNccode()));
					sheets[j].addCell(new Label(4, row, (String) wpsa.getPsproductname()));
					sheets[j].addCell(new Label(5, row, (String) wpsa.getSpecmode()));
					sheets[j].addCell(new Label(6, row, (String) "KG"));
					sheets[j].addCell(new Label(7, row, String.valueOf(wpsa.getStockpile())));
					sheets[j].addCell(new Label(8, row, String.valueOf(wpsa.getRepairstockpile())));
					sheets[j].addCell(new Label(9, row, String.valueOf(wpsa.getTotalstockpile())));
					quantity += wpsa.getStockpile();
					boxQuantity += wpsa.getRepairstockpile();
					scatterQuantity += wpsa.getTotalstockpile();
					if("1".equals(isFZfunitid)){
						sheets[j].addCell(new Label(10, row, String.valueOf(wpsa.getAssistBoxStockpile())));
						sheets[j].addCell(new Label(11, row, String.valueOf(wpsa.getAssistEAStockpile())));
					}
				}
				row = count + 10;
				sheets[j].addCell(new Label(0, row, "合计"));
				sheets[j].addCell(new Label(7, row, String.valueOf(quantity)));
				if("1".equals(isFZfunitid)){
					sheets[j].addCell(new Label(8, row, String.valueOf(boxQuantity)));
					sheets[j].addCell(new Label(9, row, String.valueOf(scatterQuantity)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.write();
				workbook.close();
			}
			if (os != null) {
				os.close();
			}
		}

	}
	
	public String getWarehouseIDBYList(List<Warehouse> warehouseList){
		StringBuffer sbtake = new StringBuffer();
		if(warehouseList.size() == 1){
			sbtake.append("'");
			sbtake.append(warehouseList.get(0).getId());
			sbtake.append("'");
		}else if(warehouseList.size() > 1){
			for (int i = 0; i < warehouseList.size(); i++) {
				if(i != warehouseList.size()-1){
					sbtake.append("'");
					sbtake.append(warehouseList.get(i).getId());
					sbtake.append("'");
					sbtake.append(",");
				}else{
					sbtake.append("'");
					sbtake.append(warehouseList.get(i).getId());
					sbtake.append("'");
				}
			}
		}else{
			//暂无逻辑
		}
		return sbtake.toString();
	}
}
