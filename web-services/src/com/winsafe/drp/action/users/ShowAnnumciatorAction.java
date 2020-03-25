package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganSafetyIntercalate;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.OrganProductAnnunciator;
import com.winsafe.drp.dao.OrganSafetyIntercalate;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ShowAnnumciatorAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();
	    int pagesize=20;
		try{
			AppOrgan ao = new AppOrgan();
			AppProduct ap = new AppProduct();
			AppOrganSafetyIntercalate aosi = new AppOrganSafetyIntercalate();
			List psls = aosi.getCurrentStock2(request, pagesize, users.getMakeorganid());
			ArrayList als = new ArrayList();
			for(int i=0;i<psls.size();i++){
				OrganProductAnnunciator opa = new OrganProductAnnunciator();
				Map o =(Map)psls.get(i);
				opa.setProductid(o.get("productid").toString());
				Product p = ap.getProductByID(opa.getProductid());
				opa.setProductidname(p.getProductname());
				opa.setSpecmode(p.getSpecmode());
				opa.setUnitid(p.getSunit());
				opa.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
				          p.getCountunit()));
				opa.setCurquantity(Double.valueOf(o.get("stockpile").toString()));
				OrganSafetyIntercalate oi = aosi.getOrganSafetyIntercalateByProductidOID(opa.getProductid(), users.getMakeorganid());
				opa.setSafetyl(oi.getSafetyl());
				opa.setSafetyh(oi.getSafetyh());
				als.add(opa);
			}

	    	request.setAttribute("als", als);
	    	request.setAttribute("organidname", ao.getOrganByID(users.getMakeorganid()).getOrganname());
	    	return mapping.findForward("show");
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}

}
