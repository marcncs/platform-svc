package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.salesman.dao.AppSalesMan;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class QueryStockMoveToAuditCountAction extends Action{
	
	private AppSalesMan appSalesMan = new AppSalesMan();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		String username = request.getParameter("Username");
		// 判断用户是否存在
		UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
		int count = 0;
		if(UserType.CM.getValue().equals(loginUsers.getUserType())) {
			//查看是否有审批权限
			boolean firstAuditRole = false;
			boolean secondAuditRole = false;
			AppRole appRole = new AppRole();
			List<Role> roles = appRole.getRolesByUserid(loginUsers.getUserid());
			for(Role role : roles) {
				if("转仓审批一".equals(role.getRolename())) {
					firstAuditRole = true;
				}
				if("转仓审批二".equals(role.getRolename())) {
					secondAuditRole = true;
				}
			}
			if(firstAuditRole || secondAuditRole) {
				count = appSalesMan.getStockMoveToAuditByCMCount(firstAuditRole, secondAuditRole);
			}
		} else {
			count = appSalesMan.getStockMoveToAuditCount(loginUsers.getUserid());
		}
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, map
				,loginUsers.getUserid(),"APP_RI","转仓单审核确认数量",false);
		return null;
	}
}

