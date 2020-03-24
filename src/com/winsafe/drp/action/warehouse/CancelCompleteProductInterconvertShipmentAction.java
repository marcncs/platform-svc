package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class CancelCompleteProductInterconvertShipmentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//	    Long userid = users.getUserid();
		String id = request.getParameter("SMID");

		super.initdata(request);try{
			AppProductInterconvert asm = new AppProductInterconvert();
			AppProductInterconvertDetail asmd = new AppProductInterconvertDetail();
			ProductInterconvert sm = asm.getProductInterconvertByID(id);
			if (sm.getIsshipment() ==0) { 
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
			if(sm.getIscomplete() ==1){
				String result = "databases.record.alreadyshipmentnocancel";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
//			if(!String.valueOf(sm.getShipmentid()).contains(userid.toString())){
//	          	 String result = "databases.record.cancelaudit";
//	               request.setAttribute("result", result);
//	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
//	           }
			
//			Long outwarehouseid =sm.getOutwarehouseid();
//			Long inwarehouseid= sm.getInwarehouseid();
			
			List ls = asmd.getProductInterconvertDetailBySamID(id);
			
			
			String productid="";
			String batch;
			Double quantity;
			AppProductStockpile aps = new AppProductStockpile();
			AppFUnit apfu = new AppFUnit();
			for(int i=0;i<ls.size();i++){
				ProductInterconvertDetail o = (ProductInterconvertDetail) ls.get(i);
				productid = o.getProductid();
		      batch = o.getBatch();
		      quantity = Double.valueOf(o.getQuantity());

		      
		      //aps.outProductStockpile(productid, batch, quantity, outwarehouseid);
		      
//		      aps.returnOutProductStockpile(productid, batch, 
//		    		  apfu.getQuantity(productid, o.getUnitid(), quantity), outwarehouseid,id,"取消商品互转-入库");
		      //aps.outProductStockpile(pid,batch,quantity,outwarehouseid);
		      
		     //aps.inProductStockpile(pid,quantity,inwarehouseid);
			}
//			 asm.updProductInterconvertIsShipment(id,0,userid);
			
		      request.setAttribute("result", "databases.add.success");
//		      DBUserLog.addUserLog(userid,"取消商品互转发货,编号:"+id);
			
			return mapping.findForward("success");
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
