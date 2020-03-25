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
import com.winsafe.drp.dao.AppIdcodeUploadLog;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.IdcodeErrorType;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListIdcodeUploadLogAction extends BaseAction{
	
	private AppIdcodeUploadLog appIdcodeUploadLog = new AppIdcodeUploadLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> param = new HashMap<>(); 
		int pagesize = 10;
		initdata(request);
		Map map = new HashMap(request.getParameterMap());
		String errCode = (String)map.get("errCode");
		if(!StringUtil.isEmpty(errCode)) {
			IdcodeErrorType errType = IdcodeErrorType.parseByIntValue(Integer.parseInt(errCode));
			if(errType != null) {
				map.put("errCode", errType.getDbValue());
			}
		}
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "IdcodeUploadLog" };
		String whereSql = EntityManager.getTmpWhereSql2ForHql(map, tablename, param);
		String timeCondition = DbUtil.getTimeCondition4ForHql(map, tmpMap," uploadDate", param);
		whereSql = whereSql + timeCondition;
		String blur = DbUtil.getOrBlur2ForHql(map, tmpMap, param, "billNo", "idcodeUploadId", "bsort", "errMsg" );
		String Condition = " o.uploadWarehouseId  in (select wv.warehouseId from  RuleUserWh as wv where wv.userId=:userid)";
		param.put("userid", userid);
		whereSql = whereSql  + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);
		DbUtil.replaceWithInteger("bsort", param);
		DbUtil.replaceWithInteger("idcodeUploadId", param);
		
		List logs = appIdcodeUploadLog.getIdcodeUploadLogs(request, pagesize, whereSql, param);
		
		request.setAttribute("logs", logs);
		DBUserLog.addUserLog(request, "[列表]");
		return mapping.findForward("list");
	}

}
