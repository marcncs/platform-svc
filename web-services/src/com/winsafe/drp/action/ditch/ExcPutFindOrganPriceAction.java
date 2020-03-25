package com.winsafe.drp.action.ditch;

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
import com.winsafe.drp.dao.AppOrganPriceii;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 * @version : 2009-9-3 下午04:18:31 www.winsafe.cn
 */
public class ExcPutFindOrganPriceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String organsql = " and op.organid=o.id and o.sysid like '"
					+ users.getOrgansysid() + "%' ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product", "OrganPriceii" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String priceCondition = DbUtil.getPriceCondition(map, tmpMap,
					"UnitPrice");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductName",
					"PYCode", "SpecMode", "p.id");
			String strCondition = "( p.id=op.productid and p.useflag=1 "
					+ organsql + ")";
			whereSql = whereSql + leftblur + blur + priceCondition
					+ strCondition;
			whereSql = DbUtil.getWhereSql(whereSql);
			AppOrganPriceii appOpii = new AppOrganPriceii();
			List list = appOpii.findOrganPriceii(whereSql);
			if (list.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=FindOrganPrice.xls");
			response.setContentType("application/msexcel");
			writeXls(list, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(userid, 4, "渠道管理>>导出订购申请!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeXls(List list, OutputStream os, HttpServletRequest request)
			throws NumberFormatException, Exception {
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

			sheets[j].mergeCells(0, start, 5, start);
			sheets[j].addCell(new Label(0, start, "查价", wchT));
			sheets[j].addCell(new Label(0, start + 1, "产品类别:", seachT));
			sheets[j].addCell(new Label(1, start + 1, request
					.getParameter("psname")));
			sheets[j].addCell(new Label(2, start + 1, "价格范围:", seachT));
			sheets[j].addCell(new Label(3, start + 1, request
					.getParameter("BeginPrice")
					+ "-" + request.getParameter("EndPrice")));
			sheets[j].addCell(new Label(4, start + 1, "机构:", seachT));
			sheets[j].addCell(new Label(5, start + 1, request
					.getParameter("oname")));

			sheets[j].addCell(new Label(0, start + 2, "关键字:", seachT));
			sheets[j].addCell(new Label(1, start + 2, request
					.getParameter("KeyWord")));

			sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start + 3, request.getAttribute(
					"porganname").toString()));
			sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start + 3, request.getAttribute(
					"pusername").toString()));
			sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start + 3, DateUtil
					.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start + 4, "产品编号", wcfFC));
			sheets[j].addCell(new Label(1, start + 4, "产品名称", wcfFC));
			sheets[j].addCell(new Label(2, start + 4, "机构", wcfFC));			
			sheets[j].addCell(new Label(3, start + 4, "单位", wcfFC));
			sheets[j].addCell(new Label(4, start + 4, "规格", wcfFC));
			sheets[j].addCell(new Label(5, start + 4, "价格", wcfFC));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Map p = (Map) list.get(i);
				sheets[j].addCell(new Label(0, row, p.get("productid").toString()));
				sheets[j].addCell(new Label(1, row, p.get("productname")
						.toString()));
				String outorgan = organs.getOrganName(p.get("organid")
						.toString());
				sheets[j].addCell(new Label(2, row, outorgan));				
				String IsStr = HtmlSelect.getResourceName(request, "CountUnit",
						(Integer) p.get("unitid"));
				sheets[j].addCell(new Label(3, row, IsStr));
				sheets[j].addCell(new Label(4, row, p.get("specmode")
						.toString()));
				sheets[j].addCell(new Number(5, row, Double.valueOf(p.get(
				"unitprice").toString()), wcfN));

			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
