package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppContactDitch;
import com.winsafe.drp.dao.AppDitch;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ContactDitch;
import com.winsafe.drp.dao.ContactDitchForm;
import com.winsafe.hbm.util.Internation;

public class ContactDitchDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = Long.valueOf(request.getParameter("id"));
		// Long cid = Long.valueOf((String)
		// request.getSession().getAttribute("cid"));
		
//		Long userid = users.getUserid();
		AppUsers au = new AppUsers();
		AppDitch ad = new AppDitch();
		ContactDitchForm clf = new ContactDitchForm();
		AppContactDitch acl = new AppContactDitch();

		try {
			//HibernateUtil.currentSession();

			ContactDitch cl = acl.getContactDitch(id);
			clf.setId(cl.getId());
			clf.setDidname(ad.getDitchByID(Long.valueOf(cl.getDid()))
					.getDname());
			clf.setContactdate(String.valueOf(cl.getContactdate()));
			clf.setContactmodename(Internation.getStringByKeyPosition(
					"ContactMode", request, cl.getContactmode(),
					"global.sys.SystemResource"));
			clf.setContactpropertyname(Internation.getStringByKeyPosition(
					"ContactProperty", request, cl.getContactproperty(),
					"global.sys.SystemResource"));
			clf.setContactcontent(cl.getContactcontent());
			clf.setFeedback(cl.getFeedback());
			clf.setLinkman(cl.getLinkman());
			clf.setNextcontact(String.valueOf(cl.getNextcontact()));
			clf.setNextgoal(cl.getNextgoal());
//			clf.setUseridname(au.getUsersByid(cl.getUserid()).getRealname());

			request.setAttribute("clf", clf);

//			DBUserLog.addUserLog(userid, "渠道联系记录详情,编号：" + id);
			return mapping.findForward("detail");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
