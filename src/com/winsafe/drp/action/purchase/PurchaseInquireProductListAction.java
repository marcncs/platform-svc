package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.AppPurchaseInquireDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquireDetailForm;
import com.winsafe.drp.dao.PurchaseInquireForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class PurchaseInquireProductListAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Integer piid = Integer.valueOf(request.getParameter("PIID"));
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();
		try{
			AppPurchaseInquire api = new AppPurchaseInquire();			
			PurchaseInquire pi = api.getPurchaseInquireByID(piid);
			PurchaseInquireForm pif = new PurchaseInquireForm();
			AppProvider ap = new AppProvider();
			pif.setPid(pi.getPid());
			pif.setProvidename(ap.getProviderByID(pi.getPid()).getPname());
			pif.setPlinkman(pi.getPlinkman());
			Double totalsum = 0.00;
			AppPurchaseInquireDetail apad = new AppPurchaseInquireDetail();
		      List ppdls = apad.getPurchaseInquireDetailByPiID(piid);
		      ArrayList als = new ArrayList();

		      for(int i=0;i<ppdls.size();i++){
		        PurchaseInquireDetailForm ppdf = new PurchaseInquireDetailForm();
		        Object[] o = (Object[])ppdls.get(i);
		        ppdf.setProductid(o[2].toString());
		        ppdf.setProductname(o[3].toString());
		        ppdf.setSpecmode(o[4].toString());
		        ppdf.setUnitid(Integer.valueOf(o[5].toString()));
		        ppdf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit",		           
		            Integer.parseInt(o[5].toString())));
		        ppdf.setUnitprice(Double.valueOf(o[6].toString()));
		        ppdf.setQuantity(Double.valueOf(o[7].toString()));
		        ppdf.setSubsum(Double.valueOf(o[8].toString()));
		        totalsum +=ppdf.getSubsum();
		        als.add(ppdf);
		      }
		      
		      String purchasesortname = Internation.getSelectTagByKeyAllDB("PurchaseSort",
						 "purchasesort", false);
		    	
		    	AppDept ad = new AppDept();
		    	List aldept = ad.getDeptByOID(users.getMakeorganid());
		        
		        
		        AppUsers au = new AppUsers();
		        List auls = au.getIDAndLoginNameByOID2(users.getMakeorganid());
		        
		        
		        String paymodename = Internation.getSelectTagByKeyAll("PayMode", request,
		                 "paymode", false, null);
		    	request.setAttribute("paymodename", paymodename);
		        request.setAttribute("auls", auls);
		        request.setAttribute("aldept", aldept);
		        request.setAttribute("purchasesortname", purchasesortname);
		        request.setAttribute("pif",pif);
		        request.setAttribute("totalsum", totalsum);
		      request.setAttribute("piid",piid);
		      request.setAttribute("als",als);
		      
		      //DBUserLog.addUserLog(userid,"采购询价存货列表");
			return mapping.findForward("productlist");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ActionForward(mapping.getInput());
	}
}
