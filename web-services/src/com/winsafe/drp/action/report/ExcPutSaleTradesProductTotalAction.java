package com.winsafe.drp.action.report;

import java.io.OutputStream;
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
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutSaleTradesProductTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " so.id=sod.stid and (so.isendcase = 1 and so.isblankout = 0 "
					+ visitorgan + " )  ";

			String[] tablename = { "SaleTrades", "SaleTradesDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppSaleTrades aso = new AppSaleTrades();
			List list = aso.getSaleTradesProductTotal(whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=SaleTradesProductTotal.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出零售换货按产品汇总");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organ = new OrganService();
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
			sheets[j].addCell(new Label(0, start, "零售换货按产品汇总",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, organ.getOrganName(map.get("MakeOrganID").toString())));
			sheets[j].addCell(new Label(2, start+1, "日期:",seachT));
			sheets[j].addCell(new Label(3, start+1, map.get("BeginDate").toString()+"-"+map.get("EndDate").toString()));
			
			sheets[j].addCell(new Label(4, start+1, "产品:",seachT));
			sheets[j].addCell(new Label(5, start+1, map.get("ProductName").toString()));
			
			sheets[j].addCell(new Label(0, start+2, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+2, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+2, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+2, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start+3, "机构编号",wcfFC));
			sheets[j].addCell(new Label(1, start+3, "机构名称",wcfFC));
			sheets[j].addCell(new Label(2, start+3, "商品编号",wcfFC));
			sheets[j].addCell(new Label(3, start+3, "商品名称",wcfFC));
			sheets[j].addCell(new Label(4, start+3, "规格",wcfFC));
			sheets[j].addCell(new Label(5, start+3, "单位",wcfFC));
			sheets[j].addCell(new Label(6, start+3, "批次",wcfFC));
			sheets[j].addCell(new Label(7, start+3, "数量",wcfFC));

			int row = 0;
			Double totalqt = 0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 4;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("makeorganid").toString()));
				sheets[j].addCell(new Label(1, row, organ.getOrganName(p.get("makeorganid").toString())));
				sheets[j].addCell(new Label(2, row, p.get("productid").toString()));
				sheets[j].addCell(new Label(3, row, p.get("productname").toString()));
				sheets[j].addCell(new Label(4, row, p.get("specmode").toString()));
				String unitname = Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.valueOf(p.get("unitid").toString()));
				sheets[j].addCell(new Label(5, row, unitname));
				sheets[j].addCell(new Label(6, row, p.get("batch")==null?"":p.get("batch").toString()));
				sheets[j].addCell(new Number(7, row, Double.valueOf(p.get("quantity").toString()),QWCF));
		
				totalqt += Double.valueOf(p.get("quantity").toString());
	
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Label(2, row + 1, ""));
			sheets[j].addCell(new Label(3, row + 1, ""));
			sheets[j].addCell(new Label(4, row + 1, ""));
			sheets[j].addCell(new Label(5, row + 1, ""));
			sheets[j].addCell(new Label(6, row + 1, ""));
			sheets[j].addCell(new Number(7, row + 1, totalqt,QWCF));

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
