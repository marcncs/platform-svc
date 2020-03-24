package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
	
			String Condition = " (sb.makeid=" + userid + " " +getOrVisitOrgan("sb.makeorganid")+ ") ";


			String[] tablename = { "ShipmentBill" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition =getTimeCondition("RequireDate");
			String blur = getKeyWordCondition("KeysContent");

			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppShipmentBill asb = new AppShipmentBill();
			List<ShipmentBill> pils = asb.searchShipmentBill(whereSql);
			

			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListShipmentBill.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 8,"配送中心>>导出送货清单");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<ShipmentBill> list, OutputStream os,
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
			sheets[j].addCell(new Label(0, start, "送货清单",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "对象类型:", seachT));
			String IsStr = HtmlSelect.getNameByOrder(request, "ObjectSort", getInt("ObjectSort"));
			sheets[j].addCell(new Label(1, start+1, IsStr));		
			sheets[j].addCell(new Label(2, start+1, "选择对象:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("cname")));
			sheets[j].addCell(new Label(4, start+1, "需求日期:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "是否转配送:", seachT));
			IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsTrans"));
			sheets[j].addCell(new Label(1, start+2, IsStr));
			sheets[j].addCell(new Label(2, start+2, "是否完成配送:", seachT));
			IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"));
			sheets[j].addCell(new Label(3, start+2, IsStr));
			sheets[j].addCell(new Label(4, start+2, "是否作废:", seachT));
			IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsBlankOut"));
			sheets[j].addCell(new Label(5, start+2, IsStr));
			
			sheets[j].addCell(new Label(0, start+3, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+3, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getParameter("deptname")));
			sheets[j].addCell(new Label(4, start+3, "制单用户:", seachT));
			sheets[j].addCell(new Label(5, start+3, request.getParameter("uname")));
			
			
			
			sheets[j].addCell(new Label(0, start+4, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+5, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+5, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+5, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+5, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+5, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+5, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+6, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+6, "对象名称",wcfFC));
			sheets[j].addCell(new Label(2, start+6, "收货人",wcfFC));
			sheets[j].addCell(new Label(3, start+6, "联系电话",wcfFC));
			sheets[j].addCell(new Label(4, start+6, "发运方式",wcfFC));
			sheets[j].addCell(new Label(5, start+6, "需求日期",wcfFC));
			sheets[j].addCell(new Label(6, start+6, "是否转配送",wcfFC));
			sheets[j].addCell(new Label(7, start+6, "是否完成配送",wcfFC));
			sheets[j].addCell(new Label(8, start+6, "是否作废",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 7;
				ShipmentBill p = (ShipmentBill) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, p.getCname()));
				sheets[j].addCell(new Label(2, row, p.getLinkman()));
				sheets[j].addCell(new Label(3, row, p.getTel()));
				sheets[j].addCell(new Label(4, row, HtmlSelect.getResourceName(request, "TransportMode", p.getTransportmode())));
				sheets[j].addCell(new Label(5, row, Dateutil.formatDate(p.getRequiredate())));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIstrans());
				sheets[j].addCell(new Label(6, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(7, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsblankout());
				sheets[j].addCell(new Label(8, row, IsStr));
				
				
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
