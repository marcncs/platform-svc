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
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class PrintProviderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = this.getOrVisitOrgan("p.makeorganid");
			}

			String Condition = " (p.makeid=" + userid + " " + visitorgan
					+ ") and p.isdel=0";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Provider" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String blur = DbUtil.getOrBlur(map, tmpMap,"ID", "PName", "Tel",
					"Mobile"); 
			whereSql = whereSql + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppProvider ap = new AppProvider();
			List<Provider> apls = ap.finAll(whereSql);

			request.setAttribute("alls", apls);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
