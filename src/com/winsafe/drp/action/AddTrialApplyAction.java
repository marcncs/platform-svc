package com.winsafe.drp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppTrailApply;
import com.winsafe.drp.dao.TrialApply;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddTrialApplyAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		try{
			AppTrailApply ata = new AppTrailApply();
			TrialApply ta = new TrialApply();
			ta.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("trial_apply",0,"")));
			ta.setCompanyname(request.getParameter("companyname"));
			ta.setCompanyaddr(request.getParameter("companyaddr"));
			ta.setLinkman(request.getParameter("linkman"));
			ta.setTel(request.getParameter("tel"));
			ta.setQq(request.getParameter("qq"));
			ta.setMsn(request.getParameter("msn"));
			ta.setEmail(request.getParameter("email"));
			ta.setCompanytel(request.getParameter("companytel"));
			ta.setCompanysite(request.getParameter("companysite"));
			ta.setIntent(Integer.valueOf(request.getParameter("intent")));
			ta.setTrialmode(Integer.valueOf(request.getParameter("trialmode")));
			ta.setRoute(Integer.valueOf(request.getParameter("route")));
			ta.setRemark(request.getParameter("remark"));
			ta.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			ta.setIsdispose(0);
			
			ata.addTrailApply(ta);

	        return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();

		}finally {

		    }
		
		return new ActionForward(mapping.getInput());
	}
}
