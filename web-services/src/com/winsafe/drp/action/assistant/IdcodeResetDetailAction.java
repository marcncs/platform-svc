package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppIdcodeReset;
import com.winsafe.drp.dao.AppIdcodeResetDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdcodeReset;
import com.winsafe.drp.dao.IdcodeResetForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class IdcodeResetDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			AppIdcodeReset appr = new AppIdcodeReset();
			AppOrgan ao = new AppOrgan();
			AppDept ad = new AppDept();
			AppUsers au = new AppUsers();

			IdcodeReset r = appr.getIdcodeResetById(id);
			IdcodeResetForm rf = new IdcodeResetForm();
			rf.setId(r.getId());
			rf.setMemo(r.getMemo());
			rf.setMakeorganidname(ao.getOrganByID(r.getMakeorganid()).getOrganname());
//			rf.setMakedeptidname(ad.getDeptByID(r.getMakedeptid()).getDeptname());
//			rf.setMakeidname(au.getUsersByid(r.getMakeid()).getRealname());			
			rf.setMakedate(DateUtil.formatDateTime(r.getMakedate()));	
			rf.setIsaudit(r.getIsaudit());
			rf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, r.getIsaudit(), "global.sys.SystemResource"));

			if (r.getAuditid() !=null && r.getAuditid() > 0) {
//				rf.setAuditidname(au.getUsersByid(r.getAuditid())
//						.getRealname());
			} else {
				rf.setAuditidname("");
			}			
			rf.setAuditdate(DateUtil.formatDate(r.getAuditdate()));
			
			
			AppIdcodeResetDetail aprp = new AppIdcodeResetDetail();
			List rplist = aprp.getIdcodeResetDetailByIrid(id);
			
					

			request.setAttribute("rf", rf);
			request.setAttribute("rplist", rplist);
//			DBUserLog.addUserLog(userid, "序号重置详情");// 日志
			return mapping.findForward("success");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
