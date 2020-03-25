package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

public class UpdSaleIndentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		// Connection conn = null;

		try {

			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", "databases.upd.success");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			AppSaleIndent aso = new AppSaleIndent();
			String id = request.getParameter("ID");
			SaleIndent so = aso.getSaleIndentByID(id);
			SaleIndent oldso = (SaleIndent)BeanUtils.cloneBean(so);
			so.setCid(cid);
			so.setCname(request.getParameter("cname"));
			so.setCustomerbillid(request.getParameter("customerbillid"));
			so.setTransportmode(Integer.valueOf(request
					.getParameter("transportmode")));

			so.setTransportaddr(request
					.getParameter("transportaddr"));
			so.setReceiveman(request.getParameter("receiveman"));
			so.setTel(request.getParameter("tel"));
			so.setPaymentmode(Integer.valueOf(request
					.getParameter("paymentmode")));
			so.setConsignmentdate(DateUtil.StringToDate(request
					.getParameter("consignmentdate")));
			so.setRemark(request.getParameter("remark"));
			so.setUpdateid(userid);
			so.setLastupdate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
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

			// Session 
			
			

			AppSaleIndentDetail asld = new AppSaleIndentDetail();
			asld.delSaleIndentDetail(id);

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
				sod.setSiid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setDiscount(Double.valueOf(strdiscount[i]));
				sod.setTaxrate(Double.valueOf(strtaxrate[i]));
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid);
				sod.setUnitprice(unitprice);
				sod.setQuantity(quantity);
				sod.setSubsum(subsum);
				asld.addSaleIndentDetail(sod);
				totalsum += sod.getSubsum();
			}
			so.setTotalsum(totalsum);
			aso.updSaleIndent(so);

			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 6,"零售预订单>>修改零售预订单,编号:"+id, oldso, so);
			// conn.commit();
			return mapping.findForward("success");
		} catch (Exception e) {
			System.out.println("the exception is:" + e.toString());
			// conn.rollback();
			e.printStackTrace();
		} finally {
			// 
		}

		return new ActionForward(mapping.getInput());
	}
}
