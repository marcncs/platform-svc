package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdSaleShipmentAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  DataValidate dv = new DataValidate();
		MakeCode mc = new MakeCode();
//		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		AppWarehouse aw = new AppWarehouse();
		AppShipmentBill asb = new AppShipmentBill();
		

		super.initdata(request);try{
			String id = request.getParameter("id");
			ShipmentBill sb = asb.getShipmentBillByID(id);
			
//			sb.setSoid(request.getParameter("soid"));
//			sb.setSaledept(Long.valueOf(request.getParameter("saledept")));
//			sb.setSaleid(Long.valueOf(request.getParameter("saleid")));
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			sb.setCid(cid);
			sb.setCname(request.getParameter("cname"));
			sb.setLinkman(request.getParameter("linkman"));
			sb.setTel(request.getParameter("tel"));
			sb.setReceiveaddr(request.getParameter("receiveaddr"));
			sb.setTransportmode(Integer.valueOf(request
					.getParameter("transportmode")));
			sb.setTransportnum(request.getParameter("transportnum"));
			sb.setRequiredate(DateUtil.StringToDate(request
					.getParameter("requiredate")));
			sb.setRemark(request.getParameter("remark"));
			// sb.setTotalsum(Double.valueOf(0.00));
			

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strwarehouseid[] = request.getParameterValues("warehouseid");
			String strunitid[] = request.getParameterValues("unitid");
			String strbatch[] = request.getParameterValues("batch");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			String strdiscount[] = request.getParameterValues("discount");
			String strtaxrate[] = request.getParameterValues("taxrate");
			String strsubsum[] = request.getParameterValues("subsum");

			Double totalsum = 0.00;		
			String productid;
			Long unitid, warehouseid;
			Double unitprice, quantity, stockpile, discount, taxrate;
			String productname, specmode, batch;

			AppProductStockpile aps = new AppProductStockpile();
			
//			if (strproductid == null) {
//				conn.rollback();
//				String result = "databases.makeshipment.nostockpile";
//				request.setAttribute("result", "databases.upd.success");
//				return new ActionForward("/sys/lockrecord.jsp");
//			}

			AppShipmentBillDetail aspb = new AppShipmentBillDetail();
			aspb.delShipmentProductBillBySbID(id);
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Long.valueOf(strunitid[i]);
				warehouseid = Long.valueOf(strwarehouseid[i]);
				batch = "";
				if (dv.IsDouble(strunitprice[i])) {
					unitprice = Double.valueOf(strunitprice[i]);
				} else {
					unitprice = Double.valueOf(0.00);
				}
				if (dv.IsDouble(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0.00);
				}
				if (dv.IsDouble(strdiscount[i])) {
					discount = Double.valueOf(strdiscount[i]);
				} else {
					discount = Double.valueOf(0.00);
				}
				if (dv.IsDouble(strtaxrate[i])) {
					taxrate = Double.valueOf(strtaxrate[i]);
				} else {
					taxrate = Double.valueOf(0.00);
				}

//				stockpile = aps.getProductStockpileByProductIDWIDBatch(
//						productid, warehouseid, batch);
//				if (stockpile < quantity || quantity <= 0.00) {
//					conn.rollback();
//					String result = "databases.makeshipment.nostockpile";
//					request.setAttribute("result", "databases.upd.success");
//					return new ActionForward("/sys/lockrecord.jsp");
//				}

				ShipmentBillDetail sbd = new ShipmentBillDetail();
//				sbd.setId(Long.valueOf(mc.getExcIDByRandomTableName(
//						"shipment_bill_detail", 0, "")));
				sbd.setSbid(id);
				sbd.setProductid(productid);
				sbd.setProductname(productname);
				sbd.setSpecmode(specmode);
//				sbd.setWarehouseid(warehouseid);
//				sbd.setUnitid(unitid);
				sbd.setBatch(batch);
				sbd.setUnitprice(Double.valueOf(strunitprice[i]));
				sbd.setQuantity(quantity);
				sbd.setDiscount(discount);
				sbd.setTaxrate(taxrate);
				sbd.setSubsum(Double.valueOf(strsubsum[i]));

				totalsum += sbd.getSubsum();				
				aspb.addShipmentBillDetail(sbd);

				// 
//				String preupd = aps.prepareOut(productid, warehouseid, batch,
//						quantity);
			}

			sb.setTotalsum(totalsum);
			asb.updShipmentBill(sb);

			
			request.setAttribute("result", "databases.upd.success");

//			DBUserLog.addUserLog(userid, "修改销售出库");

			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			//

		}

		return new ActionForward(mapping.getInput());
  }
}
