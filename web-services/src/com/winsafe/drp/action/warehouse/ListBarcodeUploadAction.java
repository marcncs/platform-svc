package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBarcodeUpload;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListBarcodeUploadAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			Map<String,Object> param = new HashMap<>();
			//String Condition = " (makeid=" + userid + " "+ getOrVisitOrgan("makeorganid")+ ") ";
			//通过用户id查询用户角色
			String Condition = " (makeid=:makeid) "; 
			param.put("makeid", userid);
			
			//管理员角色
			AppUsers appu = new AppUsers();
			if(appu.getAllUserRole(userid)){
				Condition = "";
				param.remove("makeid");
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
			String[] tablename = { "BarcodeUpload" };
			String whereSql = EntityManager.getTmpWhereSqlForHql(map, tablename, param);
			String blur = DbUtil.getOrBlurForHql(map, tmpMap, param, "ID", "MakeOrganID", "FileName");
			String timeCondition = DbUtil.getTimeConditionForHql(map, tmpMap,
					" MakeDate", param);
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			DbUtil.replaceWithInteger("id", param);

			AppBarcodeUpload abu = new AppBarcodeUpload();
			List pils = abu.getBarcodeUpload(request, pagesize, whereSql, param);
			
			request.setAttribute("alpi", pils);

			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
