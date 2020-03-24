package com.winsafe.drp.action.report;

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
import com.winsafe.drp.dao.AppPurchaseTradesDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.dao.PurchaseTradesDetail;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutPurchaseTradesDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pt.makeorganid");
			}
			String Condition = " pt.id=ptd.ptid and  isreceive=1 and pt.isblankout=0 "
					+ visitorgan + " ";

			String[] tablename = { "PurchaseTrades", "PurchaseTradesDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition(" MakeDate");
			String blur = getKeyWordCondition("ProvideName", "ProductName",
					"ProductID", "ProvideID", "MakeDate", "MakeOrganID");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseTradesDetail asod = new AppPurchaseTradesDetail();
			List sodls = asod.getDetailReport(whereSql);
			ArrayList list = new ArrayList();
			for (int d = 0; d < sodls.size(); d++) {
				DetailReportForm sodf = new DetailReportForm();
				Object[] o = (Object[]) sodls.get(d);
				PurchaseTrades pt = (PurchaseTrades) o[0];

				PurchaseTradesDetail pbd = (PurchaseTradesDetail) o[1];
	        	sodf.setMakedate(DateUtil.formatDate(pt.getMakedate()));
				sodf.setPid(pt.getProvideid());
				sodf.setOname(pt.getProvidename());
				sodf.setBillid(pbd.getPtid());
				sodf.setProductid(pbd.getProductid());
				sodf.setProductname(pbd.getProductname());
				sodf.setSpecmode(pbd.getSpecmode());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", pbd.getUnitid()));
				sodf.setQuantity(pbd.getQuantity());
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
					"attachment; filename=PurchaseTradesDetail.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出采购换货明细");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List list, OutputStream os, HttpServletRequest request)
			throws NumberFormatException, Exception {
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
			sheets[j].addCell(new Label(0, start, "采购换货明细",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "制单人:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("uname")));
			sheets[j].addCell(new Label(4, start+1, "制单日期:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "供应商:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("ProvideName")));
			sheets[j].addCell(new Label(2, start+2, "产品:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("ProductName")));

			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start+4, "供应商编号", wcfFC));
			sheets[j].addCell(new Label(1, start+4, "供应商", wcfFC));
			sheets[j].addCell(new Label(2, start+4, "单据号", wcfFC));
			sheets[j].addCell(new Label(3, start+4, "制单时间", wcfFC));
			sheets[j].addCell(new Label(4, start+4, "商品编号", wcfFC));
			sheets[j].addCell(new Label(5, start+4, "商品名称", wcfFC));
			sheets[j].addCell(new Label(6, start+4, "规格", wcfFC));
			sheets[j].addCell(new Label(7, start+4, "单位", wcfFC));
			sheets[j].addCell(new Label(8, start+4, "数量", wcfFC));
			int row = 0;
			Double totalquantity = 0.00;

			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				DetailReportForm p = (DetailReportForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getPid()));
				sheets[j].addCell(new Label(1, row, p.getOname()));
				sheets[j].addCell(new Label(2, row, p.getBillid()));
				sheets[j].addCell(new Label(3, row, p.getMakedate()));
				sheets[j].addCell(new Label(4, row, p.getProductid()));
				sheets[j].addCell(new Label(5, row, p.getProductname()));
				sheets[j].addCell(new Label(6, row, p.getSpecmode()));
				sheets[j].addCell(new Label(7, row, p.getUnitidname()));
				sheets[j].addCell(new Number(8, row, p.getQuantity(),QWCF));
				totalquantity += p.getQuantity();
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Label(2, row + 1, ""));
			sheets[j].addCell(new Label(3, row + 1, ""));
			sheets[j].addCell(new Label(4, row + 1, ""));
			sheets[j].addCell(new Label(5, row + 1, ""));
			sheets[j].addCell(new Label(6, row + 1, ""));
			sheets[j].addCell(new Label(7, row + 1, ""));
			sheets[j].addCell(new Number(8, row + 1, totalquantity,QWCF));

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
