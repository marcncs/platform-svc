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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutMoveApplyAction extends BaseAction {
	
	private String ISAUDIT;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 ISAUDIT = request.getParameter("ISAUDIT");

		super.initdata(request);
		try {

			//权限条件
			String Condition ="";
			if(DbUtil.isDealer(users)) {
				Condition = " ma.outorganid in (select visitorgan from User_Visit where  userid=" + userid + ")" +
				" and ma.outwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+")  ";
			} else { 
				Condition = "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "MoveApply" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppMoveApply aama = new AppMoveApply();
			List<Map<String,String>> sals = aama.getMoveApplyList(request, 0, whereSql);

			if (sals.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListMoveApply.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request);
			os.flush();
			os.close();
			if (ISAUDIT.equals("yes")) {
				DBUserLog.addUserLog(userid, 4, "渠道管理>>导出机构间转仓申请审核!");
			} else {
				DBUserLog.addUserLog(userid, 4, "渠道管理>>导出机构间转仓申请!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<Map<String,String>> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		UsersService us = new UsersService();

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

			sheets[j].mergeCells(0, start, 7, start);
			if (ISAUDIT.equals("yes")) {
				sheets[j].addCell(new Label(0, start, "机构间转仓申请审核", wchT));
			} else {
				sheets[j].addCell(new Label(0, start, "机构间转仓申请", wchT));
			}
			
			sheets[j].addCell(new Label(0, start + 1, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start + 1, request
					.getParameter("oname")));
			sheets[j].addCell(new Label(2, start + 1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request
					.getParameter("deptname")));
			sheets[j].addCell(new Label(4, start + 1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request
					.getParameter("uname")));

			sheets[j].addCell(new Label(0, start + 2, "调出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 2, request
					.getParameter("outoname")));
			
			sheets[j].addCell(new Label(2, start + 2, "是否批准:", seachT));
			sheets[j].addCell(new Label(3, start + 2, HtmlSelect
					.getNameByOrder(request, "YesOrNo", getInt("IsRatify"))));

			sheets[j].addCell(new Label(0, start + 3, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start + 3, request
					.getParameter("KeyWord")));

			sheets[j].addCell(new Label(0, start + 4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 4, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(2, start + 4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 4, request.getAttribute(
					"pusername").toString()));
			sheets[j].addCell(new Label(4, start + 4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 4, DateUtil
					.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start + 5, "机构间转仓需求日期", wcfFC));
			sheets[j].addCell(new Label(2, start + 5, "调出机构", wcfFC));
			sheets[j].addCell(new Label(3, start + 5, "调入机构 ", wcfFC));
			sheets[j].addCell(new Label(4, start + 5, "制单人", wcfFC));
			sheets[j].addCell(new Label(5, start + 5, "是否批准", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				Map<String,String> p = list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("id")));
				sheets[j].addCell(new Label(1, row, p.get("movedate")));
				sheets[j].addCell(new Label(2, row, p.get("outoname")));
				sheets[j].addCell(new Label(3, row, p.get("inoname")));
				String makeuser = us.getUsersName(Integer.valueOf(p.get("makeid")));
				sheets[j].addCell(new Label(4, row, makeuser));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.valueOf(p
						.get("isratify")));
				sheets[j].addCell(new Label(5, row, IsStr));
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
