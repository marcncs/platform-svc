package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

public class UpdOtherShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			AppOtherShipmentBill aosb = new AppOtherShipmentBill();
			String osid = request.getParameter("ID");
			OtherShipmentBill osb = aosb.getOtherShipmentBillByID(osid);		
			OtherShipmentBill oldosb = (OtherShipmentBill)BeanUtils.cloneBean(osb);
		
			osb.setWarehouseid(request.getParameter("warehouseid"));
			osb.setBillno(request.getParameter("billno"));
			osb.setShipmentsort(RequestTool.getInt(request, "shipmentsort"));			
			osb.setRequiredate(RequestTool.getDate(request, "requiredate"));			
			osb.setRemark(request.getParameter("remark"));
			

			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String batch[] = request.getParameterValues("batch");
			//double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			
			StringBuffer keyscontent = new StringBuffer();			
			keyscontent.append(osb.getId()).append(",").append(osb.getBillno()).append(",")
		    .append(osb.getRemark()).append(",");
			
			String isaccount = request.getParameter("isaccount");
			//不记账
			if(isaccount==null){
				osb.setIsaccount(0);
			}else{
				//记账
				osb.setIsaccount(1);
			}

			AppProductStockpileAll apsa = new AppProductStockpileAll();
			AppProductStockpile aps = new AppProductStockpile();
			AppOtherShipmentBillDetail asbd = new AppOtherShipmentBillDetail();
			
			/*
			 * 删除旧的明细，并还原预出库
			 */
			ProductStockpile ps = null;
			AppProduct ap = new AppProduct();
			List<OtherShipmentBillDetail> ls = asbd.getOtherShipmentBillDetailBySbID(osid);			
			for (OtherShipmentBillDetail osd : ls) {
				ps = new ProductStockpile();
				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_stockpile", 0, "")));
				ps.setProductid(osd.getProductid());
				ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
				ps.setBatch(osd.getBatch());
				ps.setProducedate("");
				ps.setVad("");				
				ps.setWarehouseid(oldosb.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				aps.addProductByPurchaseIncome2(ps);
				
				apsa.freeStockpile(osd.getProductid(), osd.getUnitid(), oldosb
						.getWarehouseid(), osd.getBatch(), osd.getQuantity());
				aps.freeStockpile(osd.getProductid(), osd.getUnitid(), oldosb
						.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, osd.getBatch(), osd.getQuantity());
			}

			asbd.delOtherShipmentBillDetailBySbID(osid);
			Double totalsum = 0.00;
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_shipment_bill_idcode","osid",oldosb.getWarehouseid());
			wbds.del(oldosb.getId(), productid);
			
			/*
			 * 重建明细
			 */
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
				asbd.addOtherShipmentBillDetail(osbd);
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
			aosb.updOtherShipmentBill(osb);
			
			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(users.getUserid(), 7, "库存盘点>>修改盘亏单,编号:"+osid, oldosb, osb);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return new ActionForward(mapping.getInput());
	}
}
