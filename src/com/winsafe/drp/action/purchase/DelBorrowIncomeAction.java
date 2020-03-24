package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBorrowIncome;
import com.winsafe.drp.dao.BorrowIncome;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelBorrowIncomeAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		//Connection conn = null;
		String id = request.getParameter("ID");
		try{
			AppBorrowIncome apb = new AppBorrowIncome();
			BorrowIncome pb = new BorrowIncome();
			pb = apb.getBorrowIncomeByID(id);
			if (pb.getIsaudit()==1) {
				String result = "databases.record.approvestatus";
				request.setAttribute("result", "databases.del.success");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			//Session 
	        //
	        
	        apb.delBorrowIncome(id);
	        
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		     // DBUserLog.addUserLog(userid,"删除借入单");
		      
		      return mapping.findForward("delresult");
	        
		}catch(Exception e){
			
			e.printStackTrace();
		}finally {
		      //
		      //  ConnectionEntityManager.close(conn);
		    }
		return mapping.getInputForward();
	}
}
