package com.winsafe.drp.action.assistant;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFleeProduct;
import com.winsafe.drp.dao.FleeProduct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutFleeProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.initdata(request);

		try {

			// String Condition = " ( makeid=" + users.getMakedeptid()
			// + this.getOrVisitOrgan("makeorganid") + ") ";

			String Condition = " ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "FleeProduct" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "QueryID", "productid", "specmode", "cartoncode");

			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppFleeProduct asb = new AppFleeProduct();
			List pils = asb.getFleeProduct(whereSql);

			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

			String fname = "fleeproduct";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=" + fname + ".xls");
			response.setContentType("application/msexcel");
			testWrite(request, pils, os);
			DBUserLog.addUserLog(request, "[列表]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void testWrite(HttpServletRequest request, List list, OutputStream os) throws Exception {
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(os);

		int snum = 1;
		int rowssize = 50000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OrganService organService = new OrganService();
		snum = list.size() / rowssize;
		if (list.size() % rowssize >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			sheets[j].setRowView(0, false);
			sheets[j].getSettings().setDefaultColumnWidth(20);

			int currentnum = (j + 1) * rowssize;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * rowssize;
			sheets[j].mergeCells(0, start, 13, start); 
			sheets[j].addCell(new Label(0, start, "异地销售报表  ", wchT));
			sheets[j].addCell(new Label(0, start + 1, "查询机构：", seachT));
			sheets[j].addCell(new Label(1, start + 1, StringUtil.isEmpty(request.getParameter("oname")) ? "" : ESAPIUtil.decodeForHTML(request.getParameter("oname").toString())));
			sheets[j].addCell(new Label(2, start + 1, "是否查询:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request.getParameter("IsDeal") == "" ? "" : HtmlSelect.getNameByOrder(
					request, "YesOrNo", Integer.valueOf(request.getParameter("IsDeal")))));
			sheets[j].addCell(new Label(4, start + 1, "关键字:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start + 2, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 2, ESAPIUtil.decodeForHTML(request.getAttribute("porganname").toString())));
			sheets[j].addCell(new Label(2, start + 2, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start + 2, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 2, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 3, "查询号", wcfFC));
			sheets[j].addCell(new Label(1, start + 3, "箱码", wcfFC));
			sheets[j].addCell(new Label(2, start + 3, "申请机构", wcfFC));
			sheets[j].addCell(new Label(3, start + 3, "一级机构", wcfFC));
			sheets[j].addCell(new Label(4, start + 3, "二级机构", wcfFC));
			sheets[j].addCell(new Label(5, start + 3, "产品名称", wcfFC));
			sheets[j].addCell(new Label(6, start + 3, "规格", wcfFC));
			sheets[j].addCell(new Label(7, start + 3, "发货日期", wcfFC));
			sheets[j].addCell(new Label(8, start + 3, "是否查询", wcfFC));
			sheets[j].addCell(new Label(9, start + 3, "查询时间", wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 4;
				FleeProduct ul = (FleeProduct) list.get(i);
				sheets[j].addCell(new Label(0, row, ul.getQueryid() == "" ? "" : ul.getQueryid()));
				sheets[j].addCell(new Label(1, row, ul.getCartoncode() == "" ? "" : ul.getCartoncode()));
				sheets[j].addCell(new Label(2, row, ul.getMakeorganid() == "" ? "" : organService.getOrganName(ul
						.getMakeorganid())));
				sheets[j].addCell(new Label(3, row, ul.getFirstorgan() == "" ? "" : organService.getOrganName(ul
						.getFirstorgan())));
				sheets[j].addCell(new Label(4, row, ul.getSecondorgan() == "" ? "" : organService.getOrganName(ul
						.getSecondorgan())));
				sheets[j].addCell(new Label(5, row, ul.getProductname() == "" ? "" : ul.getProductname()));
				sheets[j].addCell(new Label(6, row, ul.getSpecmode() == "" ? "" : ul.getSpecmode()));
				sheets[j].addCell(new Label(7, row, DateUtil.formatDate(ul.getDeliverdate()) == null ? null : DateUtil
						.formatDate(ul.getDeliverdate())));
				if (ul.getIsdeal() != null) {
					sheets[j].addCell(new Label(8, row, ul.getIsdeal() == null ? null : HtmlSelect.getNameByOrder(
							request, "YesOrNo", ul.getIsdeal())));
				}
				sheets[j].addCell(new Label(9, row, ul.getMakedate() == null ? null : sdf.format(ul.getMakedate())));
			}
		}

		workbook.write();
		workbook.close();
		os.close();
	}

}
