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
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdProductRedeployAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String psid = request.getParameter("ID");
		System.out.println("the id is:" + psid);
		super.initdata(request);super.initdata(request);try{

			AppUsers au = new AppUsers();
			AppProductRedeploy apr=new AppProductRedeploy();
			ProductRedeploy pr=apr.getProductRedeployByID(psid);
			 if(pr.getIsaudit()==1){
			        String result = "databases.record.lock";
			        request.setAttribute("result",result);
			        return new ActionForward("/sys/lockrecordclose.jsp");
			      }
			

			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
				ubs.setUserid(Integer.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}
			
			
			AppDept ad=new AppDept();			
			AppProductRedeployDetail aprd=new AppProductRedeployDetail();
			AppProduct ap=new AppProduct();
			List prd=aprd.getProductRedeployDetailByPid(psid);
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

			
			
			List dls = ad.getAllDept();
			
			request.setAttribute("dls", dls);
			request.setAttribute("prls", prls);
			request.setAttribute("pr", pr);
			request.setAttribute("psid", psid);
			request.setAttribute("als", als);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
