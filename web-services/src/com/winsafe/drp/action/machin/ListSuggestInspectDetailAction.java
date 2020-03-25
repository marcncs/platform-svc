package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppSuggestInspectDetail;
import com.winsafe.drp.util.DBUserLog;

public class ListSuggestInspectDetailAction extends BaseAction {
	AppSuggestInspectDetail asid = new AppSuggestInspectDetail();
	AppSuggestInspect asi = new AppSuggestInspect();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		String whereSql = "";
		try {
			String id = request.getParameter("id");
			whereSql = " where siid = ( select siid from SuggestInspect si where id = "+id+")";
			List pils = asid.getSuggestInspectDetailByPage(request, pagesize, whereSql);
			request.setAttribute("alpi", pils);

			DBUserLog.addUserLog(userid, 7,"拣货单详情");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
