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
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutCostProductDetailAction extends BaseAction {
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
			String Condition = " so.id=sod.ttid and (so.isblankout=0 "
					+ visitorgan + " ) ";

			String[] tablename = { "TakeTicket", "TakeTicketDetail" };
			String whereSql = getWhereSql(tablename);
			String blur = getKeyWordCondition("OID", "OName", "Tel", "BillNo",
					"ProductID", "ProductName", "SpecMode", "Batch");

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppTakeTicket aso = new AppTakeTicket();
			List list = aso.getCostProductDetail(whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=CostProductDetail.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,10, "报表分析>>导出成本按产品汇总");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List list, OutputStream os, HttpServletRequest request)
			throws NumberFormatException, Exception {
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
			
			sheets[j].mergeCells(0, start, 13, start);
			sheets[j].addCell(new Label(0, start, "营业成本明细",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, organ.getOrganName(map.get("MakeOrganID").toString())));
			sheets[j].addCell(new Label(2, start+1, "产品:",seachT));
			sheets[j].addCell(new Label(3, start+1, map.get("ProductName").toString()));
			sheets[j].addCell(new Label(4, start+1, "制单日期:",seachT));
			sheets[j].addCell(new Label(5, start+1, map.get("BeginDate").toString()+"-"+map.get("EndDate").toString()));
			
			sheets[j].addCell(new Label(0, start+2, "单据类型:",seachT));
			if(request.getParameter("BSort")==null||request.getParameter("BSort").equals("")){}else{
				sheets[j].addCell(new Label(1, start+2, HtmlSelect.getNameByOrder(request, "BSort", Integer.valueOf(request.getParameter("BSort")))));
				}
		
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start+4, "对象编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "对象名称",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "单据号",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "制单日期",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "制单机构",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "商品名称",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "批次",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "数量",wcfFC));
			sheets[j].addCell(new Label(10, start+4, "单价",wcfFC));
			sheets[j].addCell(new Label(11, start+4, "金额小计",wcfFC));
			sheets[j].addCell(new Label(12, start+4, "成本小计",wcfFC));
			sheets[j].addCell(new Label(13, start+4, "毛利小计",wcfFC));
			int row = 0;
			Double totalqt = 0.00;
			Double totalsum = 0.00;
			Double totalcb = 0.00;
			Double totalml = 0.00;
			Double je = 0.00;
			Double cost = 0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("oid").toString()));
				sheets[j].addCell(new Label(1, row, p.get("oname").toString()));
				sheets[j].addCell(new Label(2, row, p.get("billno").toString()));
				sheets[j].addCell(new Label(3, row, p.get("makedate")
						.toString()));
				sheets[j].addCell(new Label(4, row, organ.getOrganName(p
						.get("makeorganid").toString())));
				sheets[j].addCell(new Label(5, row, p.get("productname")
						.toString()));
				sheets[j].addCell(new Label(6, row, p.get("specmode")
						.toString()));
				sheets[j].addCell(new Label(7, row, p.get("batch").toString()));
				String unitname = HtmlSelect.getResourceName(request, "CountUnit",Integer.valueOf(p.get("unitid").toString()));
				sheets[j].addCell(new Label(8, row, unitname));
				sheets[j].addCell(new Number(9, row, Double.valueOf(p.get("quantity").toString()), QWCF));
				sheets[j].addCell(new Number(10, row, Double.valueOf(p.get("unitprice").toString()), wcfN));
				
				je = Double.valueOf(p.get("unitprice").toString())* Double.valueOf(p.get("quantity").toString());
				cost= Double.valueOf(p.get("cost").toString())* Double.valueOf(p.get("quantity").toString());
				
				sheets[j].addCell(new Number(11, row, je, wcfN));
				sheets[j].addCell(new Number(12, row, cost, wcfN));
				sheets[j].addCell(new Number(13, row, je- cost, wcfN));
				
				
				totalqt += Double.valueOf(p.get("quantity").toString());
				totalsum += je;
				totalcb += cost;
				totalml += je-cost;
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Label(2, row + 1, ""));
			sheets[j].addCell(new Label(3, row + 1, ""));
			sheets[j].addCell(new Label(4, row + 1, ""));
			sheets[j].addCell(new Label(5, row + 1, ""));
			sheets[j].addCell(new Label(6, row + 1, ""));
			sheets[j].addCell(new Label(7, row + 1, ""));
			sheets[j].addCell(new Label(8, row + 1, ""));
			sheets[j].addCell(new Number(9, row + 1, totalqt, QWCF));
			sheets[j].addCell(new Label(10, row + 1, ""));
			sheets[j].addCell(new Number(11, row + 1, totalsum, wcfN));
			sheets[j].addCell(new Number(12, row + 1, totalcb, wcfN));
			sheets[j].addCell(new Number(13, row + 1, totalml, wcfN));

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
