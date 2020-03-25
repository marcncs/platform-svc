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

public class SelectPurchaseBillAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		UsersBean users = UserManager.getUser(request);
		try {
			String findCondition = " pb.makeorganid='"+users.getMakeorganid()+"' and pb.isratify=1 and pb.istransferadsum=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PurchaseBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" ReceiveDate");
			whereSql = whereSql + timeCondition + findCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppPurchaseBill app = new AppPurchaseBill();
			List pals = app.getPurchaseBill(request,pagesize, whereSql);
			ArrayList alpa = new ArrayList();

			for (int i = 0; i < pals.size(); i++) {
				PurchaseBillForm pbf = new PurchaseBillForm();
				PurchaseBill o = (PurchaseBill) pals.get(i);
				pbf.setId(o.getId());
				pbf.setPname(o.getPname());
				pbf.setReceivedate(o.getReceivedate());
				pbf.setPaymode(o.getPaymode());
				pbf.setMakeid(o.getMakeid());

				alpa.add(pbf);
			}

			request.setAttribute("alpa", alpa);

			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
