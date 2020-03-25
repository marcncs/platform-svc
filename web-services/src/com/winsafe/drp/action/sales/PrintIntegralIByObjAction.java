package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintIntegralIByObjAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);

		try {
			request.setAttribute("OID", request.getParameter("OID"));
			request.setAttribute("OSort",request.getParameter("OSort"));
//			String Condition=" (" +getVisitOrgan("oi.organid") +") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IntegralI" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");

			whereSql = whereSql + blur;// + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppIntegralI aii = new AppIntegralI();

			List iils = aii.getIntegralI(whereSql);
			
			Integer sort  = Integer.valueOf(request.getParameter("OSort"));
			String oid  = request.getParameter("OID");
			String name ="";
			if(sort==0){
				OrganService os = new OrganService();
				 name = os.getOrganName(oid);
			}else if(sort==1){
				AppCustomer appc = new AppCustomer();
				name = appc.getCName(oid);
			}else if(sort==2){
				AppProvider appp = new AppProvider();
				name = appp.getPName(oid);
				
			}else{
				UsersService us = new UsersService();
				name = us.getUsersName(Integer.valueOf(oid));
			}
			request.setAttribute("name", name);
			request.setAttribute("iils", iils);
			DBUserLog.addUserLog(userid,5, "积分收入>>打印收入列表");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
