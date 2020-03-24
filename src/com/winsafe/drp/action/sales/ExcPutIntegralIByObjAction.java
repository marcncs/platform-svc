package com.winsafe.drp.action.sales;

import java.io.OutputStream;
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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppIntegralI;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.IntegralI;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutIntegralIByObjAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {
			request.setAttribute("OID", request.getParameter("OID"));
			request.setAttribute("OSort",request.getParameter("OSort"));
//			String Condition=" (" +getVisitOrgan("oi.organid") +") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IntegralI" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");

			whereSql = whereSql + blur;// + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppIntegralI aii = new AppIntegralI();

			List<IntegralI> iils = aii.getIntegralI(whereSql);

			if (iils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListIntegralIByObj.xls");
			response.setContentType("application/msexcel");
			writeXls(iils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,6,"零售管理>>导出积分收入!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<IntegralI> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
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
			sheets[j].addCell(new Label(0, start, "收入列表",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "对象类型:", seachT));
			sheets[j].addCell(new Label(1, start+1, HtmlSelect.getNameByOrder(request, "ObjectSort", getInt("OSort"))));
			sheets[j].addCell(new Label(2, start+1, "选择对象:", seachT));
			Integer sort  = Integer.valueOf(request.getParameter("OSort"));
			String oid  = request.getParameter("OID");
			String name ="";
			if(sort==0){
				OrganService oss = new OrganService();
				 name = oss.getOrganName(oid);
			}else if(sort==1){
				AppCustomer appc = new AppCustomer();
				name = appc.getCName(oid);
			}else if(sort==2){
				AppProvider appp = new AppProvider();
				name = appp.getPName(oid);
				
			}else{
				UsersService us = new UsersService();
				name = us.getUsersName(Integer.valueOf(oid));
			}
			sheets[j].addCell(new Label(3, start+1, name));

			sheets[j].addCell(new Label(2, start+2, "制单日期:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(0, start+2, "单据号:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+5, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+5, "单据号",wcfFC));
			sheets[j].addCell(new Label(2, start+5, "积分类别",wcfFC));
			sheets[j].addCell(new Label(3, start+5, "应收",wcfFC));
			sheets[j].addCell(new Label(4, start+5, "已收",wcfFC));
			sheets[j].addCell(new Label(5, start+5, "制单日期",wcfFC));
			sheets[j].addCell(new Label(6, start+5, "配送机构",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				IntegralI p = (IntegralI) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, p.getBillno()));
				sheets[j].addCell(new Label(2, row, HtmlSelect.getResourceName(request, "ISort", p.getIsort())));
				sheets[j].addCell(new Number(3, row, p.getRincome(),QWCF));
				sheets[j].addCell(new Number(4, row, p.getAincome(),QWCF));
				sheets[j].addCell(new Label(5, row, DateUtil.formatDate(p.getMakedate())));
				sheets[j].addCell(new Label(6, row, organs.getOrganName(p.getEquiporganid())));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
