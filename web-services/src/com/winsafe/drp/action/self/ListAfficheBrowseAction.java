package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AfficheBrowseForm;
import com.winsafe.drp.dao.AppAfficheBrowse;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListAfficheBrowseAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean usersBean = UserManager.getUser(request);
		Integer userid = usersBean.getUserid();
		AppAfficheBrowse aab = new AppAfficheBrowse();
		AppUsers au = new AppUsers();

		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			
			String Condition = " ab.afficheid="+id+" ";
			String[] tablename = { "AfficheBrowse" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			whereSql = whereSql  +Condition; 
			//System.out.println("whereSql++++++++++++++++++++"+whereSql);
			whereSql = DbUtil.getWhereSql(whereSql); 

			List aaList = aab.getAfficheBrowse(whereSql);
			ArrayList afficheList = new ArrayList();
			for (int t = 0; t < aaList.size(); t++) {
				AfficheBrowseForm abf = new AfficheBrowseForm();
				Object[] ob = (Object[]) aaList.get(t);
				abf.setId(Integer.valueOf(ob[0].toString()));
				abf.setUseridname(au.getUsersByid(Integer.valueOf(ob[2].toString())).getRealname());
				abf.setIsbrowsename(Internation.getStringByKeyPosition(
						"YesOrNo", request, Integer.parseInt(ob[3]
								.toString()), "global.sys.SystemResource"));
				afficheList.add(abf);

			}

			request.setAttribute("aaList", afficheList);

		      DBUserLog.addUserLog(userid,0,"列表公告浏览者");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("list");
	}

}
