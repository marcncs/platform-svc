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
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListIntegralOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;


		super.initdata(request);
		try {
			
		
			String Condition = " (io.makeid="+userid+" "+getOrVisitOrgan("io.makeorganid")+ getOrVisitOrgan("io.equiporganid")+") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IntegralOrder"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");

			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppIntegralOrder asl = new AppIntegralOrder();
			List pils = asl.searchIntegralOrder(request,pagesize, whereSql);
			
			
			AppInvoiceConf aic = new AppInvoiceConf();
			List ils = aic.getAllInvoiceConf();
			ArrayList ivls = new ArrayList();
			for (int u = 0; u < ils.size(); u++) {
				InvoiceConf ic = (InvoiceConf) ils.get(u);
				ivls.add(ic);
			}
			request.setAttribute("ivls", ivls);
			
			request.setAttribute("also", pils);
			request.setAttribute("CID", request.getParameter("CID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("IsBlankOut", map.get("IsBlankOut"));
			request.setAttribute("IsEndcase", map.get("IsEndcase"));
			request.setAttribute("InvMsg", request.getParameter("InvMsg"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("BillType", request.getParameter("BillType"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid,6, "零售管理>>列表积分换购");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
