package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AppAssembleRelationDetail;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.util.DBUserLog;

public class DelAssembleRelationAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String id = request.getParameter("ID");
			AppAssembleRelation aso = new AppAssembleRelation();
			AssembleRelation so = aso.getAssembleRelationByID(id);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppAssembleRelationDetail asld = new AppAssembleRelationDetail();
			aso.delAssembleRelation(id);			
			asld.delAssembleRelationDetail(id);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 3, "组装关系>>删除组装关系,编号:" + id, so);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
