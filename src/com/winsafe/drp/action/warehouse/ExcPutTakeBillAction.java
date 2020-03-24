package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcPutTakeBillAction extends BaseAction {
	private Log logger =  LogFactory.getLog(ExcPutTakeBillAction.class);
	private AppTakeTicketDetail appTTD = new AppTakeTicketDetail();
	private AppTakeTicketIdcode appTTI = new AppTakeTicketIdcode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppTakeTicket appTT = new AppTakeTicket();
		AppTakeTicketDetail appTTD = new AppTakeTicketDetail();
		AppTakeTicketIdcode appTTI = new AppTakeTicketIdcode();
		
		try {
			List<TakeTicket> ttList = new ArrayList<TakeTicket>();
			String billno = request.getParameter("billno");
			Integer recordCount = 0;
			TakeTicket tt = null;
			String fileName = "ListTakeBill.xls";
			if(!StringUtil.isEmpty(billno)) {
				fileName = billno + "_" + fileName;
				tt =  appTT.getTakeTicket(billno);
				if(tt == null) {
					request.setAttribute("result", "未找到单据号为:"+billno+"的单据");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				recordCount = recordCount + appTTD.getRecordCountByTtid(tt.getId());
				recordCount = recordCount + appTTI.getRecordCountByTtid(tt.getId());
				if(recordCount == 0) {
					recordCount ++;
				}
			} else {
				//查询条件 
				Map map = new HashMap(request.getParameterMap());
				Map tmpMap = EntityManager.scatterMap(map);
				String[] tablename = { "TakeTicket" };
				String whereSql = EntityManager.getTmpWhereSql(map, tablename);
				String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
				String blur = DbUtil.getOrBlur(map, tmpMap, "tt.ID", "tt.OName", "tt.Tel","tt.BillNo");
				whereSql = whereSql + blur + timeCondition;
				if(!StringUtil.isEmpty((String)map.get("withdrawType"))) {
					whereSql = whereSql + " tt.billno like '"+(String)map.get("withdrawType")+"%' and ";
				}
				if(!StringUtil.isEmpty((String)map.get("isNoBill"))) {
					if("1".equals(map.get("isNoBill"))) {
						whereSql = whereSql + " tt.isNoBill = 1 and ";
					} else {
						whereSql = whereSql + " tt.isNoBill is null and ";
					} 
				} 
				
				whereSql = DbUtil.getWhereSql(whereSql);
				whereSql = whereSql.replace("where", " and ");
				if(!DbUtil.isDealer(users)) {
					whereSql += " and ("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") ";
				} 
				whereSql = whereSql.replace("isaudit", "tt.isaudit");
				ttList = appTT.getTakeTicketByRule(request, 0, whereSql,users);
				
				
				if (ttList.size() > Constants.EXECL_MAXCOUNT) {
					request.setAttribute("result", "当前记录数超过"
							+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
					return new ActionForward("/sys/lockrecord2.jsp");
				} 
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=" +fileName);
			response.setContentType("application/msexcel");
			if(!StringUtil.isEmpty(billno)) { 
				writeXls(tt, os, request, recordCount);
			} else {
				writeXls(ttList, os, request);
			}
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"导出");
		}catch(Exception e){
			logger.debug("", e);
		}
		return null;
	}
	
	private void writeXls(TakeTicket tt, OutputStream os,
			HttpServletRequest request, Integer recordCount) throws NumberFormatException, Exception{
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WarehouseService ws = new WarehouseService();
		AppProduct appProduct = new AppProduct();
		
		int snum = 1;
		snum = recordCount / 50000;
		if (recordCount % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= recordCount) {
				currentnum = recordCount;
			}
			int colNo = 0;
			int start = j * 50000;
			sheets[j].mergeCells(0, start, 11, start);
			sheets[j].addCell(new Label(0, start, "扫描单据详情  ",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+1, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+1, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+1, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(colNo++, start+2, "编号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "出库机构", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "出库仓库", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "入库机构", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "入库仓库", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "收货人", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "产品编号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "物料号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "产品名称", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "规格", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "确认数量", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "单位", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "条码", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "批号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "生产日期", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "扫码日期", wcfFC));
			
			int row = 3;
			colNo = 0;
			sheets[j].addCell(new Label(colNo++, row, tt.getBillno()));
			sheets[j].addCell(new Label(colNo++, row, tt.getOname()));
			sheets[j].addCell(new Label(colNo++, row, ws.getWarehouseName(tt.getWarehouseid())));
			sheets[j].addCell(new Label(colNo++, row, tt.getInOid()));
			sheets[j].addCell(new Label(colNo++, row, ws.getWarehouseName(tt.getInwarehouseid())));
			sheets[j].addCell(new Label(colNo++, row, tt.getRlinkman()));
			List<TakeTicketDetail> ttdList =  appTTD.getTakeTicketDetailByTtid(tt.getId(), "order by productid");
			if(ttdList.size() > 0) {
				for(TakeTicketDetail ttd : ttdList) {
					colNo = 6;
					sheets[j].addCell(new Label(colNo++, row, ttd.getProductid()));
					Product product = appProduct.getProductByID(ttd.getProductid());
					sheets[j].addCell(new Label(colNo++, row, product!=null?product.getmCode():""));
					sheets[j].addCell(new Label(colNo++, row, ttd.getProductname()));
					sheets[j].addCell(new Label(colNo++, row, ttd.getSpecmode()));
					sheets[j].addCell(new Label(colNo++, row, ttd.getRealQuantity() != null ? Double.toString(ttd.getRealQuantity()):"0"));
					sheets[j].addCell(new Label(colNo++, row, HtmlSelect.getResourceName(request,
							"CountUnit", ttd.getUnitid())));
					List<TakeTicketIdcode> ttiList = appTTI.getTakeTicketIdcodeByPidTtid(ttd.getProductid(), tt.getId(), "order by productid,batch");
					if(ttiList.size() > 0) {
						for(TakeTicketIdcode tti : ttiList) {
							colNo = 12;
							sheets[j].addCell(new Label(colNo++, row, tti.getIdcode()));
							sheets[j].addCell(new Label(colNo++, row, tti.getBatch()));
							sheets[j].addCell(new Label(colNo++, row, tti.getProducedate()));
							sheets[j].addCell(new Label(colNo++, row, DateUtil.formatDateTime(tti.getMakedate())));
							row++;
						}
					} else {
						row++;
					}
				}
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

	public void writeXls(List<TakeTicket> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		UsersService us = new UsersService();
		WarehouseService ws = new WarehouseService();
		OrganService organService = new OrganService();
		
		
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
			int colNo = 0;
			int start = j * 50000;
			sheets[j].mergeCells(0, start, 11, start);
			sheets[j].addCell(new Label(0, start, "扫描单据清单 ",wchT));
			sheets[j].addCell(new Label(0, start+1, "对象类型:", seachT));
			sheets[j].addCell(new Label(1, start+1, HtmlSelect.getNameByOrder(request, "ObjectSort", getInt("ObjectSort"))));	
			sheets[j].addCell(new Label(2, start+1, "对象名称:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("OName")));		
			sheets[j].addCell(new Label(4, start+1, "是否关闭:", seachT));
			sheets[j].addCell(new Label(5, start+1, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			
			sheets[j].addCell(new Label(0, start+2, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+2, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("deptname")));		
			sheets[j].addCell(new Label(4, start+2, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("uname")));	
			
			sheets[j].addCell(new Label(0, start+3, "是否作废:",seachT));
			sheets[j].addCell(new Label(1, start+3, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsBlankOut"))));
			sheets[j].addCell(new Label(2, start+3, "制单日期:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(4, start+3, "单据类型:", seachT));
			sheets[j].addCell(new Label(5, start+3, HtmlSelect.getNameByOrder(request, "BSort", getInt("BSort"))));
			
			sheets[j].addCell(new Label(0, start+4, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+4, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+5, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+5, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+5, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+5, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+5, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+5, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(colNo++, start+6, "编号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "出库机构", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "出库仓库", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "入库机构", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "入库仓库", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "收货人", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "电话", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "制单机构", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "配送机构", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "制单人", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "是否关闭", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+6, "是否作废", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				colNo = 0;
				row = i - start + 7;
				TakeTicket p = (TakeTicket) list.get(i);
				sheets[j].addCell(new Label(colNo++, row, p.getBillno()));
				sheets[j].addCell(new Label(colNo++, row, p.getOname()));
				sheets[j].addCell(new Label(colNo++, row, ws.getWarehouseName(p.getWarehouseid())));
				sheets[j].addCell(new Label(colNo++, row, p.getInOid()));
				sheets[j].addCell(new Label(colNo++, row, ws.getWarehouseName(p.getInwarehouseid())));
				sheets[j].addCell(new Label(colNo++, row, p.getRlinkman()));
				sheets[j].addCell(new Label(colNo++, row, p.getTel()));
				sheets[j].addCell(new Label(colNo++, row, organService.getOrganName(p.getMakeorganid())));
				sheets[j].addCell(new Label(colNo++, row, ""));
				sheets[j].addCell(new Label(colNo++, row, us.getUsersName(p.getMakeid())));
				sheets[j].addCell(new Label(colNo++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsaudit())));
				sheets[j].addCell(new Label(colNo++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", p.getIsblankout())));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
