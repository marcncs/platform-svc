package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListICodeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		try {
			String strPDID = request.getParameter("PID");
			if (strPDID == null) {
				strPDID = (String) request.getSession().getAttribute("pdid");
			}
			request.getSession().setAttribute("pdid", strPDID);

			String Condition = " productid='" + strPDID+ "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ICode"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppICode apl = new AppICode();
			List apls = apl.getICodeList(request, pagesize, whereSql);
			
			

			request.setAttribute("iclist", apls);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "产品资料>>列表产品物流码前缀");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
