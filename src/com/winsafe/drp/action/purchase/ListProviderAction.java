package com.winsafe.drp.action.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListProviderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {

			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = " or instr(p.makeorganid, '"
						+ users.getVisitorgan() + "')>=1";
			}

			String Condition = " (p.makeid=" + userid + " " + visitorgan
					+ ") and p.isdel=0";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Provider" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "PName", "Tel",
					"Mobile");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProvider ap = new AppProvider();
			List apls = ap.getProvider(request, pagesize, whereSql);

			request.setAttribute("alls", apls);

			request.setAttribute("MakeOrganID", request
					.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("MakeDeptID", request
					.getParameter("MakeDeptID"));
			request.setAttribute("Genre", request.getParameter("Genre"));
			request.setAttribute("AbcSort", request.getParameter("AbcSort"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			return mapping.findForward("listprovide");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
