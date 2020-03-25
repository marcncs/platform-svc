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
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcPutStockMoveAction extends BaseAction {
	private Logger logger = Logger.getLogger(ExcPutStockMoveAction.class);
	
	private AppStockMove asa = new AppStockMove();
	private WarehouseService ws = new WarehouseService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		try{
			//查询条件 
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockMove"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			//时间条件
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MoveDate");
			//关键字条件
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			//权限条件
			String Condition = "";
			if(DbUtil.isDealer(users)) {
				Condition += " sm.outwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+") and ";
			} else { 
				Condition += "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			whereSql = whereSql + timeCondition + blur + Condition; 
			if(!StringUtil.isEmpty((String)map.get("isNoBill"))) {
				if("1".equals(map.get("isNoBill"))) {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = sm.id) = 1 and ";
				} else {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = sm.id) is null and ";
				}
			}
			whereSql = whereSql.replace("isaudit", "sm.isaudit");
			whereSql = DbUtil.getWhereSql(whereSql); 

			List sals = asa.getStockMoveList(request, 0, whereSql);

			if (sals.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListStockMove.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request,users);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request, "列表");
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
			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "机构内转仓",wchT));
			rowNum++;   //换行
			sheets[j].addCell(new Label(0, start+rowNum, "转入机构:",seachT));
			sheets[j].addCell(new Label(1, start+rowNum, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+rowNum, "转入仓库:",seachT));
			sheets[j].addCell(new Label(3, start+rowNum, request.getParameter("wname")));
			sheets[j].addCell(new Label(4, start+rowNum, "是否签收:",seachT));
			sheets[j].addCell(new Label(5, start+rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsComplete"))));
			
			rowNum++;   //换行
			sheets[j].addCell(new Label(0, start+rowNum, "转出机构:",seachT));
			sheets[j].addCell(new Label(1, start+rowNum, request.getParameter("outname")));
			sheets[j].addCell(new Label(2, start+rowNum, "转出仓库:",seachT));
			sheets[j].addCell(new Label(3, start+rowNum, request.getParameter("mname")));
			sheets[j].addCell(new Label(4, start+rowNum, "是否发货:",seachT));
			sheets[j].addCell(new Label(5, start+rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsShipment"))));
			
			rowNum++;   //换行
			sheets[j].addCell(new Label(0, start+rowNum, "是否复核:",seachT));
			sheets[j].addCell(new Label(1, start+rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(2, start+rowNum, "制单人:",seachT));
			sheets[j].addCell(new Label(3, start+rowNum, request.getParameter("uname")));
			sheets[j].addCell(new Label(2, start+rowNum, "关键字:",seachT));
			sheets[j].addCell(new Label(3, start+rowNum, request.getParameter("KeyWord")));
			
			rowNum++;   //换行
			sheets[j].addCell(new Label(0, start+rowNum, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+rowNum, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(2, start+rowNum, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+rowNum, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+rowNum, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+rowNum, DateUtil.getCurrentDateTime()));
			
			rowNum++;   //换行
			sheets[j].addCell(new Label(colNum++, start+rowNum, "编号",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转仓日期",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转出机构",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转出仓库",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转入机构",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "转入仓库",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否复核",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否发货",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否签收",wcfFC));
			
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + rowNum + 1;
				colNum = 0;
				Map<String,String> p = list.get(i);
				sheets[j].addCell(new Label(colNum++, row, p.get("id")));
				sheets[j].addCell(new Label(colNum++, row, p.get("movedate")));
				sheets[j].addCell(new Label(colNum++, row, p.get("outoname")));
				sheets[j].addCell(new Label(colNum++, row, ws.getWarehouseName(p.get("outwarehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, p.get("inoname")));
				sheets[j].addCell(new Label(colNum++, row, p.get("inwarehouseid")));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.parseInt(p.get("isaudit")));
				sheets[j].addCell(new Label(colNum++, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.parseInt(p.get("isshipment")));
				sheets[j].addCell(new Label(colNum++, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.parseInt(p.get("iscomplete")));
				sheets[j].addCell(new Label(colNum++, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
