package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.AppSaleTradesIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.SaleTradesDetail;
import com.winsafe.drp.dao.SaleTradesIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditSaleTradesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();

		try{
			String id = request.getParameter("id");
			AppSaleTrades apb = new AppSaleTrades(); 
			AppSaleTradesIdcode apidcode = new AppSaleTradesIdcode();
			AppFUnit af = new AppFUnit();
			SaleTrades pb = apb.getSaleTradesByID(id);

			if(pb.getIsblankout()==1){
	               request.setAttribute("result","databases.record.blankoutnooperator");
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			if(pb.getIsaudit()==1){
	               request.setAttribute("result", "databases.record.audit");
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			AppSaleTradesDetail apid = new AppSaleTradesDetail();
			List<SaleTradesDetail> pils = apid.getSaleTradesDetailByID(id);
			for (SaleTradesDetail pid : pils ) {
				
				double q1 = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid.getQuantity());
				double q2 = apidcode.getQuantitySumBystidProductid(pid.getProductid(), id);
				if (q1 != q2) {
					request.setAttribute("result", pid.getProductname()+"数量不匹配,不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			List<SaleTradesIdcode> idlist = apidcode.getSaleTradesIdcodeBystid(id);
			
			addProductStockpile(idlist, pb.getWarehouseinid());
			
			List<SaleTradesIdcode> idcodelist = apidcode.getSaleTradesIdcodeBystid(id, 1);
			
			addIdcode(pb, idcodelist);
			
			apb.updIsAudit(id, userid, 1);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 6, "销售换货>>复核销售换货单,编号："+id);
			return mapping.findForward("audit");
		}catch(Exception e){

			e.printStackTrace();
		}finally {

	    }
		return new ActionForward(mapping.getInput());
	}
	
	
	private void addProductStockpile(List<SaleTradesIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (SaleTradesIdcode idcode : idlist ) {
			ps = new ProductStockpile();
			
			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"product_stockpile", 0, "")));
			ps.setProductid(idcode.getProductid());
			
			ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
			ps.setBatch(idcode.getBatch());
			ps.setProducedate(idcode.getProducedate());
			ps.setVad(idcode.getValidate());				
			ps.setWarehouseid(warehouseid);
			ps.setWarehousebit(idcode.getWarehousebit());
			ps.setMakedate(DateUtil.getCurrentDate());
			aps.addProductByPurchaseIncome(ps);

			
			aps.inProductStockpile(ps.getProductid(),idcode.getUnitid(),ps.getBatch(),
					idcode.getQuantity(), ps.getWarehouseid(), ps.getWarehousebit(), idcode.getStid(), "销售换货复核-入库");
		}
	}
	
	
	private void addIdcode(SaleTrades pi, List<SaleTradesIdcode> idcodelist) throws Exception{		
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		AppFUnit af = new AppFUnit();
		Idcode ic = null;
		for (SaleTradesIdcode wi : idcodelist) {
			ic = new Idcode();				
			ic.setIdcode(wi.getIdcode());
			ic.setProductid(wi.getProductid());		
			ic.setProductname(ap.getProductNameByID(ic.getProductid()));
			ic.setBatch(wi.getBatch());
			ic.setProducedate(wi.getProducedate());
			ic.setVad(wi.getValidate());
			ic.setLcode(wi.getLcode());
			ic.setStartno(wi.getStartno());
			ic.setEndno(wi.getEndno());
			ic.setUnitid(wi.getUnitid());
			ic.setQuantity(af.getQuantity(ic.getProductid(), ic.getUnitid(), 1));
			ic.setFquantity(ic.getQuantity());
			ic.setPackquantity(wi.getPackquantity());				
			ic.setIsuse(1);				
			ic.setIsout(0);
			ic.setBillid(wi.getStid());
			ic.setIdbilltype(10);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getWarehouseinid());
			ic.setWarehousebit(wi.getWarehousebit());				
			ic.setProvideid("");
			ic.setProvidename("");
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(),ic.getMakeorganid(),ic.getWarehouseid(),ic.getWarehousebit(), 1, 0);
		}
	}
	
	

}
