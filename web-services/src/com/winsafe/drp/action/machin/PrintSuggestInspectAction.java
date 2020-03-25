package com.winsafe.drp.action.machin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PrintSuggestInspectAction extends BaseAction {
	AppSuggestInspect asi = new AppSuggestInspect();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {

			String Condition = "  ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SuggestInspect" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"makeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "id", "typeName",
					"siid", "customer_Code", "dis_WareHouse_Name",
					"sou_WareHouse_Name", "typeid");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			List<SuggestInspect> pils = asi.getSiByIds(whereSql);
			request.setAttribute("also", pils);
			request.setAttribute("isRemove", map.get("isRemove"));
			request.setAttribute("isOut", map.get("isOut"));
			request.setAttribute("isOut", map.get("isOut"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("porganname", request.getAttribute("porganname").toString());
			request.setAttribute("pusername", request.getAttribute("pusername").toString());
			request.setAttribute("ptime", DateUtil.getCurrentDateTime());
			DBUserLog.addUserLog(userid, 7, "打印拣货建议单");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
