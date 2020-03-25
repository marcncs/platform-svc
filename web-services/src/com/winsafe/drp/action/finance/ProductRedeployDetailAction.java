package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductRedeploy;
import com.winsafe.drp.dao.AppProductRedeployDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductRedeploy;
import com.winsafe.drp.dao.ProductRedeployDetail;
import com.winsafe.drp.dao.ProductRedeployDetailForm;
import com.winsafe.drp.dao.ProductRedeployForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ProductRedeployDetailAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    super.initdata(request);super.initdata(request);try{
      AppProductRedeploy apr = new AppProductRedeploy();
      AppProduct ap=new AppProduct();
      AppUsers au = new AppUsers();
      AppDept ad=new AppDept();
      ProductRedeploy pr = apr.getProductRedeployByID(id);
      ProductRedeployForm prf=new ProductRedeployForm();
      prf.setId(pr.getId());
      prf.setIsaudit(pr.getIsaudit());
      prf.setAuditdatename(DateUtil.formatDate(pr.getAuditdate()));
      prf.setAuditidname(pr.getAuditid()==null?"":au.getUsersByid(pr.getAuditid()).getRealname());
      prf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
						request, pr.getIsaudit(), "global.sys.SystemResource"));
      prf.setMakedatename(DateUtil.formatDate(pr.getMakedate()));
      prf.setMakeidname(au.getUsersByid(pr.getMakeid()).getRealname());
      prf.setRedeploydeptname(ad.getDeptByID(pr.getRedeploydept()).getDeptname());
      prf.setRedeployidname(au.getUsersByid(pr.getRedeployid()).getRealname());
      prf.setRedeploymemo(pr.getRedeploymemo());
 
       

      AppProductRedeployDetail aps = new AppProductRedeployDetail();
      List prd=aps.getProductRedeployDetailByPid(id);
      List prls=new ArrayList();
      for (int p = 0; p < prd.size(); p++) {
			ProductRedeployDetailForm pf = new ProductRedeployDetailForm();
			ProductRedeployDetail ob = (ProductRedeployDetail) prd.get(p);
			Product product = ap.getProductByID(ob.getProductid());
			pf.setCost(ob.getCost());
			pf.setId(ob.getId());
			pf.setLeastsale(ob.getLeastsale());
			pf.setPricei(ob.getPricei());
			pf.setPriceii(ob.getPriceii());
			pf.setPriceiii(ob.getPriceiii());
			pf.setPriceivs(ob.getPriceivs());
			pf.setPriceuni(ob.getPriceuni());
			pf.setPricewholesale(ob.getPricewholesale());
			pf.setProductid(ob.getProductid());
			pf.setProductidname(ap.getProductByID(ob.getProductid()).getProductname());
			
			 
			pf.setStandardpurchase(ob.getStandardpurchase());
			pf.setStandardsale(ob.getStandardsale());
			pf.setSpecmode(product.getSpecmode());
			pf.setCountunitname(Internation.getStringByKeyPositionDB("CountUnit", product.getCountunit()));
			prls.add(pf);
		}

       
      
       

      request.setAttribute("prf", prf);
    
      
      request.setAttribute("prdls", prls);
      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();
//      DBUserLog.addUserLog(userid,"商品调价详情"); 
      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
