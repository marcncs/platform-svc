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
import com.winsafe.drp.dao.AppIntegralO;
import com.winsafe.drp.dao.IntegralO;
import com.winsafe.drp.dao.IntegralOForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListIntegralOAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);

		try {

			String Condition=" (" +getVisitOrgan("io.equiporganid") + getOrVisitOrgan("io.organid")+") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IntegralO" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "oid","oname","omobile","billno");

			whereSql = whereSql + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			OrganService ao = new OrganService();
			AppCustomer ac = new AppCustomer();
			AppIntegralO aii = new AppIntegralO();

			List iils = aii.getIntegralO(request,pagesize, whereSql);
			ArrayList ails = new ArrayList();
			for (int i = 0; i < iils.size(); i++) {
				IntegralOForm iif = new IntegralOForm();
				IntegralO o = (IntegralO) iils.get(i);
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
				iif.setRout(o.getRout());
				iif.setAout(o.getAout());
				iif.setMakedate(o.getMakedate());
				iif.setEquiporganid(o.getEquiporganid());
				iif.setOrganid(o.getOrganid());
				ails.add(iif);
				
			}

			request.setAttribute("iils", ails);
			DBUserLog.addUserLog(userid, 5, "积分支出>>列表积分支出");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
