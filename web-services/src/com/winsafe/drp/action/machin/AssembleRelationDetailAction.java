package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AppAssembleRelationDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.dao.AssembleRelationForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class AssembleRelationDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");

		try {
			AppAssembleRelation aso = new AppAssembleRelation();
			AppUsers au = new AppUsers();
//			AppDept ad = new AppDept();
			AssembleRelation ar = aso.getAssembleRelationByID(id);
			AssembleRelationForm arf = new AssembleRelationForm();

			arf.setId(ar.getId());
			arf.setArproductid(ar.getArproductid());
			arf.setArproductname(ar.getArproductname());
			arf.setArspecmode(ar.getArspecmode());
			arf.setArunitid(ar.getArunitid());
//			arf.setArunitidname(Internation.getStringByKeyPositionDB(
//					"CountUnit", ar.getArunitid(),request));
			arf.setArquantity(ar.getArquantity());
			arf.setRemark(ar.getRemark());
			arf.setMakeorganid(ar.getMakeorganid());
			arf.setMakedeptid(ar.getMakedeptid());
			arf.setMakeid(ar.getMakeid());
//			arf.setMakeidname(au.getUsersByID(ar.getMakeid()).getRealname());
			arf.setMakedate(ar.getMakedate());
			arf.setIsaudit(ar.getIsaudit());
			arf.setAuditid(ar.getAuditid());
//			arf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//					request, ar.getIsaudit(), "global.sys.SystemResource"));
			arf.setAuditdate(DateUtil.formatDate(ar.getAuditdate()));
//			if (ar.getAuditid() > 0) {
//				arf.setAuditidname(au.getUsersByID(ar.getAuditid())
//						.getRealname());
//			} else {
//				arf.setAuditidname("");
//			}


			AppAssembleRelationDetail asld = new AppAssembleRelationDetail();
			List sals = asld.getAssembleRelationDetailBySIID(id);
			

			request.setAttribute("als", sals);
			request.setAttribute("arf", arf);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "组装关系详细");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
