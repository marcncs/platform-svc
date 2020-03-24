package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncome;

public class ToUpdProductIncomeAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    super.initdata(request);
    try {
      AppProductIncome api = new AppProductIncome();

//      AppCustomer ac = new AppCustomer();
//      AppUsers au = new AppUsers();
      
      ProductIncome pi = api.getProductIncomeByID(id);

      if (pi.getIsaudit() == 1) { 
        String result = "databases.record.noupdate";
        request.setAttribute("result", result);
        return mapping.findForward("lock");
      }

//      ProductIncomeForm pif= new ProductIncomeForm();
//      pif.setId(id);
//      pif.setHandwordcode(pi.getHandwordcode());
//      pif.setWarehouseid(pi.getWarehouseid());
//      pif.setIncomedate(pi.getIncomedate().toString().substring(0,10));
//      pif.setIncomesortname(Internation.getSelectTagByKeyAll("ProductIncomeSort",
//				request, "productincomesort", String.valueOf(pi.getIncomesort()), null));
//      pif.setRemark(pi.getRemark());

      AppProductIncomeDetail apid = new AppProductIncomeDetail();
      List apils = apid.getProductIncomeDetailByPbId(id);
//      ArrayList als = new ArrayList();
//      AppProduct ap = new AppProduct();
//
//      for(int i=0;i<apils.size();i++){
//    	  ProductIncomeDetailForm pidf = new ProductIncomeDetailForm();
//        Object[] o = (Object[])apils.get(i);
//        pidf.setProductid(o[2].toString());
//        pidf.setProductname(o[3].toString());
//        pidf.setSpecmode(o[4].toString());
//        pidf.setUnitid(Integer.valueOf(o[5].toString()));
//        pidf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",        
//            Integer.parseInt(o[5].toString())));
//        pidf.setQuantity(Double.valueOf(o[6].toString()));
//        pidf.setCostprice(Double.valueOf(o[7].toString()));
//        pidf.setCostsum(Double.valueOf(o[8].toString()));
//
//        als.add(pidf);
//      }
      
//      AppWarehouse aw = new AppWarehouse();
//      List wls = aw.getEnableWarehouseByVisit(userid);
//      ArrayList alw = new ArrayList();
//      for(int i=0;i<wls.size();i++){
//        Warehouse w = new Warehouse();
//        Object[] o = (Object[])wls.get(i);
//        w.setId(Long.valueOf(o[0].toString()));
//        w.setWarehousename(o[1].toString());
//        alw.add(w);
//      }
//      
//      AppDept ad = new AppDept();
//		List dls = ad.getDept();
//		ArrayList aldept = new ArrayList();
//		for (int i = 0; i < dls.size(); i++) {
//			Dept d = new Dept();
//			Object[] ob = (Object[]) dls.get(i);
//			d.setId(Long.valueOf(ob[0].toString()));
//			d.setDeptname(ob[1].toString());
//			aldept.add(d);
//		}
		
//      request.setAttribute("alw",alw);
      request.setAttribute("pif",pi);
      request.setAttribute("als",apils);
//      request.setAttribute("aldept", aldept);
      return mapping.findForward("toupd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
