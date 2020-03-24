package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.Product;

public class ProductDetailAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    AppProduct ap = new AppProduct();
    AppFUnit afu = new AppFUnit();
    AppProductStruct appps = new AppProductStruct();
    Product p = ap.getProductByID(id);
    
    List fuls = afu.getFUnitByProductIDNoIsMain(id);
    
    request.setAttribute("psidname", appps.getName(p.getPsid()));
    request.setAttribute("psidnameen", appps.getEnName(p.getPsid()));
    request.setAttribute("p", p);
    request.setAttribute("afls", fuls);
    
    return mapping.findForward("listdetail");
  }
}
