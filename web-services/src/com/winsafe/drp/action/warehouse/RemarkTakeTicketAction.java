package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ReturnPage;

public class RemarkTakeTicketAction  extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String id = request.getParameter("id");
		if(StringUtil.isEmpty(id)){
			return ReturnPage.getReturn(request, "1", "单据不存在！");
		}

		String remark = request.getParameter("remark");

//		AppTakeTicket appTT=new AppTakeTicket();
//		TakeTicket tt=appTT.getTakeTicketById(id);
		AppTakeTicketDetail appTTD=new AppTakeTicketDetail();
		TakeTicketDetail tt=appTTD.getTakeTicketDetailByID(Integer.parseInt(id));
		if(tt==null){
			return ReturnPage.getReturn(request, "1", "单据不存在！");
		}
		tt.setRemark(remark);
		appTTD.addTakeTicketDetail(tt);
			
		DBUserLog.addUserLog(users.getUserid(), 7, "修改备注");
		return ReturnPage.getReturn(request, "0", "修改成功！");

	}
}
