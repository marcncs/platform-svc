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
import com.winsafe.drp.dao.PDGoodsTransferReport;
import com.winsafe.drp.server.PDGoodsTransferReportService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcputPDGoodsTransferReportAction extends BaseAction {

	private PDGoodsTransferReportService service = new PDGoodsTransferReportService();
	private AppRegion appRegion = new AppRegion();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		super.initdata(request);
		try {
			Map map = new HashMap(request.getParameterMap());
			List<PDGoodsTransferReport> rls = new ArrayList<PDGoodsTransferReport>();
			if (request.getParameter("queryFlag") != null) {
				rls = service.queryReport(null, 0, map, users);
			}
			//获取当前日期
			String nowDate = Dateutil.getCurrentDateTimeString();
			// 写入excel中
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=GoodsTransferReport"+nowDate+".xls");
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
	public void writeXls(List<PDGoodsTransferReport> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "调拨报表", wchT));
			sheets[j].addCell(new Label(m++, start + 1, "区域:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, appRegion.getSortNameByCode(request.getParameter("region"))));
			sheets[j].addCell(new Label(m++, start + 1, "转出机构:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("oname2")));
			sheets[j].addCell(new Label(m++, start + 1, "转出仓库:", seachT));
			sheets[j].addCell(new Label(m++, start + 1, request.getParameter("wname")));

			m = 0;
			sheets[j].addCell(new Label(m++, start + 2, "产品名称:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(m++, start + 2, "规格:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("packSizeName")));
			sheets[j].addCell(new Label(m++, start + 2, "日期:", seachT));
			sheets[j].addCell(new Label(m++, start + 2, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));

			m = 0;
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
			sheets[j].addCell(new Label(m++, start + 4, "产品类别", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号中文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "批号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "调出数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "调入数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "调出时间", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "调入时间", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据编号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单据日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "调拨类型", wcfFC)); 
			sheets[j].addCell(new Label(m++, start + 4, "生产日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "过期日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "对方机构代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "对方机构内部编码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "对方机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "对方机构仓库名称", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				PDGoodsTransferReport pdGoodsTransferReport = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getRegionName() == null ? "" : String.valueOf(pdGoodsTransferReport.getRegionName())));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getAreaName() == null ? "" :String.valueOf(pdGoodsTransferReport.getAreaName())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(pdGoodsTransferReport.getOutOrganId())));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getOutOecode()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(pdGoodsTransferReport.getOutOrganName())));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getOutWarehouseName()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getSortName()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getProductChnName()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getProductEngName()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getmCode()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getProductName()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getPackSizeName()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getBatch()));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(pdGoodsTransferReport.getOutQuantity().replace(",", ""))));
				sheets[j].addCell(new Number(m++, row, Double.valueOf(pdGoodsTransferReport.getInQuantity().replace(",", ""))));
				if (StringUtil.isEmpty(pdGoodsTransferReport.getOutDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getOutDate()));
				}
				if (StringUtil.isEmpty(pdGoodsTransferReport.getInDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getInDate()));
				}
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getUnit()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getMoveApplyId()));
				if (StringUtil.isEmpty(pdGoodsTransferReport.getMoveApplyIdDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getMoveApplyIdDate()));
				}
				//调拨类型
				if (StringUtil.isEmpty(pdGoodsTransferReport.getMoveType())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, HtmlSelect.getNameByOrder(request, "MoveType", Integer.valueOf(pdGoodsTransferReport.getMoveType()))));
				}
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getProduceDate()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getExpiryDate()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getInOrganId()));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getInOecode()));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(pdGoodsTransferReport.getInOrganName())));
				sheets[j].addCell(new Label(m++, row, pdGoodsTransferReport.getInWarehouseName()));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
