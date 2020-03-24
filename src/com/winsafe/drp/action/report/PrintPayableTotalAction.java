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
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PrintPayableTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pa.makeorganid");
			}
			String Condition = " 1=1 " + visitorgan;

			String[] tablename = {"Payable"};
			String whereSql = getWhereSql(tablename);
			String brur = getKeyWordCondition("POID");
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str= new ArrayList();

			AppPayableObject aso = new AppPayableObject();
			AppPayable asod=new AppPayable(); 
			List pils = asod.getPayable(whereSql);
			List sumobj = asod.getTotalSum(whereSql);
		      for (Iterator it = pils.iterator(); it.hasNext();) {
		    	  PayableForm rf = new PayableForm();
		    	  Payable pb = (Payable)it.next();
		    	  PayableObject ro = (PayableObject)aso.getPayableObjectByOIDOrgID(pb.getPoid(), pb.getMakeorganid());
		    	rf.setId(pb.getId());
		    	rf.setPoid(pb.getPoid());
		    	rf.setMakeorganid(pb.getMakeorganid());
		    	rf.setMakeid(pb.getMakeid());
		    	rf.setMakedate(DateUtil.formatDateTime(pb.getMakedate()));
		    	rf.setPayabledescribe(pb.getPayabledescribe());
		    	rf.setPayablesum(pb.getPayablesum());
		    	rf.setAlreadysum(pb.getAlreadysum());     
		    	rf.setPayableobjectname(ro!=null?ro.getPayee():"");		        
		        str.add(rf);		        
		      }
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
		      DBUserLog.addUserLog(userid, 10,"报表分析>>打印应付款结算单汇总");
			return mapping.findForward("toprint");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
