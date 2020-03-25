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
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectReceivableAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		super.initdata(request);
		try {
			String roid = request.getParameter("roid");
			String Condition = " r.roid='" + roid + "'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from income_log as il where "+Condition;
			String[] tablename = { "Receivable" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"
			// MakeDate");

			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, "Receivable as r",
					whereSql, pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppReceivable aro = new AppReceivable();			
			AppUsers au = new AppUsers();		
			

			List pbls = aro.searchReceivable(pagesize, whereSql, tmpPgInfo);
			ArrayList alpl = new ArrayList();
			for (int i = 0; i < pbls.size(); i++) {
				Receivable r = (Receivable) pbls.get(i);
				ReceivableForm rf = new ReceivableForm();
				rf.setId(r.getId());
				rf.setPaymentmode(r.getPaymentmode());
				rf.setPaymentmodename(Internation.getStringByPayPositionDB(r.getPaymentmode()));

				rf.setBillno(r.getBillno());
				rf.setReceivablesum(r.getReceivablesum() - r.getAlreadysum());
				rf.setMakeidname(au.getUsersByid(r.getMakeid())
						.getRealname());
				rf.setMakedate(DateUtil.formatDate(r.getMakedate()));
				alpl.add(rf);
			}

			String paymentmode = Internation.getSelectPayByAllDB(
					"paymentmode", true);

			request.setAttribute("paymentmode", paymentmode);
			request.setAttribute("alpl", alpl);

			return mapping.findForward("select");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
