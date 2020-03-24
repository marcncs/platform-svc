package com.winsafe.drp.action.report;

import java.io.OutputStream;
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
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutStockMoveDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " pw.id=pwd.smid and  (pw.iscomplete=1 and pw.isblankout=0 " + visitorgan+ " ) ";

			String[] tablename = { "StockMove", "StockMoveDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String blur = getKeyWordCondition("MoveDate","MakeOrganID","OutWarehouseID","ProductID","ProductName","InOrganID","InWarehouseID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			AppStockMoveDetail asod=new AppStockMoveDetail(); 
	        List list = asod.getDetailReport(whereSql);
	        if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=StockMoveDetail.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出转仓明细");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organ = new OrganService();
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
			sheets[j].addCell(new Label(0, start, "转仓明细",wchT));
			sheets[j].addCell(new Label(0, start+1, "转出机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, organ.getOrganName(map.get("MakeOrganID").toString())));
			sheets[j].addCell(new Label(2, start+1, "转出仓库:",seachT));
			sheets[j].addCell(new Label(3, start+1, ws.getWarehouseName(map.get("OutWarehouseID").toString())));
			sheets[j].addCell(new Label(4, start+1, "制单日期:",seachT));
			sheets[j].addCell(new Label(5, start+1, map.get("BeginDate").toString()+"-"+map.get("EndDate").toString()));
			
			sheets[j].addCell(new Label(0, start+2, "转入机构:",seachT));
			sheets[j].addCell(new Label(1, start+2, organ.getOrganName(map.get("InOrganID").toString())));
			sheets[j].addCell(new Label(2, start+2, "转入仓库:",seachT));
			sheets[j].addCell(new Label(3, start+2, ws.getWarehouseName(map.get("InWarehouseID").toString())));
			sheets[j].addCell(new Label(4, start+2, "产品名称:",seachT));
			sheets[j].addCell(new Label(5, start+2, map.get("ProductName").toString()));

			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "转出机构",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "转出仓库",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "转入机构",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "转入仓库",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "制单时间",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "单据号",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "商品编号",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "商品名称",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(10, start+4, "数量",wcfFC));
			int row = 0;
			Double totalquantity = 0.00;
			
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, getOrganName(p.get("makeorganid"))));
				sheets[j].addCell(new Label(1, row, getWarehouseName(p.get("outwarehouseid"))));
				sheets[j].addCell(new Label(2, row, getOrganName(p.get("inorganid"))));
				sheets[j].addCell(new Label(3, row, getWarehouseName(p.get("inwarehouseid"))));
				sheets[j].addCell(new Label(4, row, p.get("makedate").toString()));
				sheets[j].addCell(new Label(5, row, p.get("smid").toString()));
				sheets[j].addCell(new Label(6, row, p.get("productid").toString()));
				sheets[j].addCell(new Label(7, row, p.get("productname").toString()));
				sheets[j].addCell(new Label(8, row, p.get("specmode").toString()));
				String unitname = Internation.getStringByKeyPositionDB("CountUnit",		                  
	                    Integer.valueOf(p.get("unitid").toString()));
				sheets[j].addCell(new Label(9, row, unitname));
				sheets[j].addCell(new Number(10, row, Double.valueOf(p.get("quantity").toString()),QWCF));
				totalquantity += Double.valueOf(p.get("quantity").toString());
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Label(2, row + 1, ""));
			sheets[j].addCell(new Label(3, row + 1, ""));
			sheets[j].addCell(new Label(4, row + 1, ""));
			sheets[j].addCell(new Label(5, row + 1, ""));
			sheets[j].addCell(new Label(6, row + 1, ""));
			sheets[j].addCell(new Label(7, row + 1, ""));
			sheets[j].addCell(new Label(8, row + 1, ""));
			sheets[j].addCell(new Label(9, row + 1, ""));
			sheets[j].addCell(new Number(10, row + 1, totalquantity,QWCF));

		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private String getOrganName(Object obj) throws Exception{
		OrganService oo = new OrganService();
		if(obj == null){
			return "";
		}else{
			return oo.getOrganName(obj.toString());
		}
	}
	
	private String getWarehouseName(Object obj) throws Exception{
		AppWarehouse appw = new AppWarehouse();
		if(obj==null){
			return "";
		}else{
			if(obj.toString().equals("")){
				return "";
			}else{
				Warehouse wh = appw.getWarehouseByID(obj.toString());
				return wh==null?"":wh.getWarehousename();
			}
		}
		
	}

}
