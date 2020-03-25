package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.drp.dao.PeddleOrderDetailForm;
import com.winsafe.drp.dao.PeddleOrderForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;
public class ToUpdPeddleOrderAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    
    
//    Long userid = usersBean.getUserid();
    try {

      AppPeddleOrder aso = new AppPeddleOrder();
      //AppLinkMan appLinkMan=new AppLinkMan();
      PeddleOrder so = new PeddleOrder();
      //AppCustomer ac = new AppCustomer();
      AppUsers au = new AppUsers();
      AppWarehouse aw = new AppWarehouse();
      so = aso.getPeddleOrderByID(id);
      if(so.getIsblankout()==1){
          String result = "databases.record.blankoutnooperator";
          request.setAttribute("result",result);
          return mapping.findForward("lock");
        }
      
//      if(so.getIsaudit()==1){
//        String result = "databases.record.lock";
//        request.setAttribute("result",result);
//        return mapping.findForward("lock");
//      }
      
      PeddleOrderForm sof= new PeddleOrderForm();
      sof.setId(so.getId());
//      sof.setCustomerbillid(so.getCustomerbillid());
      sof.setCid(so.getCid());
      sof.setCname(so.getCname());
      sof.setCmobile(so.getCmobile());
      sof.setReceiveman(so.getReceiveman());
      sof.setReceivetel(so.getReceivetel());
//      sof.setPeddledept(so.getPeddledept());
//      sof.setPeddleid(so.getPeddleid());
      //sof.setPaymentmodename(Internation.getSelectTagByKeyAll("PaymentMode",request,"paymentmode",String.valueOf(so.getPaymentmode()),null));
      sof.setInvmsg(so.getInvmsg());
//      sof.setConsignmentdate(DateUtil.formatDate(so.getConsignmentdate()));
//      sof.setTransportmodename(Internation.getSelectTagByKeyAllDBDef("TransportMode", "transportmode",
//    		  so.getTransportmode()));
//      sof.setTransitname(Internation.getSelectTagByKeyAllDBDef("Transit", "transit",
//    		  so.getTransit()));
//      sof.setTransportaddr(so.getTransportaddr());
      sof.setRemark(so.getRemark());
      sof.setTotalsum(so.getTotalsum());


      AppPeddleOrderDetail asld = new AppPeddleOrderDetail();
      List slls = asld.getPeddleOrderDetailObjectByPOID(id);
      ArrayList als = new ArrayList();
      AppProduct ap = new AppProduct();

      PeddleOrderDetail sod = null;   
      for(int i=0;i<slls.size();i++){
    	  sod = (PeddleOrderDetail)slls.get(i);
        PeddleOrderDetailForm sodf = new PeddleOrderDetailForm();        
        sodf.setProductid(sod.getProductid());
        sodf.setProductname(sod.getProductname());
        sodf.setSpecmode(sod.getSpecmode());
        sodf.setWarehouseid(sod.getWarehouseid());
        if ( null != sod.getWarehouseid() && sod.getWarehouseid() > 0){
//        	sodf.setWarehouseidname(aw.getWarehouseByID(sod.getWarehouseid()).getWarehousename());   
        }
        sodf.setUnitid(sod.getUnitid());
        sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",           
            Integer.parseInt(sod.getUnitid().toString())));
        sodf.setUnitprice(sod.getUnitprice());
        sodf.setQuantity(sod.getQuantity());
        sodf.setDiscount(sod.getDiscount());
        sodf.setTaxrate(sod.getTaxrate());
        sodf.setBatch(sod.getBatch());
        sodf.setCost(sod.getCost());        
        sodf.setSubsum(sod.getSubsum());
//        sodf.setWise(ap.getProductByID(sod.getProductid()).getWise());
        als.add(sodf);
      }
      
  	AppDept ad = new AppDept();
//	List dls = ad.getDept();
    ArrayList aldept = new ArrayList();
//    for(int i=0;i<dls.size();i++){
//   	 Dept d = new Dept();
//   	 Object[] ob = (Object[]) dls.get(i);
//   	 d.setId(Long.valueOf(ob[0].toString()));
//   	 d.setDeptname(ob[1].toString());
//   	 aldept.add(d);
//    }
    
	List uls = au.getIDAndLoginName();
	ArrayList userlist = new ArrayList();
	for (int u = 0; u < uls.size(); u++) {
		UsersBean ubs = new UsersBean();
		Object[] ub = (Object[]) uls.get(u);
//		ubs.setUserid(Long.valueOf(ub[0].toString()));
		ubs.setRealname(ub[2].toString());
		userlist.add(ubs);
	}
	
	AppInvoiceConf aic = new AppInvoiceConf();
	List vls = aic.getAllInvoiceConf();
	ArrayList icls = new ArrayList();
	for (int u = 0; u < vls.size(); u++) {
			InvoiceConf ic = (InvoiceConf) vls.get(u);

			icls.add(ic);
	}
	
	request.setAttribute("icls", icls);
 

      request.setAttribute("sof",sof);
      request.setAttribute("als",als);
      request.setAttribute("aldept", aldept);  
      request.setAttribute("userlist", userlist);
      return mapping.findForward("toupd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
  
}
