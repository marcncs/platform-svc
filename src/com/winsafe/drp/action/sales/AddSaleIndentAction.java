package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleIndent;
import com.winsafe.drp.dao.AppSaleIndentDetail;
import com.winsafe.drp.dao.SaleIndent;
import com.winsafe.drp.dao.SaleIndentDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddSaleIndentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		String cid = request.getParameter("cid");

		try {

			String cname = request.getParameter("cname");
			String customerbillid = request.getParameter("customerbillid");
			Integer transportmode = Integer.valueOf(request
					.getParameter("transportmode"));
			String transportaddr = request.getParameter("transportaddr");
			String receiveman = request.getParameter("receiveman");
			String tel = request.getParameter("tel");
			String consignmentdate = request.getParameter("consignmentdate");
			String paymentmode = request.getParameter("paymentmode");
			String remark = request.getParameter("remark");
			double totalsum = 0.00;

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			// String strsubsum[] = request.getParameterValues("subsum");
			String strdiscount[] = request.getParameterValues("discount");
			String strtaxrate[] = request.getParameterValues("taxrate");
			String productid;
			String productname, specmode;
			Integer unitid;
			Double quantity, discount, taxrate;
			Double unitprice, subsum;

			SaleIndent so = new SaleIndent();
			String soid = MakeCode.getExcIDByRandomTableName("sale_indent", 2,
					"");
			so.setId(soid);
			so.setCustomerbillid(customerbillid);
			so.setCid(cid);
			so.setCname(cname);

			so.setConsignmentdate(DateUtil.StringToDate(consignmentdate));
			so.setTransportmode(transportmode);
			so.setTransportaddr(transportaddr);
			so.setReceiveman(receiveman);
			so.setTel(tel);
			so.setMakeorganid(users.getMakeorganid());
			so.setMakedeptid(users.getMakedeptid());
			so.setMakeid(userid);
			so.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			so.setUpdateid(userid);
			so.setLastupdate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			so.setPaymentmode(Integer.parseInt(paymentmode));
			so.setRemark(remark);
			so.setIsaudit(0);
			so.setIsendcase(0);

			AppSaleIndent asl = new AppSaleIndent();

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];

				unitid = Integer.valueOf(strunitid[i]);

				unitprice = DataValidate.IsDouble(strunitprice[i]) ? Double
						.valueOf(strunitprice[i]) : 0;
				quantity = DataValidate.IsDouble(strquantity[i]) ? Double
						.valueOf(strquantity[i]) : 0;
				discount = DataValidate.IsDouble(strdiscount[i]) ? Double
						.valueOf(strdiscount[i]) : 0;
				taxrate = DataValidate.IsDouble(strtaxrate[i]) ? Double
						.valueOf(strtaxrate[i]) : 0;
				subsum = DataFormat.countPrice(quantity, unitprice, discount,
						taxrate);

				SaleIndentDetail sod = new SaleIndentDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"sale_indent_detail", 0, "")));
				sod.setSiid(soid);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setDiscount(discount);
				sod.setTaxrate(taxrate);
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid);
				sod.setUnitprice(unitprice);
				sod.setQuantity(quantity);
				sod.setSubsum(subsum);
				totalsum += sod.getSubsum();

				AppSaleIndentDetail asld = new AppSaleIndentDetail();
				asld.addSaleIndentDetail(sod);
			}
			so.setTotalsum(totalsum);
			asl.addSaleIndent(so);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, "新增销售订单");
			// conn.commit();
			return mapping.findForward("success");
		} catch (Exception e) {
			// conn.rollback();
			e.printStackTrace();
		} finally {
			// 
		}

		return new ActionForward(mapping.getInput());
	}
}
