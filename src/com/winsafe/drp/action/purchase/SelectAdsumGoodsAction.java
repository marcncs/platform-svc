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

import com.winsafe.drp.dao.AdsumGoodsForm;
import com.winsafe.drp.dao.AppAdsumGoods;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectAdsumGoodsAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		try {
			String findCondition = " ag.isaudit=1 and ag.istransferincome=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from sale_log as sl where "+findCondition;
			String[] tablename = { "AdsumGoods" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" ReceiveDate");
			whereSql = whereSql + timeCondition + findCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, " AdsumGoods as ag ",
					whereSql, pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppCustomer apv = new AppCustomer();
			AppDept ad = new AppDept();
			AppAdsumGoods app = new AppAdsumGoods();
			List pals = app.getAdsumGoodsToQuality(pagesize, whereSql,
					tmpPgInfo);
			ArrayList alpa = new ArrayList();

			for (int i = 0; i < pals.size(); i++) {
				AdsumGoodsForm ppf = new AdsumGoodsForm();
				Object[] o = (Object[]) pals.get(i);
				ppf.setId(o[0].toString());
				ppf.setPidname(apv.getCustomer(o[1].toString()).getCname());
				ppf.setObtaincode(String.valueOf(o[2]));
				ppf.setReceivedate(o[3].toString().substring(0,10));
				ppf.setTotalsum(Double.valueOf(o[4].toString()));
				
				alpa.add(ppf);
			}

			request.setAttribute("alpa", alpa);

			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
