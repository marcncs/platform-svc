package com.winsafe.drp.action.users;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.AppUserArea;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.keyretailer.pojo.SUserArea;
import com.winsafe.hbm.util.StringUtil;

public class UserRegionPageAction extends DispatchAction {

	public ActionForward page(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userid");
		AppUserArea dao = new AppUserArea();
		List<SUserArea> list = null;
		if (!StringUtil.isEmpty(userId)) {
			list = dao.getByUserId(Integer.parseInt(userId));
		}
		request.setAttribute("userId", userId);
		request.setAttribute("list", list);
		return mapping.findForward("page");
	}
	
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRegionManage arm = new AppRegionManage();
		List<RegionItemInfo> regions = null;
		String userId = request.getParameter("userId");
		String keyword = request.getParameter("keyword");
		if (!StringUtil.isEmpty(keyword)) {
			regions = arm.search(keyword);
			request.setAttribute("keyword", keyword);
		} else {
			regions = arm.getAllRegionItem();			
		}
		request.setAttribute("userId", userId);
		request.setAttribute("regions", regions);
		return mapping.findForward("addPage");
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppUserArea dao = new AppUserArea();
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String[] areaIds = request.getParameterValues("areaId");
		SUserArea t = null;
		if (areaIds != null && areaIds.length > 0) {
			for (String a : areaIds) {
				t = new SUserArea();
				t.setUserId(userId);
				t.setAreaId(Integer.parseInt(a.trim()));
				dao.insert(t);
			}
		}
		request.setAttribute("result","databases.add.success");
		return mapping.findForward("success");
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppUserArea dao = new AppUserArea();
		System.out.println(request.getParameter("id"));
		String id = request.getParameter("id");
		String[] ids = null;
		if (id.contains(",")) {
			ids = request.getParameter("id").split(",");
		} else {
			ids = new String[]{id};
		}
		SUserArea t = null;
		if (ids != null && ids.length > 0) {
			for (String i : ids) {
				t = new SUserArea();
				t.setId(Integer.parseInt(i.trim()));
				dao.delete(t);
			}
		}
		request.setAttribute("result", "databases.del.success");
		return mapping.findForward("success");
	}
	
	public ActionForward exportXls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");
		AppUserArea dao = new AppUserArea();
		List<SUserArea> list = null;
		if (!StringUtil.isEmpty(userId)) {
			list = dao.getByUserId(Integer.parseInt(userId));
		}
		HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("user_region");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("登录名");
        row.createCell(1).setCellValue("真实姓名");
        row.createCell(2).setCellValue("区域编号");
        row.createCell(3).setCellValue("区域名称");
        int i = 1;
        for (SUserArea r : list) {
        	row = sheet.createRow(i);
        	row.createCell(0).setCellValue(r.getLoginName());
            row.createCell(1).setCellValue(r.getRealName());
            row.createCell(2).setCellValue(r.getAreaId());
            row.createCell(3).setCellValue(r.getAreaName());
            i++;
        }
        response.setHeader("Content-disposition",
                "attachment;filename=" +
                URLEncoder.encode("user_region.xls", "utf-8"));
        response.setContentType("application/vnd.ms-excel");
        OutputStream out = response.getOutputStream();
        wb.write(out);
		return null;
	}

}
