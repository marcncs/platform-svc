package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
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
		super.initdata(request);
		try{
			OtherShipmentBillAll osb = new OtherShipmentBillAll();
			String osid = MakeCode.getExcIDByRandomTableName("other_shipment_bill_all",
					2, "QC");
			osb.setId(osid);	
			osb.setOrganid(request.getParameter("organid"));
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
			AppOtherShipmentBillDAll apobd = new AppOtherShipmentBillDAll();
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_shipment_bill_idcode","osid",osb.getWarehouseid());

			for (int i = 0; i < productid.length; i++) {				
				OtherShipmentBillDAll osbd = new OtherShipmentBillDAll();
				FUnit fu = new FUnit();
				osbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"other_shipment_bill_detail", 0, "")));
				osbd.setOsid(osid);
				osbd.setProductid(productid[i]);
				osbd.setProductname(productname[i]);
				osbd.setSpecmode(specmode[i]);
//				osbd.setUnitid(Constants.DEFAULT_UNIT_ID);
				osbd.setUnitid(unitid[i]);
				osbd.setBatch(batch[i]);
				osbd.setUnitprice(0.00);
				osbd.setQuantity(quantity[i]);
				//设置金额，应该是数量乘以单价
				osbd.setSubsum(0.00);
				osbd.setTakequantity(0d);				
				apobd.addOtherShipmentBillDetail(osbd);
				//计算总金额
				totalsum+=osbd.getSubsum();
				//增加一条idcode
				wbds.add(osb.getId(), osbd.getProductid(), osbd.getUnitid(), osbd.getQuantity(), osbd.getBatch());
				//对库存的操作
//				ps = new ProductStockpile();
//				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"product_stockpile", 0, "")));
//				ps.setProductid(osbd.getProductid());
//				ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
//				ps.setBatch(osbd.getBatch());
//				ps.setProducedate("");
//				ps.setVad("");				
//				ps.setWarehouseid(osb.getWarehouseid());
//				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
//				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//				aps.addProductByPurchaseIncome2(ps);
				//根据单位换算
//				fu = af.getFUnit(osbd.getProductid(), osbd.getUnitid());
//				apsa.prepareOut(osbd.getProductid(), osbd.getUnitid(), osb.getWarehouseid(),
//						osbd.getBatch(), osbd.getQuantity()*fu.getXquantity());			
//				aps.prepareOut(osbd.getProductid(), osbd.getUnitid(), osb.getWarehouseid(),Constants.WAREHOUSE_BIT_DEFAULT,
//						osbd.getBatch(), osbd.getQuantity()*fu.getXquantity());
			}
			//总金额
			osb.setTotalsum(totalsum);
			osb.setKeyscontent(keyscontent.toString());
			AppOtherShipmentBillAll apos = new AppOtherShipmentBillAll();
			apos.addOtherShipmentBill(osb);
			request.setAttribute("result", "databases.add.success");
            DBUserLog.addUserLog(request, "编号:"+osid);	
			return mapping.findForward("addresult");
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
}
