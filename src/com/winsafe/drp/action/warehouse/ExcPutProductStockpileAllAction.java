package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.PrintJob;

public class ExcPutProductStockpileAllAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.initdata(request);
		AppPrintJob apj = new AppPrintJob();
		String isShowAssistQuantity = request.getParameter("isShowAssistQuantity");
		String isShowStatus = request.getParameter("isShowStatus");
		AppFUnit af = new AppFUnit();
		AppProductStockpileAll aps = new AppProductStockpileAll();

		Map<String, Double> boxUnitMap = new HashMap<String, Double>();
		Map<String, Double> scatterUnitMap = new HashMap<String, Double>();
		try {
			String organid = request.getParameter("MakeOrganID");
			String wnameString = request.getParameter("wname");
			String keyword = request.getParameter("KeyWord");
			String organSql = "";
			if (!StringUtil.isEmpty(organid) && StringUtil.isEmpty(wnameString)) {
				organSql = " and  w.makeorganid='"+organid+"' ";
			}
			
			String strCondition = " ps.stockpile <> 0";
			// 权限条件
			String Condition ="";
			if(DbUtil.isDealer(users)) {
				Condition += " and ps.warehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+") ";
			} else {
				Condition += " and "+DbUtil.getWhereCondition(users, "o");
			}
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductStockpileAll", "WarehouseVisit",
					"Product" };
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "p.ProductName",
					"p.PYCode", "p.SpecMode", "p.nccode","ps.Batch");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			if (!com.winsafe.hbm.util.StringUtil.isEmpty(blur)) {
				blur = blur.substring(0,blur.indexOf(")"));
				blur = blur +" or p.mCode like '%"+keyword+"%' ) and ";
			}
			whereSql = whereSql + blur + leftblur + strCondition + Condition+organSql;
			whereSql = DbUtil.getWhereSql(whereSql);

//			List pls = aps.getStockStat(request, pagesize, whereSql, "");
			List<Map<String,String>> pls = aps.getStockStatList(request, 0, whereSql, users);
			// 所有的库龄集合
//			List<StockPileAgeing> spa = aspa.getStockPileAgeing();
			
			List alp = new ArrayList();
			for (int i = 0; i < pls.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				PrintJob pj = new PrintJob();
				Map<String,String> psMap = pls.get(i); 
				
				MapUtil.mapToObject(psMap, psf);
				
				psf.setCountunit(Constants.DEFAULT_UNIT_ID);
				Double xquantity = af.getXQuantity(psf.getProductid(), psf.getCountunit());
				if (xquantity != 0) {
					psf.setAllstockpile((psf.getStockpile() + psf.getPrepareout())/xquantity);
				}else {
					psf.setAllstockpile(0.0);
				}
				//库龄
				pj = apj.getPrintJobByBatAMc(psf.getNccode(), psf.getBatch());
				if (pj != null) {
					psf.setExpiryDate(pj.getExpiryDate());
					psf.setProductionDate(pj.getProductionDate());
				}else {
					psf.setExpiryDate(null);
					psf.setProductionDate(null);
				}
//				String color = getColorByBatch(getStockPileAgeing(DateUtil.formatDate(psf.getMakedate())), spa);
				//颜色
//				psf.setTagColor(color);
				alp.add(psf);
			} 
			// System.out.println((System.currentTimeMillis()-begin)/1000);

			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListProductStockpileAll.xls");
			response.setContentType("application/msexcel");
			writeXls(alp, os, request, isShowAssistQuantity, isShowStatus);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request, "列表");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<ProductStockpileForm> list, OutputStream os,
			HttpServletRequest request, String isShowAssistQuantity, String isShowStatus)
			throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);

		int snum = 1;
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
			sheets[j].mergeCells(0, start, 9, start);
			sheets[j].addCell(new Label(0, start, "库存统计(导出) ", wchT));
			sheets[j].addCell(new Label(0, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(1, start + 1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start + 1, "仓库:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request.getParameter("wname")));
			sheets[j].addCell(new Label(4, start + 1, "产品类别:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request.getParameter("psname")));

			sheets[j].addCell(new Label(0, start + 2, "产品:", seachT));
			sheets[j].addCell(new Label(1, start + 2, request.getParameter("ProductName")));
			sheets[j].addCell(new Label(2, start + 2, "批号:", seachT));
			sheets[j].addCell(new Label(3, start + 2, request.getParameter("Batch")));
			sheets[j].addCell(new Label(4, start + 2, "关键字:", seachT));
			sheets[j].addCell(new Label(5, start + 2, request.getParameter("KeyWord")));

			sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
			sheets[j]
					.addCell(new Label(1, start + 3, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
			sheets[j]
					.addCell(new Label(3, start + 3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 3, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 4, "仓库编号", wcfFC));
			sheets[j].addCell(new Label(1, start + 4, "仓库名称", wcfFC));
			sheets[j].addCell(new Label(2, start + 4, "产品类别", wcfFC));
			sheets[j].addCell(new Label(3, start + 4, "产品内部编号", wcfFC));
			sheets[j].addCell(new Label(4, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(5, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(6, start + 4, "批号", wcfFC));
			// sheets[j].addCell(new Label(2, start+4, "产品编号", wcfFC));
			sheets[j].addCell(new Label(7, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(8, start + 4, "实际库存", wcfFC));
			sheets[j].addCell(new Label(9, start + 4, "生产日期", wcfFC));
			sheets[j].addCell(new Label(10, start + 4, "过期日期", wcfFC));
			sheets[j].addCell(new Label(11, start + 4, "备注", wcfFC));

			if (isShowAssistQuantity != null) {
				sheets[j].addCell(new Label(9, start + 4, "辅助数量(箱)", wcfFC));
				sheets[j].addCell(new Label(10, start + 4, "辅助数量(EA)", wcfFC));
			}

			if (isShowStatus != null) {
				if (isShowAssistQuantity != null) {
					sheets[j].addCell(new Label(11, start + 4, "状态", wcfFC));
				} else {
					sheets[j].addCell(new Label(9, start + 4, "状态", wcfFC));
				}
			}

			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				ProductStockpileForm p = (ProductStockpileForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getWarehouseid()));
				sheets[j].addCell(new Label(1, row, p.getWarehourseidname()));
				sheets[j].addCell(new Label(2, row, p.getSortName()));
				sheets[j].addCell(new Label(3, row, p.getNccode()));
				sheets[j].addCell(new Label(4, row, p.getPsproductname()));
				sheets[j].addCell(new Label(5, row, p.getPsspecmode()));
				sheets[j].addCell(new Label(6, row, p.getBatch()));
				// sheets[j].addCell(new Label(2, row, p.getProductid()));
				sheets[j].addCell(new Label(7, row, HtmlSelect.getResourceName(request,
						"CountUnit", Constants.DEFAULT_UNIT_ID)));
				sheets[j].addCell(new Number(8, row, p.getAllstockpile(), QWCF));
				sheets[j].addCell(new Label(9, row, DateUtil.formatDate(DateUtil.formatStrDate(p
						.getProductionDate()))));
				sheets[j].addCell(new Label(10, row, DateUtil.formatDate(DateUtil.formatStrDate(p
						.getExpiryDate()))));
				sheets[j].addCell(new Label(11, row, p.getRemark()));
				// if (isShowAssistQuantity != null) {
				// sheets[j].addCell(new Number(9, row, p.getXnum(), QWCF));
				// sheets[j].addCell(new Number(10, row, p.getScatterNum(),
				// QWCF));
				// }

				// if (isShowStatus != null) {
				// if (isShowAssistQuantity != null) {
				// sheets[j].addCell(new Label(11, row, p.getVerifyStatus() ==
				// null ? "" : p
				// .getVerifyStatus() == 1 ? "合格" : "待检验"));
				// } else {
				// sheets[j].addCell(new Label(9, row, p.getVerifyStatus() ==
				// null ? "" : p
				// .getVerifyStatus() == 1 ? "合格" : "待检验"));
				// }
				// }

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}

	private String getWarehouseId(List wlist) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wlist.size(); i++) {
			Warehouse w = (Warehouse) wlist.get(i);
			if (i == 0) {
				sb.append("'").append(w.getId()).append("'");
			} else {
				sb.append(",'").append(w.getId()).append("'");
			}
		}
		return sb.toString();
	}
}
