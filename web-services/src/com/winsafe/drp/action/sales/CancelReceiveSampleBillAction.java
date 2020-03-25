package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.util.DBUserLog;

public class CancelReceiveSampleBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String sbid = request.getParameter("SBID");
			AppSampleBill asb = new AppSampleBill();
			SampleBill sb = asb.findByID(sbid);
			
			sb.setRecycleid(null);
			sb.setActualrecycle(null);
			sb.setIsrecycle(0);
			asb.update(sb);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, "回收样品记录,编号：" + sbid);

			return mapping.findForward("noreceive");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}

}
