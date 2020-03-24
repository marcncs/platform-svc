package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustCIntegralForm;
import com.winsafe.drp.dao.AdjustOIntegral;
import com.winsafe.drp.dao.AppAdjustOIntegral;
import com.winsafe.drp.dao.AppAdjustOIntegralDetail;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class AdjustOIntegralDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			AppAdjustOIntegral appr = new AppAdjustOIntegral();
			AppOrgan ao = new AppOrgan();
			AppDept ad = new AppDept();
			AppUsers au = new AppUsers();

			AdjustOIntegral r = appr.getAdjustOIntegralByID(id);
			AdjustCIntegralForm rf = new AdjustCIntegralForm();
			rf.setId(r.getId());
			rf.setRemark(r.getRemark());
			rf.setMakeorganidname(ao.getOrganByID(r.getMakeorganid()).getOrganname());
//			rf.setMakedeptidname(ad.getDeptByID(r.getMakedeptid()).getDeptname());
//			rf.setMakeidname(au.getUsersByid(r.getMakeid()).getRealname());			
			rf.setMakedate(DateUtil.formatDateTime(r.getMakedate()));
			rf.setIsaudit(r.getIsaudit());
			rf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, r.getIsaudit(), "global.sys.SystemResource"));
//			if (r.getAuditid() != null && r.getAuditid() > 0) {
//				rf.setAuditidname(au.getUsersByid(r.getAuditid())
//						.getRealname());
//			} else {
//				rf.setAuditidname("");
//			}
			rf.setAuditdate(DateUtil.formatDateTime(r.getAuditdate()));
			
			AppAdjustOIntegralDetail arf = new AppAdjustOIntegralDetail();
			List rflist = arf.getDetailByAocid(id);
			
			
			
			String severpath = request.getRequestURL().toString();
			severpath = severpath.substring(0, severpath.indexOf("assistant"));			

			request.setAttribute("rf", rf);
			request.setAttribute("rflist", rflist);
			request.setAttribute("severpath", severpath);
//			DBUserLog.addUserLog(userid, "机构积分调整详情");// 日志
			return mapping.findForward("info");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
