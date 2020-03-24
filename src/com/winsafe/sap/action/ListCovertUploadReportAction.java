package com.winsafe.sap.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
//import com.winsafe.drp.dao.PDGoodsReceivingStatusReport;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCovertUploadReport;
import com.winsafe.sap.pojo.CovertUploadReport;

public class ListCovertUploadReportAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListCovertUploadReportAction.class);
	
	private AppCovertUploadReport appCovertUploadReport = new AppCovertUploadReport();
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
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
			
			List rs = appCovertUploadReport.getCovertUploadReportsBySql(request, pagesize, sql.substring(0, sql.length() - 1));
			
			
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
			reports = appCovertUploadReport.getCovertUploadReports(request, pagesize, whereSql);
			for(CovertUploadReport cur : reports) {
				double totalCount = cur.getTotalCount().doubleValue();
				double correctCount = totalCount - cur.getErrorCount().doubleValue();
				double accuracy = ArithDouble.div(correctCount, totalCount, 4);
				String saccuracy = Double.toString(ArithDouble.mul(accuracy, 100))+"%";
				cur.setAccuracy(saccuracy);
			}
			request.setAttribute("cur", reports);
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
		DBUserLog.addUserLog(request, "查看");
		return mapping.findForward("list");
	}
		
}
