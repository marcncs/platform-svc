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
import com.winsafe.drp.dao.AppStockMove;
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

public class ExcPutStockMoveReceiveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExcPutStockMoveReceiveAction.class);
	
	private AppStockMove asm = new AppStockMove();
	private OrganService organs = new OrganService();
	private WarehouseService ws = new WarehouseService();
	private UsersService us = new UsersService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		super.initdata(request);
		try {
			//权限条件
			String Condition =" sm.isshipment=1 and sm.isblankout=0 ";
			if(DbUtil.isDealer(users)) {
				Condition += " and sm.inwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+")";
			} else { 
				Condition += " and ("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			// 查询条件
			Map map = new HashMap(request.getParameterMap());
			String id = (String)map.remove("ID");
			if(!StringUtil.isEmpty(id)) {
				Condition = Condition + " and id = '" + id.trim() +"'";
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockMove" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			// 时间条件
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			// 关键字条件
			String blur = DbUtil.getOrBlur2(map, tmpMap, "sm.KeysContent","sm.id");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			
			List sals = asm.getStockMoveList(request, 0, whereSql);
			
			if (sals.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListStockMoveReceive.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request,users);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"列表");
		}catch(Exception e){
			logger.error("", e);
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
			
			sheets[j].mergeCells(0, start, 7, start);
			sheets[j].addCell(new Label(0, start, "转仓签收  ",wchT));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转出机构:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, ESAPIUtil.decodeForHTML(request.getParameter("outorganname"))));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转入机构:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, ESAPIUtil.decodeForHTML(request.getParameter("inorganname"))));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单机构:", seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("oname")));	
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
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转仓日期", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转出机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转出仓库", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转入机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转入仓库", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单人", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否签收", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + rowNum + 1;
				colNum = 0;
				Map<String,String> p = list.get(i);
				sheets[j].addCell(new Label(colNum++, row, p.get("id")));
				sheets[j].addCell(new Label(colNum++, row, p.get("movedate")));
				sheets[j].addCell(new Label(colNum++, row, p.get("outoname")));
				sheets[j].addCell(new Label(colNum++, row, ws.getWarehouseName(p.get("outwarehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, organs.getOrganName(p.get("inoname"))));
				sheets[j].addCell(new Label(colNum++, row, ws.getWarehouseName(p.get("inwarehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, us.getUsersName(Integer.valueOf(p.get("makeid")))));
				sheets[j].addCell(new Label(colNum++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.valueOf(p.get("iscomplete")))));
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
