package com.winsafe.drp.action.machin;

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
import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutAssembleRelationAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {

	      String Condition=" (ar.makeid='"+userid+"' " +getOrVisitOrgan("ar.makeorganid") +") ";
			String[] tablename = {"AssembleRelation"};
			String whereSql = getWhereSql2(tablename);
			String blur = getKeyWordCondition("ID","KeysContent"); 
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + blur + timeCondition +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppAssembleRelation asb = new AppAssembleRelation();
			List<AssembleRelation> pils = asb.getAssembleRelation(whereSql);

			
			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListAssembleRelation.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,3,"生产组装>>导出组装关系!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<AssembleRelation> list, OutputStream os,
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
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start+1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deptname")));
			sheets[j].addCell(new Label(4, start+1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("uname")));	
			
			
			sheets[j].addCell(new Label(0, start+2, "是否复核:", seachT));
			Integer isaudit = getInt("IsAudit");
			String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", isaudit);
			sheets[j].addCell(new Label(1, start+2, IsStr));		
			sheets[j].addCell(new Label(2, start+2, "制单日期:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(4, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("KeyWord")));			
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "组装产品编码",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "组装产品名称",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "数量",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "是否复核",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				AssembleRelation p = (AssembleRelation) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, p.getArproductid()));
				sheets[j].addCell(new Label(2, row, p.getArproductname()));
				sheets[j].addCell(new Label(3, row, p.getArspecmode()));
				sheets[j].addCell(new Label(4, row,HtmlSelect.getResourceName(request, "CountUnit", p.getArunitid())));
				sheets[j].addCell(new Number(5, row, p.getArquantity(),QWCF));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(6, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
