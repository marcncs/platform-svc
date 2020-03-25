package com.winsafe.drp.action.ditch;

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
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class ExcPutOrganWithdrawAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExcPutOrganWithdrawAction.class);
	
	private AppOrganWithdraw appS = new AppOrganWithdraw();
	private OrganService organs = new OrganService();
	private UsersService us = new UsersService();
	private AppWarehouse appWarehouse = new AppWarehouse();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			/*//权限条件
			String Condition = "ow.id like 'OW%' and ow.inwarehouseid in (select wv.warehouseId from  RuleUserWh as wv where  wv.userId="+userid+") " +
					"  and  ow.warehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+") ";

			String[] tablename = { "OrganWithdraw" };
			String whereSql = getWhereSql2(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			List<OrganWithdraw> lists = appS.getOrganWithdrawAll(whereSql);*/
			//查询条件
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "OrganWithdraw" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ow.ID", "ow.KeysContent");
			//权限条件
			String Condition =" ow.id like 'OW%' and ";
			if(DbUtil.isDealer(users)) {
				Condition += " ow.inwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+") " +
					"  and ow.warehouseid in (select wv.wid from  Warehouse_Visit wv where wv.userid="+userid+") and ";
			} else { 
				Condition += "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			whereSql = whereSql + timeCondition + blur + Condition;
			if(!StringUtil.isEmpty((String)map.get("isNoBill"))) {
				if("1".equals(map.get("isNoBill"))) {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = ow.id) = 1 and ";
				} else {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = ow.id) is null and ";
				}
			}
			whereSql = DbUtil.getWhereSql(whereSql);
			whereSql = whereSql.replace("isaudit", "ow.isaudit");
			List lists = appS.getOrganWithdrawAllList(request, 0, whereSql);
			
			if (lists.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListOrganWithdraw.xls");
			response.setContentType("application/msexcel");
			writeXls(lists, os, request,users);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request, "列表");
		} catch (Exception e) {
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
			sheets[j].mergeCells(0, start, 10, start);
			sheets[j].addCell(new Label(0, start, "渠道退货", wchT));
			
			colNum = 0; // 换行
			rowNum ++ ; 
			sheets[j].addCell(new Label(colNum++, start + rowNum, "退货机构:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, ESAPIUtil.decodeForHTML(request.getParameter("POrganName"))));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "调入机构:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, request.getParameter("receivename")));
			
			colNum = 0; // 换行
			rowNum ++ ; 
			sheets[j].addCell(new Label(colNum++, start + rowNum, "是否复核:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "是否签收:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsComplete"))));
			
			colNum = 0; // 换行
			rowNum ++ ; 
			sheets[j].addCell(new Label(colNum++, start + rowNum, "制单机构:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, request.getParameter("oname")));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "制单人:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, request.getParameter("uname")));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "制单日期:",seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			colNum = 0; // 换行
			rowNum ++ ; 
			sheets[j].addCell(new Label(colNum++, start + rowNum, "关键字:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, request.getParameter("KeyWord")));
			
			colNum = 0; // 换行
			rowNum ++ ; 
			sheets[j].addCell(new Label(colNum++, start + rowNum, "导出机构:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, ESAPIUtil.decodeForHTML(users.getMakeorganname())));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "导出人:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, users.getRealname()));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "导出时间:", seachT));
			sheets[j].addCell(new Label(colNum++, start + rowNum, DateUtil.getCurrentDateTime()));
			
			colNum = 0; // 换行
			rowNum ++ ; 
			sheets[j].addCell(new Label(colNum++, start + rowNum, "编号", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "退货机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "退货仓库", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "调入机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "调入仓库", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "制单机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "制单人", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "制单日期", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "是否复核", wcfFC));
			sheets[j].addCell(new Label(colNum++, start + rowNum, "是否签收", wcfFC));
			int row = 0;

			for (int i = start; i < currentnum; i++) {
				row = i - start + rowNum + 1;
				Map<String,String> p = list.get(i);
				colNum = 0;
				sheets[j].addCell(new Label(colNum++, row, p.get("id")));
				sheets[j].addCell(new Label(colNum++, row, ESAPIUtil.decodeForHTML(p.get("porganname"))));
				sheets[j].addCell(new Label(colNum++, row, appWarehouse.getWarehouseNameById(p.get("warehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, p.get("inoname")));
				sheets[j].addCell(new Label(colNum++, row, appWarehouse.getWarehouseNameById(p.get("inwarehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, organs.getOrganName(p.get("makeorganid"))));
				sheets[j].addCell(new Label(colNum++, row, p.get("makeid") != null?us.getUsersName(Integer.parseInt(p.get("makeid"))):""));
				sheets[j].addCell(new Label(colNum++, row, p.get("makedate")));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.parseInt(p.get("isaudit")));
				sheets[j].addCell(new Label(colNum++, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo",  Integer.parseInt(p.get("iscomplete")));
				sheets[j].addCell(new Label(colNum++, row, IsStr));

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}