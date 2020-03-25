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
import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.AppMsgTemplate;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Msg;
import com.winsafe.drp.dao.VocationOrder;
import com.winsafe.drp.dao.VocationOrderDetail;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.MakeCode;

public class AddVocationOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Long userid = users.getUserid();
		initdata(request);
		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			AppCustomer ac = new AppCustomer();
			Customer c = ac.getCustomer(cid);
			String cname = request.getParameter("cname");
			String customerbillid = request.getParameter("customerbillid");
			String cmobile = request.getParameter("cmobile");
			String decideman = request.getParameter("decideman");
			String decidemantel = request.getParameter("decidemantel");
			String receiveman = request.getParameter("receiveman");
			String receivemobile = request.getParameter("receivemobile");
			String receivetel = request.getParameter("receivetel");
			// Long saledept = Long.valueOf(request.getParameter("saledept"));
			// Long saleid = Long.valueOf(request.getParameter("saleid"));
			Integer paymentmode = Integer.valueOf(request
					.getParameter("paymentmode"));
			String consignmentdate = request.getParameter("consignmentdate");
			String consignmenttime = request.getParameter("consignmenttime");
			Integer source = Integer.valueOf(request.getParameter("source"));
			Integer transportmode = Integer.valueOf(request
					.getParameter("transportmode"));
			// Integer transit =
			// Integer.valueOf(request.getParameter("transit"));
			String transportaddr = request.getParameter("transportaddr");
			Integer invmsg = Integer.valueOf(request.getParameter("invmsg"));
			String tickettitle = request.getParameter("tickettitle");
			String equiporganid = request.getParameter("equiporganid");

			String remark = request.getParameter("remark");
			// Integer isneedticket =
			// Integer.valueOf(request.getParameter("isneedticket"));

			double totalsum = 0.00;

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strsalesort[] = request.getParameterValues("salesort");
			String strwarehouseid[] = request.getParameterValues("warehouseid");
			String strunitid[] = request.getParameterValues("unitid");
			String strorgunitprice[] = request
					.getParameterValues("orgunitprice");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strtaxunitprice[] = request
					.getParameterValues("taxunitprice");
			String strquantity[] = request.getParameterValues("quantity");
			// String strkickback[] = request.getParameterValues("kickback");
			String strdiscount[] = request.getParameterValues("discount");
			String strtaxrate[] = request.getParameterValues("taxrate");
			// String strsubsum[] = request.getParameterValues("subsum");
			// String strbatch[] = request.getParameterValues("batch");
			String strcost[] = request.getParameterValues("cost");
			String productid;
			String productname, specmode;// ,batch;
			Long unitid, warehouseid;
			Double quantity;
			Double orgunitprice, unitprice, taxunitprice, subsum, discount, taxrate, cost;// kickback,
			Integer salesort = 0;
			StringBuffer msgconent = new StringBuffer();

			VocationOrder so = new VocationOrder();
			String soid = MakeCode.getExcIDByRandomTableName("vocation_order",
					2, "VO");
			so.setId(soid);
			so.setCustomerbillid(customerbillid);
			so.setCid(cid);
			so.setCname(cname);
			so.setCmobile(cmobile);
			// so.setRegion(c.getRegion());
			so.setProvince(c.getProvince());
			so.setCity(c.getCity());
			so.setAreas(c.getAreas());
			so.setDecideman(decideman);
			so.setDecidemantel(decidemantel);
			so.setReceiveman(receiveman);
			so.setReceivemobile(receivemobile);
			so.setReceivetel(receivetel);
			// so.setSaledept(users.getMakedeptid());
			// so.setSaleid(userid);
			so.setPaymentmode(paymentmode);
			so.setConsignmentdate(DateUtil.StringToDatetime(consignmentdate
					+ " " + consignmenttime));
			so.setSource(source);
			so.setTransportmode(transportmode);
			so.setTransit(0);
			so.setTransportaddr(transportaddr);
			so.setInvmsg(invmsg);
			so.setIsmaketicket(0);
			so.setTickettitle(tickettitle);
			so.setMakeorganid(users.getMakeorganid());
			// so.setMakedeptid(users.getMakedeptid());
			so.setEquiporganid(equiporganid);

			so.setRemark(remark);
			// so.setMakeid(userid);
			so.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			// so.setUpdateid(userid);
			// so.setLastupdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			so.setIsaudit(0);
			so.setIsendcase(0);
			so.setIsblankout(0);
			so.setTakestatus(0);

			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(so.getId()).append(",").append(so.getCname())
					.append(",").append(so.getCmobile()).append(",").append(
							so.getDecideman()).append(",").append(
							so.getDecidemantel()).append(",").append(
							so.getReceiveman()).append(",").append(
							so.getReceivemobile()).append(",");
			AppVocationOrder asl = new AppVocationOrder();
			AppVocationOrderDetail asld = new AppVocationOrderDetail();
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				salesort = DataValidate.IsInteger(strsalesort[i]) ? Integer
						.valueOf(strsalesort[i]) : 0;

				unitid = Long.valueOf(strunitid[i]);
				warehouseid = 0l;
				if (!"".equals(strwarehouseid[i])) {
					warehouseid = Long.valueOf(strwarehouseid[i]);
				}

				orgunitprice = DataValidate.IsDouble(strorgunitprice[i]) ? Double
						.valueOf(strorgunitprice[i])
						: 0;
				unitprice = DataValidate.IsDouble(strunitprice[i]) ? Double
						.valueOf(strunitprice[i]) : 0;
				taxunitprice = DataValidate.IsDouble(strtaxunitprice[i]) ? Double
						.valueOf(strtaxunitprice[i])
						: 0;
				quantity = DataValidate.IsDouble(strquantity[i]) ? Double
						.valueOf(strquantity[i]) : 0;
				discount = DataValidate.IsDouble(strdiscount[i]) ? Double
						.valueOf(strdiscount[i]) : 0;
				taxrate = DataValidate.IsDouble(strtaxrate[i]) ? Double
						.valueOf(strtaxrate[i]) : 0;
				// kickback =
				// DataValidate.IsDouble(strkickback[i])?Double.valueOf(strkickback[i]):0;
				if (DataValidate.IsDouble(strcost[i])) {
					cost = Double.valueOf(strcost[i]);
				} else {
					cost = Double.valueOf(0);
				}
				subsum = DataFormat.countPrice(quantity, taxunitprice,
						discount, taxrate);// + kickback;

				VocationOrderDetail sod = new VocationOrderDetail();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"vocation_order_detail", 0, "")));
				sod.setSoid(soid);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
				sod.setSalesort(salesort);
				sod.setWarehouseid(warehouseid);
				sod.setUnitid(unitid);
				sod.setOrgunitprice(orgunitprice);
				sod.setUnitprice(unitprice);
				sod.setTaxunitprice(taxunitprice);
				sod.setQuantity(quantity);
				sod.setTakequantity(0d);
				sod.setDiscount(discount);
				sod.setTaxrate(taxrate);
				sod.setBatch("");
				sod.setCost(cost);
				sod.setSubsum(subsum);
				asld.addVocationOrderDetail(sod);
				totalsum += sod.getSubsum();

				keyscontent.append(sod.getProductid()).append(",").append(
						sod.getProductname()).append(",").append(
						sod.getSpecmode()).append(",");
				msgconent.append(sod.getProductname()).append(
						!"".equals(sod.getSpecmode()) ? ":" + sod.getSpecmode()
								: "").append(",").append(sod.getQuantity())
						.append(
								Internation.getStringByKeyPositionDB(
										"CountUnit", Integer.parseInt(sod
												.getUnitid().toString())))
						.append(",");

			}

			so.setTotalsum(totalsum);
			so.setKeyscontent(keyscontent.toString());
			asl.addVocationOrder(so);

			if (transportaddr != null && !transportaddr.equals("")) {
				AppCAddr ca = new AppCAddr();
				Long caid = Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"c_addr", 0, ""));
				// ca.addCAddrNoExists(caid, cid,
				// StringFilters.CommaToDot(transportaddr));
			}

			if (tickettitle != null && !tickettitle.equals("")) {
				AppCTitle act = new AppCTitle();
				Long ctid = Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"c_title", 0, ""));
				// act.addCTitleNoExists(ctid, cid, tickettitle);
			}

			int sendmsg = Integer.valueOf(request.getParameter("sendmsg"));
			int autosend = Integer.valueOf(request.getParameter("autosend"));
			if (sendmsg == 1) {
				AppMsgTemplate appmt = new AppMsgTemplate();
				String content = appmt.getSaleOrderMsg(c.getCname(), msgconent
						.toString(), so.getTotalsum().toString());
				AppMsg appmsg = new AppMsg();
				Msg msg = new Msg();
				// msg.setId(Long.parseLong(MakeCode
				// .getExcIDByRandomTableName("msg", 0, "")));
				msg.setMsgsort(1);
				msg.setMobileno(c.getMobile());
				msg.setMsgcontent(content);
				msg.setMakeorganid(users.getMakeorganid());
				msg.setMakedeptid(users.getMakedeptid());
				// msg.setMakeid(userid);
				msg.setMakedate(DateUtil.getCurrentDate());
				if (autosend == 1) {
					msg.setIsaudit(1);
					// msg.setAuditid(userid);
					msg.setAuditdate(DateUtil.getCurrentDate());
				} else {
					msg.setIsaudit(0);
					// msg.setAuditid(0l);
				}
				msg.setIsdeal(0);
				appmsg.addMsg(msg);
			}

			request.setAttribute("result", "databases.add.success");
			request.setAttribute("gourl",
					"../sales/toAddVocationOrderAction.do");
			// DBUserLog.addUserLog(userid,"新增行业销售单");

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
