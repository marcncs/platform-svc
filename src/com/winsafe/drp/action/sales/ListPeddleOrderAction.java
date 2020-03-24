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
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPeddleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		super.initdata(request);
		try {
			String Condition = " (so.makeid="+userid+" "+getOrVisitOrgan("so.makeorganid")+getOrVisitOrgan("so.equiporganid")+" ) and so.sosort=0 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleOrder"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");

			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSaleOrder asl = new AppSaleOrder();
			List pils = asl.searchSaleOrder(request, pagesize, whereSql);
			
			
			
			AppInvoiceConf aic = new AppInvoiceConf();
			List ils = aic.getAllInvoiceConf();
			ArrayList ivls = new ArrayList();
			for (int u = 0; u < ils.size(); u++) {
				InvoiceConf ic = (InvoiceConf) ils.get(u);
				ivls.add(ic);
			}
			request.setAttribute("ivls", ivls);
			request.setAttribute("also", pils);
			
			DBUserLog.addUserLog(userid, 6,"列表零售单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
