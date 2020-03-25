package com.winsafe.drp.action.purchase;

import java.io.OutputStream;
import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutPurchasePlanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = this.getOrVisitOrgan("pp.makeorganid");
			}

			String Condition = " (pp.makeid=" + userid + " " + visitorgan
					+ ") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "PurchasePlan" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" PlanDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchasePlan apa = new AppPurchasePlan();
			List<PurchasePlan> pals = apa.searchPurchasePlan(whereSql);
			List<PurchasePlanForm> list = new ArrayList<PurchasePlanForm>();
			PurchasePlanForm ppf=null;
			for (PurchasePlan o:pals) {
				ppf = new PurchasePlanForm();
				ppf.setId(o.getId());
				ppf.setPurchasesort(o.getPurchasesort());
				ppf.setPlandate(o.getPlandate());
				ppf.setMakeorganid(o.getMakeorganid());
				ppf.setMakedeptid(o.getMakedeptid());
				ppf.setMakeid(o.getMakeid());
				ppf.setMakedate(o.getMakedate());
				ppf.setPlanid(o.getPlanid());
				ppf.setIscomplete(o.getIscomplete());
				ppf.setIsaudit(o.getIsaudit());
				ppf.setIsratify(o.getIsratify());

				list.add(ppf);
			}

			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPurchasePlan.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 2,"采购管理>>导出采购计划");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<PurchasePlanForm> list, OutputStream os,
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
			sheets[j].mergeCells(0, start, 10, start);
			sheets[j].addCell(new Label(0, start, "采购计划 ",wchT));
			sheets[j].addCell(new Label(0, start+1, "是否复核:",seachT));
			sheets[j].addCell(new Label(1, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(2, start+1, "是否批准:", seachT));
			sheets[j].addCell(new Label(3, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsRatify"))));
			sheets[j].addCell(new Label(4, start+1, "计划日期:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("uname")));		
			sheets[j].addCell(new Label(4, start+2, "计划人:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("uname2")));		
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "采购类型", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "计划日期", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "计划机构", wcfFC));
			sheets[j].addCell(new Label(4, start+4, "计划人", wcfFC));
			sheets[j].addCell(new Label(5, start+4, "是否复核", wcfFC));
			sheets[j].addCell(new Label(6, start+4, "是否批准", wcfFC));
			sheets[j].addCell(new Label(7, start+4, "制单机构", wcfFC));
			sheets[j].addCell(new Label(8, start+4, "制单部门", wcfFC));
			sheets[j].addCell(new Label(9, start+4, "制单人", wcfFC));
			sheets[j].addCell(new Label(10, start+4, "制单日期", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				PurchasePlanForm p = (PurchasePlanForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				String purchaseSort="";
				if(p.getPurchasesort() != null){
					purchaseSort = HtmlSelect.getResourceName(request,"PurchaseSort",p.getPurchasesort());
				}
				sheets[j].addCell(new Label(1, row, purchaseSort));
				sheets[j].addCell(new Label(2, row, DateUtil.formatDate(p.getPlandate())));
				String planorgan = organs.getOrganName(p.getMakeorganid());
				sheets[j].addCell(new Label(3, row, planorgan));
				String planman = us.getUsersName(p.getPlanid());
				sheets[j].addCell(new Label(4, row, planman));
				String isaudit = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(5, row, isaudit));
				String isratify = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsratify());
				sheets[j].addCell(new Label(6, row, isratify));
				String makeorgan = organs.getOrganName(p.getMakeorganid());
				sheets[j].addCell(new Label(7, row, makeorgan));
				String makedept = ds.getDeptName(p.getMakedeptid());
				sheets[j].addCell(new Label(8, row, makedept));
				String makeuser = us.getUsersName(p.getMakeid());
				sheets[j].addCell(new Label(9, row, makeuser));
				sheets[j].addCell(new Label(10, row, DateUtil.formatDate(p.getMakedate())));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
