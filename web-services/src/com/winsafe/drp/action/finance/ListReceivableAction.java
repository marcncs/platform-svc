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
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.UsersService;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListReceivableAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  super.initdata(request);
    int pagesize = 10;
    String roid=request.getParameter("ROID");
    String orgid = request.getParameter("ORGID");
    if(roid==null){
    	roid=(String)request.getSession().getAttribute("roid");
    }
    if(orgid==null){
		orgid = (String)request.getSession().getAttribute("orgid");
    }
    request.getSession().setAttribute("roid",roid);
    request.getSession().setAttribute("orgid", orgid);
    
    try {
      String Condition=" (r.roid ='"+roid +"' " +getOrVisitOrgan("r.makeorganid") +") and r.isclose=0 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"Receivable"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
      String blur = DbUtil.getOrBlur(map, tmpMap, "ID","BillNo");
      String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
      whereSql = whereSql  +timeCondition + blur + Condition; 
      whereSql = DbUtil.getWhereSql(whereSql); 

      AppReceivable ar=new AppReceivable();
      UsersService au = new UsersService();
      List pbls = ar.getReceivable(request,pagesize, whereSql);
      ArrayList alpl = new ArrayList();
      for(int i=0;i<pbls.size();i++){
    	  ReceivableForm rf = new ReceivableForm();
    	  Receivable o=(Receivable)pbls.get(i);
        rf.setId(o.getId());
        rf.setReceivablesum(DataFormat.dataFormat(o.getReceivablesum()-o.getAlreadysum()));
        rf.setBillno(o.getBillno());
        if(rf.getBillno().length()>0){
        	rf.setBn(rf.getBillno().substring(0,2));
        }else{
        	rf.setBn("");
        }
        rf.setReceivabledescribe(o.getReceivabledescribe());
        rf.setMakeidname(au.getUsersName(o.getMakeid()));
        rf.setMakedate(DateUtil.formatDateTime(o.getMakedate()));
        rf.setAwakedate(o.getAwakedate());
        int a=0;
        if(o.getAwakedate()!=null){
        a= DateUtil.getDayDifference(DateUtil.getCurrentDateString(),DateUtil.formatDate(o.getAwakedate()));
        }
        if(a>0){
        	rf.setOverage(a);
        }
        
        alpl.add(rf);
      }

      request.setAttribute("roid", roid);
      request.setAttribute("alpl",alpl);

      //DBUserLog.addUserLog(userid,"列表应收款"); 
      return mapping.findForward("receivable");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
