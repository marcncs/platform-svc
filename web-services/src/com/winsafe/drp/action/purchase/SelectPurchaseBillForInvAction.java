package com.winsafe.drp.action.purchase;

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

import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectPurchaseBillForInvAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		String pid = request.getParameter("pid");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {

			String Condition = " pb.makeorganid like '%"+1+"%' and pb.pid='"+pid+"' and pb.ismaketicket=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = {"PurchaseBill"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
			"MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "ID");

			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseBill apb = new AppPurchaseBill();
			List pls = apb.getPurchaseBill(request,pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				PurchaseBillForm sof = new PurchaseBillForm();
				PurchaseBill o = (PurchaseBill) pls.get(i);
				sof.setId(o.getId());
				sof.setTotalsum(o.getTotalsum());
				sof.setMakedate(o.getMakedate());
				sls.add(sof);
			}

			request.setAttribute("sls", sls);
			request.setAttribute("pid", pid);

			return mapping.findForward("selectso");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
