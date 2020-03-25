package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppServiceAgreement;
import com.winsafe.drp.dao.ServiceAgreement;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddServiceAgreementAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		AppServiceAgreement ap = new AppServiceAgreement();

		try {

			ServiceAgreement sa = new ServiceAgreement();

			BeanCopy bc = new BeanCopy();
			
			bc.copy(sa, request);
			Integer said = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"service_agreement", 0, ""));
			sa.setId(said);
			sa.setSfee(Double.valueOf(0));

			sa.setQuestion(request.getParameter("question"));
			sa.setMemo("");
			sa.setIsallot(0);
			sa.setMakeorganid(users.getMakeorganid());
			sa.setMakedeptid(users.getMakedeptid());
			sa.setMakeid(userid);
			sa.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));

			ap.addServiceAgreement(sa);

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增服务预约,编号：" + sa.getId());
			request.getSession().setAttribute("ID", sa.getId());
			request.getSession().setAttribute("ReferType", "ServiceAgreement");
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
