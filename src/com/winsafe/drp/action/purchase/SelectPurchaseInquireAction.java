package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseInquireForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectPurchaseInquireAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		try {
			String findCondition = " pi.isaudit=1 and pi.iscaseend=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from sale_log as sl where "+findCondition;
			String[] tablename = { "PurchaseInquire" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " MakeDate");
			whereSql = whereSql +timeCondition+ findCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, " PurchaseInquire as pi ",
					whereSql, pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			// AppCustomer ac = new AppCustomer();
			AppUsers au = new AppUsers();
			//AppDept ad = new AppDept();
			AppProvider ap = new AppProvider();
			AppPurchaseInquire app = new AppPurchaseInquire();
			List pals = app.getPurchaseInquireToPurchase(pagesize, whereSql,
					tmpPgInfo);
			ArrayList alpa = new ArrayList();

			for (int i = 0; i < pals.size(); i++) {
				PurchaseInquireForm pif = new PurchaseInquireForm();
				Object[] o = (Object[]) pals.get(i);
				pif.setId(Integer.valueOf(o[0].toString()));
				pif.setInquiretitle(o[2].toString());
				pif.setProvidename(ap.getProviderByID(o[3].toString()).getPname());
				pif.setPlinkman(String.valueOf(o[4]));
				pif.setMakeid(Integer.valueOf(o[5].toString()));
				pif.setMakedate((Date)o[6]);
				pif.setValiddate(Integer.valueOf(o[7].toString()));

				alpa.add(pif);
			}

			request.setAttribute("alpa", alpa);

			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
