package com.winsafe.drp.action.sys;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.util.DBUserLog;

public class ListRuleUserWHAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListRuleUserWHAction.class);
	private AppRuleUserWH appRuleUserWH = new AppRuleUserWH();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int pagesize = 20;
		//初始化
		initdata(request);
		String warehouseId = request.getParameter("WID");
		try {
			//使用连接查询
			String whereSql = " , RuleUserWh  rw where u.makeorganid=o.id and u.userid = rw.userId and rw.warehouseId= '" + warehouseId +"'";
			List<Map> rwlist = appRuleUserWH.queryRuleWHByWid(request, pagesize, whereSql);
			
			request.setAttribute("rwlist", rwlist);
			request.setAttribute("WID", warehouseId);
			DBUserLog.addUserLog(this.userid, "系统管理", "机构设置>>仓库设置>>列表管辖用户");
			return mapping.findForward("uv");
			
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
