package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDemandPrice;
import com.winsafe.drp.dao.AppDemandPriceDetail;
import com.winsafe.drp.dao.DemandPrice;
import com.winsafe.drp.dao.DemandPriceDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddDemandPriceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", "databases.add.success");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String linkman = request.getParameter("linkman");
			String demandname = request.getParameter("demandname");
			;
			String tel = request.getParameter("tel");
			String remark = request.getParameter("remark");
			// String strtotalsum = request.getParameter("totalsum");
			double totalsum = 0.00;

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			String strdiscount[] = request.getParameterValues("discount");
			String strtaxrate[] = request.getParameterValues("taxrate");
			// String strsubsum[] = request.getParameterValues("subsum");
			String productid;
			String productname, specmode;
			Long unitid;
			Double quantity;
			Double discount, taxrate;
			Double unitprice, subsum;

			DemandPrice dp = new DemandPrice();
			Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"demand_price", 0, ""));
			dp.setId(id);
			dp.setCid(cid);
			dp.setLinkman(linkman);
			dp.setTel(tel);
			dp.setDemandname(demandname);
//			dp.setMakeid(userid);
			dp.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			dp.setRemark(remark);
			dp.setIsaudit(0);
			dp.setAuditid(Long.valueOf(0));
			dp.setIsover(Integer.valueOf(0));

			AppDemandPrice asl = new AppDemandPrice();
			AppDemandPriceDetail asld = new AppDemandPriceDetail();
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Long.valueOf(strunitid[i]);

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
				totalsum += subsum;

				DemandPriceDetail sod = new DemandPriceDetail();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"demand_price_detail", 0, "")));
				sod.setDpid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid);
				sod.setUnitprice(unitprice);
				sod.setQuantity(quantity);
				sod.setDiscount(discount);
				sod.setTaxrate(taxrate);
				sod.setSubsum(subsum);
				asld.addDemandPriceDetail(sod);
			}
			dp.setTotalsum(totalsum);
			asl.addDemandPrice(dp);

			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, "新增销售报价");
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
