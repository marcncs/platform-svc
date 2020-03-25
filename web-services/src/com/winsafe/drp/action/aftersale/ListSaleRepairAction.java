package com.winsafe.drp.action.aftersale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.SaleRepairForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListSaleRepairAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);

		String isblankoutselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsBlankOut", true, null);

		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		try {

			// String Condition=" dp.makeid like '"+userid+"%' " ;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleRepair" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = DbUtil.setDynamicPager(request, "SaleRepair as dp ",
					whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppSaleRepair asl = new AppSaleRepair();
			List pils = asl.getSaleRepair(pagesize, whereSql, tmpPgInfo);
			ArrayList also = new ArrayList();
			SaleRepair w = null;
			for (int i = 0; i < pils.size(); i++) {
				w = (SaleRepair) pils.get(i);
				SaleRepairForm dpf = new SaleRepairForm();
				dpf.setId(w.getId());
				dpf.setCid(w.getCid());
				dpf.setCname(w.getCname());
				dpf.setClinkman(w.getClinkman());
				dpf.setTotalsum(w.getTotalsum());
				dpf.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, w.getIsaudit(),
						"global.sys.SystemResource"));
				dpf.setIsbacktrackname(Internation.getStringByKeyPosition(
						"YesOrNo", request, w.getIsbacktrack(),
						"global.sys.SystemResource"));
				dpf.setIsblankoutname(Internation.getStringByKeyPosition(
						"YesOrNo", request, w.getIsblankout(),
						"global.sys.SystemResource"));
//				dpf.setMakeidname(au.getUsersByid(w.getMakeid())
//						.getRealname());
				dpf.setMakedate(DateUtil.formatDate(w.getMakedate()));

				also.add(dpf);
			}

			request.setAttribute("also", also);
			request.setAttribute("isauditselect", isauditselect);
			request.setAttribute("isblankoutselect", isblankoutselect);

			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
//				ubs.setUserid(Long.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}
			request.setAttribute("als", als);

//			DBUserLog.addUserLog(userid, "列表销售返修");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
