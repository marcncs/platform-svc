package com.winsafe.drp.action.common;

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
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class SelectRegionUserAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		String acode = request.getParameter("acode");
		try {
			String Condition = null;
			if (acode.length() == 3) {
				Condition = " u.status=1  and UserType=5  "; //大区经理
			}
			if (acode.length() == 5) {
				Condition = " u.status=1   and UserType=4  "; // 办事处经理
			} 
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Users" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "UserID", "RealName","NCcode");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppUsers ap = new AppUsers();
			List pls = ap.getUsers(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				UsersForm uf = new UsersForm();
				Users o = (Users) pls.get(i);
				uf.setUserid(o.getUserid());
				uf.setLoginname(o.getLoginname());
				uf.setRealname(o.getRealname());
				uf.setSexname(Internation.getStringByKeyPosition("Sex",request, o.getSex(), "global.sys.SystemResource"));
				sls.add(uf);
			}
			request.setAttribute("sls", sls);
			return mapping.findForward("success");
			// return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
