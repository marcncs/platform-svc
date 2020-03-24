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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.PayableObjectForm;
import com.winsafe.drp.dao.RecevablePayableService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutListPayableObjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		OrganService ao = new OrganService();
		try {

			String Condition = " (po.makeid='" + userid + "' "
					+ getOrVisitOrgan("po.makeorganid") + ") ";
			String[] tablename = { "PayableObject" };
			String whereSql = getWhereSql(tablename);
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppPayableObject apo = new AppPayableObject();
			UsersService au = new UsersService();
			AppCustomer ac = new AppCustomer();
			AppProvider ap = new AppProvider();

			List pbls = apo.getPayableObject(whereSql);
			List<PayableObjectForm> alpl = new ArrayList<PayableObjectForm>();
			String oid = "";
			for (int i = 0; i < pbls.size(); i++) {
				PayableObject po = (PayableObject) pbls.get(i);
				PayableObjectForm pof = new PayableObjectForm();
				oid = po.getOid();
				pof.setOid(oid);
				pof.setObjectsort(po.getObjectsort());
				pof.setObjectsortname(HtmlSelect.getNameByOrder(request,
						"ObjectSort", po.getObjectsort()));

				if (pof.getObjectsort() == 0) {
					pof.setPayee(ao.getOrganName(oid));
				}
				if (pof.getObjectsort() == 1) {
					pof.setPayee(ac.getCustomer(oid).getCname());
				}
				if (pof.getObjectsort() == 2) {
					pof.setPayee(ap.getPName(oid));
				}

				RecevablePayableService aprd = new RecevablePayableService(oid,
						po.getMakeorganid(), map.get("BeginDate").toString(),
						map.get("EndDate").toString());
				pof.setPrevioussum(DataFormat.dataFormat(aprd
						.getPrevioussumByP()));
				pof.setCurrentsum(DataFormat.dataFormat(aprd
						.getPayableCurrentSum()));
				pof.setCurrentalreadysum(DataFormat.dataFormat(aprd
						.getPayableCurrentAlreadySum()));
				pof.setWaitpayablesum(DataFormat.dataFormat(pof
						.getPrevioussum()
						+ pof.getCurrentsum() - pof.getCurrentalreadysum()));
				pof.setMakeorganid(po.getMakeorganid());
				pof.setMakeorganidname(ao.getOrganName(po.getMakeorganid()));
				pof.setMakeidname(au.getUsersName(po.getMakeid()));
				pof.setMakedate(DateUtil.formatDate(po.getMakedate()));

				alpl.add(pof);
			}
			if (alpl.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPayableObject.xls");
			response.setContentType("application/msexcel");
			writeXls(alpl, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,9,"账务管理>>导出付款管理!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<PayableObjectForm> list, OutputStream os,
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
			
			sheets[j].mergeCells(0, start, 7, start);
			sheets[j].addCell(new Label(0, start, "付款管理",wchT));
			sheets[j].addCell(new Label(0, start+1, "结算期单:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+1, "对象类型:",seachT));
			sheets[j].addCell(new Label(3, start+1, HtmlSelect.getNameByOrder(request, "ObjectSort", getInt("ObjectSort"))));
			sheets[j].addCell(new Label(4, start+1, "选择对象:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("cname")));

			sheets[j].addCell(new Label(0, start+2, "所属机构:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+2, "关键字:",seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "对象类型",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "领款方",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "期初应付金额",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "本期应付金额",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "本期已付金额",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "期未应付金额",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "所属机构",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				PayableObjectForm p = (PayableObjectForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getOid()));
				sheets[j].addCell(new Label(1, row,p.getObjectsortname()));
				sheets[j].addCell(new Label(2, row, p.getPayee()));
				sheets[j].addCell(new Number(3, row, p.getPrevioussum(),wcfN));
				sheets[j].addCell(new Number(4, row, p.getCurrentsum(),wcfN));
				sheets[j].addCell(new Number(5, row, p.getCurrentalreadysum(),wcfN));
				sheets[j].addCell(new Number(6, row, p.getWaitpayablesum(),wcfN));
				sheets[j].addCell(new Label(7, row, p.getMakeorganidname()));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
