package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillDetail;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddOtherShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		UsersBean users = UserManager.getUser(request);

		super.initdata(request);try{
			OtherShipmentBill osb = new OtherShipmentBill();
			String osid = MakeCode.getExcIDByRandomTableName("other_shipment_bill",
					2, "PK");
			osb.setId(osid);			
			osb.setWarehouseid(request.getParameter("warehouseid"));
			osb.setBillno(request.getParameter("billno"));
			osb.setShipmentsort(RequestTool.getInt(request, "shipmentsort"));			
			osb.setRequiredate(RequestTool.getDate(request, "requiredate"));			
			osb.setRemark(request.getParameter("remark"));
			osb.setIsaudit(0);
			osb.setAuditid(0);
			osb.setMakeorganid(users.getMakeorganid());
			osb.setMakedeptid(users.getMakedeptid());
			osb.setMakeid(users.getUserid());
			osb.setMakedate(DateUtil.getCurrentDate());
			osb.setIsblankout(0);
			osb.setBlankoutid(0);
			osb.setIsendcase(0);
			osb.setEndcaseid(0);
			osb.setTakestaus(0);
			
			String isaccount = request.getParameter("isaccount");
			//不记账
			if(isaccount==null){
				osb.setIsaccount(0);
			}else{
				//记账
				osb.setIsaccount(1);
			}
			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String batch[] = request.getParameterValues("batch");
			//double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");			
			
			double totalsum = 0.00;
			StringBuffer keyscontent = new StringBuffer();			
		    keyscontent.append(osb.getId()).append(",").append(osb.getBillno()).append(",")
		    .append(osb.getRemark()).append(",");

			AppProduct ap = new AppProduct();
			ProductStockpile ps = null;
			AppProductStockpileAll apsa = new AppProductStockpileAll();
			AppProductStockpile aps = new AppProductStockpile();
			AppOtherShipmentBillDetail apobd = new AppOtherShipmentBillDetail();
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_shipment_bill_idcode","osid",osb.getWarehouseid());

			for (int i = 0; i < productid.length; i++) {				
				OtherShipmentBillDetail osbd = new OtherShipmentBillDetail();
				osbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"other_shipment_bill_detail", 0, "")));
				osbd.setOsid(osid);
				osbd.setProductid(productid[i]);
				osbd.setProductname(productname[i]);
				osbd.setSpecmode(specmode[i]);
				osbd.setUnitid(unitid[i]);
				osbd.setBatch(batch[i]);
				osbd.setUnitprice(0.00);
				osbd.setQuantity(quantity[i]);
				osbd.setSubsum(0.00);
				osbd.setTakequantity(0d);				
				apobd.addOtherShipmentBillDetail(osbd);
				totalsum+=osbd.getSubsum();
				wbds.add(osb.getId(), osbd.getProductid(), osbd.getUnitid(), osbd.getQuantity(), osbd.getBatch());
				
				ps = new ProductStockpile();
				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_stockpile", 0, "")));
				ps.setProductid(osbd.getProductid());
				ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
				ps.setBatch(osbd.getBatch());
				ps.setProducedate("");
				ps.setVad("");				
				ps.setWarehouseid(osb.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				aps.addProductByPurchaseIncome2(ps);
				
				apsa.prepareOut(osbd.getProductid(), osbd.getUnitid(), osb.getWarehouseid(),
						osbd.getBatch(), osbd.getQuantity());			
				aps.prepareOut(osbd.getProductid(), osbd.getUnitid(), osb.getWarehouseid(),Constants.WAREHOUSE_BIT_DEFAULT,
						osbd.getBatch(), osbd.getQuantity());
			}
			osb.setTotalsum(totalsum);
			osb.setKeyscontent(keyscontent.toString());
			AppOtherShipmentBill apos = new AppOtherShipmentBill();
			apos.addOtherShipmentBill(osb);

			
			request.setAttribute("result", "databases.add.success");

            DBUserLog.addUserLog(users.getUserid(), 7, "库存盘点>>新增盘亏单,编号:"+osid);	
			return mapping.findForward("addresult");
		} catch (Exception e) {			
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
