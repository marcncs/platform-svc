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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.PayableObjectForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectPayableObjectAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;

    super.initdata(request);
    try {
    	String visitorgan = "";
		if (users.getVisitorgan() != null
				&& users.getVisitorgan().length() > 0) {
			visitorgan = " or po.makeorganid in(" + users.getVisitorgan()
					+ ")";
		}
		String Condition = " (po.makeid=" + userid + " " + visitorgan
				+ ") ";
    	
    	//String Condition = " po.makeid like '"+userid+"%'";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from provide ";
      String[] tablename={"PayableObject"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
      String blur = DbUtil.getBlur(map, tmpMap, "Payee"); 
      whereSql = whereSql +blur + Condition; 
      whereSql = DbUtil.getWhereSql(whereSql); 

      Object obj[] = (DbUtil.setPager(request, "PayableObject as po", whereSql,
					pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
      
      AppPayableObject apo = new AppPayableObject();
      AppOrgan ao = new AppOrgan();
      List pls = apo.getPayableObject(request,pagesize,whereSql);
      ArrayList sls = new ArrayList();

      for(int i=0;i<pls.size();i++){
    	  PayableObject po = (PayableObject)pls.get(i);
    	  PayableObjectForm pof = new PayableObjectForm();
        
        pof.setId(po.getId());
        pof.setOid(po.getOid());
        pof.setObjectsort(po.getObjectsort());
        pof.setPayee(po.getPayee());
        pof.setObjectsortname(Internation.getStringByKeyPosition("ObjectSort",
                request,
                po.getObjectsort(), "global.sys.SystemResource"));
        pof.setMakeorganidname(ao.getOrganByID(po.getMakeorganid()).getOrganname());
//        if (pof.getObjectsort() == 0) {
//        	pof.setPayee(au.getUsersByid(Long.valueOf(o[0].toString()))
//					.getRealname());
//		}
//		if (pof.getObjectsort() == 1) {
//			pof.setPayee(ac.getCustomer(o[0].toString())
//					.getCname());
//		}
//		if (pof.getObjectsort() == 2) {
//			pof.setPayee(ap.getProvideByID(o[0].toString())
//					.getPname());
//		}
//		pof.setTotalpayablesum(apb.getPayableSumByPOID(id));
//		pof.setAlreadypayablesum(apl.getPaymentSumByPOID(id));
//		pof.setWaitpayablesum(pof.getTotalpayablesum()-pof.getAlreadypayablesum());
        sls.add(pof);
      }
      
      List ols = ao.getOrganToDown(users.getMakeorganid());

		request.setAttribute("ols",ols);
      
      String objectsortselect =Internation.getSelectTagByKeyAll("ObjectSort",
              request,
              "ObjectSort", true,null);

        request.setAttribute("objectsortselect",objectsortselect);

      request.setAttribute("sls",sls);
      return mapping.findForward("selectpo");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
