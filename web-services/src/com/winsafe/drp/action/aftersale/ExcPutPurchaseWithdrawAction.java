package com.winsafe.drp.action.aftersale;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutPurchaseWithdrawAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
	

		try {
			
	    	String Condition=" (pw.makeid="+userid+" "+getOrVisitOrgan("pw.makeorganid")+") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PurchaseWithdraw" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseWithdraw asl = new AppPurchaseWithdraw();
			List<PurchaseWithdraw> pils = asl.getPurchaseWithdraw(whereSql);
			

			request.setAttribute("also", pils);

			DBUserLog.addUserLog(userid, 2,"采购管理>>导出采购退货");
			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPurchaseWithdraw.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<PurchaseWithdraw> list, OutputStream os,
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
			sheets[j].addCell(new Label(0, start, "采购退货  ",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deptname")));		
			sheets[j].addCell(new Label(4, start+1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("uname")));	
			
			sheets[j].addCell(new Label(0, start+2, "是否复核:",seachT));
			sheets[j].addCell(new Label(1, start+2, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(2, start+2, "是否结案:", seachT));
			sheets[j].addCell(new Label(3, start+2, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsEndcase"))));
			sheets[j].addCell(new Label(4, start+2, "是否作废:", seachT));
			sheets[j].addCell(new Label(5, start+2, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsBlankOut"))));
			
			sheets[j].addCell(new Label(0, start+3, "供应商:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("pname")));
			sheets[j].addCell(new Label(4, start+3, "制单日期:", seachT));
			sheets[j].addCell(new Label(5, start+3, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+5, "供应商", wcfFC));
			sheets[j].addCell(new Label(2, start+5, "联系人", wcfFC));
			sheets[j].addCell(new Label(3, start+5, "总金额", wcfFC));
			sheets[j].addCell(new Label(4, start+5, "制单机构", wcfFC));
			sheets[j].addCell(new Label(5, start+5, "制单人", wcfFC));
			sheets[j].addCell(new Label(6, start+5, "制单日期", wcfFC));
			sheets[j].addCell(new Label(7, start+5, "是否复核", wcfFC));
			sheets[j].addCell(new Label(8, start+5, "是否结案", wcfFC));
			sheets[j].addCell(new Label(9, start+5, "是否作废", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				PurchaseWithdraw p = (PurchaseWithdraw) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, p.getPname()));
				sheets[j].addCell(new Label(2, row, p.getPlinkman()));
				sheets[j].addCell(new Number(3, row, p.getTotalsum(),wcfN));
				sheets[j].addCell(new Label(4, row, organs.getOrganName(p.getMakeorganid())));
				String makeuser = us.getUsersName(p.getMakeid());
				sheets[j].addCell(new Label(5, row, makeuser));
				sheets[j].addCell(new Label(6, row, DateUtil.formatDate(p.getMakedate())));
				String isaudit = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(7, row, isaudit));
				String receive = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsendcase());
				sheets[j].addCell(new Label(8, row, receive));
				String Isblankout = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsblankout());
				sheets[j].addCell(new Label(9, row, Isblankout));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
