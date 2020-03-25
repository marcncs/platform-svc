package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PayableTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try{
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pa.makeorganid");
			}
			
			String Condition = " 1=1 " + visitorgan;

			String[] tablename = { "Payable" };
			String whereSql = getWhereSql(tablename);
			String brur = getKeyWordCondition("POID");
			String timeCondition =getTimeCondition("MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str= new ArrayList();

			AppPayableObject aso = new AppPayableObject();
			AppPayable asod=new AppPayable(); 
			AppUsers au = new AppUsers();
			List pils = asod.getPayable(request,pagesize, whereSql);
			List sumobj = asod.getTotalSum(whereSql);
			double totalsum =0.00;
			double totalalreadysum =0.00;
		      for (Iterator it = pils.iterator(); it.hasNext();) {
		    	PayableForm rf = new PayableForm();
		    	Payable pb = (Payable)it.next();
		    	PayableObject ro = (PayableObject)aso.getPayableObjectByOIDOrgID(pb.getPoid(), pb.getMakeorganid());
		    	rf.setId(pb.getId());
		    	rf.setPoid(pb.getPoid());
		    	rf.setMakeorganid(pb.getMakeorganid());
//		    	rf.setMakeidname(au.getUsersByid(pb.getMakedeptid()).getRealname());
		    	rf.setMakedate(DateUtil.formatDateTime(pb.getMakedate()));
		    	rf.setPayabledescribe(pb.getPayabledescribe());
		    	rf.setPayablesum(pb.getPayablesum());
		    	rf.setAlreadysum(pb.getAlreadysum());     
		    	rf.setPayableobjectname(ro!=null?ro.getPayee():"");		        
		        str.add(rf);		        
		        totalsum += rf.getPayablesum();
		        totalalreadysum += rf.getAlreadysum();
		      }
		      request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
		      request.setAttribute("totalalreadysum", DataFormat.currencyFormat(totalalreadysum));			  
		      request.setAttribute("str", str);
		      
		      double allsum=0;
		      double allalreadysum=0;
		      if ( sumobj != null ){
		    	  Object[] ob = (Object[])sumobj.get(0);
		    	  allsum = Double.parseDouble(String.valueOf(ob[0]==null?"0":ob[0]));
		    	  allalreadysum = Double.parseDouble(String.valueOf(ob[1]==null?"0":ob[1]));
		      }
		      request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
		      request.setAttribute("allalreadysum", DataFormat.currencyFormat(allalreadysum));
		      

		      AppOrgan ao = new AppOrgan();
				List ols = ao.getAllOrgan();
				ArrayList alos = new ArrayList();

				for (int o = 0; o < ols.size(); o++) {
					OrganForm ub = new OrganForm();
					Organ of = (Organ) ols.get(o);
					ub.setId(of.getId());
					ub.setOrganname(of.getOrganname());
					alos.add(ub);
				}

				List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
				
				request.setAttribute("alos", alos);
				request.setAttribute("als", als);
	
				request.setAttribute("BeginDate", map.get("BeginDate"));
				request.setAttribute("EndDate", map.get("EndDate"));
				DBUserLog.addUserLog(userid, 10,"报表分析>>财务>>应付款结算单汇总");
		      return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
