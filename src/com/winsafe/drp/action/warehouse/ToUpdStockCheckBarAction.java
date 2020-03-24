package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.StockCheck;

public class ToUpdStockCheckBarAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    super.initdata(request);try{
      AppStockCheck asc = new AppStockCheck();
      StockCheck sc = asc.getStockCheckByID(id);

      if (sc.getIsaudit() == 1) { 
        request.setAttribute("result", "databases.record.noupdate");
        return new ActionForward("/sys/lockrecordclose.jsp");
      }

     
      AppStockCheckDetail asad = new AppStockCheckDetail();
      List sadls = asad.getStockCheckDetailBySmID(id);
    
      
      request.setAttribute("scf",sc);
      request.setAttribute("als",sadls);

      return mapping.findForward("toupd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
