package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.UserManager;

public class SearchRuleWarehouseAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	//得到session中的仓库权限列表
	List<RuleUserWh> rules = (ArrayList<RuleUserWh>)request.getSession().getAttribute("ruleWarehouses");
	AppRuleUserWH appRuleUserWH = new AppRuleUserWH();
	//得到session中的选中的用户id
	Integer userId = Integer.valueOf(request.getSession().getAttribute("visituser").toString());
	//得到检索条件
	Object warehouseName = request.getParameter("KeyWord");
	//如果条件为空则初始化所有的仓库权限列表
	if(warehouseName == null || "".equals(warehouseName)){
	    rules = appRuleUserWH.getRuleByUserId(userId);
	//根据条件检索    
	}else{
	    rules = appRuleUserWH.searchRulesByWHName(rules, warehouseName.toString());
	} 
	//重设session仓库权限列表
	request.getSession().setAttribute("ruleWarehouses", rules);
	return mapping.findForward("success");
    }

}
