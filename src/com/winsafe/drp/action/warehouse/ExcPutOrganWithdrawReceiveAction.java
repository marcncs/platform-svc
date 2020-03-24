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
import com.winsafe.drp.dao.AppOrganWithdraw;
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

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class ExcPutOrganWithdrawReceiveAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ExcPutOrganWithdrawReceiveAction.class);
	private AppOrganWithdraw appS = new AppOrganWithdraw();
	private OrganService organs = new OrganService();
	private UsersService us = new UsersService();
	private WarehouseService wService = new WarehouseService();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {

			//权限条件
			String Condition ="ow.id like 'OW%' and ow.takestatus=1 and ow.isblankout=0 and ";
			if(DbUtil.isDealer(users)) {
				Condition += " ow.inwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+") ";
			} else { 
				Condition += "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") ";
			}
			Map map = new HashMap(request.getParameterMap());
			String id = (String)map.remove("ID");
			if(!StringUtil.isEmpty(id)) {
				Condition = Condition + " and id = '" + id.trim() +"'";
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "OrganWithdraw" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			
			whereSql = whereSql + timeCondition + blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			List lists = appS.getOrganWithdrawAllList(request, 0, whereSql);
			if (lists.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListOrganWithdrawReceive.xls");
			response.setContentType("application/msexcel");
			writeXls(lists, os, request,users);
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
			sheets[j].mergeCells(0, start, 7, start);
			sheets[j].addCell(new Label(0, start, "渠道退货签收 ",wchT));
			sheets[j].addCell(new Label(0, start+1, "退货机构:", seachT));
			sheets[j].addCell(new Label(1, start+1, ESAPIUtil.decodeForHTML(request.getParameter("POrganName"))));	
			sheets[j].addCell(new Label(2, start+1, "入库机构:", seachT));
			sheets[j].addCell(new Label(3, start+1, ESAPIUtil.decodeForHTML(request.getParameter("receiveorganname"))));	
			
			sheets[j].addCell(new Label(0, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, ESAPIUtil.decodeForHTML(request.getParameter("oname"))));	
			sheets[j].addCell(new Label(2, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("uname")));
			
			sheets[j].addCell(new Label(0, start+3, "制单日期:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+3, "是否签收:", seachT));
			sheets[j].addCell(new Label(3, start+3, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsComplete"))));
			
			sheets[j].addCell(new Label(0, start+4, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+5, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+5, users.getMakeorganname()));
			sheets[j].addCell(new Label(2, start+5, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+5, users.getRealname()));
			sheets[j].addCell(new Label(4, start+5, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+5, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(colNum++, start+6, "编号", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+6, "退货机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+6, "退货仓库", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+6, "入库机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+6, "入库仓库", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+6, "制单机构", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+6, "制单人", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+6, "制单日期", wcfFC));
			sheets[j].addCell(new Label(colNum++, start+6, "是否签收", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				colNum = 0;
				row = i - start + 7;
				Map<String,String> p = list.get(i);
				sheets[j].addCell(new Label(colNum++, row, p.get("id")));
				sheets[j].addCell(new Label(colNum++, row, ESAPIUtil.decodeForHTML(p.get("porganname"))));
				sheets[j].addCell(new Label(colNum++, row, wService.getWarehouseName(p.get("warehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, p.get("inoname")));
				sheets[j].addCell(new Label(colNum++, row, wService.getWarehouseName(p.get("inwarehouseid"))));
				sheets[j].addCell(new Label(colNum++, row, organs.getOrganName(p.get("makeorganid"))));
				sheets[j].addCell(new Label(colNum++, row, us.getUsersName(Integer.valueOf(p.get("makeid")))));
				sheets[j].addCell(new Label(colNum++, row, p.get("makedate")));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.valueOf(p.get("iscomplete")));
				sheets[j].addCell(new Label(colNum++, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}