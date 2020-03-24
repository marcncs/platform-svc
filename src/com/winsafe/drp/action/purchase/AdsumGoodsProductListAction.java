package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdsumGoodsDetailForm;
import com.winsafe.drp.dao.AppAdsumGoodsDetail;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class AdsumGoodsProductListAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String agid = request.getParameter("AGID");
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();
		try{
			
			AppAdsumGoodsDetail apad = new AppAdsumGoodsDetail();
		      List ppdls = apad.getAdsumGoodsDetailByPbID(agid);
		      ArrayList als = new ArrayList();

		      for(int i=0;i<ppdls.size();i++){
		    	  AdsumGoodsDetailForm ppdf = new AdsumGoodsDetailForm();
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
		        ppdf.setChangequantity(Double.valueOf(o[9].toString()));

		        als.add(ppdf);
		      }
		      String iscomplete = Internation.getSelectTagByKeyAll("YesOrNo", request,
		              "iscomplete", "0", null);
		      
		    	
		    	AppDept ad = new AppDept();
		    	List aldept = ad.getDeptByOID(users.getMakeorganid());
		        request.setAttribute("aldept", aldept);

		      request.setAttribute("agid",agid);
		      request.setAttribute("iscomplete",iscomplete);
		      request.setAttribute("als",als);
		      
		     // DBUserLog.addUserLog(userid,"到货通知存货列表");
			return mapping.findForward("productlist");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ActionForward(mapping.getInput());
	}
}
