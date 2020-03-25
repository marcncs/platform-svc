package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddSaleShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		super.initdata(request);try{

			ShipmentBill sb = new ShipmentBill();
			String sbid = MakeCode
					.getExcIDByRandomTableName("shipment_bill", 2, "SL");
			sb.setId(sbid);
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
			sb.setIsaudit(0);
			sb.setMakeid(userid);
			sb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			sb.setIstrans(0);
		 

			
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

			// String strtotalsum=request.getParameter("totalsum");
			Double totalsum = 0.00;
			// if(strtotalsum!=null&&!strtotalsum.equals("")){
			// totalsum=Double.valueOf(strtotalsum);
			// }
			String productid;
			
			Double quantity, discount, taxrate;
			String productname, specmode, batch;

			AppShipmentBill asb = new AppShipmentBill();

//			if (strproductid == null) {
//				
//				String result = "databases.makeshipment.nostockpile";
//				request.setAttribute("result", "databases.add.success");
//				return new ActionForward("/sys/lockrecord.jsp");
//			}

			AppShipmentBillDetail aspb = new AppShipmentBillDetail();
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				Integer unitid = Integer.valueOf(strunitid[i]);
				String warehouseid = strwarehouseid[i];
				batch = "";
				if (DataValidate.IsDouble(strunitprice[i])) {
				} else {
				}
				if (DataValidate.IsDouble(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0.00);
				}
				if (DataValidate.IsDouble(strdiscount[i])) {
					discount = Double.valueOf(strdiscount[i]);
				} else {
					discount = Double.valueOf(0.00);
				}
				if (DataValidate.IsDouble(strtaxrate[i])) {
					taxrate = Double.valueOf(strtaxrate[i]);
				} else {
					taxrate = Double.valueOf(0.00);
				}

				ShipmentBillDetail sbd = new ShipmentBillDetail();
				sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"shipment_bill_detail", 0, "")));
				sbd.setSbid(sbid);
				sbd.setProductid(productid);
				sbd.setProductname(productname);
				sbd.setSpecmode(specmode);
				sbd.setWarehouseid(warehouseid);
				sbd.setUnitid(unitid);
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
			asb.addShipmentBill(sb);

			
			request.setAttribute("result", "databases.add.success");

			//DBUserLog.addUserLog(userid, "新增销售出库");			
			return mapping.findForward("addresult");
		} catch (Exception e) {			
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
