package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class RatifyOtherIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
//	    Long userid = users.getUserid();

		super.initdata(request);try{
			String oiid = request.getParameter("OIID");
			AppOtherIncome apb = new AppOtherIncome(); 
			OtherIncome pb = new OtherIncome();
			pb = apb.getOtherIncomeByID(oiid);

			if(pb.getIsaudit()==0){
	          	 String result = "databases.record.noauditnoratify";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
	           }

			
//			apb.updIsRatify(oiid, userid,1);
			
		      request.setAttribute("result", "databases.audit.success");

//		      DBUserLog.addUserLog(userid,"批准其它入库"); 
			
			return mapping.findForward("ratify");
		}catch(Exception e){
			
			e.printStackTrace();
		}finally {
		     // 
	    }
		return new ActionForward(mapping.getInput());
	}

}
