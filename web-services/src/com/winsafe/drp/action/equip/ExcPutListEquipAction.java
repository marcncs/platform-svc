package com.winsafe.drp.action.equip;

import java.io.OutputStream;
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
import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.dao.Equip;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutListEquipAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {

			String Condition = " (e.makeid="+userid+" "+this.getOrVisitOrgan("e.makeorganid")+") ";

			String[] tablename = { "Equip" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition(" EquipDate");
			String blur = getKeyWordCondition("ID", "KeysContent");
			whereSql = whereSql + blur +timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			 

			AppEquip asl = new AppEquip();
			List<Equip> pils = asl.getEquip(whereSql);

			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListEquip.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,8,"导出配送单!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<Equip> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		UsersService us = new UsersService();
		
		
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
			sheets[j].addCell(new Label(0, start, "配送单",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "所属机构:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "所属部门:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deptname")));
			sheets[j].addCell(new Label(4, start+1, "所属用户:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("uname")));
			
			sheets[j].addCell(new Label(0, start+2, "对象类型:", seachT));
			String IsStr = HtmlSelect.getNameByOrder(request, "ObjectSort", getInt("ObjectSort"));
			sheets[j].addCell(new Label(1, start+2, IsStr));		
			sheets[j].addCell(new Label(2, start+2, "选择对象:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("cname")));
			sheets[j].addCell(new Label(4, start+2, "配送日期:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));	
			
			sheets[j].addCell(new Label(0, start+3, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+5, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+5, "对象类型",wcfFC));
			sheets[j].addCell(new Label(2, start+5, "对象名称",wcfFC));
			sheets[j].addCell(new Label(3, start+5, "联系人",wcfFC));
			sheets[j].addCell(new Label(4, start+5, "配送日期",wcfFC));
			sheets[j].addCell(new Label(5, start+5, "件数",wcfFC));
			sheets[j].addCell(new Label(6, start+5, "发运方式",wcfFC));
			sheets[j].addCell(new Label(7, start+5, "司机",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				Equip p = (Equip) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, HtmlSelect.getNameByOrder(request, "ObjectSort", p.getObjectsort())));
				sheets[j].addCell(new Label(2, row, p.getCname()));
				sheets[j].addCell(new Label(3, row, p.getClinkman()));
				sheets[j].addCell(new Label(4, row, DateUtil.formatDate(p.getEquipdate())));
				sheets[j].addCell(new Number(5, row, p.getPiece(),QWCF));
				sheets[j].addCell(new Label(6, row, HtmlSelect.getResourceName(request, "TransportMode", p.getTransportmode())));
				String makeuser = us.getUsersName(p.getMotorman());
				sheets[j].addCell(new Label(7, row, makeuser));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
