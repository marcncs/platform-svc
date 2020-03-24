package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppSaleRepairDetail;
import com.winsafe.drp.dao.AppSaleRepairIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.SaleRepairDetail;
import com.winsafe.drp.dao.SaleRepairIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.MakeCode;

public class AuditSaleRepairAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);

	   
		try{
			String id = request.getParameter("id");
			AppSaleRepair apb = new AppSaleRepair(); 
			SaleRepair pb = apb.getSaleRepairByID(id);

			if(pb.getIsblankout()==1){
	          	 String result = "databases.record.blankoutnooperator";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			if(pb.getIsaudit()==1){
	          	 String result = "databases.record.audit";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }

			AppSaleRepairDetail apid = new AppSaleRepairDetail();
			AppSaleRepairIdcode appwi = new AppSaleRepairIdcode();
			List pils = apid.getSaleRepairDetailBySrid(id);
			
			
			int quantity;
			String batch, barcode;
			AppProduct appproduct = new AppProduct();			
			SaleRepairDetail pid = null;
			for (int i = 0; i < pils.size(); i++) {
				pid = (SaleRepairDetail)pils.get(i);
				batch = pid.getBatch();
				barcode = "";
				double num = pid.getQuantity();
				quantity = (int)num;
				
				Product p = appproduct.getProductByID(pid.getProductid());
				if (p.getIsidcode() == 1) {
					List codelist = appwi.getSaleRepairIdcodeByPidBatch(pid.getProductid(), id, batch);
					if ( quantity > codelist.size() ){
						String result = "databases.record.needidcode";
						request.setAttribute("result", result);
						return new ActionForward("/sys/lockrecord.jsp");
					}
				}
			}
			List idcodelist = appwi.getSaleRepairIdcodeByStid(id);
			
						
			AppIdcode appidcode = new AppIdcode();
			SaleRepairIdcode wi =null;
			for ( int i=0; i<idcodelist.size(); i++){
				wi = (SaleRepairIdcode)idcodelist.get(i);	
//				appidcode.updIsUse(wi.getProductid(), wi.getIdcode(), 1);
			}
			
			
//			AppTakeTicket appticket = new AppTakeTicket();
//			TakeTicket tt = new TakeTicket();
//			String ttid = MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT");
//			tt.setId(ttid);
//			tt.setWarehouseid(pb.getWarehouseinid());
//			tt.setBillno(pb.getId());
//			tt.setCid(pb.getCid());
//			tt.setLinkman(pb.getClinkman());
//			tt.setTel(pb.getTel());
//			tt.setRemark("销售返修生成检货小票");
//			tt.setIsaudit(0);
//			tt.setMakeid(userid);
//			tt.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//			tt.setIsblankout(0);
//			appticket.addTakeTicket(tt);
	
			ProductStockpile ps = new ProductStockpile();
			AppProductStockpile aps = new AppProductStockpile();
			for (int i = 0; i < pils.size(); i++) {
				pid = (SaleRepairDetail)pils.get(i);
				
				
				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile",0,"")));
				ps.setProductid(pid.getProductid());
//				ps.setPsproductname(pid.getProductname());
//				ps.setPsspecmode(pid.getSpecmode());
				ps.setCountunit(pid.getUnitid());
				ps.setBatch(pid.getBatch());
//				ps.setBarcode("");
//				ps.setWarehouseid(pb.getWarehouseinid());				
				aps.addProductByPurchaseIncome(ps);
				
//				aps.inProductStockpile(pid.getProductid(), pid.getBatch(), pid.getQuantity(), pb.getWarehouseinid(),id,"销售返修-入货");
				
//				ttd = new TakeTicketDetail();
//				ttd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail",0,"")));
//				ttd.setTtid(ttid);
//				ttd.setProductid(pid.getProductid());
//				ttd.setProductname(pid.getProductname());
//				ttd.setSpecmode(pid.getSpecmode());
//				ttd.setUnitid(pid.getUnitid());
//				ttd.setBatch(pid.getBatch());
//				ttd.setUnitprice(0d);				
//				ttd.setQuantity(Double.valueOf(pid.getQuantity().toString()));
//				ttd.setTakequantity(0d);
//				appttd.addTakeTicketDetail(ttd);
			}
			
//			apb.updIsAudit(id, userid,1);
//
//		      request.setAttribute("result", "databases.audit.success");
//		      DBUserLog.addUserLog(userid,"复核销售返修,编号："+id); 

			return mapping.findForward("audit");
		}catch(Exception e){
			e.printStackTrace();
		}finally {

	    }
		return new ActionForward(mapping.getInput());
	}

}
