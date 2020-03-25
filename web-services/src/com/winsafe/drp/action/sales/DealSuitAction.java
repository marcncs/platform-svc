package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.Suit;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class DealSuitAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String id = request.getParameter("id");
			AppSuit ast = new AppSuit();
			Suit s = ast.getSuitByID(id);

			s.setIsdeal(1);
			s.setDealway(Integer.valueOf(request.getParameter("dealway")));
			s.setDealcontent(request.getParameter("dealcontent"));
			s.setDealfinal(request.getParameter("dealfinal"));
			s.setDealdate(DateUtil.getCurrentDate());
			s.setDealuser(userid);

			ast.updSuit(s);
			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, 5, "抱怨投诉>>处理抱怨投诉,编号：" + id, s);

			return mapping.findForward("deal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
