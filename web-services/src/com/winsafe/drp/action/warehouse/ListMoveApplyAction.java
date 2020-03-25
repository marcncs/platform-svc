package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListMoveApplyAction extends BaseAction {
	
	private OrganService os = new OrganService();
	private AppMoveApply aama = new AppMoveApply();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>(); 
		int pagesize = 20;

		super.initdata(request);
		try{
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "MoveApply" };
			String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param);

			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap,
					" MoveDate", param);
			String blur = DbUtil.getBlurForSql(map, tmpMap, "KeysContent", param);
			//权限条件
			String Condition ="";
			if(DbUtil.isDealer(users)) {
				Condition = " ma.outorganid in (select visitorgan from User_Visit where  userid=" + userid + ")" +
				" and ma.outwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id=?)  ";
				param.put("wv.user_Id", userid);
			} else { 
				Condition = "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			List<Map<String,String>> sals = aama.getMoveApplyList(request, pagesize, whereSql, param);
			
			Organ organ = os.getOrganByID(users.getMakeorganid());
			request.setAttribute("MakeOrganID", users.getMakeorganid());
			request.setAttribute("MakeOrganName", users.getMakeorganname());
			request.setAttribute("organType", organ.getOrganType());
			request.setAttribute("organRank", organ.getRank());

			request.setAttribute("als", sals);
			request.setAttribute("IsAudit", request.getParameter("IsAudit"));
			request.setAttribute("OutOrganID", request.getParameter("OutOrganID"));
			request.setAttribute("IsBlankOut", request.getParameter("IsBlankOut"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("IsRatify", request.getParameter("IsRatify"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
