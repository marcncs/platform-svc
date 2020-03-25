package com.winsafe.drp.action.machin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSuggestInspectAction extends BaseAction {
	AppSuggestInspect asi = new AppSuggestInspect();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			String Condition = "  ";
			Map map = new HashMap(request.getParameterMap());
			// 判断MakeDate是否有值
			String timeBeginDate = "";
			if (map.containsKey("BeginDate")) {
				timeBeginDate = (String) map.get("BeginDate");
				if (timeBeginDate != null && !"".equals(timeBeginDate)) {
					map.put("BeginDate", timeBeginDate);
					request.setAttribute("BeginDate", timeBeginDate);
				}
			}
			String isMerge = request.getParameter("isMerge");
			String isRemove = request.getParameter("isRemove");
			String isOut = request.getParameter("isOut");
			isMerge = isMerge==null ? "0" : isMerge;
			map.put("isMerge", isMerge);
			isRemove =  isRemove==null? "0" : isRemove;
			map.put("isRemove", isRemove);
			isOut =  isOut == null? "0" : isOut;
			map.put("isOut", isOut);
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SuggestInspect" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "id", "typeName",
					"siid", "customer_Code", "dis_WareHouse_Name",
					"sou_WareHouse_Name", "typeid");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" makeDate");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			
			List pils = asi.getSuggestInspectByPage(request, pagesize, whereSql);
			request.setAttribute("alpi", pils);
			request.setAttribute("isMerge", isMerge);
			request.setAttribute("isOut", isOut);
			request.setAttribute("isRemove", isRemove);
			request.setAttribute("typeId", request.getParameter("typeId"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 7, "建议检货单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
