package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppApproveFlowService;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.ApproveFlowLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class ApproveSuitAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		String approvecontent = request.getParameter("approvecontent");
		String billno = request.getParameter("billno");
		Integer aflid = Integer.valueOf(request.getParameter("aflid"));		
		int approve = Integer.parseInt(request.getParameter("ApproveStatus"));

		

		try {
			
			AppApproveFlowService apfs = new AppApproveFlowService();
			ApproveFlowLog afl = apfs.getApproveFlowLog(aflid);
			afl.setApprovecontent(approvecontent);
			afl.setApprovedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			afl.setApprove(approve);
			afl.setOperate(0);
			int supperarrove = apfs.UpdateApproveFlowLog(afl);
			
			AppSuit apb = new AppSuit();		
			 apb.updIsApprove(billno, supperarrove);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, "审阅抱怨投诉");
			return mapping.findForward("approve");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
