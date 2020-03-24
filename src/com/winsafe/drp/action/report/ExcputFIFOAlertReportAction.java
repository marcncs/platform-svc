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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFIFOAlertReport;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.FIFOAlertReport;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class ExcputFIFOAlertReportAction extends BaseAction {
	private static Logger logger = Logger.getLogger(FIFOAlertReportAction.class);
	private AppFIFOAlertReport appFIFOAlertReport = new AppFIFOAlertReport();
	private AppRegion appRegion = new AppRegion();
	private AppProduct appProduct = new AppProduct();
	private AppFUnit appFUnit = new AppFUnit();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		super.initdata(request);
		try {
			Map paraMap = new HashMap(request.getParameterMap());
			List<FIFOAlertReport> reports = new ArrayList<FIFOAlertReport>();
			//List resultList = new ArrayList();
			//resultList = appFIFOAlertReport.getFIFOAlertReport(null, 0, paraMap, users);
			List resultList = appFIFOAlertReport.getFIFOAlertReportNew(request, -1, users);
			Map<String, FUnit> funitMap = appFUnit.getAllMap(); // 获取所有的单位换算信息
			for (int i = 0; i < resultList.size(); i++) {
				Map map = (Map) resultList.get(i);
				FIFOAlertReport report = new FIFOAlertReport();
				MapUtil.mapToObject(map, report);

				Product product = appProduct.loadProductById(report.getProductId());

				if (StringUtil.isEmpty((String) paraMap.get("countByUnit"))) {
					if (checkRate(product.getId(), product.getSunit(), Constants.DEFAULT_UNIT_ID, funitMap)) {
						// 获取库存数量
						report.setShipQuantity(changeUnit(product.getId(), product.getSunit(), report.getShipQuantity(), Constants.DEFAULT_UNIT_ID, funitMap));
						report.setStockPile(changeUnit(product.getId(), product.getSunit(), report.getStockPile(), Constants.DEFAULT_UNIT_ID, funitMap));
						report.setUnitId(Constants.DEFAULT_UNIT_ID);
					}
				} else {
					// 转换成计量单位
					if (product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
						Double shipQuantity = ArithDouble.mul(report.getShipQuantity(), product.getBoxquantity());
						report.setShipQuantity(shipQuantity);
						Double stockQuantity = ArithDouble.mul(report.getStockPile(), product.getBoxquantity());
						report.setStockPile(stockQuantity);
						report.setUnitId(product.getCountunit());
					}
				}

				reports.add(report);

			}
			//获取当前日期
			String nowDate = Dateutil.getCurrentDateTimeString();
			// 写入excel中
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=FIFOAlertReport"+nowDate+".xls");
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
	 * 检查单位是否可以正常转化 Create Time 2015-1-30 下午02:27:02
	 * 
	 * @param scForm
	 * @param funitMap
	 * @return
	 * @author lipeng
	 */
	private Boolean checkRate(String productId, Integer srcUnitId, Integer desUnitId, Map<String, FUnit> rateMap) {
		Boolean flag = true;
		try {
			String srcKey = productId + "_" + srcUnitId;
			FUnit srcFunit = rateMap.get(srcKey);
			if (srcFunit == null) {
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + srcUnitId + ") ] not exist");
			}
			String desKey = productId + "_" + desUnitId;
			FUnit desFunit = rateMap.get(desKey);
			if (desFunit == null) {
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + desUnitId + ") ] not exist");
			}
			Double desRate = desFunit.getXquantity();
			if (desRate == 0d) {
				throw new Exception("funit [ productId(" + productId + ") and unitId(" + desUnitId + ") ] is zero");
			}
		} catch (Exception e) {
			logger.error("", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 根据单位转化数量 Create Time 2015-1-30 下午02:07:55
	 * 
	 * @param productId
	 * @param srcUnit
	 * @param srcQuantity
	 * @param desUnit
	 * @param rateMap
	 * @return
	 * @author lipeng
	 */
	private Double changeUnit(String productId, Integer srcUnit, Double srcQuantity, Integer desUnit, Map<String, FUnit> rateMap) {
		// 先换算出最小单位数量
		String srcKey = productId + "_" + srcUnit;
		Double srcRate = rateMap.get(srcKey).getXquantity();
		Double minUnitQuantity = ArithDouble.mul(srcQuantity, srcRate);
		// 再换算成目标单位数量
		String desKey = productId + "_" + desUnit;
		Double desRate = rateMap.get(desKey).getXquantity();
		Double desQuantity = ArithDouble.div(minUnitQuantity, desRate);
		return desQuantity;
	}

	/**
	 * @param list
	 * @param os
	 * @param request
	 * @throws Exception
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeXls(List<FIFOAlertReport> list, OutputStream os, HttpServletRequest request) throws Exception, RowsExceededException, WriteException {
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
			sheets[j].addCell(new Label(0, start, "先进先出警示报表", wchT));
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
			sheets[j].addCell(new Label(m++, start + 3, request.getParameter("beginDate") + "  -  " + request.getParameter("endDate")));
			//sheets[j].addCell(new Label(m++, start + 3, "有效期（天）:", seachT));
			//sheets[j].addCell(new Label(m++, start + 3, request.getParameter("expiryDate")));
			sheets[j].addCell(new Label(m++, start + 3, "单位:", seachT));
			if (request.getParameter("countByUnit").equals("true")) {
				sheets[j].addCell(new Label(m++, start + 3, "升,千克"));
			} else {
				sheets[j].addCell(new Label(m++, start + 3, "件"));
			}
			// 表头
			m = 0;
			sheets[j].addCell(new Label(m++, start + 4, "区", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "省份", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构代码", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "机构名称", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "仓库", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货单号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号中文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "物料号英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称中文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "产品名称英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "规格英文", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "发货最早批次", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "生产日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "过期日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "库存旧货数量", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "库存最早批次", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "生产日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "过期日期", wcfFC));
			sheets[j].addCell(new Label(m++, start + 4, "差异天数", wcfFC));

			// 内容
			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				FIFOAlertReport fIFOAlertReport = list.get(i);
				m = 0;
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getRegionName() == null ? "" : String.valueOf(fIFOAlertReport.getRegionName())));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getProvince() == null ? "" : String.valueOf(fIFOAlertReport.getProvince())));
				sheets[j].addCell(new Label(m++, row, String.valueOf(fIFOAlertReport.getOrganId())));
				sheets[j].addCell(new Label(m++, row, ESAPIUtil.decodeForHTML(fIFOAlertReport.getOrganName())));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getWarehouseName()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getBillNo()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getmCode()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getMatericalChDes()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getMatericalEnDes()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getProductName()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getProductNameen()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getPackSizeNameEn()));
				sheets[j].addCell(new Label(m++, row, HtmlSelect.getResourceName(request, "CountUnit", fIFOAlertReport.getUnitId())));
				sheets[j].addCell(new Number(m++, row, fIFOAlertReport.getShipQuantity()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getShipBatch()));
				if (StringUtil.isEmpty(fIFOAlertReport.getShipProductionDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(fIFOAlertReport.getShipProductionDate()), "yyyy-MM-dd")));
				}
				if (StringUtil.isEmpty(fIFOAlertReport.getShipExpiryDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(fIFOAlertReport.getShipExpiryDate()), "yyyy-MM-dd")));
				}
				sheets[j].addCell(new Number(m++, row, fIFOAlertReport.getStockPile()));
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getStockBatch()));
				if (StringUtil.isEmpty(fIFOAlertReport.getStockProductionDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(fIFOAlertReport.getStockProductionDate()), "yyyy-MM-dd")));
				}
				if (StringUtil.isEmpty(fIFOAlertReport.getStockExpiryDate())) {
					sheets[j].addCell(new Label(m++, row, ""));
				} else {
					sheets[j].addCell(new Label(m++, row, DateUtil.formatDate(DateUtil.formatStrDate(fIFOAlertReport.getStockExpiryDate()), "yyyy-MM-dd")));
				}
				sheets[j].addCell(new Label(m++, row, fIFOAlertReport.getDifferentDays() == null? "": String.valueOf(fIFOAlertReport.getDifferentDays())));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
