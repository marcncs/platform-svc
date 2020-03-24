package com.winsafe.drp.action.sales;

import java.io.OutputStream;
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
import com.winsafe.drp.dao.AppIntegralDetail;
import com.winsafe.drp.dao.IntegralDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutIntegralDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {
			request.setAttribute("OID", request.getParameter("OID"));
			request.setAttribute("OSort", request.getParameter("OSort"));
			request.setAttribute("BillNo", request.getParameter("BillNo"));
			request.setAttribute("IID", request.getParameter("IID"));

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IntegralDetail" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");

			whereSql = whereSql + blur;// + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppIntegralDetail aii = new AppIntegralDetail();

			List<IntegralDetail> idls = aii.getIntegralDetail(whereSql);

			if (idls.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListIntegralOrder.xls");
			response.setContentType("application/msexcel");
			writeXls(idls, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 6, "零售管理>>导出积分换购!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List<IntegralDetail> list, OutputStream os,
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
			sheets[j].addCell(new Label(0, start, "积分明细",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "类别:", seachT));
			String Sort = HtmlSelect.getResourceName(request, "DSort", getInt("ISort"));
			sheets[j].addCell(new Label(1, start+1, Sort));
			
			
			sheets[j].addCell(new Label(0, start+2, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+2, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+2, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+2, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start+3, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+3, "单据号",wcfFC));
			sheets[j].addCell(new Label(2, start+3, "积分类别",wcfFC));
			sheets[j].addCell(new Label(3, start+3, "积分",wcfFC));
			sheets[j].addCell(new Label(4, start+3, "制单日期",wcfFC));
			sheets[j].addCell(new Label(5, start+3, "配送机构",wcfFC));
			sheets[j].addCell(new Label(6, start+3, "所属机构",wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 4;
				IntegralDetail p = (IntegralDetail) list.get(i);
				sheets[j].addCell(new Label(0, row, p.getId().toString()));
				sheets[j].addCell(new Label(1, row, p.getBillno()));
				sheets[j].addCell(new Label(2, row, HtmlSelect.getResourceName(request, "DSort", p.getIsort())));
				sheets[j].addCell(new Number(3, row, p.getIntegral()));
				sheets[j].addCell(new Label(4, row, Dateutil.formatDate(p
						.getMakedate())));
				sheets[j].addCell(new Label(5, row, organs.getOrganName(p.getEquiporganid())));
				sheets[j].addCell(new Label(6, row, organs.getOrganName(p.getOrganid())));

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
