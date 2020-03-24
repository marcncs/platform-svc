package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToCompleteStockMoveReceiveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		String smid = request.getParameter("SMID");

		super.initdata(request);try{
			AppStockMove asm = new AppStockMove();
			StockMove sm = asm.getStockMoveByID(smid);

			
			if(sm.getIsshipment() ==0){
				request.setAttribute("result", "databases.record.noshipment");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if(sm.getIsblankout() ==1){
				request.setAttribute("result", "datebases.record.isblankout");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			if (sm.getIscomplete() == 1) { 
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			if (!sm.getInorganid().equals(users.getMakeorganid())) { 
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			request.setAttribute("inwarehouseid", sm.getInwarehouseid());
			request.setAttribute("smid", smid);
			
		      request.setAttribute("result", "databases.add.success");

			return mapping.findForward("tocomplete");
		} catch (Exception e) {

			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
