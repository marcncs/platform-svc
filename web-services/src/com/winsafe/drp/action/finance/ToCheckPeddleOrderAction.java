package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ToCheckPeddleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		

		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		super.initdata(request);try{
			
			String visitorgan = "";
			if(users.getVisitorgan()!=null&&users.getVisitorgan().length()>0){
				visitorgan = " or so.makeorganid in("+users.getVisitorgan()+")";
			}
			
//			String Condition = " (so.makeid="+userid+" "+visitorgan+") and (so.paymentmode=0 or so.paymentmode=1) and so.isdaybalance=0  and so.isblankout=0 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PeddleOrder" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
//			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppPeddleOrder appo = new AppPeddleOrder();
			List list = appo.searchPeddleOrder(whereSql);
			
			double xianjin = 0.00;
			double shuaka = 0.00;
			StringBuffer poids = new StringBuffer();
			for ( int i=0; i<list.size(); i++ ){
				PeddleOrder po = (PeddleOrder)list.get(i);
				
				if ( po.getPaymentmode() == 0 ){
					xianjin += po.getTotalsum();
				}else if ( po.getPaymentmode() == 1 ){
					shuaka += po.getTotalsum();
				}
				if ( i== 0){
					poids.append(po.getId());
				}else{
					poids.append(",").append(po.getId());
				}
			}
			
			AppCashBank appcb = new AppCashBank();
		    List cblist = appcb.getAllCashBankByOID(users.getMakeorganid());
		    
			request.setAttribute("xianjin", xianjin);
			request.setAttribute("shuaka", shuaka);
			request.setAttribute("poids", poids);
			request.setAttribute("cblist", cblist);
			
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			String MakeOrganID = request.getParameter("MakeOrganID");
			AppOrgan ao = new AppOrgan();
			request.setAttribute("MakeOrgan", ao.getOrganByID(MakeOrganID).getOrganname());
			Long MakeID = Long.valueOf(request.getParameter("MakeID"));
			AppUsers au = new AppUsers();
//			request.setAttribute("MakeUser", au.getUsersByid(MakeID).getRealname());
			
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
