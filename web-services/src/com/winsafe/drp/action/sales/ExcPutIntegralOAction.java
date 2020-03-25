package com.winsafe.drp.action.sales;

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

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppIntegralO;
import com.winsafe.drp.dao.IntegralO;
import com.winsafe.drp.dao.IntegralOForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutIntegralOAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {

			String Condition = " (" + getVisitOrgan("io.equiporganid")
					+ getOrVisitOrgan("io.organid") + ") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IntegralO" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "oid", "oname",
					"omobile", "billno");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			OrganService ao = new OrganService();
			AppCustomer ac = new AppCustomer();
			AppIntegralO aii = new AppIntegralO();

			List<IntegralO> iils = aii.getIntegralO(whereSql);
			List<IntegralOForm> ails = new ArrayList<IntegralOForm>();
			for (IntegralO o : iils) {
				IntegralOForm iif = new IntegralOForm();
				iif.setId(o.getId());
				iif.setOsort(o.getOsort());
				iif.setOid(o.getOid());
				if (iif.getOsort() == 0) {
					iif.setOname(ao.getOrganName(o.getOid()));
				}
				if (iif.getOsort() == 1) {
					iif.setOname(ac.getCustomer(o.getOid()).getCname());
				}
				iif.setOmobile(o.getOmobile());
				iif.setBillno(o.getBillno());
				iif.setIsort(o.getIsort());
				iif.setRout(o.getRout());
				iif.setAout(o.getAout());
				iif.setMakedate(o.getMakedate());
				iif.setEquiporganid(o.getEquiporganid());
				iif.setOrganid(o.getOrganid());
				ails.add(iif);

			}

			if (ails.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListInteralO.xls");
			response.setContentType("application/msexcel");
			writeXls(ails, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 6, "零售管理>>导出积分支出!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<IntegralOForm> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
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
			sheets[j].addCell(new Label(0, start, "积分收入", wchT));

			sheets[j].addCell(new Label(0, start + 1, "对象类型:", seachT));
			sheets[j].addCell(new Label(1, start + 1, HtmlSelect
					.getNameByOrder(request, "ObjectSort", getInt("OSort"))));
			sheets[j].addCell(new Label(2, start + 1, "选择对象:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request
					.getParameter("cname")));
			sheets[j].addCell(new Label(4, start + 1, "配送机构:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request
					.getParameter("ename")));

			sheets[j].addCell(new Label(0, start + 2, "所属机构:", seachT));
			sheets[j].addCell(new Label(1, start + 2, request
					.getParameter("oname")));
			sheets[j].addCell(new Label(2, start + 2, "制单日期:", seachT));
			sheets[j].addCell(new Label(3, start + 2, request
					.getParameter("BeginDate")
					+ "-" + request.getParameter("EndDate")));
			sheets[j].addCell(new Label(0, start + 2, "积分类别:", seachT));
			sheets[j].addCell(new Label(1, start + 2, HtmlSelect
					.getResourceName(request, "ISort", getInt("ISort"))));

			sheets[j].addCell(new Label(0, start + 3, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start + 3, request
					.getParameter("KeyWord")));

			sheets[j].addCell(new Label(0, start + 4, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 4, request.getAttribute(
					"porganname").toString()));
			sheets[j].addCell(new Label(2, start + 4, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 4, request.getAttribute(
					"pusername").toString()));
			sheets[j].addCell(new Label(4, start + 4, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 4, DateUtil
					.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 5, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start + 5, "对象类型", wcfFC));
			sheets[j].addCell(new Label(2, start + 5, "对象编号", wcfFC));
			sheets[j].addCell(new Label(3, start + 5, "对象名称", wcfFC));
			sheets[j].addCell(new Label(4, start + 5, "单据号", wcfFC));
			sheets[j].addCell(new Label(5, start + 5, "积分类别", wcfFC));
			sheets[j].addCell(new Label(6, start + 5, "应付", wcfFC));
			sheets[j].addCell(new Label(7, start + 5, "已付", wcfFC));
			sheets[j].addCell(new Label(8, start + 5, "制单日期", wcfFC));
			sheets[j].addCell(new Label(9, start + 5, "配送机构", wcfFC));
			sheets[j].addCell(new Label(10, start + 5, "所属机构", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 6;
				IntegralOForm p = (IntegralOForm) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, HtmlSelect.getNameByOrder(
						request, "ObjectSort", p.getOsort())));
				sheets[j].addCell(new Label(2, row, p.getOid()));
				sheets[j].addCell(new Label(3, row, p.getOname()));
				sheets[j].addCell(new Label(4, row, p.getBillno()));
				sheets[j].addCell(new Label(5, row, HtmlSelect.getResourceName(
						request, "ISort", p.getIsort())));
				sheets[j].addCell(new Number(6, row, p.getRout(), QWCF));
				sheets[j].addCell(new Number(7, row, p.getAout(), QWCF));
				sheets[j].addCell(new Label(8, row, DateUtil.formatDate(p
						.getMakedate())));
				sheets[j].addCell(new Label(9, row, organs.getOrganName(p
						.getEquiporganid())));
				sheets[j].addCell(new Label(10, row, organs.getOrganName(p
						.getOrganid())));

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
