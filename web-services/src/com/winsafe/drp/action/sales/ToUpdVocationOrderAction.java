package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.SaleOrderDetailForm;
import com.winsafe.drp.dao.VocationOrder;
import com.winsafe.drp.dao.VocationOrderDetail;
import com.winsafe.drp.dao.VocationOrderForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
public class ToUpdVocationOrderAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    
    
//    Long userid = usersBean.getUserid();
    try {

      AppVocationOrder aso = new AppVocationOrder();
      VocationOrder so = new VocationOrder();

      AppWarehouse aw = new AppWarehouse();
      so = aso.getVocationOrderByID(id);
      if(so.getIsblankout()==1){
          String result = "databases.record.blankoutnooperator";
          request.setAttribute("result",result);
          return mapping.findForward("lock");
        }
      
      if(so.getIsaudit()==1){
        String result = "databases.record.lock";
        request.setAttribute("result",result);
        return mapping.findForward("lock");
      }
      VocationOrderForm sof= new VocationOrderForm();
      sof.setId(so.getId());
      sof.setCustomerbillid(so.getCustomerbillid());
      sof.setCid(so.getCid());
      sof.setCname(so.getCname());
      sof.setReceiveman(so.getReceiveman());
      
  	sof.setCmobile(so.getCmobile());	
	sof.setDecideman(so.getDecideman());
	sof.setDecidemantel(so.getDecidemantel());	
	sof.setReceiveman(so.getReceiveman());
	sof.setReceivemobile(so.getReceivemobile());
	sof.setReceivetel(so.getReceivetel());	
	sof.setInvmsg(so.getInvmsg());
	sof.setEquiporganid(so.getEquiporganid());
	
      sof.setSaledept(so.getSaledept());
      sof.setSaleid(so.getSaleid());
      sof.setPaymentmodename(Internation.getSelectPayAllDBDef("paymentmode",so.getPaymentmode()));
      sof.setConsignmentdate(DateUtil.formatDate(so.getConsignmentdate()));
      sof.setConsignmenttime(DateUtil.formatTime(so.getConsignmentdate()));
      sof.setTransportmodename(Internation.getSelectTagByKeyAllDBDef("TransportMode", "transportmode",
    		  so.getTransportmode()));
      sof.setSourcename(Internation.getSelectTagByKeyAllDBDef("Source", "source",
    		  so.getSource()));
      sof.setTransitname(Internation.getSelectTagByKeyAllDBDef("Transit", "transit",
    		  so.getTransit()));
      sof.setTickettitle(so.getTickettitle());
      sof.setTransportaddr(so.getTransportaddr());
      sof.setRemark(so.getRemark());
      sof.setTotalsum(so.getTotalsum());


      AppVocationOrderDetail asld = new AppVocationOrderDetail();
      List slls = asld.getVocationOrderDetailObjectBySOID(id);
      ArrayList als = new ArrayList();
      AppProduct ap = new AppProduct();

      VocationOrderDetail sod = null;   
      for(int i=0;i<slls.size();i++){
    	  sod = (VocationOrderDetail)slls.get(i);
        SaleOrderDetailForm sodf = new SaleOrderDetailForm();        
        sodf.setProductid(sod.getProductid());
        sodf.setProductname(sod.getProductname());
        sodf.setSpecmode(sod.getSpecmode());
//        sodf.setWarehouseid(sod.getWarehouseid());
        if ( null != sod.getWarehouseid() && sod.getWarehouseid() > 0){
//        	sodf.setWarehouseidname(aw.getWarehouseByID(sod.getWarehouseid()).getWarehousename());   
        }
//        sodf.setUnitid(sod.getUnitid());
        sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",           
            Integer.parseInt(sod.getUnitid().toString())));
        sodf.setOrgunitprice(sod.getOrgunitprice());
        sodf.setUnitprice(sod.getUnitprice());
        sodf.setTaxunitprice(sod.getTaxunitprice());
        sodf.setQuantity(sod.getQuantity());
        sodf.setDiscount(sod.getDiscount());
        sodf.setTaxrate(sod.getTaxrate());
        //sodf.setBatch(sod.getBatch());
        sodf.setCost(sod.getCost());        
        sodf.setSubsum(sod.getSubsum());
        sodf.setWise(ap.getProductByID(sod.getProductid()).getWise());
        als.add(sodf);
      }

	AppInvoiceConf aic = new AppInvoiceConf();
	List uls = aic.getAllInvoiceConf();
	ArrayList icls = new ArrayList();
	for (int u = 0; u < uls.size(); u++) {
			InvoiceConf ic = (InvoiceConf) uls.get(u);

			icls.add(ic);
	}
	
	AppOrgan ao = new AppOrgan();
    List ols = ao.getAllOrgan();
    ArrayList alos = new ArrayList();
    
    for(int o=0;o<ols.size();o++){
   	 OrganForm ub = new OrganForm();
   	 Organ of = (Organ)ols.get(o);
   	 ub.setId(of.getId());
   	 ub.setOrganname(of.getOrganname());
   	 alos.add(ub);
    }
 

      request.setAttribute("sof",sof);
      request.setAttribute("als",als);
      request.setAttribute("icls", icls);  
      request.setAttribute("alos", alos);
      return mapping.findForward("toupd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
  
}
