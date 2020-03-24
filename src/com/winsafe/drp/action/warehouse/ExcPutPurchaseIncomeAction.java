package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.List;

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
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutPurchaseIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String Condition = " (pi.makeid=" + userid + " "
					+ getOrVisitOrgan("pi.makeorganid")
					+ ") and pi.warehouseid=wv.wid and wv.userid=" + userid;
			String[] tablename = { "PurchaseIncome", "WarehouseVisit" };
			String whereSql =getWhereSql2(tablename);
			String blur = getKeyWordCondition("KeysContent");
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseIncome api = new AppPurchaseIncome();
			List<PurchaseIncome> pils = api.searchPurchaseIncome(whereSql);

			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPurchaseIncome.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 7,"仓库管理>>入库>>导出采购入库");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<PurchaseIncome> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		UsersService us = new UsersService();
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
			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "采购入库  ",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deptname")));		
			sheets[j].addCell(new Label(4, start+1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("uname")));	
			
			sheets[j].addCell(new Label(0, start+2, "供应商:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("providename")));
			sheets[j].addCell(new Label(2, start+2, "仓库:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("wname")));
			sheets[j].addCell(new Label(4, start+2, "是否复核:", seachT));
			sheets[j].addCell(new Label(5, start+2, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			
			sheets[j].addCell(new Label(0, start+3, "是否记帐:", seachT));
			sheets[j].addCell(new Label(1, start+3, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsTally"))));
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
			          
			sheets[j].addCell(new Label(0, start+5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+5, "仓库", wcfFC));
			sheets[j].addCell(new Label(2, start+5, "采购订单编号", wcfFC));
			sheets[j].addCell(new Label(3, start+5, "供应商", wcfFC));
			sheets[j].addCell(new Label(4, start+5, "入库日期", wcfFC));
			sheets[j].addCell(new Label(5, start+5, "是否复核", wcfFC));
			sheets[j].addCell(new Label(6, start+5, "是否记帐", wcfFC));
			sheets[j].addCell(new Label(7, start+5, "制单机构", wcfFC));
			sheets[j].addCell(new Label(8, start+5, "制单人", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				PurchaseIncome p = (PurchaseIncome) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, ws.getWarehouseName(p.getWarehouseid())));
				sheets[j].addCell(new Label(2, row, p.getPoid()));
				sheets[j].addCell(new Label(3, row, p.getProvidename()));
				sheets[j].addCell(new Label(4, row, Dateutil.formatDate(p.getIncomedate())));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(5, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIstally());
				sheets[j].addCell(new Label(6, row, IsStr));
				sheets[j].addCell(new Label(7, row, organs.getOrganName(p.getMakeorganid())));
				sheets[j].addCell(new Label(8, row, us.getUsersName(p.getMakeid())));
				
				
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
