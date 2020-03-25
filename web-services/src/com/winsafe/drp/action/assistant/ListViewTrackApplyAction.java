package com.winsafe.drp.action.assistant;

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
import com.winsafe.drp.dao.AppTrackApply;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListViewTrackApplyAction extends BaseAction {
	private Logger logger = Logger.getLogger(ListViewTrackApplyAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10;
		super.initdata(request);
		AppTrackApply ata = new AppTrackApply();
		try {
			Map<String,Object> param = new HashMap<>();
			// 权限条件
			String condition = " t.applyOrgId in (select uv.visitorgan from  UserVisit as uv where  uv.userid=:userId)  ";
			param.put("userId", userid);
			Map map = new HashMap(request.getParameterMap());
			// 判断MakeDate是否有值
			String timeBeginDate = "";
			if (map.containsKey("BeginDate")) {
				timeBeginDate = (String) map.get("BeginDate");
				if (timeBeginDate == null || "".equals(timeBeginDate)) {
					timeBeginDate = DateUtil.getCurrentDateString();
					map.put("BeginDate", timeBeginDate);
					request.setAttribute("BeginDate", timeBeginDate);
				}
			} else {
				timeBeginDate = DateUtil.getCurrentDateString();
				map.put("BeginDate", timeBeginDate);
				request.setAttribute("BeginDate", timeBeginDate);
			}

			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "TrackApply" };
			String whereSql = EntityManager.getTmpWhereSqlForHql(map, tablename, param);
			String blur = DbUtil.getOrBlurForHql(map, tmpMap, param, "ID", "applyOrgId", "applyUserId",
					"idCode", "codeType", "createDate", "status", "remark", "errorMsg");
			String timeCondition = DbUtil.getTimeConditionForHql(map, tmpMap, " createDate", param);
			whereSql = whereSql + timeCondition + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			// 获取查询码列表
			List list = ata.selectTrackApply(request, pagesize, whereSql, param);
			request.setAttribute("TrackApply", list);
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
