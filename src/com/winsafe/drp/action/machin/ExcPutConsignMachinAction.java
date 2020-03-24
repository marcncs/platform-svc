package com.winsafe.drp.action.machin;

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
import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.ConsignMachinForm;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutConsignMachinAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String Condition = " (cm.makeid='" + userid + "' "
					+ getOrVisitOrgan("cm.makeorganid") + ") ";

			String[] tablename = { "ConsignMachin" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("CompleteIntendDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppUsers au = new AppUsers();
			AppProvider ap = new AppProvider();
			AppConsignMachin api = new AppConsignMachin();
			List<ConsignMachin> pils = api.getConsignMachin(whereSql);
			List<ConsignMachinForm> alpi = new ArrayList<ConsignMachinForm>();
			ConsignMachinForm af=null;
			for (ConsignMachin a:pils) {
				af = new ConsignMachinForm();
				af.setId(a.getId());
				af.setPidname(ap.getProviderByID(a.getPid()).getPname());
				af.setCproductid(a.getCproductid());
				af.setCproductname(a.getCproductname());
				af.setCspecmode(a.getCspecmode().length() > 10 ? a
						.getCspecmode().substring(0, 10)
						+ "..." : a.getCspecmode());
				af.setCunitidname(HtmlSelect.getResourceName(request,
						"CountUnit", a.getCunitid()));
				af.setCquantity(a.getCquantity());
				af.setCompleteintenddate(DateUtil.formatDate(a
						.getCompleteintenddate()));
				af.setMakeidname(au.getUsersByID(a.getMakeid()).getRealname());
				af.setIsaudit(a.getIsaudit());
				af.setIsendcase(a.getIsendcase());
				alpi.add(af);
			}

			if (alpi.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListConsignMachin.xls");
			response.setContentType("application/msexcel");
			writeXls(alpi, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,3,"生产组装>>导出委外加工单!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<ConsignMachinForm> list, OutputStream os,
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
			
			sheets[j].mergeCells(0, start, 6, start);
			sheets[j].addCell(new Label(0, start, "组装关系",wchT));
			sheets[j].addCell(new Label(0, start+1, "供应商:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("ProvideName")));
			sheets[j].addCell(new Label(2, start+1, "加工产品:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("CProductName")));
			sheets[j].addCell(new Label(4, start+1, "是否复核:", seachT));
			Integer isaudit = getInt("IsAudit");
			String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", isaudit);
			sheets[j].addCell(new Label(5, start+1, IsStr));		
			
			sheets[j].addCell(new Label(0, start+2, "预计完工日期:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "序号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "供应商",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "加工产品编码",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "加工产品名称",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "数量",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "预计完工日期",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "是否复核",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "是否结案",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ConsignMachinForm p = (ConsignMachinForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, p.getPidname()));
				sheets[j].addCell(new Label(2, row, p.getCproductid()));
				sheets[j].addCell(new Label(3, row, p.getCproductname()));
				sheets[j].addCell(new Label(4, row, p.getCspecmode()));
				sheets[j].addCell(new Label(5, row, p.getCunitidname()));
				sheets[j].addCell(new Number(6, row, p.getCquantity(),QWCF));
				sheets[j].addCell(new Label(7, row, p.getCompleteintenddate()));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(8, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsendcase());
				sheets[j].addCell(new Label(9, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
