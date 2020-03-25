package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdsumGoods;
import com.winsafe.drp.dao.AppAdsumGoods;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelAdsumGoodsAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		////Connection conn = null;
		String id = request.getParameter("ID");
		try{
			AppAdsumGoods aag = new AppAdsumGoods();
			AdsumGoods ag = new AdsumGoods();
			ag = aag.getAdsumGoodsByID(id);
			if (ag.getIsaudit()==1) {
				String result = "databases.record.approvestatus";
				request.setAttribute("result", "databases.del.success");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			//Session 
	        //
	        
	        aag.delAdsumGoods(id);
	        
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		     // DBUserLog.addUserLog(userid,"删除到货通知");
		      
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
