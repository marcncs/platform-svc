package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;

public class HandworkSettlementAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);super.initdata(request);try{

			AppWarehouse aw = new AppWarehouse();
			List wls = aw.getCanUseWarehouse();
		      
		      
		      request.setAttribute("alw",wls);

//		      DBUserLog.addUserLog(userid,"显示手工结算单据"); 
			return mapping.findForward("handwork");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
