package com.winsafe.sap.action;

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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCovertErrorLog;
import com.winsafe.sap.dao.AppCovertUploadReport;
import com.winsafe.sap.pojo.CovertErrorLog;
import com.winsafe.sap.pojo.CovertUploadReport;

public class ExcputCovertErrorLogAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListCovertErrorLogAction.class);

	private AppCovertErrorLog appCovertErrorLog = new AppCovertErrorLog();
	private AppCovertUploadReport appCovertUploadReport = new AppCovertUploadReport();
	private AppUsers au = new AppUsers();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		initdata(request);
		String ids = "";
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String id = (String) map.get("uploadPrId");
		if (!StringUtil.isEmpty(id)) {
			try {
				Integer.parseInt(id);
			} catch (Exception e) {
				map.put("uploadPrId", "0");
			}
		}
		if (!StringUtil.isEmpty((String) map.get("isDetail"))) {
			if (!StringUtil.isEmpty((String) map.get("id"))) {
				ids = (String) map.get("id");
			} else {
				String[] tablename = { "CovertUploadReport" };
				String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
				String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," uploadDate");
				whereSql = whereSql + timeCondition;
				whereSql = DbUtil.getWhereSql(whereSql);
				if (StringUtil.isEmpty(whereSql)) {
					whereSql = " where materialCode is null and batch is null ";
				}
				ids = "select id from CovertUploadReport " + whereSql;
			}
		}
		String[] tablename = { "CovertErrorLog" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		if (!StringUtil.isEmpty(ids)) {
			whereSql = whereSql + " uploadPrId in (" + ids + ") and";
		}
		if (!StringUtil.isEmpty((String) map.get("uploadPrId"))) {
			whereSql = whereSql+ " uploadPrId in (select id from CovertUploadReport where uploadPrId = "+ (String) map.get("uploadPrId") + ") and";
		}
		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," uploadDate");
		whereSql = whereSql + timeCondition;
		whereSql = DbUtil.getWhereSql(whereSql);
		
		int count = appCovertErrorLog.getCountExcPutCovertErrorLog(whereSql);

		if (count > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过"
					+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		List<CovertErrorLog> logs = appCovertErrorLog.getExcPutCovertErrorLog(whereSql);
		if (logs.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过"
					+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		for (CovertErrorLog log : logs) {
			try {
				CovertUploadReport report = appCovertUploadReport.loadCovertUploadReportById(log.getUploadPrId());
				if (report != null) {
					log.setCovertUploadId(report.getUploadPrId());
					log.setProductionLine(report.getLineNo());
				}
			} catch (Exception e) {
				logger.error("CovertUploadReport not found by id "+ log.getUploadPrId(), e);
			}
		}
		
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition","attachment; filename=ListCovertErrorLog_"+DateUtil.getCurrentDateTimeString()+".xls");
		response.setContentType("application/msexcel");
		writeXls(logs, os, request);
		os.flush();
		os.close();
		DBUserLog.addUserLog(request,"暗码上传质量报告报表");
		
		return null;
	}
	
	public void writeXls(List<CovertErrorLog> logs, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		int snum = 1;
		snum = logs.size() / 50000;
		if (logs.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];

		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= logs.size()) {
				currentnum = logs.size();
			}
			int start = j * 50000;

			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "暗码上传质量报告报表", wchT));
			/*
			sheets[j].addCell(new Label(0, start+1, "发票编号:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("invoiceNumber")));
			sheets[j].addCell(new Label(2, start+1, "送货单号:",seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deliveryNumber")));
			sheets[j].addCell(new Label(4, start+1, "发票日期:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "客户编号:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("partnSold")));
			sheets[j].addCell(new Label(2, start+2, "客户名称:",seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("soldtoparty")));
			sheets[j].addCell(new Label(4, start+2, "物料号:",seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("materialCode")));
			
			sheets[j].addCell(new Label(0, start+3, "产品名称:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("productname")));
			sheets[j].addCell(new Label(2, start+3, "规格:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getParameter("packagesize")));
			sheets[j].addCell(new Label(4, start+3, "批号:",seachT));
			sheets[j].addCell(new Label(5, start+3, request.getParameter("batchNumber")));
		*/        
			sheets[j].addCell(new Label(0, start + 2, "编号"));
			sheets[j].addCell(new Label(1, start + 2, "二维码 "));
			sheets[j].addCell(new Label(2, start + 2, "暗码"));
			sheets[j].addCell(new Label(3, start + 2, "错误类型"));
			sheets[j].addCell(new Label(4, start + 2, "生产线"));
			sheets[j].addCell(new Label(5, start + 2, "上传人"));
			sheets[j].addCell(new Label(6, start + 2, "上传时间"));
			sheets[j].addCell(new Label(7, start + 2, "上传日志编号"));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 3;
				int number = 0;
				CovertErrorLog p = logs.get(i);
				sheets[j].addCell(new Label(number++, row, p.getId().toString()));
				sheets[j].addCell(new Label(number++, row, p.getTdCode()));
				sheets[j].addCell(new Label(number++, row, p.getCovertCode()));
				sheets[j].addCell(new Label(number++, row,HtmlSelect.getNameByOrder(request, "CovertErrorType", p.getErrorType())));
				sheets[j].addCell(new Label(number++, row, p.getProductionLine()));
				sheets[j].addCell(new Label(number++, row, au.getUsersByid(p.getUploadUser()).getRealname()));
				sheets[j].addCell(new Label(number++, row, p.getUploadDate().toString()));
				sheets[j].addCell(new Label(number++, row, p.getCovertUploadId().toString()));
				
				/*
				try {
					Organ organ = appOrgan.getByOecode(p.getPartnSold());
					sheets[j].addCell(new Label(3, row, organ.getOrganname()));
				} catch (Exception e) {
					
				}
				sheets[j].addCell(new Label(4, row, p.getDeliveryNumber()));
				sheets[j].addCell(new Label(5, row, p.getInvoiceLineItem()));
				sheets[j].addCell(new Label(6, row, p.getMaterialCode()));
				try {
					Product product = appProduct.getByMCode(p.getMaterialCode());
					sheets[j].addCell(new Label(7, row, product.getProductname()));
					sheets[j].addCell(new Label(8, row, product.getSpecmode()));
				} catch (Exception e) {
					
				}
				sheets[j].addCell(new Label(9, row, p.getBatchNumber()));
				if(p.getInvoiceQty() != null) {
					sheets[j].addCell(new Label(10, row, p.getInvoiceQty().toString()));
				}
				sheets[j].addCell(new Label(11, row, p.getNetVal()));
				*/
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}
