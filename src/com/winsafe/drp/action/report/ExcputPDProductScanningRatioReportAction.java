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
import com.winsafe.drp.dao.PDProductScanningRatioReport;
import com.winsafe.drp.server.PDProductScanningRatioReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcputPDProductScanningRatioReportAction extends BaseAction {

	private PDProductScanningRatioReportService service = new PDProductScanningRatioReportService();
	private AppRegion appRegion = new AppRegion();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.initdata(request);
		try {
			Map map = new HashMap(request.getParameterMap());
			List<PDProductScanningRatioReport> rls = new ArrayList<PDProductScanningRatioReport>();
			rls = service.queryReport(null, 0, map, users);
			//获取当前日期
			String nowDate = Dateutil.getCurrentDateTimeString();
			// 写入excel中
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=ProductScanningRatioReport"+nowDate+".xls");
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
	public void writeXls(List<PDProductScanningRatioReport> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "扫描率报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "出货机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("oname2")));
			sheets[j].addCell(new Label(m++, start + 1, "出货仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packSizeName")));
			sheets[j].addCell(new Label(m++, start + 2, "统计方式:", seachT));
			if ("0".equals(request.getParameter("countMode"))) {
				sheets[j].addCell(new Label(m++, start + 2, "按单据"));
			} else if ("1".equals(request.getParameter("countMode"))) {
				sheets[j].addCell(new Label(m++, start + 2, "按机构"));
			} else {
				sheets[j].addCell(new Label(m++, start + 2, "按仓库"));
			}
			
			m = 0;
			sheets[j].addCell(new Label(m++, start + 3, "单据类型:", seachT));
			if(StringUtil.isEmpty(request.getParameter("BSort"))) {
				sheets[j].addCell(new Label(m++, start + 3, ""));
			} else {
				sheets[j].addCell(new Label(m++, start + 3, HtmlSelect.getNameByOrder(request, "BSort", Integer.valueOf(request.getParameter("BSort")))));
			}
			
			sheets[j].addCell(new Label(m++, start + 3, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));

			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "大区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "省份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构内部编码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品类别", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "出/入库数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "码数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "扫描率", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				PDProductScanningRatioReport pdProductScanningRatioReport = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getRegionName() == null ? "" : String.valueOf(pdProductScanningRatioReport.getRegionName())));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getAreaName() == null ? "" : String.valueOf(pdProductScanningRatioReport.getAreaName())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(pdProductScanningRatioReport.getOrganId())));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getOecode()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(pdProductScanningRatioReport.getOrganName())));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getWarehouseName()));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getSortName()));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getmCode()));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getProductName()));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getSpecMode()));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getBillNo()));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(pdProductScanningRatioReport.getIoQuantity() + "".replace(",", ""))));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(pdProductScanningRatioReport.getIdcodeQuantity() + "".replace(",", ""))));
				sheets[j].addCell(new Label(m++, row, pdProductScanningRatioReport.getRatio()));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}

}