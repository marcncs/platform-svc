package com.winsafe.drp.action.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectMemberLinkmanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		String strcid = request.getParameter("cid");
		if (strcid == null) {
			strcid = (String) request.getSession().getAttribute("slcid");
		}
		request.getSession().setAttribute("slcid", strcid);
		super.initdata(request);try{
			
			String Condition = " l.cid='" + strcid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Linkman" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "Name","Mobile","OfficeTel","Addr");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppLinkMan al = new AppLinkMan();
			List pls = al.searchLinkMan(request, pagesize, whereSql);



			request.setAttribute("sls", pls);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
