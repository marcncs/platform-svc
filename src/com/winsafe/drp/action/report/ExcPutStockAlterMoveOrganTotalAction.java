package com.winsafe.drp.action.report;

import java.io.IOException;
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
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutStockAlterMoveOrganTotalAction extends BaseAction {
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

			String Condition = " (iscomplete=1 and isblankout=0  "
				+ visitorgan + ")";

			String[] tablename = { "StockAlterMove" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String blur = getKeyWordCondition("MakeOrganID","MakeOrganIDName","ReceiveOrganIDName","OLinkman","OTel");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppStockAlterMove aso = new AppStockAlterMove();
			List list = aso.getStockAlterOrganTotal(whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=StockAlterMoveOrganTotal.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出订购按机构汇总");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws IOException,Exception,
			RowsExceededException, WriteException {
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
			
			sheets[j].mergeCells(0, start, 4, start);
			sheets[j].addCell(new Label(0, start, "渠道订购按机构汇总",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, organ.getOrganName(map.get("MakeOrganID").toString())));
			sheets[j].addCell(new Label(2, start+1, "订购机构:",seachT));
			sheets[j].addCell(new Label(3, start+1, organ.getOrganName(map.get("ReceiveOrganID").toString())));
			sheets[j].addCell(new Label(4, start+1, "制单日期:",seachT));
			sheets[j].addCell(new Label(5, start+1, map.get("BeginDate").toString()+"-"+map.get("EndDate").toString()));
			
			sheets[j].addCell(new Label(0, start+2, "联系人:",seachT));
			sheets[j].addCell(new Label(1, start+2, map.get("OLinkman").toString()));
			sheets[j].addCell(new Label(2, start+2, "联系电话:",seachT));
			sheets[j].addCell(new Label(3, start+2, map.get("OTel").toString()));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "机构编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "机构名称",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "联系人",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "联系电话",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "金额",wcfFC));
			int row=0;
			Double totalsum=0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("receiveorganid").toString()));
				sheets[j].addCell(new Label(1, row, setNull(p.get("receiveorganidname"))));
				sheets[j].addCell(new Label(2, row, setNull(p.get("olinkman"))));
				sheets[j].addCell(new Label(3, row, setNull(p.get("otel"))));
				sheets[j].addCell(new Number(4, row, Double.valueOf(p.get("totalsum").toString()),wcfN));
				totalsum+=Double.valueOf(p.get("totalsum").toString());
				
			}
			sheets[j].addCell(new Label(0, row+1, "合计："));
			sheets[j].addCell(new Label(1,  row+1, ""));
			sheets[j].addCell(new Label(2,  row+1, ""));
			sheets[j].addCell(new Label(3,  row+1, ""));
			sheets[j].addCell(new Number(4,  row+1,totalsum,wcfN));
			
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	private String setNull(Object obj){
		return obj==null?"":obj.toString();
	}

}
