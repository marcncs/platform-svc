package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class AddReceivableObjectAction extends BaseAction{

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
    	String payer = request.getParameter("payer");
    	ReceivableObject ro = new ReceivableObject();
    	ro.setOid(cid);
    	ro.setObjectsort(objectsort);
    	ro.setPayer(payer);
    	ro.setMakeorganid(users.getMakeorganid());
    	ro.setMakedeptid(users.getMakedeptid());
    	ro.setMakeid(userid);
    	ro.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
    	
    	StringBuffer keyscontent = new StringBuffer();
    	keyscontent.append(ro.getPayer()).append(",");

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
  

      AppReceivableObject aro = new AppReceivableObject();
      aro.addReceivableObjectIsNoExist(ro);

      request.setAttribute("result", "databases.add.success");

      DBUserLog.addUserLog(userid,9,"收款管理>>新增应收款对象,编号："+cid); 
      return mapping.findForward("addresult");
    }catch(Exception e){
      String result = "databases.add.fail";
      request.setAttribute("result",result);
      e.printStackTrace();
    }

    return mapping.findForward("addresult");
  }
}
