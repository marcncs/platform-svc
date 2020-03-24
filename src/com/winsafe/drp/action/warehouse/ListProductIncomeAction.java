package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.RuleUtil;
import com.winsafe.hbm.util.DbUtil;


/**
 * 产成品入库列表
 * @author RichieYu
 *
 */
public class ListProductIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 
		super.initdata(request);
		try {
			String isAudit = request.getParameter("IsAudit");
			String Condition = " (r.activeFlag = 1 "+ getOrVisitOrgan("p.makeorganid")	+ ") and p.warehouseid=r.warehouseId and r.userId=" + userid;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			if(null==isAudit){
				isAudit = "0";
				map.put("IsAudit", isAudit);
			}
			String[] tablename = { "ProductIncome","WarehouseVisit"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" IncomeDate");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			
			AppProductIncome api = new AppProductIncome();
			List pils = api.getProductIncome(whereSql);
			
			//增加仓库权限
			//RichieYu-----20100428
			pils = RuleUtil.filterBillByWHRule(request, pils, UserManager.getUser(request).getUserid());
			
			
			//赋值给页面logic标签
			request.setAttribute("alpi", pils);
			//赋值给页面输入框（保持查询条件？）
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));

			DBUserLog.addUserLog(userid, 7, "入库>>列表产成品入库");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
