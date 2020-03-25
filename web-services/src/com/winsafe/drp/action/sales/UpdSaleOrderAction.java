package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdSaleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		AppSaleOrder aso = new AppSaleOrder();

		try {

			String cid = request.getParameter("cid");
			String id = request.getParameter("id");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.upd.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			SaleOrder so = aso.getSaleOrderByID(id);
			SaleOrder oldso = (SaleOrder) BeanUtils.cloneBean(so);

			so.setCustomerbillid(request.getParameter("customerbillid"));
			so.setCid(cid);
			so.setCname(request.getParameter("cname"));
			so.setCmobile(request.getParameter("cmobile"));
			so.setDecideman(request.getParameter("decideman"));
			so.setDecidemantel(request.getParameter("decidemantel"));
			so.setReceiveman(request.getParameter("receiveman"));
			so.setReceivemobile(request.getParameter("receivemobile"));
			so.setReceivetel(request.getParameter("receivetel"));
			so.setPaymentmode(RequestTool.getInt(request, "paymentmode"));
			String consignmentdate = request.getParameter("consignmentdate");
			String consignmenttime = request.getParameter("consignmenttime");
			so.setConsignmentdate(DateUtil.StringToDatetime(consignmentdate
					+ " " + consignmenttime));
			so.setTransportmode(Integer.valueOf(request
					.getParameter("transportmode")));
			// so.setTransit(Integer.valueOf(request.getParameter("transit")));
			so.setTransportaddr(request.getParameter("transportaddr"));
			so.setInvmsg(RequestTool.getInt(request, "invmsg"));
			so.setSource(RequestTool.getInt(request, "source"));
			so.setTickettitle(request.getParameter("tickettitle"));
			so.setEquiporganid(request.getParameter("equiporganid"));

			so.setRemark(request.getParameter("remark"));
			so.setUpdateid(userid);
			so.setLastupdate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));

			double totalsum = 0.00;
			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int salesort[] = RequestTool.getInts(request, "salesort");
			int wise[] = RequestTool.getInts(request, "wise");
			String warehouseid[] = request.getParameterValues("warehouseid");			
			int unitid[] = RequestTool.getInts(request, "unitid");
			double orgunitprice[] = RequestTool.getDoubles(request, "orgunitprice");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double taxunitprice[] = RequestTool.getDoubles(request, "taxunitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			double discount[] = RequestTool.getDoubles(request, "discount");
			double taxrate[] = RequestTool.getDoubles(request, "taxrate");
			double cost[] = RequestTool.getDoubles(request, "cost");

			StringBuffer keyscontent = new StringBuffer();
			

			// 删除明细
			AppSaleOrderDetail asld = new AppSaleOrderDetail();
			asld.delSaleOrderBySOID(id);

			for (int i = 0; i < productid.length; i++) {
				SaleOrderDetail sod = new SaleOrderDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"sale_order_detail", 0, "")));
				sod.setSoid(id);
				sod.setSalesort(salesort[i]);
				sod.setWise(wise[i]);
				sod.setProductid(productid[i]);
				sod.setProductname(productname[i]);
				sod.setSpecmode(specmode[i]);
				sod.setWarehouseid(warehouseid[i]);
				sod.setUnitid(unitid[i]);
				sod.setOrgunitprice(orgunitprice[i]);
				sod.setUnitprice(unitprice[i]);
				sod.setTaxunitprice(taxunitprice[i]);
				sod.setQuantity(quantity[i]);
				sod.setTakequantity(0d);
				sod.setDiscount(discount[i]);
				sod.setTaxrate(taxrate[i]);
				sod.setBatch("");
				sod.setCost(cost[i]);
				sod.setSubsum(DataFormat.countPrice(sod.getQuantity(), sod.getTaxunitprice(),sod.getDiscount(), sod.getTaxrate()));
				asld.addSaleOrderDetail(sod);
				totalsum += sod.getSubsum();

			}
			so.setTotalsum(totalsum);
			so.setKeyscontent(keyscontent.toString());
			aso.updSaleOrder(so);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 6, "零售管理>>修改销售单" + so.getId(),oldso, so);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
