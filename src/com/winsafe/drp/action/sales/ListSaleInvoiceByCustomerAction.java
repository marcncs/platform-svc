package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.dao.SaleInvoiceForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListSaleInvoiceByCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;
		
//		Long userid = users.getUserid();
		try {
			String strCid = request.getParameter("CID");
			if (strCid == null) {
				strCid = (String) request.getSession().getAttribute("cid");
			}
			String cid = strCid;
			request.getSession().setAttribute("cid", strCid);

			String invoicetypeselect = "";
			invoicetypeselect = Internation.getSelectTagByKeyAll("InvoiceType",
					request, "InvoiceType", true, null);
			String isauditselect = "";
			isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
					request, "IsAudit", true, null);

			String Condition = "si.cid='" + cid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleInvoice" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "InvoiceCode"); 
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 			
			Object obj[] = DbUtil.setDynamicPager(request,
					"SaleInvoice as si ", whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppSaleInvoice asl = new AppSaleInvoice();
			List pils = asl.getSaleInvoice(pagesize, whereSql, tmpPgInfo);
			ArrayList alsi = new ArrayList();
			for (int i = 0; i < pils.size(); i++) {
				SaleInvoiceForm sif = new SaleInvoiceForm();
				Object[] o = (Object[]) pils.get(i);
//				sif.setId(Long.valueOf(o[0].toString()));
				sif.setInvoicecode(o[1].toString());
				sif.setInvoicetypename(Internation.getStringByKeyPosition(
						"InvoiceType", request, Integer.parseInt(o[2]
								.toString()), "global.sys.SystemResource"));
//				sif.setMakeinvoicedate((Date) o[3]);
//				sif.setInvoicedate((Date) o[4]);
				sif.setInvoicesum(Double.valueOf(o[5].toString()));
				sif.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, Integer.parseInt(o[6].toString()),
						"global.sys.SystemResource"));

				alsi.add(sif);
			}

			request.setAttribute("cid", cid);
			request.setAttribute("alsi", alsi);
			request.setAttribute("invoicetypeselect", invoicetypeselect);
			request.setAttribute("isauditselect", isauditselect);
//			DBUserLog.addUserLog(userid, "列表销售发票从客户");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
