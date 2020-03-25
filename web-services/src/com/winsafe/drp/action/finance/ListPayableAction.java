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
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.UsersService;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListPayableAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
    String poid=request.getParameter("POID");
    String orgid= request.getParameter("ORGID");
    if(poid==null){
    	poid=(String)request.getSession().getAttribute("poid");
    }
    if(orgid==null){
    	orgid = (String)request.getSession().getAttribute("orgid");
    }
    request.getSession().setAttribute("poid",poid);
    request.getSession().setAttribute("orgid", orgid);
    super.initdata(request);
    try {
//    	String Condition = " pa.poid ='"+poid+"' and pa.makeorganid='"+orgid+"' and pa.isclose=0 " ;
    	String Condition=" (pa.poid ='"+poid+"' " +getOrVisitOrgan("pa.makeorganid") +") and pa.isclose=0 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"Payable"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
      String blur = DbUtil.getOrBlur(map, tmpMap, "ID","BillNo");
      String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
      whereSql = whereSql + blur + timeCondition + Condition; 
      whereSql = DbUtil.getWhereSql(whereSql);

      AppPayable apa=new AppPayable();
      UsersService au = new UsersService();

      List pbls = apa.getPayable(request,pagesize, whereSql);
      ArrayList alpl = new ArrayList();

      for(int i=0;i<pbls.size();i++){
    	  PayableForm pf = new PayableForm();
    	  Payable o=(Payable)pbls.get(i);
        pf.setId(o.getId());
        pf.setPayablesum(DataFormat.dataFormat(o.getPayablesum())-o.getAlreadysum());
        pf.setBillno(o.getBillno());
        if(pf.getBillno().length()>0){
        	pf.setBn(pf.getBillno().substring(0,2));
        }else{
        	pf.setBn("");
        }
        pf.setMakeidname(au.getUsersName(o.getMakeid()));
        pf.setMakedate(DateUtil.formatDateTime(o.getMakedate()));
        pf.setAwakedate(o.getAwakedate());
        int a=0;
        if(o.getAwakedate()!=null){
        a= DateUtil.getDayDifference(DateUtil.getCurrentDateString(),DateUtil.formatDate(o.getAwakedate()));
        }
        if(a>0){
        	pf.setOverage(a);
        }

        pf.setPayabledescribe(o.getPayabledescribe());
        alpl.add(pf);
      }


        request.setAttribute("poid", poid);
      request.setAttribute("alpl",alpl);

      //DBUserLog.addUserLog(userid,"列表应付款"); 
      return mapping.findForward("payable");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
