package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDocSort;
import com.winsafe.drp.dao.DocSortVisit;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UsersService;

public class ToVisitDocSortAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			Integer dsid = Integer.valueOf(request.getParameter("ID"));
			UsersService us = new UsersService();
			List uls = us.getIDAndLoginNameByOID2(users.getMakeorganid());

			AppDocSort appDocSort = new AppDocSort();
			List<DocSortVisit> alrd = appDocSort.getDocSortVisitDSID(dsid);
			List<UsersBean> alls = new ArrayList<UsersBean>();
			UsersBean alub = null;
			for (int i = 0; i < alrd.size(); i++) {
				alub = new UsersBean();
				DocSortVisit ob = (DocSortVisit) alrd.get(i);
				alub.setUserid(ob.getUserid());
				alub.setLoginname(us.getUsersName(ob.getUserid()));
				alub.setRealname(us.getUsersName(ob.getUserid()));
				alls.add(alub);
			}

			request.setAttribute("dsid", dsid);
			request.setAttribute("alls", alls);
			request.setAttribute("auls", uls);

			return mapping.findForward("toselect");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
