package com.winsafe.sap.action;

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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCovertUploadReport;
import com.winsafe.sap.pojo.CovertUploadReport;

public class ExcputCovertUploadReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListCovertErrorLogAction.class);

	private AppCovertUploadReport appCovertUploadReport = new AppCovertUploadReport();
	private AppUsers au = new AppUsers();
	private AppProduct appProduct = new AppProduct();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		String columns[] = request.getParameterValues("column");
		Set<String> columnSet = new HashSet<String>();
		if(columns != null) {
			for(String column : columns) {
				for(String col : column.split(",")) {
					columnSet.add(col);
				}
			}
			Object line_No = map.remove("lineNo");
			map.put("line_No", line_No);
			Object material_Code = map.remove("materialCode");
			map.put("material_Code", material_Code);
//			Object upload_Pr_Id = map.remove("uploadPrId");
//			map.put("upload_Pr_Id", upload_Pr_Id);
//			Object upload_User = map.remove("uploadUser");
//			map.put("upload_User", upload_User);
			
		}
		
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "CovertUploadReport" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		if(columns != null) {
			String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," upload_Date");
			whereSql = whereSql + timeCondition;
		} else {
			String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," uploadDate");
			whereSql = whereSql + timeCondition;
		}
		whereSql = DbUtil.getWhereSql(whereSql);
		List<CovertUploadReport> reports = new ArrayList<CovertUploadReport>();
		if(columns != null) {
			StringBuffer sql = new StringBuffer();
			StringBuffer selectColumn = new StringBuffer();
			
			for(String column : columnSet) {
				selectColumn.append(column).append(",");
			}
			sql.append("select 1 as id,").append(selectColumn).append(" sum(TOTAL_COUNT) as totalCount, sum(ERROR_COUNT) as errorCount, count(distinct UPLOAD_PR_ID) as uploadCount from COVERT_UPLOAD_REPORT ").append(whereSql).append(" group by ").append(selectColumn);
			
			List rs = appCovertUploadReport.getCovertUploadReportsBySql(sql.substring(0, sql.length() - 1));
			
			for(int r=0;r<rs.size();r++){
				CovertUploadReport rf = new CovertUploadReport();
				Map rsMap = (Map) rs.get(r);
//				rf.setId(rsMap.get("id") != null ? Long.valueOf((String)rsMap.get("id")) : null);
//				rf.setUploadPrId(rsMap.get("upload_pr_id") != null ? Integer.valueOf((String)rsMap.get("upload_pr_id")) : null);
				rf.setLineNo(rsMap.get("line_no") != null ? (String)rsMap.get("line_no") : "");
				rf.setMaterialCode(rsMap.get("material_code") != null ? (String)rsMap.get("material_code") : "");
				rf.setProductName(rsMap.get("product_name") != null ? (String)rsMap.get("product_name") : "");
				rf.setProductId(rsMap.get("product_id") != null ? (String)rsMap.get("product_id") : "");
				rf.setBatch(rsMap.get("batch") != null ? (String)rsMap.get("batch") : "");
				rf.setUploadCount(rsMap.get("uploadcount") != null ? (String)rsMap.get("uploadcount") : "");
				Double totalCount = Double.valueOf((String)rsMap.get("totalcount"));
				Double correctCount = totalCount - Double.valueOf((String)rsMap.get("errorcount"));
				double accuracy = ArithDouble.div(correctCount, totalCount, 4);
				String saccuracy = Double.toString(ArithDouble.mul(accuracy, 100))+"%";
				rf.setErrorCount(Integer.parseInt((String)rsMap.get("errorcount")));
				rf.setAccuracy(saccuracy);
				rf.setTotalCount(totalCount.intValue());
				reports.add(rf);
			}
			request.setAttribute("cur", reports);
		} else {
			reports = appCovertUploadReport.getCovertUploadReports(whereSql);
			for(CovertUploadReport cur : reports) {
				double totalCount = cur.getTotalCount().doubleValue();
				double correctCount = totalCount - cur.getErrorCount().doubleValue();
				double accuracy = ArithDouble.div(correctCount, totalCount, 4);
				String saccuracy = Double.toString(ArithDouble.mul(accuracy, 100))+"%";
				cur.setAccuracy(saccuracy);
			}
			request.setAttribute("cur", reports);
		}
		
		if (reports.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过"
					+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		for(CovertUploadReport report : reports) {
			try {
				if(!StringUtil.isEmpty(report.getProductId())) {
					Product product = appProduct.loadProductById(report.getProductId());
					report.setPackSizeName(product.getSpecmode());
				}
			} catch (Exception e) {
				logger.error("CovertUploadReport not found by id " + report.getUploadPrId(), e);
			}
			
		}
		
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition","attachment; filename=ListCovertUploadReport_"+DateUtil.getCurrentDateTimeString()+".xls");
		response.setContentType("application/msexcel");
		writeXls(reports, os, request);
		os.flush();
		os.close();
		DBUserLog.addUserLog(request,"暗码关联统计报表");
		
		return null;
	}
	
	public void writeXls(List<CovertUploadReport> cupr, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		int snum = 1;
		snum = cupr.size() / 50000;
		if (cupr.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];

		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= cupr.size()) {
				currentnum = cupr.size();
			}
			int start = j * 50000;

			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "暗码关联统计报表", wchT));
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
			sheets[j].addCell(new Label(0, start + 2, "生产线"));
			sheets[j].addCell(new Label(1, start + 2, "产品名称 "));
			sheets[j].addCell(new Label(2, start + 2, "规格"));
			sheets[j].addCell(new Label(3, start + 2, "批号"));
			sheets[j].addCell(new Label(4, start + 2, "总记录数"));
			sheets[j].addCell(new Label(5, start + 2, "错误记录数"));
			sheets[j].addCell(new Label(6, start + 2, "正确率"));
			sheets[j].addCell(new Label(7, start + 2, "上传次数"));
			sheets[j].addCell(new Label(8, start + 2, "上传时间"));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 3;
				int number = 0;
				CovertUploadReport p = cupr.get(i);
				sheets[j].addCell(new Label(number++, row, p.getLineNo()));
				sheets[j].addCell(new Label(number++, row, p.getProductName()));
				sheets[j].addCell(new Label(number++, row, p.getPackSizeName()));
				sheets[j].addCell(new Label(number++, row, p.getBatch()));
				sheets[j].addCell(new Number(number++,row,p.getTotalCount()));
				sheets[j].addCell(new Number(number++,row,p.getErrorCount()));
				sheets[j].addCell(new Label(number++, row,p.getAccuracy()));
				sheets[j].addCell(new Label(number++,row,p.getUploadCount()));
				sheets[j].addCell(new Label(number++, row, p.getUploadDate().toString()));
				
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
