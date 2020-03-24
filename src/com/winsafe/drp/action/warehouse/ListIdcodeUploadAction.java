package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListIdcodeUploadAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ListIdcodeUploadAction.class);
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		int pagesize = 20;
		super.initdata(request);
		try {
			Map<String,Object> param = new HashMap<>();
			String Condition = "";
			if(UserManager.isPermit("/warehouse/listIdcodeUploadAction.do?viewByAuthority=true", users.getUserid())) {
				Condition = " (makeorganid in (select visitorgan from UserVisit as uv where  uv.userid=:userid)) ";
				param.put("userid", userid); 
			} else {
				//通过用户id查询用户角色
				Condition = " (makeid=:userid) ";
				param.put("userid", userid);
			}
			//管理员角色
			AppUsers appu = new AppUsers();
			if(appu.getAllUserRole(userid)){
				Condition = "";
				param.remove("userid");
			}
			
			Map map = new HashMap(request.getParameterMap());
			//判断MakeDate是否有值
			String timeBeginDate = "";
			if(map.containsKey("BeginDate")){
				 timeBeginDate = (String) map.get("BeginDate");
				 if(timeBeginDate==null || "".equals(timeBeginDate)){
					 timeBeginDate = DateUtil.getCurrentDateString();
					 map.put("BeginDate", timeBeginDate);
					 request.setAttribute("BeginDate", timeBeginDate);
				 }
			}else{
				 timeBeginDate = DateUtil.getCurrentDateString();
				 map.put("BeginDate", timeBeginDate);
				 request.setAttribute("BeginDate", timeBeginDate);
			}
			
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IdcodeUpload" };
			String whereSql = EntityManager.getTmpWhereSqlForHql(map, tablename, param);
			String blur = DbUtil.getOrBlurForHql(map, tmpMap, param, "ID","FileName");
			String timeCondition = DbUtil.getTimeConditionForHql(map, tmpMap,
					" MakeDate", param);
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			DbUtil.replaceWithInteger("id", param);
			DbUtil.replaceWithInteger("billsort", param);
			AppIdcodeUpload api = new AppIdcodeUpload();
			List<IdcodeUpload> pils = api.getIdcodeUpload(request, pagesize, whereSql, param);
			request.setAttribute("alpi", pils);

			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
