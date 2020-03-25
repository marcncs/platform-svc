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

public class CancelDealSuitAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String sid =request.getParameter("SID");
			AppSuit ast = new AppSuit();
			Suit s = ast.getSuitByID(sid);

			if (s.getIsdeal() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (!String.valueOf(s.getDealuser()).contains(userid.toString())) {
				request.setAttribute("result", "databases.record.cancelaudit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			s.setIsdeal(0);
			s.setDealway(0);
			s.setDealcontent("");
			s.setDealfinal("");
			s.setDealdate(null);
			s.setDealuser(Integer.valueOf(0));

			ast.updSuit(s);

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, "取消处理抱怨投诉,编号：" + sid);

			return mapping.findForward("nodeal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
