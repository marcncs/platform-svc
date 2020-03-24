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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.drp.dao.CashBankForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListCashBankAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {

			String Condition =  getVisitOrgan("cb.makeorganid") + " ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "CashBank" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getBlur(map, tmpMap, "CBName");
			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"
			// MakeDate");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOrgan ao = new AppOrgan();
			AppCashBank apo = new AppCashBank();

			List pbls = apo.getCashBank(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();

			for (int i = 0; i < pbls.size(); i++) {
				CashBankForm pof = new CashBankForm();
				CashBank o = (CashBank) pbls.get(i);

				pof.setId(o.getId());
				pof.setCbname(o.getCbname());
				pof.setTotalsum(o.getTotalsum());
				pof.setMakeorganidname(ao.getOrganByID(o.getMakeorganid())
						.getOrganname());
				alpl.add(pof);
			}

			request.setAttribute("alpl", alpl);

			// DBUserLog.addUserLog(userid,"列表现金银行");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
