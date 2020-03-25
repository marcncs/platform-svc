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
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintTakeBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		String isaudit = request.getParameter("IsAudit");
		String begindate = request.getParameter("BeginDate");
		try {

			 String Condition=" (makeid="+userid+" "+getOrVisitOrgan("makeorganid")+ getOrVisitOrgan("equiporganid")+" ) " ;

			Map map = new HashMap(request.getParameterMap());
			if ( isaudit == null ) {
				map.put("IsAudit", 0);
			}
			if ( begindate == null ){
				String currentdate = DateUtil.getCurrentDateString();
				map.put("BeginDate", currentdate);
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "TakeBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			 String blur = DbUtil.getOrBlur(map, tmpMap, "ID","OName","Tel");
			whereSql = whereSql + blur +timeCondition+Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppTakeBill asl = new AppTakeBill();
			List pils = asl.getTakeBill(whereSql);
			

			request.setAttribute("also", pils);
			String objectsortselect = Internation.getSelectTagByKeyAll(
					"ObjectSort", request, "ObjectSort", true, null);
			request.setAttribute("objectsortselect", objectsortselect);
			
			
			DBUserLog.addUserLog(userid, 7, "打印检货清单");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
