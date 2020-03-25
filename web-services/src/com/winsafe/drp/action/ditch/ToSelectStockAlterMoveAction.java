package com.winsafe.drp.action.ditch;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;


/**
 * @author : jerry
 * @version : 2009-8-27 下午04:19:49 www.winsafe.cn
 */
public class ToSelectStockAlterMoveAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			
			request.setAttribute("organid", request.getParameter("organid"));
			request.setAttribute("inorout", request.getParameter("inorout"));
			request.setAttribute("billnotype", request.getParameter("billnotype"));
			return mapping.findForward("toselect");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
