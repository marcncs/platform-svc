package com.winsafe.drp.action.ditch;

import java.io.OutputStream;
import java.util.List;

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
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class ExcPutSupplySaleApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String ISAUDIT = request.getParameter("ISAUDIT");
		try {
			String Condition = "";
			if (ISAUDIT.equals("no")) {
				Condition = "ssa.makeorganid = '" + users.getMakeorganid()
						+ "'";
			} else {
				Condition = "ssa.outorganid = '" + users.getMakeorganid()
						+ "' and ssa.isaudit =1";
			}

			String[] tablename = { "SupplySaleApply" };
			String whereSql = getWhereSql2(tablename);
			String timeCondition = getTimeCondition(" MoveDate");
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppSupplySaleApply appS = new AppSupplySaleApply();
			List<SupplySaleApply> list = appS.getSupplySaleApplyAll(whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListSupplySaleApply.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 4, "渠道管理>>导出代销申请!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<SupplySaleApply> list, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organs = new OrganService();
		UsersService us = new UsersService();
		WarehouseService ws = new WarehouseService();

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

			sheets[j].mergeCells(0, start, 8, start);
			sheets[j].addCell(new Label(0, start, "代销申请", wchT));
			sheets[j].addCell(new Label(0, start + 1, "制单机构:", seachT));
			sheets[j].addCell(new Label(1, start + 1, request
					.getParameter("oname")));
			sheets[j].addCell(new Label(2, start + 1, "制单部门:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request
					.getParameter("deptname")));
			sheets[j].addCell(new Label(4, start + 1, "制单人:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request
					.getParameter("uname")));

			sheets[j].addCell(new Label(0, start + 2, "需求日期:", seachT));
			sheets[j].addCell(new Label(1, start + 2, request
					.getParameter("BeginDate")
					+ "-" + request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start + 2, "是否复核:", seachT));
			sheets[j].addCell(new Label(3, start + 2, HtmlSelect
					.getNameByOrder(request, "YesOrNo", getInt("IsAudit"))));
			sheets[j].addCell(new Label(4, start + 2, "是否批准:", seachT));
			sheets[j].addCell(new Label(5, start + 2, HtmlSelect
					.getNameByOrder(request, "YesOrNo", getInt("IsRatify"))));

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
			sheets[j].addCell(new Label(1, start + 4, "需求日期", wcfFC));
			sheets[j].addCell(new Label(2, start + 4, "调出机构", wcfFC));
			sheets[j].addCell(new Label(3, start + 4, "调入机构", wcfFC));
			sheets[j].addCell(new Label(4, start + 4, "调入仓库", wcfFC));
			sheets[j].addCell(new Label(5, start + 4, "是否复核", wcfFC));
			sheets[j].addCell(new Label(6, start + 4, "是否批准", wcfFC));
			sheets[j].addCell(new Label(7, start + 4, "是否作废", wcfFC));
			sheets[j].addCell(new Label(8, start + 4, "制单人", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				SupplySaleApply p = (SupplySaleApply) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId()));
				sheets[j].addCell(new Label(1, row, DateUtil.formatDate(p
						.getMovedate())));
				String outorgan = organs.getOrganName(p.getOutorganid());
				sheets[j].addCell(new Label(2, row, outorgan));
				outorgan = organs.getOrganName(p.getMakeorganid());
				sheets[j].addCell(new Label(3, row, outorgan));
				String inwarehouse = ws.getWarehouseName(p.getInwarehouseid());
				sheets[j].addCell(new Label(4, row, inwarehouse));
				String IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsaudit());
				sheets[j].addCell(new Label(5, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsratify());
				sheets[j].addCell(new Label(6, row, IsStr));
				IsStr = HtmlSelect.getNameByOrder(request, "YesOrNo", p
						.getIsblankout());
				sheets[j].addCell(new Label(7, row, IsStr));
				String makeuser = us.getUsersName(p.getMakeid());
				sheets[j].addCell(new Label(8, row, makeuser));

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
