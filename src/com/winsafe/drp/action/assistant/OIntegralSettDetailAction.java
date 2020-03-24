package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppOIntegralSett;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.OIntegralSett;
import com.winsafe.drp.dao.OIntegralSettForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class OIntegralSettDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			AppOIntegralSett appr = new AppOIntegralSett();
			AppOrgan ao = new AppOrgan();
			AppDept ad = new AppDept();
			AppUsers au = new AppUsers();

			OIntegralSett r = appr.getOIntegralSettByID(id);
			OIntegralSettForm rf = new OIntegralSettForm();
			rf.setId(r.getId());
			rf.setOidname(ao.getOrganByID(r.getOid()).getOrganname());
			rf.setSettintegral(r.getSettintegral());
			rf.setSettcash(r.getSettcash());
			rf.setMakeorganidname(ao.getOrganByID(r.getMakeorganid()).getOrganname());
//			rf.setMakedeptidname(ad.getDeptByID(r.getMakedeptid()).getDeptname());
//			rf.setMakeidname(au.getUsersByid(r.getMakeid()).getRealname());			
			rf.setMakedate(DateUtil.formatDateTime(r.getMakedate()));
			rf.setIsaudit(r.getIsaudit());
			rf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, r.getIsaudit(), "global.sys.SystemResource"));
			if (r.getAuditid() != null && r.getAuditid() > 0) {
//				rf.setAuditidname(au.getUsersByid(r.getAuditid())
//						.getRealname());
			} else {
				rf.setAuditidname("");
			}
			rf.setAuditdate(DateUtil.formatDateTime(r.getAuditdate()));
			
				

			request.setAttribute("rf", rf);
//			DBUserLog.addUserLog(userid, "机构积分结算详情");// 日志
			return mapping.findForward("info");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
