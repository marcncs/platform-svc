package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutStockCheckAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);

		try {
	
			String Condition = " ( makeid=" + userid + " " + getOrVisitOrgan("makeorganid")+ ") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "StockCheck" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
		

			AppStockCheck asc = new AppStockCheck();

			List<StockCheck> sals = asc.getStockCheck(request, pagesize, whereSql);
			

			
			if (sals.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListStockCheck.xls");
			response.setContentType("application/msexcel");
			writeXls(sals, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 7, "库存盘点>>导出列表库存盘点单");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<StockCheck> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		WarehouseService ws = new WarehouseService();
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
			sheets[j].addCell(new Label(0, start, "产品盘点  ",wchT));			
			sheets[j].addCell(new Label(0, start+1, "盘点仓库:",seachT));
			sheets[j].addCell(new Label(1, start+1, ws.getWarehouseName(request.getParameter("WarehouseID"))));
			sheets[j].addCell(new Label(2, start+1, "是否复核:", seachT));
			sheets[j].addCell(new Label(3, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(4, start+1, "是否生成入库出库:", seachT));
			sheets[j].addCell(new Label(5, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsCreate"))));
			
			sheets[j].addCell(new Label(0, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+2, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("deptname")));		
			sheets[j].addCell(new Label(4, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("uname")));	
			
			sheets[j].addCell(new Label(0, start+3, "盘点日期:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+3, "关键字:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+5, "盘点仓库", wcfFC));
			sheets[j].addCell(new Label(2, start+5, "仓位", wcfFC));
			sheets[j].addCell(new Label(3, start+5, "盘点日期", wcfFC));
			sheets[j].addCell(new Label(4, start+5, "制单机构", wcfFC));
			sheets[j].addCell(new Label(5, start+5, "制单人", wcfFC));
			sheets[j].addCell(new Label(6, start+5, "是否复核", wcfFC));
			sheets[j].addCell(new Label(7, start+5, "是否生成入库出库", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				StockCheck p = (StockCheck) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, ws.getWarehouseName(p.getWarehouseid())));
				sheets[j].addCell(new Label(2, row, p.getWarehousebit()));
				sheets[j].addCell(new Label(3, row, DateUtil.formatDate(p.getMakedate())));
				sheets[j].addCell(new Label(4, row, organs.getOrganName(p.getMakeorganid())));
				sheets[j].addCell(new Label(5, row, us.getUsersName(p.getMakeid())));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit());
				sheets[j].addCell(new Label(6, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIscreate());
				sheets[j].addCell(new Label(7, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
