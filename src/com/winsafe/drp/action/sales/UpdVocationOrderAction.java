package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.VocationOrder;
import com.winsafe.drp.dao.VocationOrderDetail;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdVocationOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();
		AppVocationOrder aso = new AppVocationOrder();

		try {

			String cid = request.getParameter("cid");
			String id = request.getParameter("id");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			VocationOrder so = aso.getVocationOrderByID(id);	
			
			so.setCustomerbillid(request.getParameter("customerbillid"));
			so.setCid(cid);
			so.setCname(request.getParameter("cname"));
			so.setCmobile(request.getParameter("cmobile"));
			so.setDecideman(request.getParameter("decideman"));
			so.setDecidemantel(request.getParameter("decidemantel"));			
			so.setReceiveman(request.getParameter("receiveman"));
			so.setReceivemobile(request.getParameter("receivemobile"));
			so.setReceivetel(request.getParameter("receivetel"));
//			so.setSaledept(users.getMakedeptid());
//			so.setSaleid(userid);
			so.setPaymentmode(Integer.parseInt(request
					.getParameter("paymentmode")));
			String consignmentdate = request.getParameter("consignmentdate");
		    String consignmenttime = request.getParameter("consignmenttime");
			so.setConsignmentdate(DateUtil.StringToDatetime(consignmentdate+" "+consignmenttime));
			so.setTransportmode(Integer.valueOf(request
					.getParameter("transportmode")));
			//so.setTransit(Integer.valueOf(request.getParameter("transit")));
			so.setTransportaddr(request.getParameter("transportaddr"));
			so.setSource(Integer.valueOf(request.getParameter("source")));
			so.setInvmsg(Integer.valueOf(request.getParameter("invmsg")));
			so.setTickettitle(request.getParameter("tickettitle"));
			so.setEquiporganid(request.getParameter("equiporganid"));
			
			so.setRemark(request.getParameter("remark"));
//			so.setUpdateid(userid);
			so.setLastupdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));

			double totalsum =0.00;
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strwarehouseid[] = request.getParameterValues("warehouseid");
			String strunitid[] = request.getParameterValues("unitid");
			String strorgunitprice[] = request.getParameterValues("orgunitprice");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strtaxunitprice[] = request.getParameterValues("taxunitprice");
			String strquantity[] = request.getParameterValues("quantity");
			String strkickback[] = request.getParameterValues("kickback");
			String strdiscount[] = request.getParameterValues("discount");
			String strtaxrate[] = request.getParameterValues("taxrate");
			//String strsubsum[] = request.getParameterValues("subsum");
			String strbatch[] = request.getParameterValues("batch");
		    String strcost[] = request.getParameterValues("cost");
			String productid;
			String productname, specmode, batch;
			Long unitid, warehouseid;
			Double quantity;
			Double orgunitprice,unitprice,taxunitprice, subsum, discount, taxrate, kickback,cost;
			
			 StringBuffer keyscontent = new StringBuffer();
		      keyscontent.append(so.getId()).append(",").append(so.getCname()).append(",")
		      .append(so.getCmobile()).append(",").append(so.getDecideman()).append(",")
		      .append(so.getDecidemantel()).append(",").append(so.getReceiveman()).append(",")
		      .append(so.getReceivemobile()).append(",");

			
			
			AppVocationOrderDetail asld = new AppVocationOrderDetail();
			asld.delVocationOrderBySOID(id);
			
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Long.valueOf(strunitid[i]);
				warehouseid = 0l;
				if ( !"".equals(strwarehouseid[i]) ){
					warehouseid = Long.valueOf(strwarehouseid[i]);
				}
		        //batch = "";
				orgunitprice = DataValidate.IsDouble(strorgunitprice[i])?Double.valueOf(strorgunitprice[i]):0;
		        unitprice = DataValidate.IsDouble(strunitprice[i])?Double.valueOf(strunitprice[i]):0;
		        taxunitprice = DataValidate.IsDouble(strtaxunitprice[i])?Double.valueOf(strtaxunitprice[i]):0;
		        quantity = DataValidate.IsDouble(strquantity[i])?Double.valueOf(strquantity[i]):0;   
		        discount = DataValidate.IsDouble(strdiscount[i])?Double.valueOf(strdiscount[i]):0;   
		        taxrate = DataValidate.IsDouble(strtaxrate[i])?Double.valueOf(strtaxrate[i]):0;   
		        //kickback = DataValidate.IsDouble(strkickback[i])?Double.valueOf(strkickback[i]):0;  
//				if (dv.IsDouble(strkickback[i])) {
//					kickback = Double.valueOf(strkickback[i]);
//				} else {
//					kickback = Double.valueOf(0);
//				}
				if (DataValidate.IsDouble(strcost[i])) {
		            cost = Double.valueOf(strcost[i]);
		          }
		          else {
		        	  cost = Double.valueOf(0);
		          }
				subsum = DataFormat.countPrice(quantity, taxunitprice, discount, taxrate);// + kickback;
				totalsum += subsum;

				VocationOrderDetail sod = new VocationOrderDetail();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"vocation_order_detail", 0, "")));
				sod.setSoid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
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
				keyscontent.append(sod.getProductid()).append(",")
		        .append(sod.getProductname()).append(",")
		        .append(sod.getSpecmode()).append(",");

			}
			so.setTotalsum(totalsum);
			so.setKeyscontent(keyscontent.toString());
			aso.updVocationOrder(so);

			request.setAttribute("result", "databases.upd.success");

//			DBUserLog.addUserLog(userid, "修改行业销售单");
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
