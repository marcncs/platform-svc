package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.winsafe.hbm.util.RequestTool;

public class UpdServiceAgreementAction extends Action {

	@SuppressWarnings("static-access")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		AppServiceAgreement asa = new AppServiceAgreement();
		try {

			ServiceAgreement sa = asa.getServiceAgreementByID(RequestTool.getInt(request, "id"));
			ServiceAgreement oldsa = (ServiceAgreement)BeanUtils.cloneBean(sa);
					
			BeanCopy bc = new BeanCopy();
			
			bc.copy(sa, request);

			sa.setIsallot(0);
			sa.setMakeorganid(users.getMakeorganid());
			sa.setMakedeptid(users.getMakedeptid());
			sa.setMakeid(userid);
			sa.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));			
			
				
			asa.updServiceAgreement(sa);
			
			
			request.getSession().setAttribute("ID", sa.getId());
			request.getSession().setAttribute("ReferType", "ServiceAgreement");
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>修改服务预约,编号：" + sa.getId(), oldsa, sa);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "databases.upd.fail");
			return new ActionForward("/sys/lockrecord.jsp");
		}

	}

}
