package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppShipmentBillApprove;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DbUtil;

public class ToReferSaleShipmentAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String sbid = request.getParameter("SBID");

    try{
    	if(DbUtil.judgeApproveStatusToRefer("ShipmentBill", sbid)){
       	 String result = "databases.record.approvestatus";
            request.setAttribute("result", result);
            return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
        }	
      AppUsers au = new AppUsers();
      List uls = au.getIDAndLoginName();
      ArrayList auls = new ArrayList();
      for(int i=0;i<uls.size();i++){
        UsersBean ub = new UsersBean();
        Object[] o = (Object[])uls.get(i);
//        ub.setUserid(Long.valueOf(o[0].toString()));
        ub.setLoginname(o[1].toString());
        ub.setRealname(o[2].toString());
        auls.add(ub);
      }

      AppShipmentBillApprove asba = new AppShipmentBillApprove();
      List alrd = asba.getShipmentBillApprove(sbid);
      ArrayList alls = new ArrayList();
      for(int l=0;l<alrd.size();l++){
        UsersBean alub = new UsersBean();
        Object[] ob = (Object[])alrd.get(l);
//        alub.setUserid(Long.valueOf(ob[2].toString()));
        alub.setLoginname(au.getUsersByID(alub.getUserid()).getLoginname());
        alub.setRealname(au.getUsersByID(alub.getUserid()).getRealname());
        alls.add(alub);
      }

      request.setAttribute("sbid",sbid);
      request.setAttribute("alls",alls);
      request.setAttribute("auls",auls);

      return mapping.findForward("toselect");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
