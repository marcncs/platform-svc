package com.winsafe.erp.action;

import java.io.OutputStream;
import java.util.Date;
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
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class DownloadCovertCodeAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(DownloadCovertCodeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		try {
			AppPrepareCode auv = new AppPrepareCode();      	
			List<Map<String,String>> covertCode = auv.getCodeListForUpdCovertCode(request, 0); 
			
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition","attachment; filename=CodeOf"+DateUtil.formatDateTime(new Date())+".xls");
			response.setContentType("application/msexcel");
			writeXls(covertCode, os, request);
			os.flush();
			os.close();
			
		} catch (Exception e) {
			logger.error("Download Product Plan Code  error:", e);
		}
		return null;
	}
	public void writeXls(List<Map<String, String>> covertCode, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		int snum = 1; 
		snum = covertCode.size() / 50000;
		if (covertCode.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 40000;
			if (currentnum >= covertCode.size()) {
				currentnum = covertCode.size();
			}
			int start = j * 40000;
			sheets[j].addCell(new Label(0, start, "条码", wcfFC));
			sheets[j].addCell(new Label(1, start, "单位", wcfFC));
			sheets[j].addCell(new Label(2, start, "对应暗码", wcfFC));
			sheets[j].addCell(new Label(3, start, "对应托码", wcfFC));
			sheets[j].addCell(new Label(4, start, "释放状态", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 1;
				Map<String,String> p = covertCode.get(i);
				sheets[j].addCell(new Label(0, row, p.get("code")));
				sheets[j].addCell(new Label(1, row, "箱"));
				sheets[j].addCell(new Label(2, row, p.get("covert_code")));
				sheets[j].addCell(new Label(3, row, p.get("tcode")));
				if("1".equals(p.get("isrelease"))) {
					sheets[j].addCell(new Label(4, row, "已释放"));
				} else {
					sheets[j].addCell(new Label(4, row, ""));
				}
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
