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
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquireForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutPurchaseInquireAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = this.getOrVisitOrgan("pi.makeorganid");
			}

			String Condition = " (pi.makeid=" + userid + " " + visitorgan
					+ ") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "PurchaseInquire" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseInquire api = new AppPurchaseInquire();
			AppProvider ap = new AppProvider();
			List<PurchaseInquire> pils = api.getPurchaseInquire(whereSql);
			List<PurchaseInquireForm> alpi = new ArrayList<PurchaseInquireForm>();
			PurchaseInquireForm pif = null;
			for (PurchaseInquire o:pils) {
				pif = new PurchaseInquireForm();
				pif.setId(o.getId());
				pif.setInquiretitle(o.getInquiretitle());
				pif.setProvidename(ap.getProviderByID(o.getPid())==null?"":ap.getProviderByID(o.getPid()).getPname());
				pif.setPlinkman(o.getPlinkman());
				pif.setMakeorganid(o.getMakeorganid());
				pif.setMakedeptid(o.getMakedeptid());
				pif.setMakedate(o.getMakedate());
				pif.setValiddate(o.getValiddate());
				pif.setMakeid(o.getMakeid());
				pif.setIsaudit(o.getIsaudit());
				alpi.add(pif);
			}

			if (alpi.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPurchaseInquire.xls");
			response.setContentType("application/msexcel");
			writeXls(alpi, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 2,"采购管理>>导出询价记录");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<PurchaseInquireForm> list, OutputStream os,
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
			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "询价记录  ",wchT));
			sheets[j].addCell(new Label(0, start+1, "供应商:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("ProvideName")));
			sheets[j].addCell(new Label(2, start+1, "询价日期:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(4, start+1, "是否复核:", seachT));
			sheets[j].addCell(new Label(5, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			
			sheets[j].addCell(new Label(0, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+2, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("uname")));		
			sheets[j].addCell(new Label(4, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("uname")));		
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "询价标题", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "供应商", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "供应商联系人", wcfFC));
			sheets[j].addCell(new Label(4, start+4, "有效天数", wcfFC));
			sheets[j].addCell(new Label(5, start+4, "是否复核", wcfFC));
			sheets[j].addCell(new Label(6, start+4, "制单机构", wcfFC));
			sheets[j].addCell(new Label(7, start+4, "制单人", wcfFC));
			sheets[j].addCell(new Label(8, start+4, "制单日期", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				PurchaseInquireForm p = (PurchaseInquireForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, p.getInquiretitle()));
				sheets[j].addCell(new Label(2, row, p.getProvidename()));
				sheets[j].addCell(new Label(3, row, p.getPlinkman()));
				sheets[j].addCell(new Label(4, row, p.getValiddate().toString()));
				String isaudit = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(5, row, isaudit));
				String makeorgan = organs.getOrganName(p.getMakeorganid());
				sheets[j].addCell(new Label(6, row, makeorgan));
				String makeuser = us.getUsersName(p.getMakeid());
				sheets[j].addCell(new Label(7, row, makeuser));
				sheets[j].addCell(new Label(8, row, DateUtil.formatDate(p.getMakedate())));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
