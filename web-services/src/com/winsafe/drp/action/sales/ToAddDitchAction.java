package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddDitchAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			
			AppUsers au = new AppUsers();
			
			String ditchrankname = Internation.getSelectTagByKeyAllDB(
					"DitchRank", "ditchrank", false);
			String prestigename = Internation.getSelectTagByKeyAllDB("Prestige",
					"prestige", false);
			String vocationname = Internation.getSelectTagByKeyAllDB("Vocation",
					"vocation", false);

			UsersBean us = UserManager.getUser(request);
			AppCountryArea aca = new AppCountryArea();
			List list = aca.getProvince();//只能在该用户负责的区域添加
			ArrayList cals = new ArrayList();

			for (int i = 0; i < list.size(); i++) {
				CountryArea ca = new CountryArea();
				Object ob[] = (Object[]) list.get(i);
//				ca.setId(Long.valueOf(ob[0].toString()));
				ca.setAreaname(ob[1].toString());
//				ca.setParentid(Long.valueOf(ob[2].toString()));
				ca.setRank(Integer.valueOf(ob[3].toString()));
				cals.add(ca);
			}

//			List list1 = aca.getProvince(us.getUserid());
//			ArrayList cls = new ArrayList();
//
//			for (int i = 0; i < list1.size(); i++) {
//				CountryArea ca = new CountryArea();
//				Object ob[] = (Object[]) list1.get(i);
//				ca.setId(Long.valueOf(ob[0].toString()));
//				ca.setAreaname(ob[1].toString());
//				ca.setParentid(Long.valueOf(ob[2].toString()));
//				ca.setRank(Integer.valueOf(ob[3].toString()));
//				cls.add(ca);
//			}
			
			//AppUsers au = new AppUsers();
		      List uls = au.getIDAndLoginName();
		      ArrayList als = new ArrayList();
		      for(int u=0;u<uls.size();u++){
		      	UsersBean ubs = new UsersBean();
		      	Object[] ub = (Object[]) uls.get(u);
//		      	ubs.setUserid(Long.valueOf(ub[0].toString()));
		      	ubs.setRealname(ub[2].toString());
		      	als.add(ubs);
		      }

			request.setAttribute("ditchrankname", ditchrankname);
			request.setAttribute("prestigename", prestigename);
			request.setAttribute("vocationname", vocationname);
			request.setAttribute("als", als);
			request.setAttribute("cals", cals);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
