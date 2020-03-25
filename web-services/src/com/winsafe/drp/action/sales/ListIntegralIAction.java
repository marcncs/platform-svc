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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppIntegralI;
import com.winsafe.drp.dao.IntegralI;
import com.winsafe.drp.dao.IntegralIForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListIntegralIAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);

		try {

			String Condition=" (" +getVisitOrgan("ii.equiporganid") + getOrVisitOrgan("ii.organid")+") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IntegralI" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "oid","oname","omobile","billno");

			whereSql = whereSql + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			OrganService ao = new OrganService();
			AppCustomer ac = new AppCustomer();
			AppIntegralI aii = new AppIntegralI();

			List iils = aii.getIntegralI(request,pagesize, whereSql);
			ArrayList ails = new ArrayList();
			for (int i = 0; i < iils.size(); i++) {
				IntegralIForm iif = new IntegralIForm();
				IntegralI o = (IntegralI) iils.get(i);
				iif.setId(o.getId());
				iif.setOsort(o.getOsort());
				iif.setOid(o.getOid());
				if (iif.getOsort() == 0) {
					iif.setOname(ao.getOrganName(o.getOid()));
				}
				if (iif.getOsort() == 1) {
					iif.setOname(ac.getCustomer(o.getOid()).getCname());
				}
				iif.setOmobile(o.getOmobile());
				iif.setBillno(o.getBillno());
				iif.setIsort(o.getIsort());
				iif.setRincome(o.getRincome());
				iif.setAincome(o.getAincome());
				iif.setMakedate(o.getMakedate());
				iif.setEquiporganid(o.getEquiporganid());
				iif.setOrganid(o.getOrganid());
				ails.add(iif);
				
			}

			request.setAttribute("iils", ails);
			DBUserLog.addUserLog(userid,5, "积分收入>>列表积分收入");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
