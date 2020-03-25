package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutListReceivableAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {
			String gz = request.getParameter("GreatZero");
			if (gz == null || gz.equals("")) {
				gz = "0";
			}

			String Condition = " (r.makeid='" + userid + "' "
					+ getOrVisitOrgan("r.makeorganid") + ") ";

			if (gz.equals("1")) {
				Condition += " and (r.receivablesum-r.alreadysum)!=0 and ";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Receivable" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "BillNo");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppReceivable ar = new AppReceivable();
			String receivabledescribe = "";
			List pbls = ar.getReceivable(whereSql);
			List<ReceivableForm> alpl = new ArrayList<ReceivableForm>();
			AppReceivableObject aro = new AppReceivableObject();
			
			for (int i = 0; i < pbls.size(); i++) {
				ReceivableForm rf = new ReceivableForm();
				Receivable o = (Receivable) pbls.get(i);
				rf.setId(o.getId());
				rf.setRoid(o.getRoid());
				ReceivableObject ro = aro.getReceivableObjectByIDOrgID(o
						.getRoid(), o.getMakeorganid());
				if (ro != null) {
					rf.setReceivableobjectname(ro.getPayer());
				} else {
					rf.setReceivableobjectname("");
				}
				rf.setSettlementsum(o.getReceivablesum());
				rf.setReceivablesum(o.getReceivablesum() - o.getAlreadysum());
				rf.setBillno(o.getBillno());
				if (rf.getBillno().length() > 0) {
					rf.setBn(rf.getBillno().substring(0, 2));
				} else {
					rf.setBn("");
				}
				receivabledescribe = o.getReceivabledescribe();
				rf.setReceivabledescribe(receivabledescribe.length() > 20 ? receivabledescribe
								.substring(0, 17)
								+ "..."
								: receivabledescribe);
				rf.setMakeorganid(o.getMakeorganid());
				rf.setMakedate(String.valueOf(o.getMakedate()).substring(
								0, 10));
				rf.setAwakedate(o.getAwakedate());
				int a = 0;
				rf.setOverage(0);
				if (o.getAwakedate() != null) {
					a = DateUtil.getDayDifference(DateUtil
							.getCurrentDateString(), DateUtil.formatDate(o
							.getAwakedate()));
				}
				if (a > 0) {
					rf.setOverage(a);
				}

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
					"attachment; filename=ListReceivableAll.xls");
			response.setContentType("application/msexcel");
			writeXls(alpl, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,9,"账务管理>>导出应收款结算!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<ReceivableForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organService = new OrganService();
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
			sheets[j].addCell(new Label(0, start, "应收款结算",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单日期:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+1, "对象类型:",seachT));
			sheets[j].addCell(new Label(3, start+1, HtmlSelect.getNameByOrder(request, "ObjectSort", getInt("ObjectSort"))));
			sheets[j].addCell(new Label(4, start+1, "选择对象:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("cname")));
			
			sheets[j].addCell(new Label(0, start+2, "应收金额不等于零:",seachT));
			sheets[j].addCell(new Label(1, start+2, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("GreatZero"))));
			sheets[j].addCell(new Label(2, start+2, "所属机构:",seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("oname")));
			sheets[j].addCell(new Label(4, start+2, "关键字:",seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "应收款对象名称",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "结算金额",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "应收款金额",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "所属机构",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "制单日期",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "到期日",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "超龄(天)",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "单据号",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "描述",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ReceivableForm p = (ReceivableForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row,p.getReceivableobjectname()));
				sheets[j].addCell(new Number(2, row, p.getSettlementsum(),wcfN));
				sheets[j].addCell(new Number(3, row, p.getReceivablesum(),wcfN));
				sheets[j].addCell(new Label(4, row, organService.getOrganName(p.getMakeorganid())));
				sheets[j].addCell(new Label(5, row, p.getMakedate()));
				sheets[j].addCell(new Label(6, row, DateUtil.formatDate(p.getAwakedate())));
				sheets[j].addCell(new Number(7, row, p.getOverage(),QWCF));
				sheets[j].addCell(new Label(8, row, p.getBillno()));
				sheets[j].addCell(new Label(9, row, p.getReceivabledescribe()));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
