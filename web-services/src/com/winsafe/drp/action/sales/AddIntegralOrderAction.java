package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCAddr;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.IntegralOrderDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringFilters;

public class AddIntegralOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
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
			String cmobile = request.getParameter("cmobile");
			String decideman = request.getParameter("decideman");
			String decidemantel = request.getParameter("decidemantel");
			String receiveman = request.getParameter("receiveman");
			String receivemobile = request.getParameter("receivemobile");
			String receivetel = request.getParameter("receivetel");
			// Long saledept = Long.valueOf(request.getParameter("saledept"));
			// Long saleid = Long.valueOf(request.getParameter("saleid"));
			String consignmentdate = request.getParameter("consignmentdate");
			String consignmenttime = request.getParameter("consignmenttime");
			Integer source = RequestTool.getInt(request,"source");
			Integer transportmode = RequestTool.getInt(request,"transportmode");
			// Integer transit =
			// Integer.valueOf(request.getParameter("transit"));
			String transportaddr = request.getParameter("transportaddr");
			String equiporganid = request.getParameter("equiporganid");
			String remark = request.getParameter("remark");

			double totalsum = 0.00;

			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			String warehouseid[] = request.getParameterValues("warehouseid");
			int unitid[] = RequestTool.getInts(request, "unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");

			Double subsum;// kickback,

			IntegralOrder so = new IntegralOrder();
			String soid = MakeCode.getExcIDByRandomTableName("integral_order",
					2, "IO");
			so.setId(soid);
			so.setCid(cid);
			so.setCname(cname);
			so.setCmobile(cmobile);
			so.setProvince(c.getProvince());
			so.setCity(c.getCity());
			so.setAreas(c.getAreas());
			so.setDecideman(decideman);
			so.setDecidemantel(decidemantel);
			so.setReceiveman(receiveman);
			so.setReceivemobile(receivemobile);
			so.setReceivetel(receivetel);
			so.setSaledept(users.getMakedeptid().intValue());
			so.setSaleid(userid);
			so.setConsignmentdate(DateUtil.StringToDatetime(consignmentdate
					+ " " + consignmenttime));
			so.setSource(source);
			so.setTransportmode(transportmode);
			so.setTransit(0);
			so.setTransportaddr(transportaddr);
			so.setMakeorganid(users.getMakeorganid());
			so.setMakedeptid(users.getMakedeptid());
			so.setEquiporganid(equiporganid);

			so.setRemark(remark);
			so.setMakeid(userid);
			so.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			so.setIsaudit(0);
			so.setIsendcase(0);
			so.setIsblankout(0);
			so.setTakestatus(0);
			so.setBilltype(0);

			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(so.getId()).append(",").append(so.getCname())
					.append(",").append(so.getCmobile()).append(",").append(
							so.getDecideman()).append(",").append(
							so.getDecidemantel()).append(",").append(
							so.getReceiveman()).append(",").append(
							so.getReceivemobile()).append(",");

			AppIntegralOrder asl = new AppIntegralOrder();
			AppIntegralOrderDetail asld = new AppIntegralOrderDetail();
			for (int i = 0; i < productid.length; i++) {

				subsum = quantity[i] * unitprice[i];// + kickback;

				IntegralOrderDetail sod = new IntegralOrderDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"integral_order_detail", 0, "")));
				sod.setIoid(soid);
				sod.setProductid(productid[i]);
				sod.setProductname(productname[i]);
				sod.setSpecmode(specmode[i]);
				sod.setWarehouseid(warehouseid[i]);
				sod.setUnitid(unitid[i]);
				sod.setIntegralprice(unitprice[i]);
				sod.setQuantity(quantity[i]);
				sod.setTakequantity(0d);
				sod.setSubsum(subsum);
				asld.addIntegralOrderDetail(sod);
				totalsum += sod.getSubsum();
				keyscontent.append(sod.getProductid()).append(",").append(
						sod.getProductname()).append(",").append(
						sod.getSpecmode()).append(",");
			}

			so.setIntegralsum(totalsum);
			so.setKeyscontent(keyscontent.toString());
			asl.addIntegralOrder(so);

			
			if (transportaddr != null && !transportaddr.equals("")) {
				AppCAddr ca = new AppCAddr();
				Integer caid = Integer.valueOf(MakeCode
						.getExcIDByRandomTableName("c_addr", 0, ""));
				ca.addCAddrNoExists(caid, cid, StringFilters
						.CommaToDot(transportaddr));
			}

			
			IntegralService ids = new IntegralService();
			ids.IntegralOrderDealIntegral(so);

			request.setAttribute("result", "databases.add.success");
			request.setAttribute("gourl",
					"../sales/toAddIntegralOrderAction.do");
			DBUserLog.addUserLog(userid,6, "积分换购>>新增积分换购单,编号："+soid);

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
