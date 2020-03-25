package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.util.DBUserLog;

public class AuditIncomeLogAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{
			String lid = request.getParameter("ILID");
			AppIncomeLog ap = new AppIncomeLog(); 
			IncomeLog p = ap.getIncomeLogByID(lid);

			if(p.getIsaudit()==1){
	          	 String result = "databases.record.audit";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			
			AppReceivableObject appro = new AppReceivableObject();
			ReceivableObject ro = appro.getReceivableObjectByIDOrgID(p.getRoid(), p.getMakeorganid());
			if ( ro.getObjectsort().intValue() == 1 ){
				UpgradeIntegralService uis = new UpgradeIntegralService();
				uis.upgrade(p.getRoid(), p.getIncomesum());
			}

			ap.updIsAudit(lid, userid,1);
		      request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid,9,"收款管理>>复核收款,编号："+lid); 
			return mapping.findForward("audit");
		}catch(Exception e){
			request.setAttribute("result", "databases.audit.fail");
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
