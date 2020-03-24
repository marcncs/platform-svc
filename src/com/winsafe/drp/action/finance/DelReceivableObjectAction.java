package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.util.DBUserLog;

public class DelReceivableObjectAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String oid = request.getParameter("OID");
			String orgid = request.getParameter("ORGID");

			AppReceivableObject apo = new AppReceivableObject();
			AppReceivable ar = new AppReceivable();

			ReceivableObject po = apo.getReceivableObjectByIDOrgID(oid, orgid);

			// if (po.getTotalreceivablesum() >
			// 0||po.getAlreadyreceivablesum()>0) { 
			// String result = "databases.record.lock";
			// request.setAttribute("result", result);
			// return new ActionForward("/sys/lockrecordclose.jsp");
			// }

			apo.delReceivableObject(oid, orgid);
			ar.delReceivableByROID(oid, orgid);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 9, "删除应收款对象");

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
