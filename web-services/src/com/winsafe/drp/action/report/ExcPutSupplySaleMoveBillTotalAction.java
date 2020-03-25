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
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutSupplySaleMoveBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}

			String[] tablename = { "SupplySaleMove" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String Condition = "(iscomplete=1 and isblankout=0  "
					+ visitorgan + ")";
			String blur =getKeyWordCondition("MakeOrganID","ReceiveOrganID","ID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppSupplySaleMove aso = new AppSupplySaleMove();
			List list = aso.getSupplySaleMoveBillTotal(whereSql);

			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=SupplySaleMoveBillTotal.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出渠道代销按单据汇总");
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
			sheets[j].addCell(new Label(0, start, "渠道代销按单据汇总",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("MakeOrganIDName")));
			sheets[j].addCell(new Label(2, start+1, "申请机构:",seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("SupplyOrganIDName")));
			sheets[j].addCell(new Label(4, start+1, "调入机构:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("InOrganIDName")));
			
			sheets[j].addCell(new Label(0, start+2, "订购日期:",seachT));
			sheets[j].addCell(new Label(1, start+2, map.get("BeginDate").toString()+"-"+map.get("EndDate").toString()));
			sheets[j].addCell(new Label(2, start+2, "单据编号:",seachT));
			sheets[j].addCell(new Label(3, start+2, map.get("ID").toString()));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			
			sheets[j].addCell(new Label(0, start+4, "单据编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "订购时间",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "调入机构",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "制单机构",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "制单时间",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "申请机构",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "订购金额",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "销售金额",wcfFC));
			
			
			int row = 0;
			Double totalqt = 0.00;
			Double totalsum = 0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("id").toString()));
				sheets[j].addCell(new Label(1, row, p.get("movedate").toString()));
				sheets[j].addCell(new Label(2, row, organ.getOrganName(p.get("inorganid").toString())));
				sheets[j].addCell(new Label(3, row, organ.getOrganName(p.get("makeorganid").toString())));
				sheets[j].addCell(new Label(4, row, p.get("makedate").toString()));
				sheets[j].addCell(new Label(5, row, organ.getOrganName(p.get("supplyorganid").toString())));
				sheets[j].addCell(new Number(6, row, Double.valueOf(p.get("totalsum").toString()),QWCF));
				sheets[j].addCell(new Number(7, row, Double.valueOf(p.get("stotalsum").toString()),wcfN));
				totalqt += Double.valueOf(p.get("totalsum").toString());
				totalsum += Double.valueOf(p.get("stotalsum").toString());
			}
			sheets[j].addCell(new Label(0, row + 1, "合计："));
			sheets[j].addCell(new Label(1, row + 1, ""));
			sheets[j].addCell(new Label(2, row + 1, ""));
			sheets[j].addCell(new Label(3, row + 1, ""));
			sheets[j].addCell(new Label(4, row + 1, ""));
			sheets[j].addCell(new Label(5, row + 1, ""));
			sheets[j].addCell(new Number(6, row + 1, totalqt,QWCF));
			sheets[j].addCell(new Number(7, row + 1, totalsum,wcfN));

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
