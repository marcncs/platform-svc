package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdPeddleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();
		AppPeddleOrder aso = new AppPeddleOrder();
		
		try {

			String cid = request.getParameter("cid");
			String id = request.getParameter("id");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", "databases.upd.success");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			PeddleOrder so = aso.getPeddleOrderByID(id);	
			
//			so.setCustomerbillid(request.getParameter("customerbillid"));
			so.setCid(cid);
			so.setCname(request.getParameter("cname"));
			so.setCmobile(request.getParameter("cmobile"));
			so.setReceiveman(request.getParameter("decideman"));
			so.setReceivetel(request.getParameter("decidemantel"));
//			so.setPeddledept(Long.valueOf(request.getParameter("Peddledept")));
//			so.setPeddleid(Long.valueOf(request.getParameter("Peddleid")));

			so.setRemark(request.getParameter("remark"));
			//so.setTotalsum(Double.valueOf(request.getParameter("totalsum")));
//			so.setUpdateid(userid);
			so.setLastupdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));

			double totalsum =0.00;
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strwarehouseid[] = request.getParameterValues("warehouseid");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
		 
			String strdiscount[] = request.getParameterValues("discount");
			String strtaxrate[] = request.getParameterValues("taxrate");
			//String strsubsum[] = request.getParameterValues("subsum");
			String strbatch[] = request.getParameterValues("batch");
		    String strcost[] = request.getParameterValues("cost");
			String productid;
			String productname, specmode, batch;
			Long unitid, warehouseid;
			Double quantity;
			Double unitprice, subsum, discount, taxrate, kickback,cost;
			 StringBuffer keyscontent = new StringBuffer();
		      keyscontent.append(so.getId()).append(",").append(so.getCname()).append(",")
		      .append(so.getCmobile()).append(",").append(so.getReceiveman()).append(",")
		      .append(so.getReceivetel()).append(",");

			
			
			AppPeddleOrderDetail asld = new AppPeddleOrderDetail();
			asld.delPeddleOrderBySOID(id);
			
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Long.valueOf(strunitid[i]);
				warehouseid = 0l;
				if ( !"".equals(strwarehouseid[i]) ){
					warehouseid = Long.valueOf(strwarehouseid[i]);
				}
		        batch = "";
		        unitprice = DataValidate.IsDouble(strunitprice[i])?Double.valueOf(strunitprice[i]):0;
		        quantity = DataValidate.IsDouble(strquantity[i])?Double.valueOf(strquantity[i]):0;   
		        discount = DataValidate.IsDouble(strdiscount[i])?Double.valueOf(strdiscount[i]):0;   
		        taxrate = DataValidate.IsDouble(strtaxrate[i])?Double.valueOf(strtaxrate[i]):0;   

				if (DataValidate.IsDouble(strcost[i])) {
		            cost = Double.valueOf(strcost[i]);
		          }
		          else {
		        	  cost = Double.valueOf(0);
		          }
				subsum = DataFormat.countPrice(quantity, unitprice, discount, taxrate) ;
				totalsum += subsum;

				PeddleOrderDetail sod = new PeddleOrderDetail();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"Peddle_order_detail", 0, "")));
				sod.setPoid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
				sod.setWarehouseid(warehouseid);
				sod.setUnitid(unitid);
				sod.setUnitprice(unitprice);
				sod.setQuantity(quantity);
				sod.setDiscount(discount);
				sod.setTaxrate(taxrate);
				sod.setBatch(batch);
			    sod.setCost(cost);
				sod.setSubsum(subsum);
				asld.addPeddleOrderDetail(sod);
				keyscontent.append(sod.getProductid()).append(",")
		        .append(sod.getProductname()).append(",")
		        .append(sod.getSpecmode()).append(",");

			}
			so.setTotalsum(totalsum);
			so.setKeyscontent(keyscontent.toString());
			aso.updPeddleOrder(so);

			
			request.setAttribute("result", "databases.upd.success");

//			DBUserLog.addUserLog(userid, "修改销售订单");
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return new ActionForward(mapping.getInput());
	}
}
