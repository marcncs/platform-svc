package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganPrice;
import com.winsafe.drp.dao.AppProductPrice;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganPrice;
import com.winsafe.drp.dao.ProductPrice;

public class ImportOrganPriceAction extends Action{

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
	                               HttpServletRequest request,
	                               HttpServletResponse response) throws Exception {
		  try{
			  AppOrganPrice aop = new AppOrganPrice();
			  AppOrgan ao = new AppOrgan();
			  List ols = ao.getAllOrgan();
			  String oid = "";
			  for(int i=0;i<ols.size();i++){
				  Organ o = (Organ)ols.get(i);
				  oid = o.getId();
//				  
//			  ProductPrice pp = new ProductPrice();
				  AppProductPrice app = new AppProductPrice();
//				  List ls=app.getAAAA();
//				  for(int p=0;p<ls.size();p++){
//					  Object[] pr= (Object[])ls.get(p);
//					  ProductPrice pp = new ProductPrice();
////System.out.println(MakeCode.getExcIDByRandomTableName("product_price",0,"")+","+String.valueOf(pr[0])+","+String.valueOf(pr[1])+","+String.valueOf(pr[2])+","+Double.valueOf(pr[3].toString()));
//					  pp.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("product_price",0,"")));
//					  pp.setProductid(String.valueOf(pr[0]));
//					  pp.setUnitid(Integer.valueOf(pr[1].toString()));
//					  pp.setPolicyid(1l);
//					  pp.setUnitprice(Double.valueOf(pr[3].toString()));
//					  app.addProductPrice(pp);
//					  System.out.println(pp.getId()+","+pp.getProductid()+","+pp.getUnitid()+","+pp.getPolicyid()+","+pp.getUnitprice());
//////					  
//				  }
				  
				  
				  
				  List pls = app.getAAAA();
				  for(int p=0;p<pls.size();p++){
				   ProductPrice pp=(ProductPrice)pls.get(p);
				  OrganPrice op = new OrganPrice();
				  
				  op.setOrganid(oid);
				  op.setProductid(pp.getProductid());
				  op.setUnitid(pp.getUnitid());
				  op.setPolicyid(pp.getPolicyid());
				  op.setUnitprice(pp.getUnitprice());
				  aop.addOrganPrice(op);
System.out.println(op.getId()+","+op.getOrganid()+","+op.getProductid()+","+op.getUnitid()+","+op.getPolicyid()+","+op.getUnitprice());

				  }
				  
			  }
			  
			  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return null;
	  }

}
