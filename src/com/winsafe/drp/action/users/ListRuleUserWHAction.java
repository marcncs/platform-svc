package com.winsafe.drp.action.users;

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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.util.DBUserLog;

public class ListRuleUserWHAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListRuleUserWHAction.class);
	private AppRuleUserWH appRuleUserWH = new AppRuleUserWH();
	private AppOrgan appOrgan = new AppOrgan();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int pagesize = 20;
		//初始化
		initdata(request);
		String userid = request.getParameter("userid");
		try {
			//使用连接查询
			String whereSql = " , RuleUserWh  rw where w.id = rw.warehouseId and rw.userId= " + userid ;
			List<Map> rwlist = appRuleUserWH.queryRuleWHByUid(request, pagesize, whereSql);
			// 查询机构名称
			for(Map map : rwlist){
				if(map == null) continue;
				Organ organ = appOrgan.getOrganByID(map.get("makeorganid")+"");
				if(organ != null){
					map.put("makeorganname", organ.getOrganname());
				}
			}
			request.setAttribute("rwlist", rwlist);
			request.setAttribute("uid", userid);
			DBUserLog.addUserLog(this.userid, "系统管理", "用户管理>>列表用户管辖权限");
			return mapping.findForward("uv");
			
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
