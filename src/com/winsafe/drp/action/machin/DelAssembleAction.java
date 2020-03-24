package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.AppAssembleDetail;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.util.DBUserLog;

public class DelAssembleAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String id = request.getParameter("ID");
			AppAssemble aso = new AppAssemble();
			Assemble so = aso.getAssembleByID(id);

			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppAssembleDetail asld = new AppAssembleDetail();
			aso.delAssemble(id);			
			asld.delAssembleDetailByAid(id);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 3, "删除组装>>删除组装,编号:" + id, so);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
