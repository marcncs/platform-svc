package com.winsafe.drp.keyretailer.action;

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
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSCustomerArea;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExportSCustomerAreasAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ExportSCustomerAreasAction.class);
	
	private AppSCustomerArea appSCustomerArea = new AppSCustomerArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		
		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			
			String whereSql = "";
			String blur = DbUtil.getOrBlur(map, tmpMap, "o.id","o.ORGANNAME","SUA.AREAID","po.organname","sba.name_loc");
			if(!StringUtil.isEmpty(blur)) {
				whereSql = " where " + blur;
			}
			whereSql = DbUtil.getWhereSql(whereSql);
			
			List<Map<String,String>> sCustomerAreas = appSCustomerArea.getSCustomerArea(request, 0, whereSql);
			
			if (sCustomerAreas.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=sCustomerAreas_"+DateUtil.getCurrentDateTimeString()+".xls");
			response.setContentType("application/msexcel");
			// 写入excel
			writeXls(sCustomerAreas, os, request);
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
	
	/*
	 * 将数据写入excel中
	 */
	public void writeXls(List<Map<String,String>> list,OutputStream os,HttpServletRequest request) throws Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		int m = 0;
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

			sheets[j].mergeCells(0, start,24, start);
			sheets[j].addCell(new Label(0, start, "区域机构", wchT));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 1, "关键字:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("KeyWord")));

			m = 0;
//			sheets[j].addCell(new Label(m++, start + 2, "用户编号", wcfFC));
//			sheets[j].addCell(new Label(m++, start + 2, "用户名", wcfFC));
//			sheets[j].addCell(new Label(m++, start + 2, "用户姓名 ", wcfFC));
//			sheets[j].addCell(new Label(m++, start + 2, "区域代码", wcfFC));
//			sheets[j].addCell(new Label(m++, start + 2, "区域名称 ", wcfFC));
			
			sheets[j].addCell(new Label(m++, start + 2, "机构代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "客户类型 ", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "区域代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "区域名称 ", wcfFC));
			sheets[j].addCell(new Label(m++, start + 2, "上级机构 ", wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 3;
				Map<String,String> sbt = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, sbt.get("oid")));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(sbt.get("oname"))));
				if(!StringUtil.isEmpty(sbt.get("organmodel"))) {
					sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "AccountType", Integer.valueOf(sbt.get("organmodel")))));
				} else {
					sheets[j].addCell(new Label(m++, row, ""));
				}
				sheets[j].addCell(new Label(m++, row, sbt.get("areaid")));
				sheets[j].addCell(new Label(m++, row, sbt.get("areaname")));
				sheets[j].addCell(new Label(m++, row, sbt.get("poname")));
			}
		}
		workbook.write();
		workbook.close();
		os.flush();
		os.close();
	}
		
}
