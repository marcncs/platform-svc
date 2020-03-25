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
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutStockStatAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WarehouseService aw = new WarehouseService();
		AppFUnit af = new AppFUnit();
		super.initdata(request);
		try {
			String strCondition = " ps.warehouseid=wv.wid and wv.userid="
					+ userid + " and p.id=ps.productid and ps.stockpile!=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductStockpile", "WarehouseVisit",
					"Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName",
					"PYCode", "SpecMode", "ps.batch");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProductStockpile aps = new AppProductStockpile();
			List pls = aps.getStockStat(whereSql);
			List<ProductStockpileForm> alp = new ArrayList<ProductStockpileForm>();

			for (int i = 0; i < pls.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				Object[] o = (Object[]) pls.get(i);
				ProductStockpile ps = (ProductStockpile) o[0];
				Product p = (Product) o[1];
				psf.setId(ps.getId());
				psf.setProductid(ps.getProductid());
				psf.setPsproductname(p.getProductname());
				psf.setPsspecmode(p.getSpecmode());
				psf.setBatch(ps.getBatch());
				psf.setSpecmode(p.getSpecmode());
				psf.setCountunit(Constants.DEFAULT_UNIT_ID);
				
				psf.setStrstockpile(af.getStockpileQuantity2(ps.getProductid(),psf.getCountunit(), ps.getStockpile()));
				psf.setWarehouseid(ps.getWarehouseid());
				psf.setWarehourseidname(aw
						.getWarehouseName(ps.getWarehouseid()));
				psf.setWarehousebit(ps.getWarehousebit());
				alp.add(psf);
			}
			request.setAttribute("list", alp);
			
			if (alp.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListStockStat.xls");
			response.setContentType("application/msexcel");
			writeXls(alp, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"列表");
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
			sheets[j].mergeCells(0, start, 6, start);
			sheets[j].addCell(new Label(0, start, "仓位统计 ",wchT));				
			sheets[j].addCell(new Label(0, start+1, "仓库:", seachT));
			sheets[j].addCell(new Label(1, start+1, ws.getWarehouseName(request.getParameter("WarehouseID"))));
			sheets[j].addCell(new Label(2, start+1, "仓位:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("bitname")));			
			sheets[j].addCell(new Label(4, start+1, "产品:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("ProductName")));	
			
			sheets[j].addCell(new Label(0, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("KeyWord")));		
		
	
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "仓位", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "批号", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "产品编号", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(4, start+4, "规格", wcfFC));
			sheets[j].addCell(new Label(5, start+4, "单位", wcfFC));
			sheets[j].addCell(new Label(6, start+4, "当前库存量", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ProductStockpileForm p = (ProductStockpileForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getWarehousebit()));
				sheets[j].addCell(new Label(1, row, p.getBatch()));
				sheets[j].addCell(new Label(2, row, p.getProductid()));
				sheets[j].addCell(new Label(3, row, p.getPsproductname()));
				sheets[j].addCell(new Label(4, row, p.getSpecmode()));
				sheets[j].addCell(new Label(5, row, HtmlSelect.getResourceName(request, "CountUnit", p.getCountunit())));
				sheets[j].addCell(new Number(6, row, p.getStrstockpile(),QWCF));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
