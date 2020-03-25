package com.winsafe.drp.action.sales;

import java.io.OutputStream;
import java.util.List;

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
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutIntegralOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String Condition = " (io.makeid=" + userid + " "
					+ getOrVisitOrgan("io.makeorganid")
					+ getOrVisitOrgan("io.equiporganid") + ") ";

			String[] tablename = { "IntegralOrder" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("KeysContent");

			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppIntegralOrder asl = new AppIntegralOrder();
			List<IntegralOrder> pils = asl.searchIntegralOrder(whereSql);

			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListIntegralOrder.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 6, "零售管理>>导出积分换购!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<IntegralOrder> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		UsersService us = new UsersService();
		OrganService organs = new OrganService();
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
			
			sheets[j].mergeCells(0, start, 9, start);
			sheets[j].addCell(new Label(0, start, "积分换购",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "会员名称:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("cname")));
			sheets[j].addCell(new Label(2, start+1, "是否复核:", seachT));
			String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"));
			sheets[j].addCell(new Label(3, start+1, IsStr));
			sheets[j].addCell(new Label(4, start+1, "是否作废:", seachT));
			IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsBlankOut"));
			sheets[j].addCell(new Label(5, start+1, IsStr));	
			
			
			sheets[j].addCell(new Label(0, start+2, "是否结案:", seachT));
			IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsEndcase"));
			sheets[j].addCell(new Label(1, start+2, IsStr));		
			sheets[j].addCell(new Label(2, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("oname")));
			sheets[j].addCell(new Label(4, start+2, "制单部门:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("dname")));
			
			sheets[j].addCell(new Label(0, start+3, "制单人:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("uname")));
			sheets[j].addCell(new Label(2, start+3, "制单日期:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(4, start+3, "关键字:", seachT));
			sheets[j].addCell(new Label(5, start+3, request.getParameter("KeyWord")));
			
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start+5, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+5, "会员名称",wcfFC));
			sheets[j].addCell(new Label(2, start+5, "制单机构",wcfFC));
			sheets[j].addCell(new Label(3, start+5, "制单日期",wcfFC));
			sheets[j].addCell(new Label(4, start+5, "制单人",wcfFC));
			sheets[j].addCell(new Label(5, start+5, "总积分",wcfFC));
			sheets[j].addCell(new Label(6, start+5, "是否复核",wcfFC));
			sheets[j].addCell(new Label(7, start+5, "是否配送",wcfFC));
			sheets[j].addCell(new Label(8, start+5, "是否作废",wcfFC));
			sheets[j].addCell(new Label(9, start+5, "是否检货",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				IntegralOrder p = (IntegralOrder) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, p.getCname()));
				sheets[j].addCell(new Label(2, row, organs.getOrganName(p
						.getMakeorganid())));
				sheets[j].addCell(new Label(3, row, Dateutil.formatDate(p
						.getMakedate())));
				sheets[j].addCell(new Label(4, row, us.getUsersName(p
						.getMakeid())));
				sheets[j].addCell(new Number(5, row, p.getIntegralsum()
						,QWCF));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsaudit());
				sheets[j].addCell(new Label(6, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsendcase());
				sheets[j].addCell(new Label(7, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsblankout());
				sheets[j].addCell(new Label(8, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getTakestatus());
				sheets[j].addCell(new Label(9, row, IsStr));

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
