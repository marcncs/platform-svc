package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWebIndentDetail;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.WebIndentDetail;
import com.winsafe.drp.dao.WebIndentDetailForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class WebIndentDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = " and so.equiporganid in(" + users.getVisitorgan()+ ")";
			}
			String Condition = " so.id=sod.woid and so.isendcase=1 and so.isblankout=0 "
					+ visitorgan;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "WebIndent"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String brur = DbUtil.getOrBlur(map, tmpMap, "CName", "CID",
					"ProductID", "ProductName", "SpecMode");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request,
					"WebIndent as so, WebIndentDetail as sod ", whereSql,
					pagesize, "WebIndentDetailReport");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppWebIndentDetail asod = new AppWebIndentDetail();
			double totalsum = asod.getTotalSubum(whereSql);

			List sodls = asod.getWebIndentDetail(pagesize, whereSql, tmpPgInfo);
			ArrayList alsod = new ArrayList();
			Double subsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				WebIndentDetailForm sodf = new WebIndentDetailForm();
				Object[] o = (Object[])sodls.get(d);
				String cname = (String)o[0];
				WebIndentDetail od = (WebIndentDetail) o[1];
				sodf.setCname(cname);
				sodf.setWoid(od.getWoid());
				sodf.setProductid(od.getProductid());
				sodf.setProductname(od.getProductname());
				sodf.setSpecmode(od.getSpecmode());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", od.getUnitid().intValue()));
				sodf.setUnitprice(od.getTaxunitprice());
				sodf.setQuantity(od.getQuantity());
				sodf.setSubsum(od.getSubsum());
				subsum += sodf.getSubsum();
				alsod.add(sodf);
			}
			request.setAttribute("subsum", DataFormat.currencyFormat(subsum));
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			request.setAttribute("alsod", alsod);

		
			AppInvoiceConf aic = new AppInvoiceConf();
			List uls = aic.getAllInvoiceConf();
			ArrayList icls = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				InvoiceConf ic = (InvoiceConf) uls.get(u);

				icls.add(ic);
			}

			AppOrgan ao = new AppOrgan();
			List alos = ao.getOrganToDown(users.getMakeorganid());

			request.setAttribute("alos", alos);
			request.setAttribute("EquipOrganID", request.getParameter("EquipOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("CName", request.getParameter("CName"));
			request.setAttribute("icls", icls);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
