package com.winsafe.drp.action.purchase;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganPrice;
import com.winsafe.drp.dao.AppOrganPriceHistory;
import com.winsafe.drp.dao.AppPriceOrganLog;
import com.winsafe.drp.dao.AppProductPrice;
import com.winsafe.drp.dao.OrganPrice;
import com.winsafe.drp.dao.PriceOrganLog;
import com.winsafe.drp.dao.ProductPrice;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.MakeCode;

public class AppLctProductPriceAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    
	 String pid = (String) request.getSession().getAttribute("pid");
	 UsersBean users = UserManager.getUser(request);
     Integer userid = users.getUserid();
    String strspeed = request.getParameter("speedstr");
    int count = Integer.parseInt(request.getParameter("uscount"));

    try{
      AppProductPrice app = new AppProductPrice();
      List apls = app.getProductPriceByProductID(pid);

      StringTokenizer st = new StringTokenizer(strspeed, ",");
      AppOrganPrice aop= new AppOrganPrice();
      AppOrganPriceHistory apoph = new AppOrganPriceHistory();
      AppPriceOrganLog apppo = new AppPriceOrganLog();
      
      apppo.delPriceOrganLogByPid(pid);
      
      for(int o=0;o<count;o++){
        String oid = st.nextToken().trim();
        aop.delOrganPriceByOIDPID(oid, pid);
       
        PriceOrganLog pol = new PriceOrganLog();
        pol.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("price_organ_log",0,"")));
        pol.setOrganid(oid);
        pol.setProductid(pid);
        apppo.addPriceOrganLog(pol);
        
        
        for(int p=0;p<apls.size();p++){
        	ProductPrice pp = (ProductPrice)apls.get(p);
        	OrganPrice op = new OrganPrice();
        	 //op.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("organ_price",0,"")));
        	 op.setOrganid(oid);
             op.setProductid(pid);
             op.setUnitid(pp.getUnitid());
             op.setPolicyid(pp.getPolicyid());
             op.setUnitprice(pp.getUnitprice());
             aop.addOrganPrice(op);   
             
             
//             OrganPriceHistory oph = new OrganPriceHistory();
//             oph.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("organ_price_history",0,"")));
//             oph.setOrganid(oid);
//             oph.setProductid(pid);
//             oph.setUnitid(pp.getUnitid());
//             oph.setPolicyid(pp.getPolicyid());
//             oph.setUnitprice(pp.getUnitprice());
//             oph.setUserid(userid);
//             oph.setModifydate(DateUtil.getCurrentDate());
//             apoph.addOrganPriceHistory(oph);
        }

      }

      request.setAttribute("result", "databases.add.success");

     
      //DBUserLog.addUserLog(userid,"应用到机构");

      return mapping.findForward("appprice");
    }catch(Exception e){
      e.printStackTrace();
    }finally {

    }

    return new ActionForward(mapping.getInput());
  }
}
