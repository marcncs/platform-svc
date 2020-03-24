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
import com.winsafe.drp.dao.AppLeftMenu;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil; 
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcPutStockAlterMoveAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ExcPutStockAlterMoveAction.class);
	
	private AppStockAlterMove asa = new AppStockAlterMove();
	private OrganService organs = new OrganService();
	private UsersService us = new UsersService();
	private WarehouseService ws = new WarehouseService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try { 
			//条件
			Map map = new HashMap(request.getParameterMap());
			String productname = (String)map.remove("ProductName");
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockAlterMove" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
			//关键字条件
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			//权限条件
			String Condition ="";
			if(DbUtil.isDealer(users)) {
				Condition = " sm.outwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+")  " +
				" and  (sm.inwarehouseid in (select wv.wid from  Warehouse_Visit wv where wv.userid="+userid+") or sm.receiveorganid in (select oppOrganId from S_Transfer_Relation where  organizationId='" + users.getMakeorganid() + "') or sm.inwarehouseid is null) and";
			} else {
				Condition = "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			
//			String Condition = "( sm.outOrganId in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ") or sm.outOrganId in (select visitorgan from OrganVisit as ov where  ov.userid=" + userid + "))";
			
			whereSql = whereSql + timeCondition + blur + Condition; 
			
			if(!StringUtil.isEmpty(productname)) {
				whereSql = whereSql + " exists (select productname from Stock_Alter_Move_Detail smd where smd.samid = sm.id and smd.productname = '" + productname + "') and";
			}
			if(!StringUtil.isEmpty((String)map.get("isNoBill"))) {
				if("1".equals(map.get("isNoBill"))) {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = sm.id) = 1 and ";
				} else {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = sm.id) is null and ";
				}
			}
			whereSql = DbUtil.getWhereSql(whereSql); 
			whereSql = whereSql.replace("isaudit", "sm.isaudit");
			
			List<Map<String,String>> sals = asa.getStockAlterMoveList(request, 0, whereSql);

			if (sals.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListStockAlterMove.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"列表");
			
		}catch(Exception e){
			logger.error("", e);
		}
		return null;
	}
	
	public void writeXls(List<Map<String, String>> sals, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		String lmenuName = AppLeftMenu.getMenuNameByUrl(request.getServletPath());
		int snum = 1;
		snum = sals.size() / 50000;
		if (sals.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];

		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= sals.size()) {
				currentnum = sals.size();
			}
			int start = j * 50000;
			int rowNum = 0;
			int colNum = 0;
			sheets[j].mergeCells(0, start, 10, start);
			sheets[j].addCell(new Label(0, start, lmenuName,wchT));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调出机构:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, ESAPIUtil.decodeForHTML(request.getParameter("outOrganName"))));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调入机构:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("oname2")));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否复核:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否发货:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsShipment"))));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否签收:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsComplete"))));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否作废:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsBlankOut"))));

			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单日期:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单机构:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("oname")));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单人:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("uname")));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "关键字:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, request.getParameter("KeyWord")));
		
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "导出机构:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, users.getMakeorganname()));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "导出人:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, users.getRealname()));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "导出时间:",seachT));
			sheets[j].addCell(new Label(colNum++, start+rowNum, DateUtil.getCurrentDateTime()));
			
			colNum = 0 ; // 换行
			rowNum ++;
			sheets[j].addCell(new Label(colNum++, start+rowNum, "编号",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调拨日期",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单机构",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "制单人",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调出仓库",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调入机构",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "调入仓库",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否复核",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否发货",wcfFC));
			sheets[j].addCell(new Label(colNum++, start+rowNum, "是否签收",wcfFC));
			//sheets[j].addCell(new Label(colNum++, start+rowNum, "是否作废",wcfFC));
			
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + rowNum + 1;
				colNum = 0 ;
				Map<String, String> smMap = sals.get(i);
				StockAlterMove p = new StockAlterMove();
				MapUtil.mapToObject(smMap, p);
				
				sheets[j].addCell(new Label(colNum++, row, p.getId()));
				sheets[j].addCell(new Label(colNum++, row, DateUtil.formatDate(p.getMovedate())));
				sheets[j].addCell(new Label(colNum++, row, p.getMakeorganidname()));
				String makeuser = us.getUsersName(p.getMakeid());
				sheets[j].addCell(new Label(colNum++, row, makeuser));
				String warehouse = ws.getWarehouseName(p.getOutwarehouseid());
				sheets[j].addCell(new Label(colNum++, row, warehouse));
				String organ=organs.getOrganName(p.getReceiveorganid());
				sheets[j].addCell(new Label(colNum++, row, organ));
				warehouse = ws.getWarehouseName(p.getInwarehouseid());
				sheets[j].addCell(new Label(colNum++, row, warehouse));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(colNum++, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsshipment());
				sheets[j].addCell(new Label(colNum++, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIscomplete());
				sheets[j].addCell(new Label(colNum++, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsblankout());
				//sheets[j].addCell(new Label(colNum++, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
