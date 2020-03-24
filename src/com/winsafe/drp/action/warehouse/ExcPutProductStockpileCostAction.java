package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutProductStockpileCostAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		WarehouseService aw = new WarehouseService();
		try {
			String strCondition = " ps.warehouseid=wv.wid and wv.userid="
					+ userid + " and (ps.stockpile+ps.prepareout)!=0 and p.id=ps.productid ";

			String[] tablename = { "ProductStockpileAll", "WarehouseVisit",
					"Product" };
			String whereSql = getWhereSql2(tablename);
			String blur = getKeyWordCondition("p.id", "ProductName",
					"PYCode", "SpecMode");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppFUnit af = new AppFUnit();
			AppProductStockpileAll aps = new AppProductStockpileAll();
			List pls = aps.getStockStat(whereSql);
			List<ProductStockpileForm> alp = new ArrayList<ProductStockpileForm>();

			for (int i = 0; i < pls.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				Object[] o = (Object[]) pls.get(i);
				ProductStockpileAll ps = (ProductStockpileAll) o[0];
				Product p = (Product) o[1];
				psf.setId(ps.getId());
				  psf.setProductid(ps.getProductid());
				  psf.setPsproductname(p.getProductname());
				  psf.setPsspecmode(p.getSpecmode());
				  psf.setCountunit(p.getSunit());
				  psf.setUnitname(HtmlSelect.getResourceName(request, "CountUnit", p.getSunit()));
				  psf.setBatch(ps.getBatch());
				  psf.setAllstockpile(af.getStockpileQuantity2(ps.getProductid(), p.getSunit(), ps.getStockpile()+ps.getPrepareout()));
				  psf.setWarehouseid(ps.getWarehouseid());
				  psf.setWarehourseidname(aw.getWarehouseName(ps.getWarehouseid()));
				  psf.setCost(ps.getCost()*af.getXQuantity(ps.getProductid(), p.getSunit()));
				  psf.setTotalcost(psf.getAllstockpile()*psf.getCost());
				  alp.add(psf);
			}


			if (alp.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListProductStockpileAll.xls");
			response.setContentType("application/msexcel");
			writeXls(alp, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 7,"库存统计>>导出库存统计");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<ProductStockpileForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
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
			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "库存成本 ",wchT));				
			sheets[j].addCell(new Label(0, start+1, "仓库:", seachT));
			sheets[j].addCell(new Label(1, start+1, ws.getWarehouseName(request.getParameter("WarehouseID"))));
			sheets[j].addCell(new Label(2, start+1, "产品类别:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("psname")));			
			sheets[j].addCell(new Label(4, start+1, "产品:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("ProductName")));	
			
			sheets[j].addCell(new Label(0, start+2, "批次:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("Batch")));		
			sheets[j].addCell(new Label(2, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("KeyWord")));				
	
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			          
			sheets[j].addCell(new Label(0, start+4, "仓库名", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "批次", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "产品编号", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(4, start+4, "规格", wcfFC));
			sheets[j].addCell(new Label(5, start+4, "单位", wcfFC));
			sheets[j].addCell(new Label(6, start+4, "实际库存", wcfFC));
			sheets[j].addCell(new Label(7, start+4, "成本单价", wcfFC));
			sheets[j].addCell(new Label(8, start+4, "成本合计", wcfFC));
			int row = 0;
			double totalst=0.00;
			double totalcost=0.00;
			double allcost=0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ProductStockpileForm p = (ProductStockpileForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getWarehourseidname()));
				sheets[j].addCell(new Label(1, row, p.getBatch()));
				sheets[j].addCell(new Label(2, row, p.getProductid()));
				sheets[j].addCell(new Label(3, row, p.getPsproductname()));
				sheets[j].addCell(new Label(4, row, p.getPsspecmode()));
				sheets[j].addCell(new Label(5, row, p.getUnitname()));
				sheets[j].addCell(new Number(6, row, p.getAllstockpile(),QWCF));
				sheets[j].addCell(new Number(7, row, p.getCost(),QWCF));
				sheets[j].addCell(new Number(8, row, p.getTotalcost(),QWCF));
				totalst += p.getAllstockpile();
				totalcost +=p.getCost();
				allcost += p.getTotalcost();
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));			
			sheets[j].addCell(new Number(6, row + 1,totalst,QWCF));
			sheets[j].addCell(new Number(7, row + 1,totalcost,QWCF));
			sheets[j].addCell(new Number(8, row + 1,allcost,QWCF));

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
