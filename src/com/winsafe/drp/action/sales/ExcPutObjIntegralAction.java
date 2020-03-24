package com.winsafe.drp.action.sales;

import java.io.OutputStream;
import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.IntegralIOService;
import com.winsafe.drp.dao.ObjIntegral;
import com.winsafe.drp.dao.ObjIntegralForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutObjIntegralAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		OrganService ao = new OrganService();
		try {
			String begindate = request.getParameter("BeginDate");
			String enddate = request.getParameter("EndDate");

			String Condition=" (" +getVisitOrgan("oi.organid") +") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ObjIntegral" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppObjIntegral aro = new AppObjIntegral();
			AppCustomer ac = new AppCustomer();

			List pbls = aro.getObjIntegral(whereSql);
			ArrayList alpl = new ArrayList();
			
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");

			for (int i = 0; i < pbls.size(); i++) {
				ObjIntegralForm rf = new ObjIntegralForm();
				ObjIntegral o = (ObjIntegral) pbls.get(i);
				rf.setOiid(o.getOiid());
				rf.setOid(o.getOid());
				rf.setOsort(o.getOsort());

				if (rf.getOsort() == 0) {
					rf.setOname(ao.getOrganName(o.getOid()));
				}
				if (rf.getOsort() == 1) {
					rf.setOname(ac.getCustomer(o.getOid()).getCname());
				}
				rf.setOmobile(o.getOmobile());
				
				IntegralIOService iis = new IntegralIOService(rf.getOid(),users.getMakeorganid(),begindate, enddate);
				rf.setRvincome(iis.getRvIncome(timeCondition));
				rf.setAlincome(iis.getAlIncome(timeCondition));
				rf.setRvout(iis.getRvOut(timeCondition));
				rf.setAlout(iis.getAlOut(timeCondition));
				rf.setBalance(iis.getBalance());
				rf.setOrganid(o.getOrganid());

				alpl.add(rf);
			}
			if (alpl.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListObjIntegral.xls");
			response.setContentType("application/msexcel");
			writeXls(alpl, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,6,"零售管理>>导出零售发票!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<ObjIntegralForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
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
			sheets[j].addCell(new Label(0, start, "积分对象",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "期间:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+1, "对象类型:", seachT));
			String IsStr = HtmlSelect.getNameByOrder(request, "ObjectSort", getInt("ObjectSort"));
			sheets[j].addCell(new Label(3, start+1, IsStr));
			sheets[j].addCell(new Label(4, start+1, "选择对象:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("cname")));
		
			sheets[j].addCell(new Label(0, start+2, "所属机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("KeWord")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "对象类型",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "编号",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "对象名称",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "手机",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "应收",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "已收",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "应付",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "已付",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "结余",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "所属机构",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ObjIntegralForm p = (ObjIntegralForm) list.get(i);
				sheets[j].addCell(new Label(0, row, HtmlSelect.getNameByOrder(request, "ObjectSort", p.getOsort())));
				sheets[j].addCell(new Label(1, row, p.getOid()));
				sheets[j].addCell(new Label(2, row, p.getOname()));
				sheets[j].addCell(new Label(3, row, p.getOmobile()));
				sheets[j].addCell(new Number(4, row, p.getRvincome(),QWCF));
				sheets[j].addCell(new Number(5, row, p.getAlincome(),QWCF));
				sheets[j].addCell(new Number(6, row, p.getRvout(),QWCF));
				sheets[j].addCell(new Number(7, row, p.getAlout(),QWCF));
				sheets[j].addCell(new Number(8, row, p.getBalance(),QWCF));
				sheets[j].addCell(new Label(9, row, organs.getOrganName(p.getOrganid())));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
