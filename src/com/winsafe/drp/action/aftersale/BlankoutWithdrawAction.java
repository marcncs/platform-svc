package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Withdraw;
import com.winsafe.drp.util.DBUserLog;

public class BlankoutWithdrawAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		try{
			AppWithdraw apb = new AppWithdraw();
			Withdraw pb = apb.getWithdrawByID(id);
			if (pb.getIsblankout()==1) {
				request.setAttribute("result", "databases.record.approvestatus");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if(pb.getIsaudit()==1){
	               request.setAttribute("result", "databases.record.nodel");
	               return new ActionForward("/sys/lockrecordclose.jsp");
	        }

	        UsersBean users = UserManager.getUser(request);
	        int userid = users.getUserid();
	        apb.BlankoutWithdraw(id, userid);
	        
		      request.setAttribute("result", "databases.del.success");
		     
		      DBUserLog.addUserLog(userid, 6, "销售退货>>作废销售退货单,编号:"+id); 

		      return mapping.findForward("delresult");
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally {

		    }
		return mapping.getInputForward();
	}
}
