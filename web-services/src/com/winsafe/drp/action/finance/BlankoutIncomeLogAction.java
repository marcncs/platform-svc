package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.util.DBUserLog;

public class BlankoutIncomeLogAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		super.initdata(request);
		try{
			AppIncomeLog apb = new AppIncomeLog();
			IncomeLog pb = apb.getIncomeLogByID(id);
			if (pb.getIsaudit()==1) {
				String result = "databases.record.approvestatus";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}


//	        apb.blankout(id, userid);

		      request.setAttribute("result", "databases.del.success");
		     
		      DBUserLog.addUserLog(userid,9, "收款管理>>作废收款单，编号:"+id); 
		      return mapping.findForward("del");
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
