package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import com.winsafe.drp.dao.AppOrganPriceii;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutStockAlterMoveDetailFLiAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = "pw.id=pwd.samid and (pw.iscomplete=1 and pw.isblankout=0 " + visitorgan+ " )  ";
			String[] tablename = { "StockAlterMove", "StockAlterMoveDetail"  };

			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppOrganPriceii aop = new AppOrganPriceii();
			AppStockAlterMoveDetail asod=new AppStockAlterMoveDetail(); 
	        List sodls = asod.getDetailReport(whereSql);
	        ArrayList list=new ArrayList();
	        Double totalsum=0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	DetailReportForm sodf = new DetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	String morganname = (String)o[1];
	        	String rorganname = (String)o[3];
	        	String makedate = DateUtil.formatDateTime((Date)o[4]);
	        	StockAlterMoveDetail pbd = (StockAlterMoveDetail)o[5];
	        	sodf.setOname(morganname);
	        	sodf.setSoname(rorganname);
	        	sodf.setMakedate(makedate);
	        	sodf.setBillid(pbd.getSamid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",		                  
	                    Integer.valueOf(pbd.getUnitid().toString())));
	        	sodf.setUnitprice(pbd.getUnitprice());
	        	sodf.setQuantity(pbd.getQuantity());
	        	sodf.setSubsum(pbd.getSubsum());
	        	sodf.setFrate(aop.getOrganPriceiiByOidPidUid((String)o[2],pbd.getProductid(),pbd.getUnitid()).getFrate());
	        	totalsum += sodf.getSubsum();
	        	list.add(sodf);
	        }
		       
	        if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=StockAlterMoveDetail.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 10,"报表分析>>导出渠道订购返利");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organ = new OrganService();
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
			
			sheets[j].mergeCells(0, start, 12, start);
			sheets[j].addCell(new Label(0, start, "渠道订购返利",wchT));
			sheets[j].addCell(new Label(0, start+1, "制单机构:",seachT));
			sheets[j].addCell(new Label(1, start+1, organ.getOrganName(map.get("MakeOrganID").toString())));
			sheets[j].addCell(new Label(2, start+1, "出货仓库:",seachT));
			sheets[j].addCell(new Label(3, start+1, ws.getWarehouseName(map.get("OutWarehouseID").toString())));
			sheets[j].addCell(new Label(4, start+1, "订购机构:",seachT));
			sheets[j].addCell(new Label(5, start+1, organ.getOrganName(map.get("ReceiveOrganID").toString())));
			
			sheets[j].addCell(new Label(0, start+2, "制单日期:",seachT));
			sheets[j].addCell(new Label(1, start+2, map.get("BeginDate").toString()+"-"+map.get("EndDate").toString()));
			sheets[j].addCell(new Label(2, start+2, "产品名称:",seachT));
			sheets[j].addCell(new Label(3, start+2, map.get("ProductName").toString()));

			
			sheets[j].addCell(new Label(0, start+3, "导出机构:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:",seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			          
			sheets[j].addCell(new Label(0, start+4, "调出机构",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "调入机构",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "制单时间",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "单据号",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "商品编号",wcfFC));
			sheets[j].addCell(new Label(5, start+4, "商品名称",wcfFC));
			sheets[j].addCell(new Label(6, start+4, "规格",wcfFC));
			sheets[j].addCell(new Label(7, start+4, "单位",wcfFC));
			sheets[j].addCell(new Label(8, start+4, "单价",wcfFC));
			sheets[j].addCell(new Label(9, start+4, "数量",wcfFC));
			sheets[j].addCell(new Label(10, start+4, "金额",wcfFC));
			sheets[j].addCell(new Label(11, start+4, "返点比例",wcfFC));
			sheets[j].addCell(new Label(12, start+4, "返点金额小计",wcfFC));
			int row = 0;
			Double totalsum = 0.00;
			Double ftotal = 0.00;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				DetailReportForm p = (DetailReportForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getOname()));
				sheets[j].addCell(new Label(1, row, p.getSoname()));
				sheets[j].addCell(new Label(2, row, p.getMakedate()));
				sheets[j].addCell(new Label(3, row, p.getBillid()));
				sheets[j].addCell(new Label(4, row, p.getProductid()));
				sheets[j].addCell(new Label(5, row, p.getProductname()));
				sheets[j].addCell(new Label(6, row, p.getSpecmode()));
				sheets[j].addCell(new Label(7, row, p.getUnitidname()));
				sheets[j].addCell(new Number(8, row, p.getUnitprice(),wcfN));
				sheets[j].addCell(new Number(9, row, p.getQuantity(),QWCF));
				sheets[j].addCell(new Number(10, row, p.getSubsum(),wcfN));
				sheets[j].addCell(new Number(11, row, p.getFrate()*100,wcfN));
				sheets[j].addCell(new Number(12, row, p.getSubsum()*p.getFrate(),wcfN));
				totalsum += Double.valueOf(p.getSubsum().toString());
				ftotal += Double.valueOf(p.getSubsum()*p.getFrate());
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
			sheets[j].addCell(new Label(9, row + 1, ""));
			sheets[j].addCell(new Number(10, row + 1, totalsum,wcfN));
			sheets[j].addCell(new Label(11, row + 1, ""));
			sheets[j].addCell(new Number(12, row + 1, ftotal,wcfN));

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
