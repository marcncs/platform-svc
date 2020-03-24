package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.CashWasteBookForm;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutListCashWasteBookAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			AppCashBank appcb = new AppCashBank();
			List cblist = appcb.getAllCashBankByOID(users.getMakeorganid());
			StringBuffer cbid = new StringBuffer();
			cbid.append("0");
			for (int i = 0; i < cblist.size(); i++) {
				CashBank cb = (CashBank) cblist.get(i);
				cbid.append(",").append(cb.getId());
			}

			String Condition = " cwb.cbid in (" + cbid.toString() + ") ";
			String[] tablename = { "CashWasteBook" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("RecordDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppCashWasteBook ail = new AppCashWasteBook();

			List<CashWasteBook> slls = ail.searchCashWasteBook(whereSql);
			List<CashWasteBookForm> arls = new ArrayList<CashWasteBookForm>();

			for (CashWasteBook cwb : slls) {
				CashWasteBookForm ilf = new CashWasteBookForm();
				ilf.setId(cwb.getId());
				ilf.setCbid(cwb.getCbid());
				ilf.setCbidname(appcb.getCashBankById(cwb.getCbid())
						.getCbname());
				ilf.setBillno(cwb.getBillno());
				ilf.setMemo(cwb.getMemo());
				ilf.setCyclefirstsum(cwb.getCyclefirstsum());
				ilf.setCycleinsum(cwb.getCycleinsum());
				ilf.setCycleoutsum(cwb.getCycleoutsum());
				ilf.setCyclebalancesum(cwb.getCyclebalancesum());
				ilf.setRecorddate(DateUtil.formatDate(cwb.getRecorddate()));
				arls.add(ilf);
			}
			if (arls.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListCashWasteBook.xls");
			response.setContentType("application/msexcel");
			writeXls(arls, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,9,"账务管理>>导出资金台帐!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<CashWasteBookForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		AppCashBank appcb = new AppCashBank();
		
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
			sheets[j].addCell(new Label(0, start, "查价",wchT));
			sheets[j].addCell(new Label(0, start+1, "现金银行:",seachT));
			String CBID = request.getParameter("CBID");
			String cbname ="";
			if(!CBID.equals("")){
				cbname  = appcb.getCBName(Integer.valueOf(CBID));
			}
			sheets[j].addCell(new Label(1, start+1, cbname));
			sheets[j].addCell(new Label(2, start+1, "记录日期:",seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));

			sheets[j].addCell(new Label(0, start+2, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+2, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+2, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+2, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+3, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+3, "现金银行",wcfFC));
			sheets[j].addCell(new Label(2, start+3, "单据号",wcfFC));
			sheets[j].addCell(new Label(3, start+3, "摘要",wcfFC));
			sheets[j].addCell(new Label(4, start+3, "期初金额",wcfFC));
			sheets[j].addCell(new Label(5, start+3, "本期收入",wcfFC));
			sheets[j].addCell(new Label(6, start+3, "本期支出",wcfFC));
			sheets[j].addCell(new Label(7, start+3, "本期结存金额",wcfFC));
			sheets[j].addCell(new Label(8, start+3, "记录日期",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 4;
				CashWasteBookForm p = (CashWasteBookForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, p.getCbidname()));
				sheets[j].addCell(new Label(2, row, p.getBillno()));
				sheets[j].addCell(new Label(3, row, p.getMemo()));
				sheets[j].addCell(new Number(4, row, p.getCyclefirstsum(),wcfN));
				sheets[j].addCell(new Number(5, row, p.getCycleinsum(),wcfN));
				sheets[j].addCell(new Number(6, row, p.getCycleoutsum(),wcfN));
				sheets[j].addCell(new Number(7, row, p.getCyclebalancesum(),wcfN));
				sheets[j].addCell(new Label(8, row, p.getRecorddate()));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
