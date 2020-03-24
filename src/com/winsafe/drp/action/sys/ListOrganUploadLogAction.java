package com.winsafe.drp.action.sys;

import java.net.URLEncoder; 
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.sap.dao.AppOrganUploadLog;
import com.winsafe.sap.pojo.OrganUploadLog;

public class ListOrganUploadLogAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ListOrganUploadLogAction.class);
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			String Condition = " (makeorganid in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ")) ";
			//通过用户id查询用户角色
//			Condition = " (makeid=" + userid + ") ";
			
			//管理员角色
			AppUsers appu = new AppUsers();
			if(appu.getAllUserRole(userid)){
				Condition = "";
			}
			
			Map map = new HashMap(request.getParameterMap());
			//判断MakeDate是否有值
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "OrganUploadLog" };
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "id","fileName");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" makeDate");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppOrganUploadLog api = new AppOrganUploadLog();
			List<OrganUploadLog> pils = api.getOrganUploadLog(request, pagesize, whereSql);
			request.setAttribute("alpi", pils);

			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}
}
