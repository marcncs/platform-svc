package com.winsafe.drp.action.sys;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.alibaba.fastjson.JSON;
import com.winsafe.app.action.form.UploadFileForm;
import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.server.SalesAreaServices;

public class RegionManageAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(RegionManageAction.class);

	public ActionForward mainPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("mainPage");
	}

	public ActionForward regionTreeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRegionManage arm = new AppRegionManage();
		List<RegionItemInfo> list = arm.getAllRegionItem();
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.print(JSON.toJSONString(list));
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward exportXls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRegionManage arm = new AppRegionManage();
//		List<RegionItemInfo> list = arm.getAllRegionItem();
		List<Map<String, String>> regionList = arm.getAllSBonusArea();
		HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("region");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("大区");
        row.createCell(1).setCellValue("地区");
        row.createCell(2).setCellValue("小区");
//        row.createCell(3).setCellValue("县");
        int i = 1;
        for (Map<String, String> r : regionList) {
        	row = sheet.createRow(i);
        	row.createCell(0).setCellValue(r.get("bigregion"));
            row.createCell(1).setCellValue(r.get("middleregion"));
            row.createCell(2).setCellValue(r.get("smallregion"));
//            row.createCell(3).setCellValue(r.get("areaname"));
            i++;
        }
        response.setHeader("Content-disposition",
                "attachment;filename=" +
                URLEncoder.encode("region.xls", "utf-8"));
        response.setContentType("application/vnd.ms-excel");
        OutputStream out = response.getOutputStream();
        wb.write(out);
		return null;
	}
	
	public ActionForward importXls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UploadFileForm uf = (UploadFileForm)form;
		FormFile file = uf.getFile();
		try {
			SalesAreaServices sas = new SalesAreaServices();
			String msg = sas.importSbonusArea(file.getInputStream());
			request.setAttribute("result", msg);
			return new ActionForward("/sys/lockrecord2.jsp");
		} catch (Exception e) {
			request.setAttribute("result", "上传文件处理失败");
			logger.error("importXls error ", e);
			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}
	
}
