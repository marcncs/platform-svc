package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutProductInterconvertTotalAction extends BaseAction {
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
			String Condition = " pw.id=pwd.piid and  pw.iscomplete=1"
					+ visitorgan
					+ " ";

			String[] tablename = { "ProductInterconvert", "ProductInterconvertDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList list = new ArrayList();

			AppProductInterconvertDetail asod = new AppProductInterconvertDetail();
			List pils = asod.getTotalReport(whereSql);
			for (Iterator it = pils.iterator(); it.hasNext();) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) it.next();
				sodf.setProductid(String.valueOf(o[0]));
				sodf.setProductname(String.valueOf(o[1]));
				sodf.setSpecmode(String.valueOf(o[2]));
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.valueOf(o[3].toString())));
				sodf.setQuantity(Double.valueOf(o[4].toString()));
				list.add(sodf);
			}
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ProductInterConvertTotal.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出商品互转汇总");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
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
			sheets[j].mergeCells(0, start, 5, start);
			sheets[j].addCell(new Label(0, start, "产品互转汇总",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "制单人:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("uname")));
			sheets[j].addCell(new Label(4, start+1, "制单日期:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "转出仓库:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("wname")));
			sheets[j].addCell(new Label(2, start+2, "转入仓库:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("wname2")));
			sheets[j].addCell(new Label(4, start+2, "产品:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("ProductName")));

			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "商品编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "商品名称",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "数量",wcfFC));
			int row = 0;
			Double totalquantity = 0.00;
			
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				DetailReportForm p = (DetailReportForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getProductid()));
				sheets[j].addCell(new Label(1, row, p.getProductname()));
				sheets[j].addCell(new Label(2, row, p.getSpecmode()));
				sheets[j].addCell(new Label(3, row, p.getUnitidname()));
				sheets[j].addCell(new Number(4, row, p.getQuantity(),QWCF));
				totalquantity += p.getQuantity();
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Label(2, row + 1, ""));
			sheets[j].addCell(new Label(3, row + 1, ""));
			sheets[j].addCell(new Number(4, row + 1, totalquantity,QWCF));

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
