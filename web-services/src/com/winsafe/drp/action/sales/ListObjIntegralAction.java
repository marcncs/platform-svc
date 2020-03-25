package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.IntegralIOService;
import com.winsafe.drp.dao.ObjIntegral;
import com.winsafe.drp.dao.ObjIntegralForm;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListObjIntegralAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		OrganService ao = new OrganService();
		try {
			//String begindate = request.getParameter("BeginDate");
			//String enddate = request.getParameter("EndDate");
			//request.getSession().setAttribute("BeginDate", begindate);
			//request.getSession().setAttribute("EndDate", enddate);

			String Condition=" (" +getVisitOrgan("oi.organid") +") ";

			String[] tablename = { "ObjIntegral" };

			String whereSql = getWhereSql(tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppObjIntegral aro = new AppObjIntegral();
			AppCustomer ac = new AppCustomer();

			List pbls = aro.getObjIntegral(request,pagesize, whereSql);
			ArrayList alpl = new ArrayList();
			
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");

			for (int i = 0; i < pbls.size(); i++) {
				ObjIntegralForm rf = new ObjIntegralForm();
				ObjIntegral o = (ObjIntegral) pbls.get(i);
				rf.setOiid(o.getOiid());
				rf.setOid(o.getOid());
				rf.setOsort(o.getOsort());

				if (rf.getOsort() == 0) {
					rf.setOname(ao.getOrganName(o.getOid()));
				}
				if (rf.getOsort() == 1) {
					rf.setOname(ac.getCustomer(o.getOid()).getCname());
				}
				rf.setOmobile(o.getOmobile());
				
				IntegralIOService iis = new IntegralIOService(rf.getOid(),users.getMakeorganid(),
						map.get("BeginDate").toString(), map.get("EndDate").toString());
				rf.setRvincome(iis.getRvIncome(timeCondition));
				rf.setAlincome(iis.getAlIncome(timeCondition));
				rf.setRvout(iis.getRvOut(timeCondition));
				rf.setAlout(iis.getAlOut(timeCondition));
				rf.setBalance(iis.getBalance());
				rf.setOrganid(o.getOrganid());

				alpl.add(rf);
			}
//
			request.setAttribute("alpl", alpl);
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			
			 DBUserLog.addUserLog(userid,5,"会员管理>>列表应收款对象");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
