package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.hbm.util.Internation;

public class ToUpdProductAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strid = request.getParameter("ID");
    String id=strid;
    super.initdata(request);super.initdata(request);try{
      AppProductStruct appProductStruct = new AppProductStruct();
       
      
      AppProduct ap = new AppProduct();
      Product p =  ap.getProductByID(id);
      ProductStruct  ps=appProductStruct.getProductStructById(p.getPsid());
      String ulsname=ps.getSortname();
     

      
       
     
//      String brand = Internation.getSelectTagByKeyAllDBDef("Brand",
//				"brand", p.getBrand());
      String brand=Internation.getStringByKeyPositionDB("Brand", p.getBrand());
//      String wise =Internation.getSelectTagByKeyAll("Wise", request,
//              "wise", String.valueOf(p.getWise()), null);
      
      
      
       
      request.setAttribute("p",p);
       
     
      
      request.setAttribute("ulsname", ulsname);
       
      request.setAttribute("brand",brand);
   
  
      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
