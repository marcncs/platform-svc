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
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

/**
 * 库存预警报表导出
 * @Title: ExportAlarmByProductStockpileAllAction.java
 * @author: WEILI	
 * @version:
 */
public class ExportAlarmByProductStockpileAllAction extends BaseAction{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化参数
		super.initdata(request);
		OutputStream os = null;
		//分页
		int pageSize=15;
		AppWarehouse aw = new AppWarehouse();
		AppProductStockpileAll apsa = new AppProductStockpileAll();
		//获取页面检索条件中的参数
		String outOrganID = request.getParameter("MakeOrganID");
		String outWarehouseID = request.getParameter("WarehouseID");
		map = request.getParameterMap();
		tmpMap = EntityManager.scatterMap(map);
		String blur = DbUtil.getOrBlur(map, tmpMap, "w.warehousename", "w.id");
		
		String whereSql = " where 1=1 and ";
		whereSql += blur;
		if(null != outOrganID && !"".equals(outOrganID)){
			whereSql += " w.makeorganid = '"+outOrganID+"' and ";
		}else{
			whereSql += " w.makeorganid = '"+users.getMakeorganid()+"' and ";
		}
		if(null != outWarehouseID && !"".equals(outWarehouseID)){
			whereSql += " w.id = '"+outWarehouseID+"' and";
		}
		whereSql = DbUtil.getWhereSql(whereSql); 
		
		//根据当前用户的所属机构查询所有仓库
		List<Warehouse> warehouseList = aw.getWarehouseByNoPage(whereSql);
		//循环仓库集合
		for (Warehouse warehouse : warehouseList) {
			//根据仓库ID查询所有库存量
			double stockpileNumber = apsa.getProductStockpileAllByWID(warehouse.getId());
			warehouse.setStockpile(stockpileNumber);
			/**
			 * 判断库存状态
			 */
			if(null == warehouse.getHighNumber() || "".equals(warehouse.getHighNumber())){
				warehouse.setStockpilestruts(0);
				continue;
			}
			if(null == warehouse.getBelowNumber() || "".equals(warehouse.getBelowNumber())){
				warehouse.setStockpilestruts(0);
				continue;
			}
			//如果库存大于上限
			if(stockpileNumber > Double.valueOf(warehouse.getHighNumber()).doubleValue()){
				warehouse.setStockpilestruts(2);
			}
			//如果库存大于下限小于上限
			if(stockpileNumber > Double.valueOf(warehouse.getBelowNumber()).doubleValue() && 
					stockpileNumber < Double.valueOf(warehouse.getHighNumber()).doubleValue()){
				warehouse.setStockpilestruts(0);
			}
			//如果库存小于下限
			if(stockpileNumber < Double.valueOf(warehouse.getBelowNumber()).doubleValue()){
				warehouse.setStockpilestruts(1);
			}
		}
		
		//获取检索条件中的库存状态并判断集合中的数据
		List<Warehouse> wList = new ArrayList<Warehouse>();
		String stockpilestruts = request.getParameter("sstruts");
		if(null != stockpilestruts && !"".equals(stockpilestruts)){
			if(!"-1".equals(stockpilestruts)){
				for (Warehouse warehouse : warehouseList) {
					//如果页面传值的库存状态与集合中的数据相等
					if(stockpilestruts.equals(String.valueOf(warehouse.getStockpilestruts()))){
						wList.add(warehouse);
					}
				}
			}else{
				wList = warehouseList;
			}
		}else{
			wList = warehouseList;
		}

		if (wList.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=AlarmByProductStockAll.xls");
		response.setContentType("application/msexcel");
		writeXls(wList, os, request);
		os.flush();
		return null;
	}
	
	public void writeXls(List<Warehouse> ttlist, OutputStream os, HttpServletRequest request) throws NumberFormatException, Exception {
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
				sheets[j].addCell(new Label(0, start, "库存预警报表", wchT));
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
				sheets[j].addCell(new Label(2, start + 4, "基本单位", wcfFC));
				sheets[j].addCell(new Label(3, start + 4, "实际库存", wcfFC));
				sheets[j].addCell(new Label(4, start + 4, "安全库存(下限)", wcfFC));
				sheets[j].addCell(new Label(5, start + 4, "安全库存(上限)", wcfFC));
				sheets[j].addCell(new Label(6, start + 4, "库存状态", wcfFC));
				sheets[j].addCell(new Label(7, start + 4, "备注", wcfFC));
				int row = 0;
				for (int i = start; i < currentnum; i++) {
					row = i - start + 5;
					Warehouse warehouse = ttlist.get(i);
					sheets[j].addCell(new Label(0, row, (String) warehouse.getId()));
					sheets[j].addCell(new Label(1, row, (String) warehouse.getWarehousename()));
					sheets[j].addCell(new Label(2, row, "KG"));
					sheets[j].addCell(new Label(3, row, String.valueOf(warehouse.getStockpile())));
					sheets[j].addCell(new Label(4, row, (String) warehouse.getBelowNumber()));
					sheets[j].addCell(new Label(5, row, (String) warehouse.getHighNumber()));
					String status = "";
					if(warehouse.getStockpilestruts() == 0){
						status = "安全";
					}
					if(warehouse.getStockpilestruts() == 1){
						status = "库存不足";
					}
					if(warehouse.getStockpilestruts() == 2){
						status = "库存超出";
					}
					sheets[j].addCell(new Label(6, row, status));
					sheets[j].addCell(new Label(7, row, (String) warehouse.getRemark()));
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
}
