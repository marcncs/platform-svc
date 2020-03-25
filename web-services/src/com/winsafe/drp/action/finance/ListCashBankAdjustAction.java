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
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashBankAdjust;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CashBankAdjust;
import com.winsafe.drp.dao.CashBankAdjustForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListCashBankAdjustAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		String id = request.getParameter("id");
		if (id == null) {
			id = (String) request.getSession().getAttribute("cbid");
		} else {
			request.getSession().setAttribute("cbid", id);
		}

		try {

			String Condition = " (cba.makeid='" + userid + "' "
					+ getOrVisitOrgan("cba.makeorganid") + ") and cba.cbid="
					+ id + " ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "CashBankAdjust" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppCashBank acb = new AppCashBank();
			AppCashBankAdjust apo = new AppCashBankAdjust();
			AppUsers au = new AppUsers();

			List pbls = apo.getCashBankAdjust(request,pagesize, whereSql);
			ArrayList alpl = new ArrayList();
			CashBankAdjust cba = null;
			for (int i = 0; i < pbls.size(); i++) {
				cba = (CashBankAdjust) pbls.get(i);
				CashBankAdjustForm pof = new CashBankAdjustForm();

				pof.setId(cba.getId());
				pof.setCbid(cba.getCbid());
				pof.setCbidname(acb.getCashBankById(pof.getCbid()).getCbname());
				pof.setAdjustsum(cba.getAdjustsum());
				pof.setIsaudit(cba.getIsaudit());
				pof.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, pof.getIsaudit(),
						"global.sys.SystemResource"));
				pof.setMakeidname(au.getUsersByid(cba.getMakeid())
						.getRealname());
				pof.setMakedate(cba.getMakedate().toString().substring(0, 10));
				alpl.add(pof);
			}

			request.setAttribute("alpl", alpl);
			AppOrgan ao = new AppOrgan();
			List ols = ao.getOrganToDown(users.getMakeorganid());
			List uls = au.getIDAndLoginNameByOID2(users.getMakeorganid());

			request.setAttribute("ols", ols);
			request.setAttribute("uls", uls);

			 DBUserLog.addUserLog(userid,9,"现金银行>>列表现金银行调整");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
