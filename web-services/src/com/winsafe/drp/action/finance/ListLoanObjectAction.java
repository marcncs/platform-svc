package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppLoan;
import com.winsafe.drp.dao.AppLoanObject;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.LoanObject;
import com.winsafe.drp.dao.LoanObjectForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListLoanObjectAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
    super.initdata(request);
    try {

		String Condition=" (lo.makeid="+userid+" " +getOrVisitOrgan("lo.makeorganid") +") ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"LoanObject"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
      String timeCondition = DbUtil.getTimeCondition(map, tmpMap," PromiseDate");
      whereSql = whereSql +timeCondition+Condition ; 
      whereSql = DbUtil.getWhereSql(whereSql); 


      AppLoanObject apo=new AppLoanObject();
      AppLoan al = new AppLoan();
      AppReckoning ar = new AppReckoning();
      AppUsers au = new AppUsers();

      List pbls = apo.getLoanObject(request,pagesize, whereSql);
      ArrayList alpl = new ArrayList();

       String promisedate="";

      for(int i=0;i<pbls.size();i++){
    	  LoanObjectForm pof = new LoanObjectForm();
    	  LoanObject o=(LoanObject)pbls.get(i);

        pof.setId(o.getId());
        pof.setUid(o.getUid());
        pof.setUidname(au.getUsersByid(pof.getUid()).getRealname());
		pof.setTotalloansum(al.getLoanSumByUID(pof.getUid()));
		pof.setTotalbacksum(ar.getReckoningSumByUID(pof.getUid()));
		pof.setWaitbacksum(pof.getTotalloansum()-pof.getTotalbacksum());
		 promisedate = String.valueOf(o.getPromisedate());
	        if(promisedate !=null && !promisedate.equals("null") && promisedate.length()>0){
	        	pof.setPromisedate(promisedate.substring(0,10));
	        }else{
	        	pof.setPromisedate("");
	        }
        alpl.add(pof);
      }
      

//		AppOrgan ao = new AppOrgan();
//		List ols = ao.getOrganToDown(users.getMakeorganid());		    
//		List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
		
//		request.setAttribute("als", als);
//		request.setAttribute("ols", ols);
		request.setAttribute("alpl",alpl);
      
      //DBUserLog.addUserLog(userid,"列表借款对象"); 
      return mapping.findForward("listobj");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
