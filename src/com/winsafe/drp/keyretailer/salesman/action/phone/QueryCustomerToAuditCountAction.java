package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.salesman.dao.AppSalesMan;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class QueryCustomerToAuditCountAction extends Action{
	
	private AppSalesMan appSalesMan = new AppSalesMan();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		String username = request.getParameter("Username");
		// 判断用户是否存在
		UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
		String condition = SBonusService.getWhereConditionForAuditCustomer(loginUsers);
		
		int count = appSalesMan.getCustomerToAuditCount(condition);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, map
				,loginUsers.getUserid(),"APP_RI","客户信息确认数量",true);
		return null;
	}

}

