package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductForm;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.hbm.util.Internation;

public class ViewProductAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strid = request.getParameter("ID");
    super.initdata(request);try{
      AppProductStruct appProductStruct = new AppProductStruct();
       
      
      AppProduct ap = new AppProduct();
      Product p = ap.getProductByID(strid);
      ProductStruct  ps=appProductStruct.getProductStructById(p.getPsid());
      //String ulsname=ps.getSortname();

      	ProductForm pf = new ProductForm();
	  	pf.setId(p.getId());
	  	pf.setProductname(p.getProductname());
	  	pf.setPsid(ps.getSortname());
	  	pf.setBrand(p.getBrand());
	  	pf.setBrandname(Internation.getStringByKeyPositionDB("Brand",p.getBrand()));
	  	pf.setWise(p.getWise());
	  	pf.setCountunit(p.getCountunit());
	  	pf.setCountunitname(Internation.getStringByKeyPositionDB("CountUnit", p.getCountunit()));
	  	pf.setProductcode(p.getProductcode());
	  	pf.setSpecmode(p.getSpecmode());
	  	pf.setBarcode(p.getBarcode());
	  	pf.setLeastsale(p.getLeastsale());
	  	pf.setCost(p.getCost());
	  	pf.setAbcsort(p.getAbcsort());        	
      
      
       
      request.setAttribute("paf",pf);
       

   
  
      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
