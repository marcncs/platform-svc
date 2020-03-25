package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Number;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.PDGoodsReceivingStatusReport;
import com.winsafe.drp.server.PDGoodsReceivingStatusReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcputPDGoodsReceivingStatusReportAction extends BaseAction {

	private PDGoodsReceivingStatusReportService pdGoodsRecevieService = new PDGoodsReceivingStatusReportService();
	private AppRegion appRegion = new AppRegion();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		super.initdata(request);
		try {
			List<PDGoodsReceivingStatusReport> rls = new ArrayList<PDGoodsReceivingStatusReport>();
			Map map = new HashMap(request.getParameterMap());
			rls = pdGoodsRecevieService.queryReport(null, 0, map, users);
			//获取当前日期
			String nowDate = Dateutil.getCurrentDateTimeString();
			// 写入excel中
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=GoodsReceivingStatusReport"+nowDate+".xls");
			response.setContentType("application/msexcel");
			writeXls(rls, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request, "列表");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void writeXls(List<PDGoodsReceivingStatusReport> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "货物签收状态", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "收货机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("oname2")));
			sheets[j].addCell(new Label(m++, start + 1, "收货仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "SAP订单号:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("sapOrderId")));
			sheets[j].addCell(new Label(m++, start + 2, "是否签收:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, HtmlSelect.getNameByOrder(request, "YesOrNo", !StringUtil.isEmpty(request.getParameter("isComplete")) ? Integer.valueOf(request
					.getParameter("isComplete")) : 0)));
			sheets[j].addCell(new Label(m++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 3, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("packsizename")));
			sheets[j].addCell(new Label(m++, start + 3, "单位:", seachT));
			if (request.getParameter("countByUnit").equals("true")) {
				sheets[j].addCell(new Label(m++, start + 3, "升,千克"));
			} else {
				sheets[j].addCell(new Label(m++, start + 3, "件"));
			}
			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "省份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构内部编码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料中文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "批次", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "是否签收", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "签收日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "签收数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "原因", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				PDGoodsReceivingStatusReport pdGoodsReceivingStatusReport = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getRegionName() == null ? "" : String.valueOf(pdGoodsReceivingStatusReport.getRegionName())));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getAreaName() == null ? "" : String.valueOf(pdGoodsReceivingStatusReport.getAreaName())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(pdGoodsReceivingStatusReport.getOrganId())));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getOecode()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(pdGoodsReceivingStatusReport.getOrganName())));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getWarehouseName()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getProductName()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getmCode()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getMatericalChDes()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getMatericalEnDes()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getSpecMode()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getBatch()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getUnit()));
				sheets[j].addCell(new Label(m++, row, pdGoodsReceivingStatusReport.getBillCode()));
				if (StringUtil.isEmpty(pdGoodsReceivingStatusReport.getShipmentDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(pdGoodsReceivingStatusReport.getShipmentDate()))));
				}
				sheets[j].addCell(new Number(m++, row, Double.valueOf(getDoubleString(pdGoodsReceivingStatusReport.getTakequantity()))));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "YesOrNo", Integer.valueOf(pdGoodsReceivingStatusReport.getIsComplete()))));
				if (StringUtil.isEmpty(pdGoodsReceivingStatusReport.getReceiveDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(pdGoodsReceivingStatusReport.getReceiveDate()))));
				}
				if (StringUtil.isEmpty(pdGoodsReceivingStatusReport.getReceiveQuantity())) {
					sheets[j].addCell(new Number(m++, row, 0.0));

				}else {
					sheets[j].addCell(new Number(m++, row, Double.valueOf(getDoubleString(pdGoodsReceivingStatusReport.getReceiveQuantity()))));
				}
				sheets[j].addCell(new Label(m++, row, String.valueOf(pdGoodsReceivingStatusReport.getReason())));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
	private String getDoubleString(String value) {
		if(StringUtil.isEmpty(value)) {
			return "0";
		} else {
			return value;
		}
	}
}
