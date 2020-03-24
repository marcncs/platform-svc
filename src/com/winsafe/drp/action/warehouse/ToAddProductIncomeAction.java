package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Dept;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.util.Internation;

public class ToAddProductIncomeAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    super.initdata(request);
    try{
      AppWarehouse aw = new AppWarehouse();
      List wls = aw.getEnableWarehouseByVisit(userid);
      ArrayList alw = new ArrayList();
      for(int i=0;i<wls.size();i++){
//        Warehouse w = new Warehouse();
//        Object[] o = (Object[])wls.get(i);
//        w.setId(o[0].toString());
//        w.setWarehousename(o[1].toString());
    	  Warehouse w = (Warehouse)wls.get(i);
    	  alw.add(w);
      }
      
      AppDept ad = new AppDept();
		List dls = ad.getDept(request,10,"");
		ArrayList aldept = new ArrayList();
		for (int i = 0; i < dls.size(); i++) {
			Dept d = (Dept)dls.get(i);
//			Object[] ob = (Object[]) dls.get(i);
//			d.setId(Integer.valueOf(ob[0].toString()));
//			d.setDeptname(ob[1].toString());
			aldept.add(d);
		}
		
	  String productincomesortname = Internation.getSelectTagByKeyAll("ProductIncomeSort", request, "productincomesort", false, null);
      request.setAttribute("productincomesortname", productincomesortname);
      request.setAttribute("alw",alw);
      request.setAttribute("aldept", aldept);

      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
