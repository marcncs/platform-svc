package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutProductStockpileIdcodeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Idcode" };
			String Batch=(String)map.remove("Batch");
//			String Condition = " (isuse=1 or quantity>0 and quantity!=fquantity) and batch='" + Batch + "'";
			String Condition = " (isuse=1 or quantity>0 ) and batch='" + Batch + "'";
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProduceDate",
					"StartNo", "EndNo", "IDCode");

			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppIdcode ap = new AppIdcode();
			List<Idcode> idlist = ap.searchIdcode(whereSql);

			if (idlist.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListStockStat.xls");
			response.setContentType("application/msexcel");
			writeXls(idlist, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"列表");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<Idcode> list, OutputStream os,
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
			sheets[j].addCell(new Label(0, start, "库存条码 ",wchT));				
			sheets[j].addCell(new Label(0, start+1, "仓库:", seachT));
			sheets[j].addCell(new Label(1, start+1, ws.getWarehouseName(request.getParameter("WarehouseID"))));
			sheets[j].addCell(new Label(2, start+1, "仓位:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("bitname")));			
			sheets[j].addCell(new Label(4, start+1, "产品:", seachT));
			AppProduct product = new AppProduct();
			String ProductName  = product.getProductNameByID(request.getParameter("ProductID"));
			sheets[j].addCell(new Label(5, start+1, ProductName));	
			
			sheets[j].addCell(new Label(0, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("KeyWord")));		
		
	
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "仓位", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "条码", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "外箱条码", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "批号", wcfFC));
			sheets[j].addCell(new Label(4, start+4, "生产日期", wcfFC));
//			sheets[j].addCell(new Label(4, start+4, "物流码起始号", wcfFC));
//			sheets[j].addCell(new Label(5, start+4, "物流码结束号", wcfFC));
			sheets[j].addCell(new Label(5, start+4, "包装数量", wcfFC));
			sheets[j].addCell(new Label(6, start+4, "单位", wcfFC));
			sheets[j].addCell(new Label(7, start+4, "数量", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Idcode p = (Idcode) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getWarehousebit()));
				sheets[j].addCell(new Label(1, row, p.getIdcode()));
				sheets[j].addCell(new Label(2, row, p.getCartonCode()));
				sheets[j].addCell(new Label(3, row, p.getBatch()));
				sheets[j].addCell(new Label(4, row, DateUtil.formatDate(DateUtil.formatStrDate(p.getProducedate()))));
//				sheets[j].addCell(new Label(4, row, p.getStartno()));
//				sheets[j].addCell(new Label(5, row, p.getEndno()));
				sheets[j].addCell(new Number(5, row, p.getPackquantity(),QWCF));
				sheets[j].addCell(new Label(6, row, HtmlSelect.getResourceName(request, "CountUnit", p.getUnitid())));
				sheets[j].addCell(new Number(7, row, p.getQuantity(),QWCF));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
