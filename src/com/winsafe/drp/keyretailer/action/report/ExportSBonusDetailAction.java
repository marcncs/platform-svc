package com.winsafe.drp.keyretailer.action.report;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Number;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusDetail;
import com.winsafe.drp.keyretailer.metadata.BonusType;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

import common.Logger;

public class ExportSBonusDetailAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ExportSBonusDetailAction.class);

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppSBonusDetail appSBonusDetail = new AppSBonusDetail();
		// 初始化参数
		super.initdata(request);
		// 获取查询条件
		int pagesize = 10;
		initdata(request);
		
		String condition = SBonusService.getWhereCondition(users);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "SBonusDetail" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," sbd.makeDate");
		String blur = DbUtil.getOrBlur(map, tmpMap, "sbd.productName","sbd.spec","sbd.remark");
		whereSql = whereSql + timeCondition + blur + condition;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<Map<String,String>> sBonusDetails = appSBonusDetail.getSBonusDetail(request, pagesize, whereSql);
		
		//获取当前日期
		String nowDate = Dateutil.getCurrentDateTimeString();
		// 写入excel中
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=SalesConsumeReport"+nowDate+".xls");
		response.setContentType("application/msexcel");
		writeXls(sBonusDetails, os, request);
		
		os.flush();
		os.close();
		DBUserLog.addUserLog(request, "列表");
		return null;
	}

	/**
	 * @param list
	 * @param os
	 * @param request
	 * @throws Exception
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeXls(List<Map<String,String>> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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

			sheets[j].mergeCells(0, start, 23, start);
			sheets[j].addCell(new Label(0, start, "积分明细", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "当前机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("outOrganName"))));
			sheets[j].addCell(new Label(m++, start + 1, "对方机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, ESAPIUtil.decodeForHTML(request.getParameter("oname2"))));
			sheets[j].addCell(new Label(m++, start + 1, "积分类型:", seachT));
			BonusType bonusType =null;
			if(!StringUtil.isEmpty(request.getParameter("bonusType"))) {
				bonusType = BonusType.parseByValue(Integer.parseInt(request.getParameter("bonusType")));
			}
			if(bonusType != null){
				sheets[j].addCell(new Label(m++, start + 1, bonusType.getName()));
			} else {
				sheets[j].addCell(new Label(m++, start + 1, ""));
			}
			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("BeginDate") + "  -  " + request.getParameter("EndDate")));
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("materialName")));
			sheets[j].addCell(new Label(m++, start + 2, "关键字:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("KeyWord")));
			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("regionName")));
			sheets[j].addCell(new Label(m++, start + 3, "客户类型:", seachT));
			DealerType dealerType =null;
			if(!StringUtil.isEmpty(request.getParameter("organModel"))) {
				dealerType = DealerType.parseByValue(Integer.parseInt(request.getParameter("organModel")));
			}
			if(dealerType != null){
				sheets[j].addCell(new Label(m++, start + 3, dealerType.getName()));
			} else {
				sheets[j].addCell(new Label(m++, start + 3, ""));
			}
			sheets[j].addCell(new Label(m++, start + 3, "年度:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("year")));

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "地区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "小区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "年度", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "当前机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "对方机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "积分类型", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "分值", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "数量", wcfFC));
//			sheets[j].addCell(new Label(m++, start + 4, "上传编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "备注", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				Map<String,String> reportForm = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, reportForm.get("bigregion")));
				sheets[j].addCell(new Label(m++, row, reportForm.get("middleregion")));
				sheets[j].addCell(new Label(m++, row, reportForm.get("smallregion")));
				sheets[j].addCell(new Label(m++, row, reportForm.get("year")));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(reportForm.get("organname"))));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(reportForm.get("opporganname"))));
				bonusType = BonusType.parseByValue(Integer.parseInt(reportForm.get("bonustype")));
				sheets[j].addCell(new Label(m++, row, bonusType.getName()));
				sheets[j].addCell(new Number(m++, row, getDouble(reportForm.get("bonuspoint")),QWCF2));
				sheets[j].addCell(new Label(m++, row, reportForm.get("mcode")));
				sheets[j].addCell(new Label(m++, row, reportForm.get("productname")));
				sheets[j].addCell(new Label(m++, row, reportForm.get("spec")));
				sheets[j].addCell(new Number(m++, row, getDouble(reportForm.get("amount")),QWCF2));
				sheets[j].addCell(new Label(m++, row, reportForm.get("makedate")));
				sheets[j].addCell(new Label(m++, row, reportForm.get("remark")));
				
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private double getDouble(String value) {
		if(!StringUtil.isEmpty(value)) {
			return Double.parseDouble(value);
		} 
		return 0d;
	}
}
