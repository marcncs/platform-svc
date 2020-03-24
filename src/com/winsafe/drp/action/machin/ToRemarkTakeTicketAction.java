package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.util.ReturnPage;
import com.winsafe.hbm.util.RequestTool;

public class ToRemarkTakeTicketAction  extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String id = request.getParameter("id");
		if(StringUtil.isEmpty(id)){
			return ReturnPage.getReturn(request, "1", "单据不存在！");
		}

		AppTakeTicketDetail appTTD=new AppTakeTicketDetail();
		TakeTicketDetail tt=appTTD.getTakeTicketDetailByID(Integer.parseInt(id));
		if(tt==null){
			return ReturnPage.getReturn(request, "1", "单据不存在！");
		}

		request.setAttribute("tt", tt);

		return mapping.findForward("toupd");

	}
}
