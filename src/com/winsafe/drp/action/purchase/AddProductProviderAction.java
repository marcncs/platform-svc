package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProviderProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddProductProviderAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		String pdid = request.getParameter("pdid");

    
    try{
    String pvid = request.getParameter("pvid");
    if(pvid==null||pvid.equals("null")||pvid.equals("")){
    	String result = "databases.upd.fail";
    	request.setAttribute("result", result);
    	return new ActionForward("/sys/lockrecord.jsp");
    }
        
      ProviderProduct pp = new ProviderProduct();
      pp.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("provider_product",0,"")));
      pp.setProviderid(pvid);
      pp.setProductid(pdid);
      AppProviderProduct ac=new AppProviderProduct();
	  int count = ac.getProviderProductByPPID(pp.getProviderid(),pp.getProductid());
      if(count>0){
       	 String result = "databases.add.fail";
            request.setAttribute("result", result);
            return new ActionForward("/sys/lockrecordclose.jsp");
        }
      
      AppProduct ap = new AppProduct();
      pp.setPvproductname(ap.getProductByID(pdid).getProductname());


      Product p= ap.getProductByID(pdid);
      pp.setPvspecmode(p.getSpecmode());
      pp.setCountunit(p.getCountunit());
      pp.setBarcode(p.getBarcode());
      pp.setPrice(Double.valueOf(request.getParameter("price")));
      
      String pricedate = request.getParameter("pricedate");
      if(pricedate !=null && pricedate.trim().length()>0){
    	  pp.setPricedate(RequestTool.getDate(request, "pricedate"));
        }else{
        	pp.setPricedate(DateUtil.getCurrentDate());
        }

      AppProviderProduct app = new AppProviderProduct();
      app.addProviderProduct(pp);

      request.setAttribute("result", "databases.add.success");

      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      DBUserLog.addUserLog(userid,11, "产品资料>>新增供应商商品,编号:"+pp.getId());
      
      return mapping.findForward("addresult");
    }catch(Exception e){
      
      e.printStackTrace();
    }
    

    return mapping.getInputForward();
  }
}
