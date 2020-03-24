package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDemandPrice;
import com.winsafe.drp.dao.AppDemandPriceDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.DemandPrice;
import com.winsafe.drp.dao.DemandPriceDetailForm;
import com.winsafe.drp.dao.DemandPriceForm;
import com.winsafe.hbm.util.Internation;
public class ToUpdDemandPriceAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    Long id = Long.valueOf(request.getParameter("ID"));
    
//    Long userid = usersBean.getUserid();
    try {

      AppDemandPrice aso = new AppDemandPrice();
      DemandPrice so = new DemandPrice();
      AppCustomer ac = new AppCustomer();
      AppUsers au = new AppUsers();
      so = aso.getDemandPriceByID(id);
      if(so.getIsaudit()==1){
        String result = "databases.record.lock";
        request.setAttribute("result",result);
        return new ActionForward("/sys/lockrecord.jsp");
      }
      DemandPriceForm sof= new DemandPriceForm();
      sof.setId(so.getId());
      sof.setCid(so.getCid());
      sof.setCidname(ac.getCustomer(so.getCid()).getCname());
      sof.setLinkman(so.getLinkman());
      sof.setTel(so.getTel());
      sof.setDemandname(so.getDemandname());
      sof.setTotalsum(so.getTotalsum());
      sof.setRemark(so.getRemark());

      AppDemandPriceDetail asld = new AppDemandPriceDetail();
      List slls = asld.getDemandPriceDetailByDpID(id);
      ArrayList als = new ArrayList();
      AppProduct ap = new AppProduct();

      for(int i=0;i<slls.size();i++){
    	  DemandPriceDetailForm sodf = new DemandPriceDetailForm();
        Object[] o = (Object[])slls.get(i);
        sodf.setProductid(String.valueOf(o[2]));
        sodf.setProductname(o[3].toString());
        sodf.setSpecmode(o[4].toString());
        sodf.setUnitid(Long.valueOf(o[5].toString()));
        sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",           
            Integer.parseInt(o[5].toString())));
        sodf.setUnitprice(Double.valueOf(o[6].toString()));
        sodf.setQuantity(Double.valueOf(o[7].toString()));
        sodf.setSubsum(Double.valueOf(o[8].toString()));
        sodf.setDiscount(Double.valueOf(o[9].toString()));
        sodf.setTaxrate(Double.valueOf(o[10].toString()));
        als.add(sodf);
      }

      request.setAttribute("sof",sof);
      request.setAttribute("als",als);

      return mapping.findForward("toupd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
