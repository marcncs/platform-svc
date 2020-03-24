package com.winsafe.drp.keyretailer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusDetail;
import com.winsafe.drp.keyretailer.pojo.SBonusDetail;

public class ToAddSBonusDetailAction extends BaseAction {
	//private static Logger logger = Logger.getLogger(ListSBonusDetailAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		try {
			return mapping.findForward("add");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
