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
import com.winsafe.drp.dao.InventoryDetailReport;
import com.winsafe.drp.server.SalesConsumeInventoryHistoryService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class ExcputInventoryDetailHistoryAction extends BaseAction {
	private AppRegion appRegion = new AppRegion();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		initdata(request);
		try {
			List<InventoryDetailReport> reports = new ArrayList<InventoryDetailReport>();
			Map map = new HashMap(request.getParameterMap());
			SalesConsumeInventoryHistoryService scihs = new SalesConsumeInventoryHistoryService();
			reports = scihs.queryInventoryDetailHistory(null, 0, map, users);
			//获取当前日期
			String nowDate = Dateutil.getCurrentDateTimeString();
			// 写入excel中
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=InventoryDetailReport"+nowDate+".xls");
			response.setContentType("application/msexcel");
			writeXls(reports, os, request);
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
	public void writeXls(List<InventoryDetailReport> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "库存明细历史", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("oname2")));
			sheets[j].addCell(new Label(m++, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packSizeName")));
			sheets[j].addCell(new Label(m++, start + 2, "批次:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("batch")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("EndDate")));
			sheets[j].addCell(new Label(m++, start + 3, "单位:", seachT));
			if (request.getParameter("countByUnit").equals("true")) {
				sheets[j].addCell(new Label(m++, start + 3, "升,千克"));
			} else {
				sheets[j].addCell(new Label(m++, start + 3, "件"));
			}
			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "月份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "省份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构内部编码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品类别", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品中文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号中文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "批次", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "在仓库存", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "在途库存", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "待发货库存", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "总库存", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "生产日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "过期日期", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				InventoryDetailReport inventoryDetailReport = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getYearMonth()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getRegionName() == null ? "" : String.valueOf(inventoryDetailReport.getRegionName())));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getAreaName() == null ? "" : String.valueOf(inventoryDetailReport.getAreaName())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(inventoryDetailReport.getOrganId())));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getOecode()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(inventoryDetailReport.getOrganName())));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getWarehouseName()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getSortName()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getProductName()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getmCode()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getMatericalChDes()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getMatericalEnDes()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getPackSizeName()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getBatch()));
				sheets[j].addCell(new Label(m++, row, inventoryDetailReport.getUnit()));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(inventoryDetailReport.getStockpile().replace(",", ""))));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(inventoryDetailReport.getStockInTransit().replace(",", ""))));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(inventoryDetailReport.getStockpileToShip().replace(",", ""))));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(inventoryDetailReport.getTotalStockpile().replace(",", ""))));
				if (StringUtil.isEmpty(inventoryDetailReport.getProductionDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row,  DateUtil.formatDate(DateUtil.formatStrDate(inventoryDetailReport.getProductionDate()), "yyyy-MM-dd")));
				}
				if (StringUtil.isEmpty(inventoryDetailReport.getExpiryDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(inventoryDetailReport.getExpiryDate()), "yyyy-MM-dd")));
				}
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
