package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.Reckoning;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdReckoningAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("id");
			AppReckoning apa = new AppReckoning();
			Reckoning l = apa.getReckoningByID(id);
			Reckoning oldW = (Reckoning)BeanUtils.cloneBean(l);

			l.setUid(RequestTool.getInt(request,"uid"));
			//String loandate = request.getParameter("loandate");
			//l.setLoandate(DateUtil.StringToDate(loandate));
			l.setPurpose(request.getParameter("purpose"));
			l.setBacksum(Double.valueOf(request.getParameter("backsum")));
			l.setMemo(request.getParameter("memo"));
			l.setFundattach(RequestTool.getInt(request,"fundattach"));
			apa.updReckoning(l);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 9,"个人借支>>修改还款,编号："+id,oldW,l); 
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
