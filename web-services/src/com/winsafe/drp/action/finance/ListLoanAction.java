package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppLoan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Loan;
import com.winsafe.drp.dao.LoanForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListLoanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		String uid = request.getParameter("uid");
		if (uid == null) {
			uid = (String) request.getSession().getAttribute("uid");
		}
		request.getSession().setAttribute("uid", uid);
		super.initdata(request);
		try {
			String Condition = " l.uid ='" + uid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "Loan" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql +timeCondition+ Condition ;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppLoan apa = new AppLoan();
			AppUsers au = new AppUsers();

			List pbls = apa.getLoan(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();

			for (int i = 0; i < pbls.size(); i++) {
				LoanForm lf = new LoanForm();
				Loan o = (Loan) pbls.get(i);
				lf.setId(o.getId());
				lf.setUidname(au.getUsersByid(o.getUid()).getRealname());
				lf.setLoandate(String.valueOf(o.getLoandate()));
				lf.setLoansum(o.getLoansum());
				lf.setMakeid(o.getMakeid());
				// lf.setMakeidname(au.getUsersByid(o.getMakeid()).getRealname());
				lf.setMakedate(String.valueOf(o.getMakedate()));
				lf.setIsaudit(o.getIsaudit());
				// lf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
				// request,
				// , "global.sys.SystemResource"));

				alpl.add(lf);
			}

			request.setAttribute("alpl", alpl);

			// DBUserLog.addUserLog(userid,"列表借款");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
