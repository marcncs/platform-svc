package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToAddSaleInvoiceAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  super.initdata(request);
    try{
//    	String invoicetypeselect = Internation.getSelectTagByKeyAll("InvoiceType", request,
//    	          "invoicetype", false, null);
    	
//   	
//    	AppDept ad = new AppDept();
//    	List dls = ad.getDept();
//        ArrayList aldept = new ArrayList();
//        for(int i=0;i<dls.size();i++){
//       	 Dept d = new Dept();
//       	 Object[] ob = (Object[]) dls.get(i);
//       	 d.setId(Long.valueOf(ob[0].toString()));
//       	 d.setDeptname(ob[1].toString());
//       	 aldept.add(d);
//        }
//        
//        AppUsers au = new AppUsers();
//        List uls = au.getIDAndLoginName();
//        ArrayList als = new ArrayList();
//        for(int u=0;u<uls.size();u++){
//        	UsersBean us = new UsersBean();
//        	Object[] ub = (Object[]) uls.get(u);
//        	us.setUserid(Long.valueOf(ub[0].toString()));
//        	us.setRealname(ub[2].toString());
//        	als.add(us);
//        }
    		
//    	request.setAttribute("invoicetypeselect", invoicetypeselect);
//    	request.setAttribute("aldept", aldept);
//    	request.setAttribute("als", als);
      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
