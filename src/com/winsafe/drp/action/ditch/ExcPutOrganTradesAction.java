package com.winsafe.drp.action.ditch;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class ExcPutOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String isshow = request.getParameter("isshow");
		super.initdata(request);
		try {
			String Condition = "";
			if (isshow.equals("yes")) {
				Condition = "ot.isaudit = 1 and ot.porganid = '"
						+ users.getMakeorganid() + "'";
			} else {
				Condition = "ot.makeorganid = '" + users.getMakeorganid() + "'";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "OrganTrades" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOrganTrades appS = new AppOrganTrades();
			List<OrganTrades> lists = appS.getOrganTradesAll(whereSql);

			if (lists.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListOrganTrades.xls");
			response.setContentType("application/msexcel");
			writeXls(lists, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 4, "渠道管理>>导出渠道换货!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<OrganTrades> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		UsersService us = new UsersService();

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
			sheets[j].addCell(new Label(0, start, "渠道换货", wchT));
			sheets[j].addCell(new Label(0, start + 1, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start + 1, request
					.getParameter("oname")));
			sheets[j].addCell(new Label(2, start + 1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request
					.getParameter("deptname")));
			sheets[j].addCell(new Label(4, start + 1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request
					.getParameter("uname")));

			sheets[j].addCell(new Label(0, start + 2, "是否复核:", seachT));
			sheets[j].addCell(new Label(1, start + 2, HtmlSelect
					.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(2, start + 2, "是否签收:", seachT));
			sheets[j].addCell(new Label(3, start + 2, HtmlSelect
					.getNameByOrder(request, "YesOrNo", getInt("IsReceive"))));
			sheets[j].addCell(new Label(4, start + 2, "供货机构:", seachT));
			sheets[j].addCell(new Label(5, start + 2, request
					.getParameter("POrganName")));

			sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 3, request.getAttribute(
					"porganname").toString()));
			sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 3, request.getAttribute(
					"pusername").toString()));
			sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 3, DateUtil
					.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 4, "编号", wcfFC));
			sheets[j].addCell(new Label(1, start + 4, "供货机构,wcfFC"));
			sheets[j].addCell(new Label(2, start + 4, "联系人", wcfFC));
			sheets[j].addCell(new Label(3, start + 4, "制单机构", wcfFC));
			sheets[j].addCell(new Label(4, start + 4, "制单人", wcfFC));
			sheets[j].addCell(new Label(5, start + 4, "制单日期", wcfFC));
			sheets[j].addCell(new Label(6, start + 4, "是否复核", wcfFC));
			sheets[j].addCell(new Label(7, start + 4, "是否批准", wcfFC));
			sheets[j].addCell(new Label(8, start + 4, "是否确认", wcfFC));
			sheets[j].addCell(new Label(9, start + 4, "是否签收", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				OrganTrades p = (OrganTrades) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, p.getPorganname()));
				sheets[j].addCell(new Label(2, row, p.getPlinkman()));
				sheets[j].addCell(new Label(3, row, organs.getOrganName(p
						.getMakeorganid())));
				sheets[j].addCell(new Label(4, row, us.getUsersName(p
						.getMakeid())));
				sheets[j].addCell(new Label(5, row, Dateutil.formatDate(p
						.getMakedate())));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsaudit());
				sheets[j].addCell(new Label(6, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsratify());
				sheets[j].addCell(new Label(7, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsaffirm());
				sheets[j].addCell(new Label(8, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsreceive());
				sheets[j].addCell(new Label(9, row, IsStr));

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}

}