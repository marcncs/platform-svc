package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.Outlay;

public class ToAuditOutlayAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		super.initdata(request);
		try{
			String id = request.getParameter("id");
			AppOutlay apb = new AppOutlay(); 
			Outlay pb =  apb.getOutlayByID(id);
//			if (pb.getApprovestatus()!=2) {
//				String result = "databases.record.noapprove";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecord.jsp");
//			}
//			if(pb.getIsblankout()==1){
//	          	 String result = "databases.record.blankoutnooperator";
//	               request.setAttribute("result", result);
//	               return new ActionForward("/sys/lockrecordclose.jsp");
//	           }
			if(pb.getIsaudit()==1){
	          	 String result = "databases.record.audit";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			AppCashBank acb = new AppCashBank();
			List cbs = acb.getAllCashBank();		
			request.setAttribute("cbs", cbs);
			request.setAttribute("id", id);
		      
		      
			return mapping.findForward("toaudit");
		}catch(Exception e){			
			e.printStackTrace();
		}finally {
		      ////HibernateUtil.closeSession();
	    }
		return new ActionForward(mapping.getInput());
	}

}
