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
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class UpdPayableObjectAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

    super.initdata(request);super.initdata(request);try{
    	String oid = request.getParameter("OID");
    	String orgid= request.getParameter("ORGID");
      AppPayableObject apo = new AppPayableObject();
      PayableObject po = apo.getPayableObjectByOIDOrgID(oid, orgid);
      //po.setOid(request.getParameter("oid"));
      po.setObjectsort(Integer.valueOf(request.getParameter("objectsort")));
      
      String payeeid = request.getParameter("cid");
      if(payeeid==null||payeeid.equals("null")||payeeid.equals("")){
      	String result = "databases.upd.fail";
      	request.setAttribute("result", result);
      	return new ActionForward("/sys/lockrecord.jsp");
      }      
      po.setPayee(payeeid);
      
      StringBuffer keyscontent = new StringBuffer();
      keyscontent.append(po.getPayee()).append(",");
		
		if ( po.getObjectsort() == 1 ){
			AppCustomer ac = new AppCustomer();
			Customer c = ac.getCustomer(po.getOid());
			if ( c != null ){
				keyscontent.append(c.getMobile()).append(",").append(c.getOfficetel()).append(",");
			}
		}
		if ( po.getObjectsort() == 2){
			AppProvider ap = new AppProvider();
			Provider p = ap.getProviderByID(po.getOid());
			if ( p != null ){
				keyscontent.append(p.getMobile()).append(",");
			}
		}
		po.setKeyscontent(keyscontent.toString());

      apo.updPayableObject(po);

      request.setAttribute("result", "databases.upd.success");

      UsersBean users = UserManager.getUser(request);

//      DBUserLog.addUserLog(userid,"修改应付款对象"); 

      return mapping.findForward("updresult");
    }
    catch (Exception e) {

      e.printStackTrace();
    }
    finally {

    }

    return new ActionForward(mapping.getInput());
  }
}
