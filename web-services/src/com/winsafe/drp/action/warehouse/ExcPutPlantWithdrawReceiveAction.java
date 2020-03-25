package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.AppOrganWithdrawIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.PrintJob;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class ExcPutPlantWithdrawReceiveAction extends BaseAction {
	private AppOrganWithdrawIdcode auv = new AppOrganWithdrawIdcode();   
	private AppOrganWithdraw appOrganWithdraw = new AppOrganWithdraw();   
	private AppOrganWithdrawDetail appOrganWithdrawDetail = new AppOrganWithdrawDetail();   
	private AppPrintJob appPrintJob = new AppPrintJob();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
			String type = request.getParameter("excputType");
			String owid = request.getParameter("owid");
			OrganWithdraw ow = null;
			List<OrganWithdrawDetail> owdList = new ArrayList<OrganWithdrawDetail>();
			if("idcode".equals(type)) {
				 ow = appOrganWithdraw.getOrganWithdrawByID(owid);
				 owdList = appOrganWithdrawDetail.getOrganWithdrawDetailByOwid(owid);
			} else {
				String Condition =" ow.id like 'PW%' and ow.isblankout=0 and ";
				if(DbUtil.isDealer(users)) {
					Condition += " ow.inwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+") ";
					
				} else {
					Condition += DbUtil.getWhereCondition(users, "ino");
				}
				
				Map map = new HashMap(request.getParameterMap());
				String id = (String)map.remove("ID");
				if(!StringUtil.isEmpty(id)) {
					Condition = Condition + " and id = '" + id.trim() +"'";
				}
				Map tmpMap = EntityManager.scatterMap(map);
				String[] tablename = { "OrganWithdraw" };
				String whereSql = EntityManager.getTmpWhereSql(map, tablename);
				String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
						" MakeDate");
				String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
				whereSql = whereSql + timeCondition + blur+ Condition; 
				whereSql = DbUtil.getWhereSql(whereSql); 
				AppOrganWithdraw appS = new AppOrganWithdraw();
				lists = appS.getOrganWithdrawAllList(request, 0, whereSql);
				
				if (lists.size() > Constants.EXECL_MAXCOUNT) {
					request.setAttribute("result", "当前记录数超过"
							+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListPlantWithdrawReceive_"+DateUtil.getCurrentDateTimeString()+".xls");
			response.setContentType("application/msexcel");
			if("idcode".equals(type)) {
				writeXls(ow,owdList, os, request);
			} else {
				writeXls(lists, os, request);
			}
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"导出");

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private void writeXls(OrganWithdraw ow, List<OrganWithdrawDetail> owdList,
			OutputStream os, HttpServletRequest request) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WarehouseService ws = new WarehouseService();
		AppProduct appProduct = new AppProduct();
		AppOrgan appOrgan = new AppOrgan();
		Map<String,PrintJob> existsPrintJobMap = new HashMap<String, PrintJob>();
		Set<String> notExistsPrintJobMap = new HashSet<String>();
		int snum = 1;
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int colNo = 0;
			int start = j * 50000;
			sheets[j].mergeCells(0, start, 11, start);
			sheets[j].addCell(new Label(0, start, "工厂退回签收详情  ",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+1, ESAPIUtil.decodeForHTML(users.getMakeorganname())));
			sheets[j].addCell(new Label(2, start+1, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+1, users.getLoginname()));
			sheets[j].addCell(new Label(4, start+1, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+1, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(colNo++, start+2, "编号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "出库机构SAP编号", wcfFC));
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
			sheets[j].addCell(new Label(colNo++, start+2, "到期日期", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "SAP发货单号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "SAP发货日期", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "PD发货单号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "BKD/BKR名称", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "转仓单号", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "转仓客户", wcfFC));
			sheets[j].addCell(new Label(colNo++, start+2, "内部生产类型", wcfFC));
			
			int row = 3;
			colNo = 0;
			
			Organ outOrgan = appOrgan.getOrganByID(ow.getPorganid());
			sheets[j].addCell(new Label(colNo++, row, ow.getId()));
			if(outOrgan != null) {
				sheets[j].addCell(new Label(colNo++, row, outOrgan.getOecode()));
			} else {
				sheets[j].addCell(new Label(colNo++, row, ""));
			}
			sheets[j].addCell(new Label(colNo++, row, ESAPIUtil.decodeForHTML(ow.getPorganname())));
			sheets[j].addCell(new Label(colNo++, row, ws.getWarehouseName(ow.getWarehouseid())));
			sheets[j].addCell(new Label(colNo++, row, ow.getReceiveorganid()));
			sheets[j].addCell(new Label(colNo++, row, ws.getWarehouseName(ow.getInwarehouseid())));
			sheets[j].addCell(new Label(colNo++, row, ow.getPlinkman()));
			AppTakeTicket att = new AppTakeTicket();
    		TakeTicket tt = att.getTakeTicket(ow.getId());
			if(owdList != null && owdList.size() > 0) {
				for(OrganWithdrawDetail ttd : owdList) {
					colNo = 7;
					sheets[j].addCell(new Label(colNo++, row, ttd.getProductid()));
					Product product = appProduct.getProductByID(ttd.getProductid());
					sheets[j].addCell(new Label(colNo++, row, product!=null?product.getmCode():""));
					sheets[j].addCell(new Label(colNo++, row, ttd.getProductname()));
					sheets[j].addCell(new Label(colNo++, row, ttd.getSpecmode()));
					sheets[j].addCell(new Label(colNo++, row, ttd.getTakequantity() != null ? Double.toString(ttd.getTakequantity()):"0"));
					sheets[j].addCell(new Label(colNo++, row, HtmlSelect.getResourceName(request,
							"CountUnit", ttd.getUnitid())));
		    		String whereSql = " where owi.ttid='"+tt.getId()+"' and owi.productid='"+ttd.getProductid()+"'"; 
//					String whereSql = " where owi.owid='"+ow.getId()+"' and owi.productid='"+ttd.getProductid()+"' "; 
					List<Map<String,String>> ttiList = auv.searchPlantWithdrawIdcode(whereSql);
					if(ttiList.size() > 0) {
						for(Map<String,String> tti : ttiList) {
							colNo = 13;
							PrintJob printJob = getPrintJob(ttd.getProductid(),tti.get("batch"),existsPrintJobMap,notExistsPrintJobMap);
							sheets[j].addCell(new Label(colNo++, row, tti.get("idcode")));
							sheets[j].addCell(new Label(colNo++, row, tti.get("batch")));
							sheets[j].addCell(new Label(colNo++, row, tti.get("producedate")));
							if(printJob != null) {
								sheets[j].addCell(new Label(colNo++, row, printJob.getExpiryDate()));
							} else {
								sheets[j].addCell(new Label(colNo++, row, ""));
							}
							sheets[j].addCell(new Label(colNo++, row, tti.get("nccode")));
							sheets[j].addCell(new Label(colNo++, row, tti.get("movedate")));
							sheets[j].addCell(new Label(colNo++, row, tti.get("id")));
							sheets[j].addCell(new Label(colNo++, row, tti.get("receiveorganidname")));
							sheets[j].addCell(new Label(colNo++, row, tti.get("smid")));
							sheets[j].addCell(new Label(colNo++, row, ESAPIUtil.decodeForHTML(tti.get("transferorganname"))));
							if(product.getInnerProduceType() != null) {
								sheets[j].addCell(new Label(colNo++, row, HtmlSelect.getNameByOrder(request, "InnerProduceType", product.getInnerProduceType())));
							} else {
								sheets[j].addCell(new Label(colNo++, row, ""));
							}
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

	private PrintJob getPrintJob(String productid, String batch,
			Map<String, PrintJob> existsPrintJobMap,
			Set<String> notExistsPrintJobMap) throws Exception {
		if(existsPrintJobMap.containsKey(productid+"_"+batch)) {
			return existsPrintJobMap.get(productid+"_"+batch);
		} else if(notExistsPrintJobMap.contains(productid+"_"+batch)) {
			return null;
		} else {
			PrintJob printJob = appPrintJob.getPrintJobByBatAPd(productid, batch);
			if(printJob != null) {
				existsPrintJobMap.put(productid+"_"+batch, printJob);
			} else {
				notExistsPrintJobMap.add(productid+"_"+batch);
			}
			return printJob;
		}
	}

	public void writeXls(List<Map<String,String>> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
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
			sheets[j].addCell(new Label(0, start, "工厂退回签收 ",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("oname")));	
			sheets[j].addCell(new Label(2, start+1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deptname")));		
			sheets[j].addCell(new Label(4, start+1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("uname")));	
			
			sheets[j].addCell(new Label(0, start+2, "供货机构:",seachT));
			sheets[j].addCell(new Label(1, start+2, ESAPIUtil.decodeForHTML(request.getParameter("POrganName"))));
			sheets[j].addCell(new Label(2, start+2, "制单日期:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(4, start+2, "是否签收:", seachT));
			sheets[j].addCell(new Label(5, start+2, HtmlSelect.getNameByOrder(request, "YesOrNo", getInt("IsComplete"))));
			
			sheets[j].addCell(new Label(0, start+3, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+4, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(2, start+4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+4, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+4, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start+5, "供货机构", wcfFC));
			sheets[j].addCell(new Label(2, start+5, "联系人", wcfFC));
			sheets[j].addCell(new Label(3, start+5, "总金额", wcfFC));
			sheets[j].addCell(new Label(4, start+5, "制单机构", wcfFC));
			sheets[j].addCell(new Label(5, start+5, "制单人", wcfFC));
			sheets[j].addCell(new Label(6, start+5, "制单日期", wcfFC));
			sheets[j].addCell(new Label(7, start+5, "是否签收", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				Map<String,String> p = list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("id")));
				sheets[j].addCell(new Label(1, row, ESAPIUtil.decodeForHTML(p.get("porganname"))));
				sheets[j].addCell(new Label(2, row, p.get("plinkman")));
				sheets[j].addCell(new Number(3, row, p.get("totalsum")!=null?Double.valueOf(p.get("totalsum")):0d,wcfN));
				sheets[j].addCell(new Label(4, row, organs.getOrganName(p.get("makeorganid"))));
				sheets[j].addCell(new Label(5, row, us.getUsersName(Integer.valueOf(p.get("makeid")))));
				sheets[j].addCell(new Label(6, row, p.get("makedate")));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.valueOf(p.get("iscomplete")));
				sheets[j].addCell(new Label(7, row, IsStr));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}