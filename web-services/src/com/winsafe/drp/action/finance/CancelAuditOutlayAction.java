package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditOutlayAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{
			String oid = request.getParameter("OID");
			AppOutlay aso = new AppOutlay(); 
			Outlay so = new Outlay();
			so = aso.getOutlayByID(oid);

			if(so.getIsaudit()==0){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }

			if(!String.valueOf(so.getAuditid()).contains(userid.toString())){
	          	 String result = "databases.record.cancelaudit";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }

			aso.updIsAudit(oid, userid,0);

		      request.setAttribute("result", "databases.cancel.success");
		      DBUserLog.addUserLog(userid,9,"费用报销>>取消初审费用单,编号:"+oid); 
			
			return mapping.findForward("noaudit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
