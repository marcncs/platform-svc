package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcPutStockAlterMoveReceiveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExcPutStockAlterMoveReceiveAction.class);
	
	private AppStockAlterMove asa = new AppStockAlterMove();
	private OrganService organs = new OrganService();
	private UsersService us = new UsersService();
	private WarehouseService ws = new WarehouseService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {
			String Condition = "";
			
			Map map = new HashMap(request.getParameterMap());
			String id = (String)map.remove("ID");
			String mcode = (String)map.remove("mcode");
			if(!StringUtil.isEmpty(id)) {
				Condition = Condition + " and id = '" + id.trim() +"'";
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockAlterMove" };
			String whereSql = EntityManager.getTmpWhereSql(tmpMap, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"MoveDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition; 
			if(!StringUtil.isEmpty(mcode)) {
				whereSql = whereSql + " exists (select smd.samid from Stock_Alter_Move_Detail smd where sm.id = smd.samid and smd.nccode = '" + mcode.trim() + "')";
			}
			whereSql = DbUtil.getWhereSql(whereSql); 	
			whereSql = whereSql.replaceFirst("where", "and");
			
			List sals = asa.getStockAlterMoveBySql(request, 0, whereSql,users);

			if (sals.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListStockAlterMoveReceive.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request,users);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"列表");
		}catch(Exception e){
			logger.error("",e);
		}
		return null;
	}
	
	public void writeXls(List<Map<String,String>> list, OutputStream os,
			HttpServletRequest request,UsersBean users) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
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
			
			int colNum = 0;
			int rowNum = 0;
			sheets[j].mergeCells(0, start, 6, start);
			sheets[j].addCell(new Label(0, start, "订购入库  ",wchT));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调出机构:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("oname")));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调入机构:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("rname")));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单人:", seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("uname")));	
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否签收:", seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsComplete"))));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "关键字:", seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("KeyWord")));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "导出机构:", seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, ESAPIUtil.decodeForHTML(users.getMakeorganname())));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "导出人:", seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, users.getRealname()));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "导出时间:", seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, DateUtil.getCurrentDateTime()));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "编号", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "发货日期", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调出机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调出仓库", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调入机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调入仓库", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单人", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否签收", wcfFC));
			
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + rowNum + 1;
				colNum = 0;
				Map<String,String> p = list.get(i);
				sheets[j].addCell(new Label(colNum++, row, p.get("id")));
				sheets[j].addCell(new Label(colNum++, row, p.get("movedate")));
				sheets[j].addCell(new Label(colNum++, row, ESAPIUtil.decodeForHTML(p.get("outorganname"))));
				sheets[j].addCell(new Label(colNum++, row, ws.getWarehouseName(p.get("outwarehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, organs.getOrganName(p.get("receiveorganid"))));
				sheets[j].addCell(new Label(colNum++, row, ws.getWarehouseName(p.get("inwarehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, us.getUsersName(Integer.valueOf(p.get("makeid")))));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.valueOf(p.get("iscomplete")));
				sheets[j].addCell(new Label(colNum++, row, IsStr));
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
