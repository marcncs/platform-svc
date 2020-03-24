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

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWlmIdcodeLog;
import com.winsafe.drp.dao.WlmIdcodeLog;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutWlmIdcodeLogAction extends BaseAction {

	private OrganService organService = new OrganService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.initdata(request);
		try {
			// String Condition = " ( makeid=" + users.getUserid()
			// + this.getOrVisitOrgan("makeorganid") + ") ";
			String Condition = "  makeorganid in (select uv.visitorgan from  UserVisit as uv where  uv.userid="
					+ userid + ")  ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "WlmIdcodeLog" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap,"ID", "WlmIdcode", "WarehouseID", "ProductID",
					"ProductName", "SpecMode", "MakeID", "MakeDate");

			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppWlmIdcodeLog asb = new AppWlmIdcodeLog();
			List pils = asb.getWlmIdcodeLog(whereSql);

			if (pils.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

			String fname = "idcodelog";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=" + fname + ".xls");
			response.setContentType("application/msexcel");
			testWrite(request, pils, os);
			DBUserLog.addUserLog(request,"列表");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void testWrite(HttpServletRequest request, List list, OutputStream os) throws Exception {
		UsersService us = new UsersService();
		OrganService organs = new OrganService();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(os);

		int snum = 1;
		int rowssize = 50000;
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

			sheets[j].mergeCells(0, start, 15, start);
			sheets[j].addCell(new Label(0, start, "产品追溯报表  ", wchT));
			sheets[j].addCell(new Label(0, start + 1, "查询机构：", seachT));
			sheets[j].addCell(new Label(1, start + 1, request.getParameter("oname")));
			sheets[j].addCell(new Label(2, start + 1, "查询人:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request.getParameter("uname")));
			sheets[j].addCell(new Label(4, start + 1, "查询日期:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request.getParameter("BeginDate") + " - " + request.getParameter("EndDate")));

			sheets[j].addCell(new Label(0, start + 2, "查询分类：", seachT));
			sheets[j].addCell(new Label(1, start + 2, request.getParameter("QuerySort") == "" ? "" : HtmlSelect.getNameByOrder(request,"WlmQuerySort",Integer.valueOf(request.getParameter("QuerySort")))));
			sheets[j].addCell(new Label(2, start + 2, "关键字:", seachT));
			sheets[j].addCell(new Label(3, start + 2, request.getParameter("KeyWord")));

			sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 3, users.getMakeorganid() == null ? null
					: organs.getOrganName(users.getMakeorganid())));
			sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 3, users.getLoginname()));
			sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 3, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 4, "查询编号", wcfFC));
			sheets[j].addCell(new Label(1, start + 4, "查询码", wcfFC));
			sheets[j].addCell(new Label(2, start + 4, "箱码", wcfFC));
			sheets[j].addCell(new Label(3, start + 4, "查询机构", wcfFC));
			sheets[j].addCell(new Label(4, start + 4, "查询省份", wcfFC));
			sheets[j].addCell(new Label(5, start + 4, "产品编号", wcfFC));
			sheets[j].addCell(new Label(6, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(7, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(8, start + 4, "查询时间", wcfFC));
			sheets[j].addCell(new Label(9, start + 4, "查询人", wcfFC));
			sheets[j].addCell(new Label(10, start + 4, "查询分类", wcfFC));
			sheets[j].addCell(new Label(11, start + 4, "实际销售机构", wcfFC));

			for (int i = start; i < currentnum; i++) {
				int row = i - start + 5;
				WlmIdcodeLog ul = (WlmIdcodeLog) list.get(i);
				sheets[j].addCell(new Label(0, row, ul.getId() == null ? "" : ul.getId().toString()));
				sheets[j].addCell(new Label(1, row, ul.getWlmidcode() == "" ? "" : ul.getWlmidcode()));
				sheets[j].addCell(new Label(2, row, ul.getCartoncode() == "" ? "" : ul.getCartoncode()));
				sheets[j].addCell(new Label(3, row, ul.getMakeorganid() == "" ? "" : organs.getOrganName(ul.getMakeorganid())));
				sheets[j].addCell(new Label(4, row, ""));
				sheets[j].addCell(new Label(5, row, ul.getProductid() == "" ? "" : ul.getProductid()));
				sheets[j].addCell(new Label(6, row, ul.getProductname() == "" ? "" : ul.getProductname()));
				sheets[j].addCell(new Label(7, row, ul.getSpecmode() == "" ? "" : ul.getSpecmode()));
				sheets[j].addCell(new Label(8, row, ul.getMakedate() == null ? "" : sdf.format(ul.getMakedate())));
				sheets[j].addCell(new Label(9, row, ul.getMakeid() == null ? "" : us.getUsersName(ul.getMakeid())));
				sheets[j].addCell(new Label(10, row, ul.getQuerysort() == null ? "" : HtmlSelect.getNameByOrder(request,"WlmQuerySort",ul.getQuerysort() == null ? 0 : ul.getQuerysort())));
				sheets[j].addCell(new Label(11, row, ul.getOrganid() == "" ? "" : organService.getOrganName(ul.getOrganid())));
			}
		}

		workbook.write();
		workbook.close();
		os.close();
	}
}
