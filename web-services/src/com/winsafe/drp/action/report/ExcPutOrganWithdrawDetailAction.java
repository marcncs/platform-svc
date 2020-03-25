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
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutOrganWithdrawDetailAction extends BaseAction {
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
			String Condition = "ow.id=owd.owid and (ow.iscomplete=1 and ow.isblankout=0 "
					+ visitorgan + " )  ";
			String[] tablename = { "OrganWithdraw", "OrganWithdrawDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String blur =getKeyWordCondition("MakeOrganID","POrganID","POrganName","ID","ProductID","ProductName");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppOrganWithdraw asod = new AppOrganWithdraw();
			List list = asod.getOrganWithdrawDetail(whereSql);

	        if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=OrganWithdrawDetail.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出渠道退货明细");
		}catch(Exception e){
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
			
			sheets[j].mergeCells(0, start, 10, start);
			sheets[j].addCell(new Label(0, start, "渠道退货明细",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, organ.getOrganName(map.get("MakeOrganID").toString())));
			sheets[j].addCell(new Label(2, start+1, "供货机构:",seachT));
			sheets[j].addCell(new Label(3, start+1, organ.getOrganName(map.get("POrganID").toString())));
			sheets[j].addCell(new Label(4, start+1, "制单日期:",seachT));
			sheets[j].addCell(new Label(5, start+1, map.get("BeginDate").toString()+"-"+map.get("EndDate").toString()));

			sheets[j].addCell(new Label(0, start+2, "产品:",seachT));
			sheets[j].addCell(new Label(1, start+2, map.get("ProductName").toString()));

			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "单据号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "制单日期",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "供货机构",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "退货机构",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "商品编号",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "商品名称",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "单价",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "数量",wcfFC));
			sheets[j].addCell(new Label(10, start+4, "金额",wcfFC));
			int row = 0;
			Double totalquantity = 0.00;
			Double totalsubsum = 0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("id").toString()));
				sheets[j].addCell(new Label(1, row, p.get("makedate").toString()));
				sheets[j].addCell(new Label(2, row, p.get("porganname").toString()));
				sheets[j].addCell(new Label(3, row, getOrganName(p.get("makeorganid"))));
				sheets[j].addCell(new Label(4, row, p.get("productid").toString()));
				sheets[j].addCell(new Label(5, row, p.get("productname").toString()));
				sheets[j].addCell(new Label(6, row, p.get("specmode").toString()));
				String unitname = Internation.getStringByKeyPositionDB("CountUnit",		                  
	                    Integer.valueOf(p.get("unitid").toString()));
				sheets[j].addCell(new Label(7, row, unitname));
				sheets[j].addCell(new Number(8, row, Double.valueOf(p.get("unitprice").toString()),wcfN));
				sheets[j].addCell(new Number(9, row, Double.valueOf(p.get("quantity").toString()),QWCF));
				sheets[j].addCell(new Number(10, row, Double.valueOf(p.get("subsum").toString()),wcfN));
				totalquantity += Double.valueOf(p.get("quantity").toString());
				totalsubsum+=Double.valueOf(p.get("subsum").toString());
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
			sheets[j].addCell(new Number(9, row + 1, totalquantity,QWCF));
			sheets[j].addCell(new Number(10, row + 1, totalsubsum,wcfN));

		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private String getOrganName(Object obj) throws Exception{
		OrganService oo = new OrganService();
		if(obj == null){
			return "";
		}else{
			return oo.getOrganName(obj.toString());
		}
	}

}
