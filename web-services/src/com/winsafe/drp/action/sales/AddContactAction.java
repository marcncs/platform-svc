package com.winsafe.drp.action.sales;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppContactLog;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.ContactLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddContactAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		initdata(request);
		AppContactLog appContactLog = new AppContactLog();

		try {

			ContactLog cl = new ContactLog();
			cl.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("contact_log", 0, "")));
			cl.setObjsort(Integer.valueOf(request.getParameter("objsort")));
			cl.setCid(request.getParameter("cid"));
			cl.setCname(request.getParameter("cname"));
			cl.setContactdate(DateUtil.StringToDate(request.getParameter("contactdate")));			
			cl.setContactmode(Integer.valueOf(request
					.getParameter("contactmode")));
			cl.setContactproperty(Integer.valueOf(request
					.getParameter("contactproperty")));
			cl.setContactcontent(request.getParameter("contactcontent"));
			cl.setFeedback(request.getParameter("feedback"));
			cl.setLinkman(request.getParameter("linkman"));
			
			cl.setNextcontact(DateUtil.StringToDate(request.getParameter("nextcontact")));
		
			cl.setNextgoal(request.getParameter("nextgoal"));
			
			cl.setMakeorganid(users.getMakeorganid());
			cl.setMakedeptid(users.getMakedeptid());
			cl.setMakeid(userid);
			cl.setMakedate(DateUtil.getCurrentDate());

			appContactLog.addContactLog(cl);

			AppCustomer ac = new AppCustomer();
			ac.updContactDate(cl.getCid(),DateUtil.formatDate(cl.getContactdate())
					,DateUtil.formatDate(cl.getNextcontact()));
			
			DBUserLog.addUserLog(userid,5, "会员/积分管理>>新增商务联系,编号：" + cl.getCid());

			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
