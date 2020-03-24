package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLoanOut;
import com.winsafe.drp.dao.AppLoanOutDetail;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.LoanOutDetail;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class MakeSaleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AppLoanOutDetail apppod = new AppLoanOutDetail();
//		I userid = users.getUserid();
		
		try {

			String cid = request.getParameter("uid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", "databases.add.success");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String cname = request.getParameter("uname");
			String loid = request.getParameter("loid");
			String receiveman = request.getParameter("receiveman");
			String tel = request.getParameter("tel");
			Long saledept = Long.valueOf(request.getParameter("saledept"));
			Long saleid = Long.valueOf(request.getParameter("saleid"));
			String consignmentdate = request.getParameter("consignmentdate");
			Integer transportmode = Integer.valueOf(request
					.getParameter("transportmode"));
			Integer transit = Integer.valueOf(request.getParameter("transit"));
			String transportaddr = request.getParameter("transportaddr");
			String[] strproductid = request.getParameterValues("pid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String strwarehouseid[] = request.getParameterValues("warehouseid");
			String strbatch[] = request.getParameterValues("batch");
			String[] strunitprice = request.getParameterValues("unitprice");
			String[] stroperatorquantity = request
					.getParameterValues("operatorquantity");
			String[] strcost = request.getParameterValues("cost");
			String productid,batch;
			Double subsum, operatorquantity,unitprice;
			Double totalsum = 0.00;

		
			
			SaleOrder so = new SaleOrder();
		      String soid = MakeCode.getExcIDByRandomTableName("sale_order",2,"SO");
		      so.setId(soid);
		      so.setCustomerbillid(loid);
		      so.setCid(cid);
		      so.setCname(cname);
		      so.setReceiveman(receiveman);
//		      so.setTel(tel);
//		      so.setSalesort(2);
//		      so.setSaledept(saledept);
//		      so.setSaleid(saleid);
		      so.setPaymentmode(3);
		      so.setConsignmentdate(DateUtil.StringToDate(consignmentdate));
		      so.setTransportmode(transportmode);
		      so.setTransit(transit);
		      so.setTransportaddr(transportaddr);
		      so.setRemark("借出单转销售");
		      so.setTotalsum(totalsum);
//		      so.setMakeid(userid);
		      so.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//		      so.setUpdateid(userid);
		      so.setLastupdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		      so.setIsaudit(0);
		      so.setIsendcase(0);
		      so.setIsblankout(0);

			AppSaleOrderDetail apbd = new AppSaleOrderDetail();
			for (int i = 0; i < strproductid.length; i++) {
				batch = "";
				if (DataValidate.IsDouble(strunitprice[i])) {
					unitprice = Double.valueOf(strunitprice[i]);
				} else {
					unitprice = Double.valueOf(0.00);
				}
				if (DataValidate.IsDouble(stroperatorquantity[i])) {
					operatorquantity = Double.valueOf(stroperatorquantity[i]);
				} else {
					operatorquantity = Double.valueOf(0.00);
				}
				if (DataValidate.IsDouble(strcost[i])) {
				} else {
				}
				SaleOrderDetail pbd = new SaleOrderDetail();
//				pbd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"purchase_income_detail", 0, "")));
				pbd.setSoid(soid);
				productid = strproductid[i];
				pbd.setProductid(productid);
				pbd.setProductname(strproductname[i]);
				pbd.setSpecmode(strspecmode[i]);
//				pbd.setWarehouseid(Long.valueOf(strwarehouseid[i]));
				pbd.setBatch(batch);
//				pbd.setUnitid(Long.valueOf(strunitid[i]));
				pbd.setUnitprice(unitprice);				
				pbd.setQuantity(operatorquantity);
				pbd.setTakequantity(0d);
				pbd.setDiscount(0d);
				pbd.setTaxrate(0d);
				subsum = unitprice * operatorquantity;
				pbd.setSubsum(subsum);
				totalsum += subsum;

				apbd.addSaleOrderDetail(pbd);
				
				apppod.updBackQuantity(loid, productid, batch, operatorquantity);
				// AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
				// String upd =
				// apad.updPurchasePlanDetailChangeQuantity(ppid,productid,quantity);

			}
			so.setTotalsum(totalsum);

			AppSaleOrder apb = new AppSaleOrder();
			 apb.addSaleOrder(so);

			
			List podlist = apppod.getLoanOutDetailByLoid(loid);
			double totalquantity = 0;
			double totalincomequantity = 0;
			for (int i = 0; i < podlist.size(); i++) {
				LoanOutDetail pod = (LoanOutDetail) podlist.get(i);
				totalquantity += pod.getQuantity();
				totalincomequantity += pod.getBackquantity();
			}
			// 判断是否完成转采购入库单
			AppLoanOut appl = new AppLoanOut();
			if (totalquantity == totalincomequantity) {
//				appl.updIsTranssale(loid, userid, 1);
			}

			
			request.setAttribute("result", "databases.add.success");

//			DBUserLog.addUserLog(userid, "生成销售单");
			
			return mapping.findForward("make");
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
