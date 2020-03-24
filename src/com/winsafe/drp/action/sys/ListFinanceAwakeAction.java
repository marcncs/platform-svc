package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DbUtil;

public class ListFinanceAwakeAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean usersBean = UserManager.getUser(request);
		Integer userid = usersBean.getUserid();
		int pagesize=1;
		try{

			String settlementCondition = " where  s.isratify=0 ";
			String settlementTable="Settlement s ";
			int settlementratify =DbUtil.getRecordCount(pagesize, settlementCondition, settlementTable);
			
			request.setAttribute("settlementratify", settlementratify);
			
			return mapping.findForward("financeawake");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
