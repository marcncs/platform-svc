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
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59
 * www.winsafe.cn
 */
public class ExcPutSupplySaleMoveReceiveAction extends BaseAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		super.initdata(request);try{

	    	String Condition = "ssa.inorganid = '"+users.getMakeorganid()+"' and ssa.isshipment=1 and ssa.isblankout=0";
	    	
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "SupplySaleMove"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur+ timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSupplySaleMove appS = new AppSupplySaleMove();
			List<SupplySaleMove> lists = appS.getSupplySaleMoveAll(whereSql);
			
			if (lists.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListSupplySaleMoveReceive.xls");
			response.setContentType("application/msexcel");
			writeXls(lists, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid,4,"渠道管理>>导出渠道代销!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List<SupplySaleMove> list, OutputStream os,
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
			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "代销签收 ",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deptname")));		
			sheets[j].addCell(new Label(4, start+1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("uname")));	
			
			sheets[j].addCell(new Label(0, start+2, "调入机构:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname1")));
			sheets[j].addCell(new Label(2, start+2, "申请机构:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("oname2")));
			sheets[j].addCell(new Label(4, start+2, "是否签收:", seachT));
			sheets[j].addCell(new Label(5, start+2, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsComplete"))));
			
			sheets[j].addCell(new Label(0, start+3, "订购需求日期:", seachT));
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
			sheets[j].addCell(new Label(1, start+5, "需求日期", wcfFC));
			sheets[j].addCell(new Label(2, start+5, "申请机构", wcfFC));
			sheets[j].addCell(new Label(3, start+5, "调入机构", wcfFC));
			sheets[j].addCell(new Label(4, start+5, "调入仓库", wcfFC));
			sheets[j].addCell(new Label(5, start+5, "调出仓库", wcfFC));
			sheets[j].addCell(new Label(6, start+5, "制单机构", wcfFC));
			sheets[j].addCell(new Label(7, start+5, "制单人", wcfFC));
			sheets[j].addCell(new Label(8, start+5, "是否签收", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				SupplySaleMove p = (SupplySaleMove) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, DateUtil.formatDate(p.getMovedate())));
				String organ = organs.getOrganName(p.getSupplyorganid());
				sheets[j].addCell(new Label(2, row, organ));
				organ=organs.getOrganName(p.getInorganid());
				sheets[j].addCell(new Label(3, row, organ));
				String warehouse = ws.getWarehouseName(p.getInwarehouseid());
				sheets[j].addCell(new Label(4, row, warehouse));
				warehouse = ws.getWarehouseName(p.getOutwarehouseid());
				sheets[j].addCell(new Label(5, row, warehouse));
				organ=organs.getOrganName(p.getMakeorganid());
				sheets[j].addCell(new Label(6, row, organ));
				String makeuser = us.getUsersName(p.getMakeid());
				sheets[j].addCell(new Label(7, row, makeuser));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIscomplete());
				sheets[j].addCell(new Label(8, row, IsStr));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
