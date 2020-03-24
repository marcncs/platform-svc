package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class RatifyPurchaseBillAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();
	    
		try{
			String pbid = request.getParameter("PBID");
			AppPurchaseBill apb = new AppPurchaseBill(); 
			PurchaseBill pb = new PurchaseBill();
			pb = apb.getPurchaseBillByID(pbid);

			if(pb.getIsaudit()==0){
	          	 String result = "databases.record.noauditnoratify";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			if(pb.getIsratify()==1){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }

			pb.setRatifydate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			pb.setRatifyid(userid);
			pb.setIsratify(1);
			apb.updPurchaseBillByID(pb);

		      request.setAttribute("result", "databases.audit.success");

		      //DBUserLog.addUserLog(userid,"批准采购单");
			
			return mapping.findForward("ratify");
		}catch(Exception e){
			
			e.printStackTrace();
		}finally {
		      
	    }
		return new ActionForward(mapping.getInput());
	}

}
