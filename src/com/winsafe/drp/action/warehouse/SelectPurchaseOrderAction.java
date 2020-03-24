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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectPurchaseOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);try{
			String findCondition = " po.approvestatus=2 and po.isendcase=0 and po.isblankout=0 and po.ischange=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from sale_log as sl where "+findCondition;
			String[] tablename = { "PurchaseOrder" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + findCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, " PurchaseOrder as po ",
					whereSql, pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			// AppCustomer ac = new AppCustomer();
			AppUsers au = new AppUsers();
			AppCustomer ap = new AppCustomer();
			AppDept ad = new AppDept();
			AppPurchaseOrder app = new AppPurchaseOrder();
			List pals = app.searchPurchaseOrder(pagesize, whereSql, tmpPgInfo);
			ArrayList alpa = new ArrayList();

			PurchaseOrder po = null;
			for (int i = 0; i < pals.size(); i++) {
				po = (PurchaseOrder) pals.get(i);
				PurchaseOrderForm pof = new PurchaseOrderForm();
				pof.setId(po.getId());
				pof.setProvidename(ap.getCustomer(po.getPid()).getCname());
				pof.setPlinkman(po.getPlinkman());
				pof.setPurchasedeptname(ad.getDeptByID(po.getPurchasedept())
						.getDeptname());
				pof
						.setMakeidname(au.getUsersByid(po.getMakeid())
								.getRealname());
				pof.setMakedate(DateUtil.formatDate(po.getMakedate()));
				pof.setBatch(po.getBatch());
				alpa.add(pof);
			}

			request.setAttribute("alpa", alpa);

			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
