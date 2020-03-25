package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillDetail;
import com.winsafe.drp.dao.OtherShipmentBillIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditOtherShipmentBillAction extends BaseAction {
	private AppIdcode appidcode = new AppIdcode();
	private AppProduct ap = new AppProduct();
	private AppProductStockpile appps = new AppProductStockpile();
	private AppProductStockpileAll apppsa = new AppProductStockpileAll();
	private AppFUnit af = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();

		super.initdata(request);try{
			
			String id = request.getParameter("ID");
			AppOtherShipmentBill apb = new AppOtherShipmentBill(); 
			OtherShipmentBill tt = apb.getOtherShipmentBillByID(id);
			
			if ( tt.getIsblankout() == 1){
	             request.setAttribute("result", "databases.record.blankoutnooperator");
	             return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if(tt.getIsaudit()==1){
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			
			AppOtherShipmentBillDetail apid = new AppOtherShipmentBillDetail();
			List<OtherShipmentBillDetail> pils = apid.getOtherShipmentBillDetailBySbID(id);
			

			//----huangxy----20121129----del----S
			//----不验证条码数量
//			AppOtherShipmentBillIdcode apidcode = new AppOtherShipmentBillIdcode();
//			for (OtherShipmentBillDetail ttd : pils ) {
//
//				double q1 = af.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getQuantity());
//				double q2 = apidcode.getQuantitySumByosidProductid(ttd.getProductid(), id, ttd.getBatch());
//				System.out.println("=========q1=========>"+q1);
//				System.out.println("=========q2=========>"+q2);
//				if (q1 != q2) {
//					request.setAttribute("result", "产品:"+ttd.getProductname()+" 批次:"+ttd.getBatch()+"数量不匹配,不能复核!");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
//			}
//			
//			List<OtherShipmentBillIdcode> alllist = apidcode.getOtherShipmentBillIdcodeByosid(id);			
//			for ( OtherShipmentBillIdcode tti : alllist){
//				double q1 = af.getQuantity(tti.getProductid(), tti.getUnitid(), tti.getQuantity());
//				double q2 = appps.getStockpileByPidWid(tti.getProductid(), tt.getWarehouseid(), tti.getWarehousebit(), tti.getBatch());
//				if (q1 > q2) {
//					request.setAttribute("result", ap.getProductNameByID(tti.getProductid())+"库存数量不足,不能复核!");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
//			}
//			
//			List<OtherShipmentBillIdcode> idcodelist = apidcode.getOtherShipmentBillIdcodeByosid(id, 1);
//			
//			for ( OtherShipmentBillIdcode tti : idcodelist){
//				if ( appidcode.getIdcodeById(tti.getIdcode(), 0) != null ){
//					request.setAttribute("result", tti.getIdcode()+"条码不存在或者已经出库,不能复核!");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
//			}		
//			
//			
//			
//			outProductStockpile(alllist, tt.getWarehouseid());
//			
//			setIdcodeNoUse(idcodelist, tt);
			//----huangxy----20121129----del----E	
			AppProduct ap = new AppProduct();
			ProductStockpile ps = null;
			for (OtherShipmentBillDetail ttd : pils ) {
				ps = new ProductStockpile();
				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_stockpile", 0, "")));
				ps.setProductid(ttd.getProductid());
				ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
				ps.setBatch(ttd.getBatch());
				ps.setProducedate("");
				ps.setVad("");				
				ps.setWarehouseid(tt.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				appps.addProductByPurchaseIncome2(ps);
				
				if(tt.getIsaccount()!=null && tt.getIsaccount()==1){
					//记台账
					appps.outPrepareout(tt.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, ttd.getProductid(), ttd.getUnitid(), ttd.getBatch(), ttd.getQuantity(),tt.getId(),"复核盘亏单-出货",true);
				}else{
					appps.outPrepareout(tt.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, ttd.getProductid(), ttd.getUnitid(), ttd.getBatch(), ttd.getQuantity(),tt.getId(),"复核盘亏单-出货",false);
				}
				apppsa.outPrepareout(tt.getWarehouseid(), ttd.getProductid(), ttd.getUnitid(), ttd.getBatch(), ttd.getQuantity());

			}
			
			apb.updIsAudit(id, userid,1);
			

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 7, "库存盘点>>复核盘亏单,编号："+id);
			return mapping.findForward("audit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void outProductStockpile(List<OtherShipmentBillIdcode> idlist, String warehouseid) throws Exception{
		for (OtherShipmentBillIdcode idcode : idlist ) {			
			appps.outProductStockpile(idcode.getProductid(), idcode.getUnitid(),idcode.getBatch(), 
				idcode.getQuantity(), warehouseid, idcode.getWarehousebit(),idcode.getOsid(),"复核盘亏单-出货");
		}
	}
	
	private void setIdcodeNoUse(List<OtherShipmentBillIdcode> idlist, OtherShipmentBill tt) throws Exception{
		for ( OtherShipmentBillIdcode idcode : idlist ){
			
			if ( idcode.getIssplit() == 1 ){
				
				Idcode ic = appidcode.getIdcodeByWLM(idcode.getStartno(), idcode.getEndno());
				ic.setQuantity(ic.getQuantity()-af.getQuantity(idcode.getProductid(), idcode.getUnitid(), 1));
				ic.setIsuse(0);
				appidcode.updIdcode(ic);
				
				addIdcode(idcode, tt);
			}else{
				appidcode.updIsUseOut(idcode.getIdcode(), 0, 1);
			}
		}
	}
	
	private void addIdcode(OtherShipmentBillIdcode tti, OtherShipmentBill tt) throws Exception{		
		Idcode ic = new Idcode();
		ic.setIdcode(tti.getIdcode());
		ic.setProductid(tti.getProductid());		
		ic.setProductname(ap.getProductNameByID(ic.getProductid()));
		ic.setBatch(tti.getBatch());
		ic.setProducedate(tti.getProducedate());
		ic.setVad(tti.getValidate());
		ic.setLcode(tti.getLcode());
		ic.setStartno(tti.getStartno());
		ic.setEndno(tti.getEndno());
		ic.setUnitid(tti.getUnitid());
		ic.setQuantity(af.getQuantity(ic.getProductid(), ic.getUnitid(), 1));
		ic.setFquantity(ic.getQuantity());
		ic.setPackquantity(tti.getPackquantity());				
		ic.setIsuse(0);	
		ic.setIsout(1);
		ic.setBillid(tti.getOsid());
		ic.setIdbilltype(0);
		ic.setMakeorganid(tt.getMakeorganid());
		ic.setWarehouseid(tt.getWarehouseid());
		ic.setWarehousebit(tti.getWarehousebit());				
		ic.setProvideid("");
		ic.setProvidename("");
		ic.setMakedate(tti.getMakedate());
		appidcode.addIdcode(ic);
	}


}
