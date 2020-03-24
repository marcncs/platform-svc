package com.winsafe.drp.action.purchase;

import java.io.OutputStream;
import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillForm;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutPurchaseBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getOrVisitOrgan("pb.makeorganid");
			}

			String Condition = " (pb.makeid=" + userid + " " + visitorgan
					+ ") ";
			String[] tablename = { "PurchaseBill" };
			String whereSql =getWhereSql2(tablename);

			String timeCondition = getTimeCondition("ReceiveDate");
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseBill apb = new AppPurchaseBill();
			List<PurchaseBill> pbls = apb.getPurchaseBill(whereSql);
			List<PurchaseBillForm> alpb = new ArrayList<PurchaseBillForm>();
			for (int i = 0; i < pbls.size(); i++) {
				PurchaseBillForm pbf = new PurchaseBillForm();
				PurchaseBill o = (PurchaseBill) pbls.get(i);
				pbf.setId(o.getId());
				pbf.setPname(o.getPname());
				pbf.setTotalsum(o.getTotalsum());
				pbf.setMakeorganid(o.getMakeorganid());
				pbf.setMakedate(o.getMakedate());
				pbf.setMakeid(o.getMakeid());
				pbf.setMakedeptid(o.getMakedeptid());
				pbf.setReceivedate(o.getReceivedate());
				pbf.setIsaudit(o.getIsaudit());
				pbf.setIsratify(o.getIsratify());
				pbf.setPaymode(o.getPaymode());
				alpb.add(pbf);
			}


			if (alpb.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPurchaseBill.xls");
			response.setContentType("application/msexcel");
			writeXls(alpb, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 2,"采购管理>>导出采购订单");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<PurchaseBillForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		UsersService us = new UsersService();
		DeptService ds = new DeptService();
		
		
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
			sheets[j].addCell(new Label(0, start, "采购订单 ",wchT));
			sheets[j].addCell(new Label(0, start+1, "供应商:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("ProvideName")));
			sheets[j].addCell(new Label(2, start+1, "是否复核:", seachT));
			sheets[j].addCell(new Label(3, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(4, start+1, "是否批准:", seachT));
			sheets[j].addCell(new Label(5, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsRatify"))));
			
			sheets[j].addCell(new Label(0, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+2, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("deptname")));		
			sheets[j].addCell(new Label(4, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("uname")));
			
			sheets[j].addCell(new Label(0, start+3, "预计到货日期:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+3, "关键字:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("KeyWord").toString()));
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+5, "供应商", wcfFC));
			sheets[j].addCell(new Label(2, start+5, "金额", wcfFC));
			sheets[j].addCell(new Label(3, start+5, "预计到货日期", wcfFC));
			sheets[j].addCell(new Label(4, start+5, "是否复核", wcfFC));
			sheets[j].addCell(new Label(5, start+5, "是否批准", wcfFC));
			sheets[j].addCell(new Label(6, start+5, "结算方式", wcfFC));
			sheets[j].addCell(new Label(7, start+5, "制单机构", wcfFC));
			sheets[j].addCell(new Label(8, start+5, "制单部门", wcfFC));
			sheets[j].addCell(new Label(9, start+5, "制单人", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				PurchaseBillForm p = (PurchaseBillForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, p.getPname()));
				sheets[j].addCell(new Number(2, row, p.getTotalsum(),wcfN));
				sheets[j].addCell(new Label(3, row, DateUtil.formatDate(p.getReceivedate())));
				String isaudit = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(4, row, isaudit));
				String isratify = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsratify());
				sheets[j].addCell(new Label(5, row, isratify));
				String paymode = HtmlSelect.getNameByOrder(request, "PayMode", p.getPaymode());
				sheets[j].addCell(new Label(6, row, paymode));
				String makeorgan = organs.getOrganName(p.getMakeorganid());
				sheets[j].addCell(new Label(7, row, makeorgan));
				String makedept = ds.getDeptName(p.getMakedeptid());
				sheets[j].addCell(new Label(8, row, makedept));
				String makeuser = us.getUsersName(p.getMakeid());
				sheets[j].addCell(new Label(9, row, makeuser));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
