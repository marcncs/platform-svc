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
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutSaleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String Condition = " (so.makeid=" + userid + " "
					+ getOrVisitOrgan("so.makeorganid")
					+ getOrVisitOrgan("so.equiporganid")
					+ " ) and so.sosort=1 ";

			String[] tablename = { "SaleOrder" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("KeysContent");

			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSaleOrder asl = new AppSaleOrder();
			List<SaleOrder> pils = asl.searchSaleOrder(whereSql);

			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListSaleOrder.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,6,"零售管理>>导出零售单!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<SaleOrder> list, OutputStream os,
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
			
			sheets[j].mergeCells(0, start, 9, start);
			sheets[j].addCell(new Label(0, start, "零售单",wchT));
			
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
			sheets[j].addCell(new Label(2, start+2, "开票信息:", seachT));
			IsStr = HtmlSelect.getNameByOrder(request, "InvoiceType", getInt("InvMsg"));
			sheets[j].addCell(new Label(3, start+2, IsStr));
			sheets[j].addCell(new Label(4, start+2, "配送机构:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("eoname")));	
			
			sheets[j].addCell(new Label(0, start+3, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+3, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getParameter("dname")));
			sheets[j].addCell(new Label(4, start+3, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+3, request.getParameter("uname")));
			
			sheets[j].addCell(new Label(0, start+4, "制单日期:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+4, "关键字:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getParameter("KeyWord")));
			
			
			sheets[j].addCell(new Label(0, start+5, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+5, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+5, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+5, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+5, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+5, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+6, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+6, "会员名称",wcfFC));
			sheets[j].addCell(new Label(2, start+6, "制单机构",wcfFC));
			sheets[j].addCell(new Label(3, start+6, "制单人",wcfFC));
			sheets[j].addCell(new Label(4, start+6, "制单日期",wcfFC));
			sheets[j].addCell(new Label(5, start+6, "总金额",wcfFC));
			sheets[j].addCell(new Label(6, start+6, "是否复核",wcfFC));
			sheets[j].addCell(new Label(7, start+6, "是否检货",wcfFC));
			sheets[j].addCell(new Label(8, start+6, "是否配送",wcfFC));
			sheets[j].addCell(new Label(9, start+6, "是否作废",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 7;
				SaleOrder p = (SaleOrder) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, p.getCname()));
				sheets[j].addCell(new Label(2, row, organs.getOrganName(p.getMakeorganid())));
				sheets[j].addCell(new Label(3, row, us.getUsersName(p.getMakeid())));
				sheets[j].addCell(new Label(4, row, Dateutil.formatDate(p.getMakedate())));
				sheets[j].addCell(new Number(5, row, p.getTotalsum(),wcfN));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(6, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getTakestatus());
				sheets[j].addCell(new Label(7, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsendcase());
				sheets[j].addCell(new Label(8, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsblankout());
				sheets[j].addCell(new Label(9, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
