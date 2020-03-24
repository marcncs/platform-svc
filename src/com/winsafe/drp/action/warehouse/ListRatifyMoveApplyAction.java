package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyForm;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListRatifyMoveApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		
		String isSearch = request.getParameter("isSearch");

		super.initdata(request);
		try{
			AppRole appRole = new AppRole();
			String Condition = users.getMakeorganid() + " in (select parentid from Organ where id = ma.outorganid) and ma.outorganid in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ") ";
			
			//查看是否有审批权限
			boolean firstAuditRole = false;
			boolean secondAuditRole = false;
			
			List<Role> roles = appRole.getRolesByUserid(userid);
			for(Role role : roles) {
				if("转仓审批一".equals(role.getRolename())) {
					firstAuditRole = true;
				}
				if("转仓审批二".equals(role.getRolename())) {
					secondAuditRole = true;
				}
			}
			
			if(firstAuditRole || secondAuditRole) {
				if(StringUtil.isEmpty(isSearch)) {
					if(firstAuditRole) {
						request.setAttribute("IsRatify", StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue());
						Condition = " isratify in ("+StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue()+") ";
					} else if(secondAuditRole) {
						request.setAttribute("IsRatify", StockMoveConfirmStatus.IN_ASM_APPROVED.getValue());
						Condition = " isratify in ("+StockMoveConfirmStatus.IN_ASM_APPROVED.getValue()+") ";
					}
				} else {
					Condition = "";
				}
				
			}
			
			//ASM用户
			if(UserType.ASM.getValue().equals(users.getUserType())) {
				Condition = " (outorganid in (select organId from UserCustomer where userId="+userid+") or inorganid in (select organId from UserCustomer where userId="+userid+")) ";
			}
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "MoveApply" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "MoveDate", "OutOrganID","InOrganID", "MakeID", "IsRatify");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppMoveApply aama = new AppMoveApply();
			
			List sals = aama.getMoveApply(request, pagesize, whereSql);
 			ArrayList als = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				MoveApplyForm saf = new MoveApplyForm();
				MoveApply o = (MoveApply) sals.get(i);
				saf.setId(o.getId());
				saf.setMovedate(o.getMovedate());
				saf.setOutorganid(o.getOutorganid());
				saf.setIsaudit(o.getIsaudit());
				saf.setIsratify(o.getIsratify());
				saf.setMakeorganid(o.getMakeorganid());
				saf.setIsblankout(o.getIsblankout());
				saf.setIsblankout(o.getIsblankout());
				saf.setMakeid(o.getMakeid());
				saf.setInorganid(o.getInorganid());
				saf.setMoveType(o.getMoveType());
				als.add(saf);
			}

			if(StringUtil.isEmpty(isSearch)) {
				request.setAttribute("isSearch", 1);
			}
			request.setAttribute("als", als);
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("OutOrganID", request.getParameter("OutOrganID"));
			request.setAttribute("IsBlankout", map.get("IsBlankout"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
