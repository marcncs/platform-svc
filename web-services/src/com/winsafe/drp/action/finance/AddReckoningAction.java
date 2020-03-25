package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.Reckoning;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddReckoningAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		super.initdata(request);
		try {
			Reckoning r = new Reckoning();

			r.setId(MakeCode.getExcIDByRandomTableName("reckoning", 2, "RK"));
			r.setUid(RequestTool.getInt(request,"uid"));
			// String loandate = request.getParameter("loandate").replace('-',
			// '/');
			// if (loandate != null && loandate.trim().length() > 0) {
			// l.setLoandate(new Date(loandate));
			// }
			r.setLoansum(Double.valueOf(0.00));
			r.setPurpose(request.getParameter("purpose"));
			r.setBacksum(Double.valueOf(request.getParameter("backsum")));
			r.setFundattach(RequestTool.getInt(request,"fundattach"));
			r.setMemo(request.getParameter("memo"));
			r.setIscash(1);
			r.setMakeid(userid);
			r.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			r.setMakeorganid(users.getMakeorganid());
		    r.setMakedeptid(users.getMakedeptid());
			r.setIsaudit(0);

			
			AppReckoning apo = new AppReckoning();
			apo.addReckoning(r);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid,9,"人个借支>>新增还款清算,编号："+r.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}
}
