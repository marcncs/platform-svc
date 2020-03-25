package com.winsafe.drp.action.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIntegralDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintIntegralDetailAction extends BaseAction {

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

			List idls = aii.getIntegralDetail(whereSql);

			request.setAttribute("idls", idls);
			DBUserLog.addUserLog(userid,5, "积分收入>>列表积分明细");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
