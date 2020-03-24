package com.winsafe.drp.keyretailer.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;

public class ToUpdSBonusAppraiseAction extends BaseAction {
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String accountId = request.getParameter("accountId");
			List list = appSBonusAppraise.getUpdSBonusAppraiseByAccountId(accountId);
			Map map = (Map)list.get(0);
			request.setAttribute("sba", map);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
