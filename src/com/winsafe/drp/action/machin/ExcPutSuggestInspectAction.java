package com.winsafe.drp.action.machin;

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
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutSuggestInspectAction extends BaseAction {
	AppSuggestInspect asi = new AppSuggestInspect();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String Condition = "  ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SuggestInspect" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"makeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "id", "typeName",
					"siid", "customer_Code", "dis_WareHouse_Name",
					"sou_WareHouse_Name", "typeid");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			List<SuggestInspect> pils = asi.getSiByIds(whereSql);
			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=suggestinspect.xls");
			response.setContentType("application/msexcel");
			writeXls(pils, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,4,"检货出库>>导出拣货建议单列表!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<SuggestInspect> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		UsersService us = new UsersService();
		WarehouseService ws = new WarehouseService();
		
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
			sheets[j].addCell(new Label(0, start, "拣货建议单列表  ",wchT));			
			sheets[j].addCell(new Label(0, start+1, "仓库:",seachT));
			sheets[j].addCell(new Label(1, start+1, ws.getWarehouseName(request.getParameter("WarehouseID"))));
			sheets[j].addCell(new Label(2, start+1, "入库日期:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
//			sheets[j].addCell(new Label(4, start+1, "是否复核:", seachT));
//			sheets[j].addCell(new Label(5, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			
			sheets[j].addCell(new Label(0, start+2, "是否排除:", seachT));
			sheets[j].addCell(new Label(1, start+2, HtmlSelect.getNameByOrder(request, "IsRemove", getInt("IsRemove"))));	
			sheets[j].addCell(new Label(2, start+2, "是否合并:", seachT));
			sheets[j].addCell(new Label(3, start+2, HtmlSelect.getNameByOrder(request, "IsMerge", getInt("IsMerge"))));		
			sheets[j].addCell(new Label(4, start+2, "是否出库:", seachT));
			sheets[j].addCell(new Label(5, start+2, HtmlSelect.getNameByOrder(request, "IsOut", getInt("IsOut"))));
			
			sheets[j].addCell(new Label(0, start+3, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+5, "单据类型", wcfFC));
			sheets[j].addCell(new Label(2, start+5, "单据名称", wcfFC));
			sheets[j].addCell(new Label(3, start+5, "单据编号", wcfFC));
			sheets[j].addCell(new Label(4, start+5, "来往仓库", wcfFC));
			sheets[j].addCell(new Label(5, start+5, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(6, start+5, "客户编号", wcfFC));
			sheets[j].addCell(new Label(7, start+5, "制单日期", wcfFC));
			sheets[j].addCell(new Label(8, start+5, "是否过账", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				SuggestInspect p = list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				String typename=HtmlSelect.getNameByOrder(request, "typeId", Integer.valueOf(p.getTypeId()));
				sheets[j].addCell(new Label(1, row, typename));
				sheets[j].addCell(new Label(2, row, p.getTypeName()));
				sheets[j].addCell(new Label(3, row, p.getSiid()));
				sheets[j].addCell(new Label(4, row, p.getDisWareHouseName()));
				sheets[j].addCell(new Label(5, row, p.getSouWareHouseName()));
				sheets[j].addCell(new Label(6, row, p.getCustomerCode()));
				sheets[j].addCell(new Label(7, row, Dateutil.formatDate(p.getMakeDate())));
				sheets[j].addCell(new Label(8, row, p.getIsPost()));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
