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
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class CompleteProductInterconvertShipmentAction extends BaseAction {
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
			if (sm.getIsaudit() ==0) { 
				String result = "databases.record.noauditnoshipment";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
			AppWarehouseVisit awv = new AppWarehouseVisit();
//			int w=awv.findWarehouseByUseridWid(sm.getOutwarehouseid(), userid);

//			if(w==0){
//	          	 String result = "databases.record.nopurview";
//	               request.setAttribute("result", result);
//	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
//	           }
			
			
			if(sm.getIsshipment() ==1){
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
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
		      quantity = o.getQuantity();
//		      Double pilesum=aps.getProductStockpileByProductIDWIDBatch(productid, outwarehouseid, batch);
		     // System.out.println("the pilesum-====================:" + pilesum);
				

//				if(pilesum <1){
//					String result = "databases.record.warehouseno";
//					request.setAttribute("result", result);
//					return mapping.findForward("lock");
//				}

		      
//		      aps.outProductStockpile(productid, batch, 
//		    		  apfu.getQuantity(productid, o.getUnitid(), quantity), outwarehouseid,id,"商品互转-出货");

			}
//			 asm.updProductInterconvertIsShipment(id,1,userid);
			
		      request.setAttribute("result", "databases.add.success");
//		      DBUserLog.addUserLog(userid,"商品互转发货,编号："+id);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
