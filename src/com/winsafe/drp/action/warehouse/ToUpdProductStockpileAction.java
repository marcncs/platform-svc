package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStockpileForm;

public class ToUpdProductStockpileAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    Long id = Long.valueOf(request.getParameter("ID"));

    super.initdata(request);try{
      AppProductStockpile aps = new AppProductStockpile();
      ProductStockpile ps = new ProductStockpile();
      //AppUsers au = new AppUsers();
      ps = aps.getProductStockpileByID(id);


      ProductStockpileForm psf= new ProductStockpileForm();
      psf.setId(ps.getId());
      psf.setProductid(ps.getProductid());
      psf.setStockpile(ps.getStockpile());
      psf.setPrepareout(ps.getPrepareout());

      request.setAttribute("psf", psf);

      return mapping.findForward("toupd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
