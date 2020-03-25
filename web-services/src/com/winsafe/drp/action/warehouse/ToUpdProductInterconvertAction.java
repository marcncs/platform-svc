package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.dao.ProductInterconvertDetailForm;
import com.winsafe.drp.dao.ProductInterconvertForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdProductInterconvertAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    super.initdata(request);
    try {
      AppProductInterconvert asm = new AppProductInterconvert();
      ProductInterconvert sm = new ProductInterconvert();
      //AppUsers au = new AppUsers();
      sm = asm.getProductInterconvertByID(id);

      if (sm.getIsaudit() == 1) { 
        String result = "databases.record.noupdate";
        request.setAttribute("result", result);
        return mapping.findForward("lock");
      }

      ProductInterconvertForm smf= new ProductInterconvertForm();
      smf.setId(sm.getId());
      smf.setMovedatename(sm.getMovedate().toString());
      smf.setOutwarehouseid(sm.getOutwarehouseid());
      smf.setInwarehouseid(sm.getInwarehouseid());
      smf.setTotalsum(sm.getTotalsum());
      smf.setMovecause(sm.getMovecause());
      smf.setRemark(sm.getRemark());


      AppProductInterconvertDetail asmd = new AppProductInterconvertDetail();
      List smdls = asmd.getProductInterconvertDetailBySamID(id);
      ArrayList als = new ArrayList();
      AppProduct ap = new AppProduct();

      for(int i=0;i<smdls.size();i++){
    	  ProductInterconvertDetailForm smdf = new ProductInterconvertDetailForm();
    	  ProductInterconvertDetail o = (ProductInterconvertDetail)smdls.get(i);
        smdf.setProductid(o.getProductid());
        smdf.setProductname(o.getProductname());
        smdf.setSpecmode(o.getSpecmode());
        smdf.setUnitid(o.getUnitid());
        smdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",            
            o.getUnitid()));
        smdf.setBatch(o.getBatch());
        smdf.setUnitprice(o.getUnitprice());
        smdf.setQuantity(o.getQuantity());
        smdf.setSubsum(o.getSubsum());
        als.add(smdf);
      }

//
      AppWarehouse aw = new AppWarehouse();
//      List wls = aw.getEnableWarehouseByVisit(userid);
//      ArrayList alw = new ArrayList();
//      for (int i = 0; i < wls.size(); i++) {
//        Warehouse w = new Warehouse();
//        Object[] o = (Object[]) wls.get(i);
//        w.setId(o[0].toString());
//        w.setWarehousename(o[1].toString());
//        alw.add(w);
//      }
      

      List iwls = aw.getCanUseWarehouseByOid(users.getMakeorganid());
      
      
//      request.setAttribute("alw", alw);
      request.setAttribute("aliw", iwls);
      request.setAttribute("smf",smf);
      request.setAttribute("als",als);

      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
