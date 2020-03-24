package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.hbm.util.Internation;

public class ToAddOutlayAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

	      String outlayprojectselect = Internation.getSelectTagByKeyAllDB("OutlayProject","OutlayProject", false);
	      
//	         AppDept ad = new AppDept();
//	  	  List dls = ad.getDept();
//	      ArrayList aldept = new ArrayList();
//	      for(int i=0;i<dls.size();i++){
//	     	 Dept d = new Dept();
//	     	 Object[] ob = (Object[]) dls.get(i);
//	     	 d.setId(Long.valueOf(ob[0].toString()));
//	     	 d.setDeptname(ob[1].toString());
//	     	 aldept.add(d);
//	      }
	      
//	      AppUsers au = new AppUsers();
//	      List uls = au.getIDAndLoginName();
//	      ArrayList als = new ArrayList();
//	      for(int u=0;u<uls.size();u++){
//	      	UsersBean us = new UsersBean();
//	      	Object[] ub = (Object[]) uls.get(u);
//	      	us.setUserid(Long.valueOf(ub[0].toString()));
//	      	us.setRealname(ub[2].toString());
//	      	als.add(us);
//	      }
	      
	      AppCashBank apcb = new AppCashBank();
	      List cblist = apcb.getAllCashBank();

	      request.setAttribute("outlayprojectselect", outlayprojectselect);
//	      request.setAttribute("aldept", aldept);
	      request.setAttribute("cblist", cblist);
//	      request.setAttribute("als", als);
	      request.setAttribute("userid", userid);
	      return mapping.findForward("toadd");
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
