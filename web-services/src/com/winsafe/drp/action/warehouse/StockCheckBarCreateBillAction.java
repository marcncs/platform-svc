package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckIdcode;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncomeIdcode;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillDetail;
import com.winsafe.drp.dao.OtherShipmentBillIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class StockCheckBarCreateBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String id = request.getParameter("SCID");

		super.initdata(request);try{
			AppStockCheck asc = new AppStockCheck();
			StockCheck sc =  asc.getStockCheckByID(id);

			if (sc.getIsaudit()== 0) { 
				request.setAttribute("result", "databases.record.notallynocreate");
				return mapping.findForward("lock");
			}
			if (sc.getIscreate() == 1) { 
				request.setAttribute("result", "databases.record.already");
				return mapping.findForward("lock");
			}
			
					
			
			int instock = 0;
			
			int outstock = 0;
			AppStockCheckIdcode appsci = new AppStockCheckIdcode();
			List<StockCheckIdcode> ls = appsci.getStockCheckIdcodeByscid(id);	
			for ( StockCheckIdcode scd : ls ) {				
				
				if (scd.getCquantity().doubleValue() > scd.getQuantity().doubleValue() ) {
					instock += 1;
				} 
				
				if (scd.getQuantity().doubleValue() > scd.getCquantity().doubleValue()) {
					outstock += 1;
				} 
			}

			if (instock > 0) {
				addOtherIncome(sc, ls, users);
			}

			if (outstock > 0) {
				addOtherShipmentBill(sc, ls, users);
			}

			 asc.updStockCheckIsComplete(id, userid);
			
			request.setAttribute("result", "databases.add.success");

		      DBUserLog.addUserLog(userid, 7, "库存盘点>>盘点单生成盘盈盘亏单,编号:"+id); 

			return mapping.findForward("create");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}
	
	private void addOtherIncome(StockCheck sc, List<StockCheckIdcode> list, UsersBean users) throws Exception{
		AppFUnit af = new AppFUnit();
		OtherIncome oi = new OtherIncome();
		String pyid = MakeCode.getExcIDByRandomTableName("other_income", 2,"PY");
		oi.setId(pyid);
		oi.setWarehouseid(sc.getWarehouseid());
		oi.setIncomesort(0);
		oi.setBillno(sc.getId());
		oi.setRemark("从盘点单生成的盘盈入库单");
	    oi.setIsaudit(0);
	    oi.setAuditid(0);
	    oi.setMakeorganid(users.getMakeorganid());
	    oi.setMakedeptid(users.getMakedeptid());
	    oi.setMakeid(users.getUserid());
	    oi.setMakedate(DateUtil.getCurrentDate());
	    oi.setKeyscontent(oi.getId()+","+oi.getBillno());
		
		AppOtherIncome aoi = new AppOtherIncome();
		aoi.addOtherIncome(oi);

		AppOtherIncomeDetail aoid = new AppOtherIncomeDetail();
		AppOtherIncomeIdcode appoii = new AppOtherIncomeIdcode();
		AppProduct ap = new AppProduct();
		for ( StockCheckIdcode scd : list ) {
			double quantity = scd.getCquantity() - scd.getQuantity();
			if( quantity > 0 ){
				OtherIncomeDetail oid = aoid.getOtherIncomeDetailByOiidPid(pyid, scd.getProductid(), scd.getBatch());
				if ( oid == null ){
					Product product = ap.getProductByID(scd.getProductid());
					oid = new OtherIncomeDetail();
					oid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"other_income_detail", 0, "")));
					oid.setOiid(pyid);
					oid.setProductid(scd.getProductid());
					oid.setProductname(product.getProductname());
					oid.setSpecmode(product.getSpecmode());
					oid.setUnitid(product.getCountunit());
					oid.setBatch(scd.getBatch());
					oid.setQuantity(quantity);
					oid.setUnitprice(ProductCostService.getCost(sc.getWarehouseid(), scd.getProductid(), scd.getBatch()));
					oid.setSubsum(0d);
					aoid.addOtherIncomeDetail(oid);	
				}else{
					oid.setQuantity(oid.getQuantity()+quantity);
					aoid.updOtherIncomeDetail(oid);
				}
				
				OtherIncomeIdcode oii = new OtherIncomeIdcode();
				oii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"other_income_idcode", 0, "")));
				oii.setOiid(pyid);
				oii.setProductid(scd.getProductid());
				oii.setIsidcode(1);
				oii.setWarehousebit(scd.getWarehousebit());
				oii.setBatch(scd.getBatch());
				oii.setProducedate(scd.getProducedate());
				oii.setValidate("");
				oii.setUnitid(scd.getUnitid());
				oii.setQuantity(af.getStockpileQuantity2(oii.getProductid(), oii.getUnitid(), quantity));
				oii.setPackquantity(scd.getPackquantity());
				oii.setLcode(scd.getLcode());
				oii.setStartno(scd.getStartno());
				oii.setEndno(scd.getEndno());
				oii.setIdcode(scd.getCidcode());
				oii.setMakedate(DateUtil.getCurrentDate());
				appoii.addOtherIncomeIdcode(oii);
			}
		}
	}
	
	private void addOtherShipmentBill(StockCheck sc, List<StockCheckIdcode> list, UsersBean users) throws Exception{
		AppFUnit af = new AppFUnit();
		OtherShipmentBill osb = new OtherShipmentBill();
		String pkid = MakeCode.getExcIDByRandomTableName("other_shipment_bill", 2, "PK");
		osb.setId(pkid);
		osb.setWarehouseid(sc.getWarehouseid());
		osb.setShipmentsort(Integer.valueOf(1));
		osb.setBillno(sc.getId());
		osb.setRequiredate(DateUtil.getCurrentDate());
		osb.setTotalsum(Double.valueOf(0.00));
		osb.setRemark("从盘点单生成的盘亏出库单");
		osb.setIsaudit(0);
		osb.setAuditid(0);
		osb.setIsendcase(0);
		osb.setIsblankout(0);
		osb.setMakeorganid(users.getMakeorganid());
		osb.setMakedeptid(users.getMakedeptid());
		osb.setMakeid(users.getUserid());
		osb.setMakedate(DateUtil.getCurrentDate());
		osb.setKeyscontent(osb.getId()+","+osb.getBillno());

		AppOtherShipmentBill aosb = new AppOtherShipmentBill();
		aosb.addOtherShipmentBill(osb);
		
		AppOtherShipmentBillDetail aspb = new AppOtherShipmentBillDetail();
		AppOtherShipmentBillIdcode apposi = new AppOtherShipmentBillIdcode();
		AppProductStockpileAll aps = new AppProductStockpileAll();
		AppProduct ap = new AppProduct();
		for ( StockCheckIdcode scd : list ) {
			double quantity = scd.getQuantity()-scd.getCquantity();
			if(quantity>0){
				OtherShipmentBillDetail osbd = aspb.getOtherShipmentBillDetailByOsidPid(pkid, scd.getProductid(), scd.getBatch());
				if ( osbd == null ){
					Product product = ap.getProductByID(scd.getProductid());
					osbd = new OtherShipmentBillDetail();
					osbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"other_shipment_bill_detail", 0, "")));
					osbd.setOsid(pkid);
					osbd.setProductid(scd.getProductid());
					osbd.setProductname(product.getProductname());
					osbd.setSpecmode(product.getSpecmode());
					osbd.setUnitid(product.getCountunit());
					osbd.setBatch(scd.getBatch());
					osbd.setUnitprice(0d);
					osbd.setQuantity(quantity);
					osbd.setSubsum(0d);
					aspb.addOtherShipmentBillDetail(osbd);
				}else{
					osbd.setQuantity(osbd.getQuantity()+quantity);
					aspb.updOtherShipmentBillDetail(osbd);
				}
				//未被拆过的条码
				if ( scd.getQuantity().doubleValue() == scd.getFquantity() ){
					OtherShipmentBillIdcode oii = new OtherShipmentBillIdcode();
					oii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
							"other_shipment_bill_idcode", 0, "")));
					oii.setOsid(pkid);
					oii.setProductid(scd.getProductid());
					oii.setIsidcode(1);
					oii.setWarehousebit(scd.getWarehousebit());
					oii.setBatch(scd.getBatch());
					oii.setProducedate(scd.getProducedate());
					oii.setValidate("");
					oii.setUnitid(scd.getUnitid());
					oii.setQuantity(af.getStockpileQuantity2(oii.getProductid(), oii.getUnitid(), quantity));
					oii.setPackquantity(scd.getPackquantity());
					oii.setLcode(scd.getLcode());
					oii.setStartno(scd.getStartno());
					oii.setEndno(scd.getEndno());
					oii.setIdcode(scd.getIdcode());
					oii.setIssplit(0);
					oii.setMakedate(DateUtil.getCurrentDate());
					apposi.addOtherShipmentBillIdcode(oii);
				}
				 
				 
				aps.prepareOut(scd.getProductid(), osbd.getUnitid(), sc.getWarehouseid(), scd.getBatch(), quantity);
			}
		}
	}

}
