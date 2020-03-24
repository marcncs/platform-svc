package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppLoanOut;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.LoanOut;
import com.winsafe.drp.dao.LoanOutForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectLoanOutAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		try {
			String findCondition = " isaudit=1 and istranssale=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "LoanOut" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + findCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, " LoanOut ", whereSql,
					pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			AppLoanOut app = new AppLoanOut();
			List pals = app.searchLoanOut(pagesize, whereSql, tmpPgInfo);
			ArrayList also = new ArrayList();

			LoanOut so = null;
			for (int i = 0; i < pals.size(); i++) {
				so = (LoanOut) pals.get(i);
				LoanOutForm sof = new LoanOutForm();
				sof.setId(so.getId());
				sof.setUid(so.getUid());
				sof.setUname(so.getUname());
				if (null != so.getSaledept() && so.getSaledept() > 0) {
//					sof.setSaledeptname(ad.getDeptByID(
//							Long.valueOf(so.getSaledept().toString()))
//							.getDeptname());
				}
				sof.setReceiveidname(so.getReceiveman());
				sof.setMakedate(DateUtil.formatDate(so.getMakedate()));
				sof.setConsignmentdate(DateUtil.formatDate(so
						.getConsignmentdate()));
				sof.setTotalsum(so.getTotalsum());
//				sof.setMakeidname(au.getUsersByid(so.getMakeid())
//						.getRealname());
				sof.setMakedate(DateUtil.formatDate(so.getMakedate()));
				also.add(sof);
			}

			request.setAttribute("also", also);

			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
