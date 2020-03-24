package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ReturnPage;
import com.winsafe.hbm.util.StringFilters;

public class SetRuleUserWhAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
		AppRuleUserWH appRuleUserWH = new AppRuleUserWH();
		String type=request.getParameter("type");
    	Integer userId = Integer.valueOf(request.getSession().getAttribute("visituser").toString());

		String KeyWord=request.getParameter("KeyWord");
		KeyWord=StringFilters.filterSql(KeyWord);
		AppUsers appUsers = new AppUsers();
		Users users = appUsers.getUsersByid(userId);
		
		if(StringUtil.isEmpty(type)){
			DBUserLog.addUserLog(userId,11,"用户管理>>配置可见仓库,用户名："+users.getRealname());

			String idsString = request.getParameter("ids");
			String valuesString = request.getParameter("values");
			
			if(StringUtil.isEmpty(idsString) || StringUtil.isEmpty(valuesString)){
//				request.setAttribute("result", "databases.add.success");
//				return mapping.findForward("success");
				return ReturnPage.getReturn(request, "0", "操作成功！");
			}
			String[] ids=idsString.split(",");
			String[] values=valuesString.split(",");
			
			 
	    	//更新数据库仓库权限
	    	appRuleUserWH.updateRuleUserWH(ids,values); 
			 
//			request.setAttribute("result", "databases.add.success");
//			
//			return mapping.findForward("success");
			return ReturnPage.getReturn(request, "0", "操作成功！");
		}else if("checkAll".equals(type)){
			DBUserLog.addUserLog(userId,11,"用户管理>>配置可见仓库,用户名："+users.getRealname());
			
			appRuleUserWH.updateActiveFlag(userId,true,KeyWord);
//			request.setAttribute("result", "databases.add.success");
//			return mapping.findForward("success");
			return ReturnPage.getReturn(request, "0", "操作成功！");
		}else if("uncheckAll".equals(type)){
			DBUserLog.addUserLog(userId,11,"用户管理>>配置可见仓库,用户名："+users.getRealname());

			appRuleUserWH.updateActiveFlag(userId,false,KeyWord);
//			request.setAttribute("result", "databases.add.success");
//			return mapping.findForward("success");
			return ReturnPage.getReturn(request, "0", "操作成功！");
		}else{
			return null;
		}
    }
    
}
