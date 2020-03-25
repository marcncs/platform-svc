package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCAddr;
import com.winsafe.drp.dao.AppCTitle;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringFilters;

public class AddSaleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.add.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			AppCustomer ac = new AppCustomer();
			Customer c = ac.getCustomer(cid);			

			String consignmentdate = request.getParameter("consignmentdate");
			String consignmenttime = request.getParameter("consignmenttime");
			String transportaddr = request.getParameter("transportaddr");
			String tickettitle = request.getParameter("tickettitle");
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
			

			SaleOrder so = new SaleOrder();
			String soid = MakeCode.getExcIDByRandomTableName("sale_order", 2,"SO");
			so.setId(soid);
			so.setSosort(1);
			so.setCustomerbillid(request.getParameter("customerbillid"));
			so.setCid(cid);
			so.setCname(request.getParameter("cname"));
			so.setCmobile(request.getParameter("cmobile"));
			so.setProvince(c.getProvince());
			so.setCity(c.getCity());
			so.setAreas(c.getAreas());
			so.setDecideman(request.getParameter("decideman"));
			so.setDecidemantel(request.getParameter("decidemantel"));
			so.setReceiveman(request.getParameter("receiveman"));
			so.setReceivemobile(request.getParameter("receivemobile"));
			so.setReceivetel(request.getParameter("receivetel"));
			so.setPaymentmode(RequestTool.getInt(request, "paymentmode"));
			so.setConsignmentdate(DateUtil.StringToDatetime(consignmentdate
					+ " " + consignmenttime));
			so.setSource(RequestTool.getInt(request, "source"));
			so.setTransportmode(RequestTool.getInt(request, "transportmode"));
			so.setTransit(0);
			so.setTransportaddr(request.getParameter("transportaddr"));
			so.setInvmsg(RequestTool.getInt(request, "invmsg"));
			so.setIsmaketicket(0);
			so.setTickettitle(tickettitle);
			so.setMakeorganid(users.getMakeorganid());
			so.setMakedeptid(users.getMakedeptid());
			so.setEquiporganid(request.getParameter("equiporganid"));
			so.setRemark( request.getParameter("remark"));
			so.setMakeid(userid);
			so.setMakedate(DateUtil.getCurrentDate());
			so.setIsaudit(0);
			so.setIsendcase(0);
			so.setIsblankout(0);
			so.setTakestatus(0);
			so.setIsdaybalance(1);
			so.setIsaccount(1);

			StringBuffer msgconent = new StringBuffer();
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(so.getId()).append(",").append(so.getCid()).append(",")
			.append(so.getCname()).append(",").append(so.getCmobile());

			AppSaleOrder asl = new AppSaleOrder();
			AppSaleOrderDetail asld = new AppSaleOrderDetail();
			SaleOrderDetail sod = null;
			for (int i = 0; i < productid.length; i++) {
				sod = new SaleOrderDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"sale_order_detail", 0, "")));
				sod.setSoid(soid);
				sod.setProductid(productid[i]);
				sod.setProductname(productname[i]);
				sod.setSpecmode(specmode[i]);
				sod.setSalesort(salesort[i]);
				sod.setWise(wise[i]);
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
				msgconent.append(sod.getProductname()).append("|").append(sod.getSpecmode()).append(",");
			}

			so.setTotalsum(totalsum);
			so.setKeyscontent(keyscontent.toString());
			asl.addSaleOrder(so);

			
			if (transportaddr != null && !transportaddr.equals("")) {
				AppCAddr ca = new AppCAddr();
				Integer caid = Integer.valueOf(MakeCode
						.getExcIDByRandomTableName("c_addr", 0, ""));
				ca.addCAddrNoExists(caid, cid, StringFilters
						.CommaToDot(transportaddr));
			}


			if (tickettitle != null && !tickettitle.equals("")) {
				AppCTitle act = new AppCTitle();
				Integer ctid = Integer.valueOf(MakeCode
						.getExcIDByRandomTableName("c_title", 0, ""));
				act.addCTitleNoExists(ctid, cid, tickettitle);
			}

			// 是否发送短信
			int sendmsg = Integer.valueOf(request.getParameter("sendmsg"));
			int autosend = Integer.valueOf(request.getParameter("autosend"));
			if (sendmsg == 1) {							
				String[] param = new String[]{"name","product","money"};
				String[] values = new String[]{c.getCname(),msgconent.toString(),so.getTotalsum().toString()};
				MsgService ms = new MsgService(param, values, users, 2);
				ms.addmag(autosend, c.getMobile());			
			}

			request.setAttribute("result", "databases.add.success");
			request.setAttribute("gourl", "../sales/toAddSaleOrderAction.do");
			DBUserLog.addUserLog(userid, 6, "零售管理>>新增销售订单,编号：" + so.getId());

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
