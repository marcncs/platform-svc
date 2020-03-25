package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppContactLog;
import com.winsafe.drp.dao.ContactLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;

public class UpdContactLogAction extends BaseAction {
	Logger logger = Logger.getLogger(UpdContactLogAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		AppContactLog appContactLog = new AppContactLog();
		
		try {

			ContactLog cl = null;
	
			Integer id  = Integer.valueOf(request.getParameter("id"));
			cl = appContactLog.getContactLog(id);
			ContactLog oldcl = (ContactLog) BeanUtils.cloneBean(cl);
			cl.setObjsort(Integer.valueOf(request.getParameter("objsort")));
			cl.setCid(RequestTool.getString(request, "cid"));
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
			cl.setMakeid(userid);
			cl.setMakeorganid(users.getMakeorganid());	
			cl.setMakedeptid(users.getMakedeptid());
			cl.setMakedate(DateUtil.getCurrentDate());
			
			appContactLog.updateContactLog(cl);

			DBUserLog.addUserLog(userid, 5,"会员/积分管理>>修改联系记录,编号："+cl.getCid(),oldcl,cl);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return mapping.getInputForward();
	}

}

