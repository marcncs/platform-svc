package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAdsumGoods;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class HiddenAdsumGoodsAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//Session 
		//Connection //
		
		try{
			String agid = request.getParameter("ID");
			Integer iscomplete = 1;
			AppAdsumGoods app = new AppAdsumGoods();
			app.updIsComplete(agid,iscomplete);
			
			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid,"隐藏到货通知");
			
			return mapping.findForward("hidden");
		}catch(Exception e){
			
			e.printStackTrace();
		}finally {
			//
			//  ConnectionEntityManager.close(conn);
		}
		return new ActionForward(mapping.getInput());
	}
}
