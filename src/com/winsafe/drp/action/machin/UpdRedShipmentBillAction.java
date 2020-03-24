package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdRedShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			AppOtherShipmentBillAll aosb = new AppOtherShipmentBillAll();
			String osid = request.getParameter("ID");
			OtherShipmentBillAll osb = aosb.getOtherShipmentBillByID(osid);		
			OtherShipmentBillAll oldosb = (OtherShipmentBillAll)BeanUtils.cloneBean(osb);
			osb.setOrganid(request.getParameter("organid"));
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
			AppOtherShipmentBillDAll asbd = new AppOtherShipmentBillDAll();
			ProductStockpile ps = null;
			AppProduct ap = new AppProduct();
			
			/*
			 * 删除旧的明细，并还原预出库
			 */
//			List<OtherShipmentBillDAll> ls = asbd.getOtherShipmentBillDetailBySbID(osid);			
//			for (OtherShipmentBillDAll osd : ls) {
//				ps = new ProductStockpile();
//				FUnit fu = new FUnit();
//				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"product_stockpile", 0, "")));
//				ps.setProductid(osd.getProductid());
//				ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
//				ps.setBatch(osd.getBatch());
//				ps.setProducedate("");
//				ps.setVad("");				
//				ps.setWarehouseid(oldosb.getWarehouseid());
//				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
//				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//				aps.addProductByPurchaseIncome2(ps);
//				//根据单位换算
//				fu = af.getFUnit(osd.getProductid(), osd.getUnitid());
//				apsa.freeStockpile(osd.getProductid(), osd.getUnitid(), oldosb
//						.getWarehouseid(), osd.getBatch(), osd.getQuantity()*fu.getXquantity());
//				aps.freeStockpile(osd.getProductid(), osd.getUnitid(), oldosb
//						.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, osd.getBatch(), osd.getQuantity()*fu.getXquantity());
//			}

			asbd.delOtherShipmentBillDetailBySbID(osid);
			Double totalsum = 0.00;
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_shipment_bill_idcode","osid",oldosb.getWarehouseid());
			wbds.del(oldosb.getId(), productid);
			
			/*
			 * 重建明细
			 */
			for (int i = 0; i < productid.length; i++) {					
				OtherShipmentBillDAll osbd = new OtherShipmentBillDAll();
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
				//库存操作
//				ps = new ProductStockpile();
//				FUnit fu = new FUnit();
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
//				//根据单位换算
//				fu = af.getFUnit(osbd.getProductid(), osbd.getUnitid());
//				apsa.prepareOut(osbd.getProductid(), osbd.getUnitid(), osb.getWarehouseid(),
//						osbd.getBatch(), osbd.getQuantity()*fu.getXquantity());
//				aps.prepareOut(osbd.getProductid(), osbd.getUnitid(), osb.getWarehouseid(),Constants.WAREHOUSE_BIT_DEFAULT,
//						osbd.getBatch(), osbd.getQuantity()*fu.getXquantity());
				
			}
			osb.setTotalsum(totalsum);
			osb.setKeyscontent(keyscontent.toString());
			aosb.updOtherShipmentBill(osb);
			
			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(request, "编号:"+osid, oldosb, osb);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return new ActionForward(mapping.getInput());
	}
}
