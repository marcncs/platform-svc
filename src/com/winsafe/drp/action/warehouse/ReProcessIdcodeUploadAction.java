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
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ReProcessIdcodeUploadAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ReProcessIdcodeUploadAction.class);
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		Integer uploadId = Integer.valueOf(request.getParameter("uploadId"));
		try {
			AppIdcodeUpload api = new AppIdcodeUpload();
			api.reProcessFile(uploadId);
			String Condition = "";
			if(UserManager.isPermit("/warehouse/listIdcodeUploadAction.do?viewByAuthority=true", users.getUserid())) {
				Condition = " (makeorganid in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ")) ";
			} else {
				//通过用户id查询用户角色
				Condition = " (makeid=" + userid + ") ";
			}
			
			
			
			//管理员角色
			AppUsers appu = new AppUsers();
			if(appu.getAllUserRole(userid)){
				Condition = "";
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
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID","FileName");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			List pils = api.getIdcodeUpload(request, pagesize, whereSql);


			request.setAttribute("alpi", pils);

			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
