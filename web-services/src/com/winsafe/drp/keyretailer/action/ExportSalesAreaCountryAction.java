package com.winsafe.drp.keyretailer.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSalesAreaCountry;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ExportSalesAreaCountryAction extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		AppSalesAreaCountry appSalesAreaCountry = new AppSalesAreaCountry();
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		
		String whereSql = "";
		String blur = DbUtil.getOrBlur(map, tmpMap, "CA.AREANAME","CA2.AREANAME","CA3.AREANAME");
		if(!StringUtil.isEmpty(blur)) {
			whereSql = " where " + blur;
		}
		whereSql = DbUtil.getWhereSql(whereSql);
		
		List<Map<String,String>> salesAreaCountries = appSalesAreaCountry.getSalesAreaCountry(request, 0, whereSql);
		
		HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("region");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("大区");
        row.createCell(1).setCellValue("地区");
        row.createCell(2).setCellValue("小区");
        row.createCell(3).setCellValue("省");
        row.createCell(4).setCellValue("市");
        row.createCell(5).setCellValue("区/县");
        int i = 1;
        for (Map<String, String> r : salesAreaCountries) {
        	row = sheet.createRow(i);
        	row.createCell(0).setCellValue(r.get("name1"));
            row.createCell(1).setCellValue(r.get("name2"));
            row.createCell(2).setCellValue(r.get("name3"));
            row.createCell(3).setCellValue(r.get("province"));
            row.createCell(4).setCellValue(r.get("city"));
            row.createCell(5).setCellValue(r.get("area"));
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
		
}
