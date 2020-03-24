package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class AddPayableObjectAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

	  super.initdata(request);
    try{
    	Integer objectsort=Integer.valueOf(request.getParameter("objectsort"));
    	String cid = request.getParameter("cid");
        if(cid==null||cid.equals("null")||cid.equals("")){
        	String result = "databases.upd.fail";
        	request.setAttribute("result", result);
        	return new ActionForward("/sys/lockrecord.jsp");
        }
    	
    	PayableObject ro = new PayableObject();
    	ro.setOid(cid);
    	ro.setObjectsort(objectsort);
    	ro.setPayee(request.getParameter("payee"));    
    	ro.setMakeorganid(users.getMakeorganid());
    	ro.setMakedeptid(users.getMakedeptid());
    	ro.setMakeid(userid);
    	ro.setMakedate(DateUtil.getCurrentDate());
    	
    	StringBuffer keyscontent = new StringBuffer();
    	keyscontent.append(ro.getPayee()).append(",");
    	
    	if ( objectsort == 1 ){
    		AppCustomer ac = new AppCustomer();
    		Customer c = ac.getCustomer(cid);
    		if ( c != null ){
    			keyscontent.append(c.getMobile()).append(",").append(c.getOfficetel()).append(",");
    		}
    	}
    	if ( objectsort == 2){
    		AppProvider ap = new AppProvider();
    		Provider p = ap.getProviderByID(cid);
    		if ( p != null ){
    			keyscontent.append(p.getMobile()).append(",");
    		}
    	}
    	ro.setKeyscontent(keyscontent.toString());

      AppPayableObject aro = new AppPayableObject();
      aro.noExistsToAdd(ro);

      request.setAttribute("result", "databases.add.success");

      DBUserLog.addUserLog(userid,9,"付款管理>>新增应付款对象,编号："+cid); 
      
      return mapping.findForward("addresult");
    }catch(Exception e){
      request.setAttribute("result", "databases.add.fail");
      e.printStackTrace();
    }

    return mapping.findForward("addresult");
  }
}
